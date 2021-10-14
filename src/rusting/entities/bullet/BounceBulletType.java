package rusting.entities.bullet;

import arc.Core;
import arc.struct.Seq;
import mindustry.content.Fx;
import mindustry.entities.Effect;
import mindustry.entities.Units;
import mindustry.gen.*;
import mindustry.graphics.Trail;

public class BounceBulletType extends ConsBulletType {
    //how much of it's velocity is kept on bounce
    public double bounciness = 1;
    //Cap for how many times it can bounce. Set to -1 to disable, 0 or null to stop bouncing.
    public int bounceCap = -1;
    public boolean bounceUnits, bounceBuildings;
    //Effect displayed on bounce
    public Effect bounceEffect = Fx.casing1;
    //trail length for the bullet
    public int trailLength = 5;
    //How thick trail is
    public float trailWidth = 1;

    public BounceBulletType(float speed, float damage, String sprite) {
        super(speed, damage, sprite);
        this.sprite = sprite;
        this.speed = speed;
        this.damage = damage;
        this.pierceBuilding = this.pierce = this.bounceBuildings = this.bounceUnits = true;
        this.trailWidth = width/5;
        this.shrinkX = 0.8f;
    }

    public void init(Bullet b) {
        super.init(b);
        b.data = new Seq<Trail>();
        ((Seq<Trail>)b.data).add(new Trail(trailLength));
    }

    @Override
    public void update(Bullet b){
        super.update(b);
        if(Core.settings.getBool("settings.er.drawtrails")) ((Seq<Trail>)b.data).each(t -> t.update(b.x, b.y));
    }

    @Override
    public void draw(Bullet b){
        if(Core.settings.getBool("settings.er.drawtrails")) ((Seq<Trail>)b.data).each(t -> t.draw(trailColor, trailWidth * b.fout()));
        super.draw(b);
    }

    @Override
    public void hit(Bullet b, float x, float y) {
        super.hit(b, x, y);
        Teamc teamc = Units.closestEnemy(b.team, x, y, hitSize * 2 + 4, e -> b.collides(e));
        if(!(teamc instanceof Unit)) return;
        Unit unit = null;
        float difX = Math.abs(b.vel.x - x), difY = Math.abs(b.vel.y - y);
        unit = (Unit)teamc;
        if ((bounceCap == -1 || b.collided.size <= bounceCap ) && bounceUnits){
            float rotation = b.vel.angle();
            b.vel.setAngle(360 + rotation - (rotation - b.angleTo(unit)) * 2);
            b.vel.rotate(180);
            b.vel.add(unit.vel);
            bounceEffect.at(x, y, b.angleTo(x + b.vel.x, y + b.vel.y));
        }
    }


    @Override
    public void hitTile(Bullet b, Building build, float initialHealth, boolean direct) {
        super.hitTile(b, build, initialHealth, direct);
        float x = b.x, y = b.y;
        float difX = Math.abs(b.vel.x - x), difY = Math.abs(b.vel.y - y);
        if(build != null){
            difX = Math.abs(build.x - x);
            difY = Math.abs(build.y - y);
        }
        boolean flipX = false;
        boolean flipY = true;
        if(difX > difY) {
            flipX = true;
            flipY = false;
        }
        if ((bounceCap == -1 || b.collided.size <= bounceCap) && bounceBuildings) {
            if (flipX) {
                b.vel.x *= -1 * bounciness;
                //check if bullet is inside block
                if(difX < build.block.size * 4){
                    //translate to edge of block that bullet is closest to + an ofset
                    b.x += b.x - build.x > 0 ? build.block.size * 4 - difX + b.hitSize + 1: (build.block.size * 4 - difX + b.hitSize + 1) * -1;
                }
            }
            if (flipY) {
                b.vel.y *= -1 * bounciness;
                if(difY < build.block.size * 4){
                    b.y += b.y - build.y > 0 ? build.block.size * 4 - difY + b.hitSize + 1 : (build.block.size * 4 - difY + b.hitSize + 1) * -1;
                }
            }

            bounceEffect.at(x, y, b.angleTo(build));
        }
    }
}
