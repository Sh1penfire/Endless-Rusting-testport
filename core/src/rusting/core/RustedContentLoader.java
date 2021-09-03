package rusting.core;

import arc.func.Cons;
import arc.struct.Seq;
import mindustry.ctype.ContentList;
import rusting.content.*;
import rusting.ctype.*;
import rusting.type.Capsule;

//class to store special content, not found in Vars, and ER's content
public class RustedContentLoader {

    private final Seq<ContentList> contentLists = Seq.with(
            new RustingResearchTypes(),
            new RustingStatusEffects(),
            new RustingLiquids(),
            new RustingItems(),
            new RustingBullets(),
            new RustingUnits(),
            new RustingBlocks(),
            new RustingWeathers(),
            new RustingPlanets(),
            new RustingCapsules(),
            new RustingAchievements(),
            new RustingSectorPresets(),
            new RustingTechTree()
    );

    public Seq<ERContentType> ContentTypes = Seq.with(
            new ERContentType("unused"),
            new ERContentType("capsule"),
            new ERContentType("researchType"),
            new ERContentType("unlockableAchievement"),
            new ERContentType("logicFormat")
    );

    private Seq<MappableERContent>[] contentMap;

    public ERContentType getContentType(String name){
        return ContentTypes.find(c -> c.name == name);
    }

    public MappableERContent getByName(ERContentType content, String key){
        return null;
    }

    public <T extends ERContent> Seq<T> getBy(ERContentType type){
        return (Seq<T>)contentMap[type.ordinal];
    }

    public void load(){
        each(c -> {
            if (c instanceof UnlockableERContent) {
                UnlockableERContent content = (UnlockableERContent) c;
                content.load();
                content.loadIcon();
            }
        });
    }

    public void createContent(){
        contentMap = new Seq[ContentTypes.size];
        for (int i = 0; i < contentMap.length; i++) {
            contentMap[i] = new Seq();
        }
        contentLists.each(ContentList::load);
    }

    public void init(){
        each(c -> {
            if (c instanceof UnlockableERContent) {
                UnlockableERContent content = (UnlockableERContent) c;
                content.init();
            }
        });
    }

    public void each(Cons c){
        for (int i = 0; i < contentMap.length; i++) {
            contentMap[i].each(content -> {
                c.get(content);
            });
        }
    }

    public Seq<Capsule> capsules(){
        return getBy(getContentType("capsule"));
    }

    public Seq<ResearchType> researchTypes(){
        return getBy(getContentType("researchType"));
    }

    public Seq<UnlockableAchievement> achievements(){
        return getBy(getContentType("unlockableAchievement"));
    }

    public void handleContent(MappableERContent content){
        if(contentMap[content.getContentType().ordinal] == null) contentMap[content.getContentType().ordinal] = new Seq();
        contentMap[content.getContentType().ordinal].add(content);
    }

}
