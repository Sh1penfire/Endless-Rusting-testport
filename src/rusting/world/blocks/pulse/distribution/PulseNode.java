package rusting.world.blocks.pulse.distribution;

import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Lines;
import arc.math.Mathf;
import arc.math.geom.Geometry;
import arc.math.geom.Point2;
import arc.struct.Seq;
import arc.util.*;
import arc.util.io.Reads;
import arc.util.io.Writes;
import mindustry.entities.units.BuildPlan;
import mindustry.game.Team;
import mindustry.gen.Building;
import mindustry.graphics.Drawf;
import mindustry.graphics.Pal;
import mindustry.world.Tile;
import mindustry.world.meta.Stat;
import rusting.content.Palr;
import rusting.interfaces.PulseBlockc;
import rusting.interfaces.ResearchableBlock;
import rusting.world.blocks.pulse.PulseBlock;

import static mindustry.Vars.tilesize;
import static mindustry.Vars.world;

//a block which can connect to other pulse blocks and transmit a pulse
public class PulseNode extends PulseBlock implements ResearchableBlock {
    //Reload of the node till it can transmit a pulse to a nearby block
    public float pulseReloadTime = 60;
    //How many bursts the node sends
    public float pulseBursts = 1;
    //Spacing between bursts
    public float pulseBurstSpacing = 0;
    //How much energy is transmitted
    public float energyTransmission = 3;
    //How many nodes you can connect to
    public int connectionsPotential = 1;
    //Range of the node
    public double laserRange = 15;
    //Colour of the laser
    public Color laserColor = chargeColourStart;

    //used as a placeholder to avoid unnecessary variable creation
    protected static BuildPlan otherReq;

    public PulseNode(String name) {
        super(name);
        schematicPriority = -10;
        configurable = true;
        hasPower = false;
        consumesPower = false;
        outputsPower = false;
        canOverdrive = false;
        swapDiagonalPlacement = true;
        drawDisabled = false;


        config(Integer.class, (PulseNodeBuild entity, Integer i) -> {
            Building other = world.build(i);
            if(!(other instanceof PulseBlockc)) return;
            if(entity.connections.contains(i)){
                //unlink
                entity.connections.remove(i);
            }
            else if(nodeCanConnect(entity, other)){
                entity.connections.add(i);
            }
            else entity.previousConnections.add(i);
        });

        config(Point2[].class, (PulseNodeBuild entity, Point2[] points) -> {
            String outputString = "";

            for (Point2 point: points){
                entity.configure(Point2.pack(point.x + entity.tile.x, point.y + entity.tile.y));
            }
        });
    }

    @Override
    public void setStats(){
        super.setStats();
        this.stats.add(Stat.range, (float) laserRange);
        this.stats.add(Stat.reload, pulseReloadTime /60);
    }

    @Override
    public void setPulseStats() {
        super.setPulseStats();
        pStats.connections.setValue(connectionsPotential);
        pStats.pulseReloadTime.setValue(60/pulseReloadTime);
        pStats.energyTransmission.setValue(energyTransmission);
        pStats.pulseBursts.setValue(pulseBursts);
        pStats.pulseBurstSpacing.setValue(pulseBurstSpacing);
    }

    @Override
    public void drawPlace(int x, int y, int rotation, boolean valid){
        super.drawPlace(x, y, rotation, valid);
        Tile tile = world.tile(x, y);

        if(tile != null && laserRange > 0 && connectionsPotential > 0) {
            Lines.stroke(1f);
            Draw.color(Pal.placing);
            Drawf.circles(x * tilesize + offset, y * tilesize + offset, (float) (laserRange * tilesize));
        }
    }

    @Override
    public void drawRequestConfigTop(BuildPlan req, Eachable<BuildPlan> list){
        if(req.config instanceof Point2[]){
            Point2[] ps = (Point2[]) req.config;
            for(Point2 point : ps){
                int px = req.x + point.x, py = req.y + point.y;
                otherReq = null;
                list.each(other -> {
                    if(other.x == px && other.y == py && other.block instanceof PulseBlock) otherReq = other;
                });

                if(!(otherReq == null || otherReq.block == null) && req.block instanceof PulseNode) drawLaser(req.drawx(), req.drawy(), otherReq.drawx(), otherReq.drawy(), laserOffset, otherReq.block instanceof PulseBlock ? ((PulseBlock) otherReq.block).laserOffset : otherReq.block.size - 5, 0.5f, chargeColourStart, chargeColourEnd);

            }
            Draw.color();
        }
    }

    public static boolean nodeCanConnect(PulseNodeBuild build, Building target){
        return (!(target instanceof PulseBlockc) || ((PulseBlockc) target).connectableTo()) && !build.connections.contains(target.pos()) && build.connections.size < ((PulseNode) (build.block)).connectionsPotential && ((PulseNode) build.block).laserRange * 8 >= Mathf.dst(build.x, build.y, target.x, target.y);
    }

    protected void getPotentialLinks(PulseNode.PulseNodeBuild build, Team team, Seq<Building> others){

        tempTileEnts.clear();

        Geometry.circle((int)build.x, (int)build.y, (int)(laserRange + 2), (x, y) -> {
            Building other = world.build(x, y);
            if(nodeCanConnect(build, other)){
                tempTileEnts.add(other);
            }
        });

        tempTileEnts.sort((a, b) -> {
            int type = -Boolean.compare(a.block instanceof PulseNode, b.block instanceof PulseNode);
            if(type != 0) return type;
            return Float.compare(a.dst2(build), b.dst2(build));
        });
    }


    public class PulseNodeBuild extends PulseBlockBuild{
        public Seq<Integer> connections = new Seq();
        public Seq<Integer> previousConnections = new Seq();
        public float reload = 0;

        public void dropped(){
            connections.clear();
        }

        @Override
        public void placed() {
            super.placed();
        }

        @Override
        public boolean onConfigureTileTapped(Building other){

            if(this == other){
                deselect();
                return false;
            }

            if(nodeCanConnect(this, other) || connections.contains(other.pos())){
                configure(other.pos());

                return false;
            }


            return super.onConfigureTileTapped(other);
        }

        @Override
        public void updateTile() {
            super.updateTile();

            connections.each(l -> {
                Building j = world.build(l);
                if(j == null || j.isNull() || !j.isAdded()) {
                    previousConnections.add(l);
                    connections.remove(l);
                }
            });

            previousConnections.each(l -> {
                Building j = world.build(l);
                if(j != null && j.isAdded() && nodeCanConnect(this, j)) {
                    connections.add(l);
                    previousConnections.remove(l);
                }
            });

            if(connections.size >= connectionsPotential) previousConnections.clear();

            if(reload >= pulseReloadTime && chargef(true) >= minRequiredPulsePercent) {
                interactConnected();
                reload = 0;
            }
            else reload += pulseEfficiency() * Time.delta;
        }

        public void interactConnected(){
            addPulseConnected();
        }

        public void addPulseConnected(){
            final int[] index = {0};
            connections.each(l -> {
                Building j = world.build(l);
                //need to double check, jic, because I've experienced crashes while a generator was pumping out energy
                if(chargef() <= 0 || j == null) return;
                if(index[0] > connectionsPotential) connections.remove(l);
                float energyTransmitted = Math.min(pulseModule.pulse, energyTransmission);
                if(((PulseBlockc)j).receivePulse(energyTransmitted, this)) removePulse(energyTransmitted);
                index[0]++;
            });
        }

        @Override
        public void drawConfigure(){

            float scl = Mathf.absin(Time.time, 4f, 1f);

            Drawf.circles(x, y, tile.block().size * tilesize / 2f + 1f + scl);
            Drawf.circles(x, y, (float) (laserRange * tilesize));


            previousConnections.each(l -> {
                Tmp.v1.set(Point2.x(l) * 8, Point2.y(l) * 8);
                Drawf.dashCircle(Tmp.v1.x, Tmp.v1.y, 2f + 1f + scl, Tmp.c1.set(Palr.dustriken).a(scl/8 + 0.5f));
            });

            connections.each(l -> {
                Building link = world.build(l);
                //prevent crashes that can happen when config is being drawn and a block is destroyed which the node is linekd up to
                if(link == null) return;
                Drawf.square(link.x, link.y, link.block.size * tilesize / 2f + 1f + scl, Pal.place);
            });

            Draw.reset();
        }

        @Override
        public void draw() {
            super.draw();
            connections.each(l -> {
                Building other = world.build(l);
                if(other == null || other.isNull() || !other.isAdded()) return;
                drawLaser((PulseBlockc) other, chargef(), laserColor, chargeColourEnd);
            });
        }

        @Override
        public Point2[] config(){
            Point2[] out = new Point2[connections.size];
            for(int i = 0; i < connections.size; i++){
                out[i] = Point2.unpack(connections.get(i)).sub(tile.x, tile.y);
            }
            return out;
        }

        @Override
        public byte version() {
            return 1;
        }

        @Override
        public void write(Writes w){
            super.write(w);
            w.f(reload);
            w.s(connections.size);
            for(int i = 0;  i < connections.size; i++){
                w.d(connections.get(i));
            }
            w.s(previousConnections.size);
            for (int i = 0; i < previousConnections.size; i++) {
                w.d(previousConnections.get(i));
            }
        }

        @Override
        public void read(Reads r, byte revision){
            super.read(r, revision);
            reload = r.f();
            int n = r.s();
            connections = new Seq<Integer>();
            connections.clear();
            int rpos;
            for(int i = 0;  i < n; i++){
                rpos = ((int) r.d());
                connections.add(rpos);
            }
            if(revision >= 1){
                previousConnections = new Seq<Integer>();
                previousConnections.clear();
                n = r.s();
                for(int i = 0;  i < n; i++){
                    rpos = ((int) r.d());
                    previousConnections.add(rpos);
                }
            }
        }
    }
}