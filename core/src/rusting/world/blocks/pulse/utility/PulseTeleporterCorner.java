package rusting.world.blocks.pulse.utility;

import arc.Core;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.TextureRegion;
import arc.util.Eachable;
import mindustry.entities.units.BuildPlan;
import mindustry.graphics.Layer;

//acts as simply a filler for the teleporter corners
public class PulseTeleporterCorner extends PulseTeleporterPart {

    public TextureRegion topRegion, fullRegion, mirrorRegion;

    public PulseTeleporterCorner(String name) {
        super(name);
        rotate = true;
        schematicPriority = 10;
    }

    @Override
    public void load() {
        super.load();
        region = Core.atlas.find(name + "-base", region);
        topRegion = Core.atlas.find(name + "-top", Core.atlas.find("empty"));
        fullRegion = Core.atlas.find(name + "-full", region);
        mirrorRegion = Core.atlas.find(name + "-mirror", Core.atlas.find("empty"));
    }

    @Override
    public void drawRequestRegion(BuildPlan req, Eachable<BuildPlan> list){
        Draw.rect(region, req.drawx(), req.drawy());
        Draw.rect(mirrorRegion, req.drawx(), req.drawy(), req.rotation * 90);
        Draw.z(Layer.blockOver + 0.1f);

        Draw.rect(topRegion, req.drawx(), req.drawy());
    }

    public class PulseTeleporterCornerBuild extends PulseBlockBuild{
        @Override
        public void draw() {
            Draw.rect(region, x, y);
            Draw.rect(mirrorRegion, x, y, rotation * 90);
            Draw.z(Layer.blockOver + 0.1f);

            Draw.rect(topRegion, x, y);
        }
    }
}
