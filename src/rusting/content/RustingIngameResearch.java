package rusting.content;

import mindustry.ctype.ContentList;
import rusting.EndlessRusting;
import rusting.ctype.ResearchType;

public class RustingIngameResearch implements ContentList {

    public ResearchType
    pulse
    ;

    @Override
    public void load() {

        pulse = new ResearchType(EndlessRusting.modname + "pulse"){{

        }};

    }
}
