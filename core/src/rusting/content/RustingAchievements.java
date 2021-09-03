package rusting.content;

import mindustry.ctype.ContentList;
import rusting.ctype.SectorBasedAchievement;
import rusting.ctype.UnlockableAchievement;

import static rusting.EndlessRusting.modname;

public class RustingAchievements implements ContentList {

    public static UnlockableAchievement
    //planet 1
    shardlingSteps, theBoatmansCursedBoatman, powerfulLight, pulseTeleporterConstructed, planet1Clear,
    //msc
    youMonster, giganticQuestionMark, GTFO;

    @Override
    public void load() {
        shardlingSteps = new SectorBasedAchievement( modname + "-shardling-steps", 36, RustingPlanets.err) {{

        }};

        theBoatmansCursedBoatman = new UnlockableAchievement(modname + "-the-boatmans-cursed-boatman") {{

        }};

        powerfulLight = new UnlockableAchievement(modname + "-powerful-light"){{

        }};

        pulseTeleporterConstructed = new UnlockableAchievement(modname + "-pulse-teleporter-constructed") {{

        }};

        planet1Clear = new UnlockableAchievement(modname + "-planet-1-clear") {{

        }};

        youMonster = new UnlockableAchievement(modname + "-you-monster"){{

        }};

        GTFO = new UnlockableAchievement(modname + "-GTFO"){{

        }};
    }
}
