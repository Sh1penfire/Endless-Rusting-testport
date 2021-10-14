package rusting.interfaces;

import arc.Core;
import arc.graphics.g2d.TextureRegion;
import arc.struct.Seq;
import mindustry.Vars;
import mindustry.ctype.UnlockableContent;
import mindustry.game.Team;
import mindustry.type.ItemStack;
import rusting.Varsr;
import rusting.ctype.ResearchType;
import rusting.world.modules.ResearchModule;

public interface ResearchableObject {

    default Seq<ResearchType> researchTypes(){
        return null;
    }

    default TextureRegion researchUIcon(){
        return Core.atlas.find("");
    }

    default void centerResearchRequirements(ItemStack[] stack){
        centerResearchRequirements(true, true, stack);
    }

    default void centerResearchRequirements(boolean requiresResearching, ItemStack[] stack){
        centerResearchRequirements(true, requiresResearching, stack);
    }

    //backwards compatibility with my own code.
    // I can't believe I've come to this and I haven't even hit rock bottom yet.
    // When do I get to keep my stats?
    default ResearchModule getResearchModule(){
        return null;
    }

    default void centerResearchRequirements(boolean reset, boolean requiresResearching, ItemStack[] stack){
        if(getResearchModule() == null) return;
        getResearchModule().needsResearching = requiresResearching;
        getResearchModule().centerResearchRequirements = stack;
        String[] string = {""};
        if(this instanceof UnlockableContent){
            string[0] += ((UnlockableContent) this).localizedName + "'s stack contains: ";
        }
        Varsr.logStack(string[0], stack);
    }

    default boolean researched(Team team){
        return getResearchModule().needsResearching && Vars.state.rules.infiniteResources || Varsr.research.getTeamModule(team, this).researched;
    }

    default void hideFromUI(){
        getResearchModule().isHidden = true;
    }
}
