package rusting.world.blocks.capsules;

import arc.graphics.g2d.Draw;
import mindustry.world.blocks.defense.Wall;
import rusting.Varsr;
import rusting.type.Capsule;

public class CapsuleCenter extends Wall {
    public CapsuleCenter(String name) {
        super(name);
        configurable = true;
    }

    public class CapsuleCenterBuild extends WallBuild{
        public Capsule cap = (Capsule) Varsr.content.capsules().get(0);
        @Override
        public void draw() {
            Draw.rect(cap.fullIcon, this.x, this.y, 0);
        }
    }

}
