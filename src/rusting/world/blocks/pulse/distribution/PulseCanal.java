package rusting.world.blocks.pulse.distribution;

import arc.Core;
import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.TextureRegion;
import arc.math.Mathf;
import arc.struct.Queue;
import arc.struct.Seq;
import arc.util.*;
import mindustry.entities.units.BuildPlan;
import mindustry.gen.Building;
import mindustry.graphics.Layer;
import mindustry.world.Tile;
import rusting.Varsr;
import rusting.content.Palr;
import rusting.interfaces.*;
import rusting.interfaces.PulseCanalInput;
import rusting.world.blocks.pulse.PulseBlock;
import rusting.world.blocks.pulse.utility.PulseTeleporterController.PulseTeleporterControllerBuild;

import static mindustry.Vars.world;

public class PulseCanal extends PulseBlock {
    public TextureRegion topRegion, fullRegion;

    protected final static Queue<PulseCanalBuild> canalQueue = new Queue<>();

    public PulseCanal(String name) {
        super(name);
        rotate = true;
        schematicPriority = 10;
        resistance = 0;
        connectable = false;
    }

    @Override
    public void load() {
        super.load();
        region = Core.atlas.find(name + "-base", region);
        topRegion = Core.atlas.find(name + "-top", Core.atlas.find("empty"));
        fullRegion = Core.atlas.find(name + "-full", region);
    }

    @Override
    public void drawRequestRegion(BuildPlan req, Eachable<BuildPlan> list){
        Draw.rect(region, req.drawx(), req.drawy());
        Draw.z(Layer.blockOver + 0.1f);

        Draw.rect(topRegion, req.drawx(), req.drawy(), req.rotation * 90);
    }

    public static PulseCanalBuild asCanal(Building build){
        return (PulseCanalBuild) build;
    }

    private PulseTeleporterControllerBuild build;
    private static Building tmpBuild;
    private static Tile tmpTile = null;
    private static PulseCornerpiece corner = null;

    //find a chain of canal's ending when the cannal is placed
    //if it can't find a canal end within a certain distance, return starting point.

    public static Tile findCanalEnding(Tile tile, float rotation, float maxDst){
        Tmp.v1.set(tile.x * 8, tile.y * 8);
        for (int i = 0; i < maxDst; i++) {
            Tmp.v1.trns(rotation, i * 8);
            tmpTile = world.tileWorld(tile.x * 8 + Tmp.v1.x, tile.y * 8 + Tmp.v1.y);
            tmpBuild = tmpTile.build;
            if(!(tmpBuild instanceof rusting.interfaces.PulseCanal)) break;
        }
        if(tmpBuild instanceof PulseCornerpiece){
            corner = (PulseCornerpiece) tmpBuild;
            tmpTile = findCanalEnding(tmpTile, corner.reflectedRotation(tmpBuild.rotation * 90 - 90, rotation), maxDst);
        }
        return tmpTile;
    }

    public class PulseCanalBuild extends PulseBlockBuild implements rusting.interfaces.PulseCanal {

        public Seq<PulseInstantTransportation> connected = new Seq<PulseInstantTransportation>();

        public Tile canalEnding = tile;
        //if it's the starting canal
        public boolean starting = false;
        //how long pulse bursts are actively displayed for
        public float pulseBurstTime = 0;
        //variable used to cycle through the colours. resets at 100
        public float cycle = 0;

        @Override
        public Seq<PulseInstantTransportation> connected() {
            return connected;
        }

        @Override
        public void onProximityAdded(){
            super.onProximityAdded();
            updateChained();
        }

        @Override
        public void onProximityRemoved(){
            super.onProximityRemoved();

            for(Building b : proximity){
                if(b instanceof rusting.interfaces.PulseCanal){
                    asCanal(b).updateChained();
                }
            }
        }

        public void updateChained() {
            connected.clear();
            canalQueue.clear();
            canalQueue.add(this);

            Varsr.updateConnectedCanals(this, 10);

        }

        @Override
        public void addPulse(float pulse, Building building) {
            if(canalEnding != tile && canalEnding.build instanceof PulseBlockBuild) {
                ((PulseBlockBuild) canalEnding.build).addPulse(pulse);
                connected.each(e -> {
                    ((PulseCanalBuild) e).pulseBurstTime = 60;
                });
            }
        }

        @Override
        public boolean canRecievePulse(float pulse, Building build) {
            return (build instanceof PulseCanalInput || build == this) && canRecievePulse(pulse);
        }

        //Vars.world.buildWorld(Core.input.mouseWorld().x, Core.input.mouseWorld().y).setupEnding()

        @Override
        public boolean canRecievePulse(float amount) {
            return canalEnding != tile &&
                    canalEnding.build instanceof PulseBlockBuild &&
                    ((PulseBlockBuild) canalEnding.build).canRecievePulse(amount, this);
        }

        public float trueRotation(){
            return rotation * 90 - 90;
        }
        public Tile getCanalEnding() {
            if(canalEnding == null) setupEnding();
            return canalEnding;
        }

        public Tile setupEnding(){
            return canalEnding = findCanalEnd(tile, 10);
        }

        @Override
        public Tile findCanalEnd(Tile tile, float maxDst){
            Tmp.v1.trns(trueRotation() + 90, 8);
            tmpTile = world.tileWorld(x + Tmp.v1.x, y + Tmp.v1.y);
            tmpTile = findCanalEnding(tile, trueRotation() + 90, maxDst);
            return tmpTile;
        }

        @Override
        public void placed() {
            super.placed();
            setupEnding();
        }

        @Override
        public void updateTile() {
            super.updateTile();
            if(pulseBurstTime > 0) {
                pulseBurstTime -= Time.delta;
                cycle += Time.delta/50;
                cycle %= 2;
            }
            else Mathf.lerpDelta(cycle, 0, 0.1f);
            Math.max(pulseBurstTime, 0);

        }

        @Override
        public void draw() {
            Draw.rect(region, x, y);
            Draw.z(Layer.blockOver + 0.1f);

            Draw.color(Palr.pulseBullet, Color.sky, Color.white, Math.abs(1 - cycle));
            Draw.rect(topRegion, x, y, rotation * 90);
        }
    }
}
