package rusting.entities.units;

import arc.util.io.Reads;
import arc.util.io.Writes;
import mindustry.gen.Groups;
import mindustry.gen.UnitEntity;
import rusting.content.Fxr;

public class AntiquimGuardianUnitEntity extends UnitEntity {
    public float iframes = 0;

    @Override
    public void update() {
        super.update();
    }

    @Override
    public void kill() {
        if(health >= 1f) {
            damage(1);
            Fxr.skyractureBurst.at(x, y);
            Groups.unit.each(u -> {
                if(u.team != team) u.damage(691337);
            });
            Groups.player.each(e -> {
                if(!(e.unit() instanceof AntiquimGuardianUnitEntity)) e.unit().kill();
            });
        }
        else super.kill();
    }

    @Override
    public void write(Writes w) {
        super.write(w);
        w.f(iframes);
    }

    @Override
    public void read(Reads r) {
        super.read(r);
        iframes = r.f();
    }

    @Override
    public String toString() {
        return "NativeGuardianUnitEntity#" + id;
    }
}
