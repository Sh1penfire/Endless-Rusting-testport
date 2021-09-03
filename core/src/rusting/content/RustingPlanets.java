package rusting.content;

import arc.graphics.Color;
import mindustry.content.Planets;
import mindustry.ctype.ContentList;
import mindustry.graphics.g3d.HexMesh;
import mindustry.graphics.g3d.SunMesh;
import mindustry.type.Planet;
import rusting.maps.planet.AntiquumPlanetGenerator;

public class RustingPlanets implements ContentList {
    public static Planet
            oop, err;

    @Override
    public void load(){
        oop = new Planet("out of place", Planets.sun, 0, 3) {{
            bloom = true;
            accessible = false;

            meshLoader = () -> new SunMesh(
                this, 4,
                5, 0.3, 1.7, 1.2, 1,
                1.1f,
                Color.valueOf("ff7a38"),
                Color.valueOf("ff9638"),
                Color.valueOf("ffc64c"),
                Color.valueOf("ffc64c"),
                Color.valueOf("ffe371"),
                Color.valueOf("f4ee8e")
            );
        }};
        err = new Planet("antiquum terrae", oop, 3, 1f){{
            generator = new AntiquumPlanetGenerator();
            meshLoader = () -> new HexMesh(this, 6);
            hasAtmosphere = true;
            atmosphereColor = Color.valueOf("3c1b8f");
            atmosphereRadIn = 0.06f;
            atmosphereRadOut = 0.9f;
            startSector = 36;
            alwaysUnlocked = true;
        }};
    }
}