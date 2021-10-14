package rusting.world.blocks.pulse.distribution;

import arc.Core;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.TextureRegion;
import arc.math.Mathf;
import arc.struct.Queue;
import arc.util.*;
import mindustry.Vars;
import mindustry.entities.units.BuildPlan;
import mindustry.gen.Building;
import mindustry.graphics.Layer;
import mindustry.logic.LAccess;
import mindustry.world.meta.Stat;
import rusting.interfaces.PrimitiveControlBlock;
import rusting.interfaces.PulseInstantTransportation;
import rusting.world.blocks.pulse.PulseBlock;
import rusting.world.blocks.pulse.distribution.PulseCanal.PulseCanalBuild;

public class PulseCanalInput extends PulseBlock {

    //Reload of the node till it can transmit a pulse to a nearby block
    public float pulseReloadTime = 10;
    //How many bursts the node sends
    public float pulseBursts = 1;
    //Spacing between bursts
    public float pulseBurstSpacing = 0;
    //How much energy is transmitted
    public float energyTransmission = 3;

    private Building tmpBuilding;

    public TextureRegion topRegion, fullRegion;

    protected final static Queue<PulseCanalBuild> canalQueue = new Queue<>();

    public PulseCanalInput(String name) {
        super(name);
        rotate = true;
        resistance = 0;
    }

    @Override
    public void setStats(){
        super.setStats();
        this.stats.add(Stat.reload, pulseReloadTime /60);
    }

    @Override
    public void setPulseStats() {
        super.setPulseStats();
        pStats.pulseReloadTime.setValue(60/pulseReloadTime);
        pStats.energyTransmission.setValue(energyTransmission);
        pStats.pulseBursts.setValue(pulseBursts);
        pStats.pulseBurstSpacing.setValue(pulseBurstSpacing);
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

    public class PulseCanalInputBuild extends PulseBlockBuild implements rusting.interfaces.PulseCanalInput, PrimitiveControlBlock {
        public float reload = 0;

        @Override
        public void updateTile() {
            super.updateTile();
            if(reload >= pulseReloadTime){
                reload = 0;
                Tmp.v1.trns(rotation * 90, 8);
                tmpBuilding = Vars.world.buildWorld(x + Tmp.v1.x, y + Tmp.v1.y);
                if(tmpBuilding instanceof PulseInstantTransportation && tmpBuilding instanceof PulseCanalBuild && pulseModule.pulse >= energyTransmission){
                    if(((PulseCanalBuild) tmpBuilding).receivePulse(energyTransmission, this)) {
                        removePulse(energyTransmission);
                    }
                }
            }
            else reload += Time.delta * timeScale();
        }

        @Override
        public void draw() {
            Draw.rect(region, x, y);
            Draw.z(Layer.blockOver + 0.1f);

            Draw.rect(topRegion, x, y, rotation * 90);
        }

        @Override
        public void primitiveControl(LAccess type, double p1, double p2, double p3, double p4){

        }

        @Override
        public void primitiveControl(LAccess type, Object p1, double p2, double p3, double p4){

        }

        @Override
        public void rawControl(double p1, double p2, double p3, double p4){
            rotation = ((int) p2)/90 + 1;
            enabled = !Mathf.zero(p3);
        }

        @Override
        public void exportInformationDefault(Building build){
            if(build instanceof PrimitiveControlBlock) ((PrimitiveControlBlock) build).rawControl(1, rotation * 90 - 90, enabled ? 1 : 0, 1F);
        }
    }
}
