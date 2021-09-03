package rusting.game;

import arc.Core;
import mindustry.game.Objectives.Objective;
import rusting.Varsr;

public class RustedObjectives {
    public static class SettingLockedObjective implements Objective{

        public String key, text;

        @Override
        public boolean complete() {
            return Core.settings.getBool(key, false);
        }

        @Override
        public String display() {
            return Varsr.username + ", " + text;
        }

        public SettingLockedObjective(String key, String text){
            this.key = key;
            this.text = text;
        }
    }
}
