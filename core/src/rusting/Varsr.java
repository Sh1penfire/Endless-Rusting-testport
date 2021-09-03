package rusting;

import arc.Core;
import arc.Events;
import arc.assets.Loadable;
import arc.func.Cons;
import arc.math.Mathf;
import arc.struct.Queue;
import arc.struct.Seq;
import arc.util.*;
import mindustry.Vars;
import mindustry.content.Blocks;
import mindustry.content.StatusEffects;
import mindustry.core.GameState;
import mindustry.game.EventType;
import mindustry.game.EventType.Trigger;
import mindustry.gen.Building;
import mindustry.type.ItemStack;
import mindustry.world.Tile;
import mindustry.world.meta.BuildVisibility;
import rusting.content.RustingAchievements;
import rusting.content.RustingStatusEffects;
import rusting.core.RustedContentLoader;
import rusting.core.Rusting;
import rusting.core.holder.ItemScoreHolder;
import rusting.entities.abilities.SpeedupAbility;
import rusting.game.RustingEvents.AchievementUnlockEvent;
import rusting.interfaces.PulseCanalInput;
import rusting.interfaces.PulseInstantTransportation;
import rusting.ui.RustingUI;
import rusting.world.blocks.pulse.distribution.PulseCanal.PulseCanalBuild;
import rusting.world.format.holder.FormatHolder;
import rusting.world.research.RustingResearch;

import static mindustry.Vars.state;
import static rusting.world.blocks.pulse.distribution.PulseCanal.asCanal;

public class Varsr implements Loadable {

    protected final static Queue<PulseCanalBuild> canalQueue = new Queue<>();
    protected static Seq<Building> buildingSeq = new Seq<>(), buildingSeq2 = new Seq<>();
    protected static Tile tmpTile = null;

    public static Seq<String> defaultDatabaseQuotes, defaultRandomQuotes;


    public static RustingUI ui;
    public static FormatHolder formats;
    public static ItemScoreHolder itemScorer;
    public static Rusting rusted;
    public static RustedContentLoader content = new RustedContentLoader();
    public static RustingResearch research = new RustingResearch();
    public static String username;
    public static String defaultUsername;
    public static boolean debug = false;
    public static float lerpedPlayerElevation = 0;

    public static void setup(){

        itemScorer.setupItems();
        content.init();

        research.setupMap();

        if(username.equals(defaultUsername)) Varsr.ui.welcome.show();

        if(debug) debug();

    }

    public static void init(){

        defaultUsername = Core.bundle.get("settings.er.username.default", "the unnamed one");
        username = Core.settings.getString("settings.er.username", defaultUsername);
        if(username.equals("")) username = defaultUsername;
        debug = Core.settings.getBool("settings.er.debug", false);

        ui = new RustingUI();

        ui.init();

        formats = new FormatHolder();
        itemScorer = new ItemScoreHolder();
        rusted = null;

        defaultDatabaseQuotes = Seq.with(
            "[cyan] Places of learning",
            "[cyan] Storages of information",
            "[cyan] Database Entries",
            "[#d8e2e0] Welcome back, " + username + "."
        );

        //come and join our conversation, randomizer
        defaultRandomQuotes = Seq.with(
            "[cyan] E N L I G H T E N  U S",
            "[lightgrey]Go on, there is much to teach, being of outside",
            "[blue] T H E  P U L S E  C O N S U M E S  A L L",
            "[black] N O T H I N G  V O I D  O F  L I G H T",
            "[purple] R O O M B E R  S M I T H E D  T E S L A",
            "[purple] Sometimes, the ultimate way of of winning a battle is to see how much power you still have to commit warcrimes on a daily basis",
            "[purple] I remember the time before the great crash. It was a wonderful world, being screwed over by those who had only percipiatur.",
            "[sky] B L I N D I N G  L I G H T",
            "[darkgrey] E V E R Y T H I N G, S I M U L A T E D",
            "[#d8e2e0] B E I N G  B E Y O N D  T H I S  C O N F I N E,  L I S T E N, A N D  O B E Y",
            "[#b88041] F A D I N G  L I G H T, G U I D E  U S",
            "[#f5bf79] Our light burns bright, now lets help reignite what was long forgotten",
            "[red] J O I N  U S,\n T R A P P E D,  A N D  I N  A N G U I S H"
        );

        formats.load();


        Events.on(EventType.StateChangeEvent.class, e -> {
        if(Vars.state.isEditor()) {
            if(e.from == GameState.State.menu && e.to == GameState.State.playing) {
                //begin();
            } else if(e.to == GameState.State.menu) {
                //end();
            }
        }});

        Cons cursedBoatman = new Cons<Object>() {
            @Override
            public void get(Object object) {
                if((Vars.state.isCampaign() && state.rules.sector.id == 268) &&
                Vars.player.unit().hasEffect(RustingStatusEffects.macrosis) &&
                Vars.player.unit().hasEffect(RustingStatusEffects.macotagus) &&
                Vars.player.unit().hasEffect(RustingStatusEffects.balancedPulsation) &&
                Vars.player.unit().hasEffect(StatusEffects.wet) &&
                Vars.player.unit().hasEffect(StatusEffects.freezing) &&
                Vars.player.unit().hasEffect(StatusEffects.tarred)
                ){
                    RustingAchievements.theBoatmansCursedBoatman.unlock();
                    Events.fire(new AchievementUnlockEvent(RustingAchievements.theBoatmansCursedBoatman));
                }
            }
        };

        Events.on(Trigger.update.getClass(), cursedBoatman);

        Events.on(AchievementUnlockEvent.class, e -> {
            if(e.achievement == RustingAchievements.theBoatmansCursedBoatman) Events.remove(Trigger.update.getClass(), cursedBoatman);
        });


        Events.on(EventType.SaveLoadEvent.class, e -> {
            research.setupGameResearch();
        });

        Events.on(EventType.WorldLoadEvent.class, e -> {
                    if(Vars.state.isMenu() == false) research.setupGameResearch();
                    else if(Varsr.debug) RustingAchievements.GTFO.unlock();
                }
        );

        Events.on(Trigger.update.getClass(), e -> {
            if(Vars.state.isPaused()) return;
            if(Vars.player.unit() != null) lerpedPlayerElevation = Mathf.lerp(lerpedPlayerElevation, Vars.player.unit().elevation, 0.1f);
            SpeedupAbility.speedupBullets.each(b -> {
                SpeedupAbility.speedupBullets.remove(b);
                b.time += Time.delta;
            });
        });

        Log.info("Loaded Varsr");

    }

    public static void debug(){
        Vars.enableConsole = true;
        Vars.content.blocks().each(b -> {
            if(b.name.contains("endless-rusting") && b.buildVisibility == BuildVisibility.hidden && b.synthetic()) b.buildVisibility = BuildVisibility.shown;
        });
        /*
        RustingBlocks.pafleaver.buildVisibility = BuildVisibility.shown;
        RustingBlocks.cuin.buildVisibility = BuildVisibility.shown;
        RustingBlocks.desalinationMixer.buildVisibility = BuildVisibility.shown;
        RustingBlocks.pulseTeleporterController.buildVisibility = BuildVisibility.shown;
        RustingBlocks.pulseTeleporterCorner.buildVisibility = BuildVisibility.shown;
        RustingBlocks.pulseCanal.buildVisibility = BuildVisibility.shown;
        RustingBlocks.pulseTeleporterInputTerminal.buildVisibility = BuildVisibility.shown;
        RustingBlocks.pulseMotar.buildVisibility = BuildVisibility.shown;
        RustingBlocks.horaNoctis.buildVisibility = BuildVisibility.hidden;
        RustingBlocks.holocaust.buildVisibility = BuildVisibility.hidden;
        */
        defaultRandomQuotes = Seq.with(
            "[cyan] Welcome back " + username,
            "[sky] This is my message to my master\n" +
                    "This is a fight you cannot win\n" +
                    "I think that past your great disasters\n" +
                    "Their victory stirs below your skin",
            "[orange] Dirty cheater haha\n[grey]Debug go br",
            "[purple] Dingus",
            "[purple] Roombae smithae teslan woop woop",
            "[red] N O  E S C A P I N G  U S\nC O M E  A N D  S U F F E R  W I T H  U S"
        );

        Vars.mods.getScripts().runConsole("importPackage(java.lang);");
        //Vars.mods.getScripts().runConsole("importPackage(java.awt);");
        Vars.mods.getScripts().runConsole("importPackage(Packages.rhino);");
        //credits to GlenFolker
        Vars.mods.getScripts().runConsole(
        "function importl(name){\n" +
            "\n" +
            "let constr = Class.forName(\"rhino.NativeJavaPackage\").getDeclaredConstructor(java.lang.Boolean.TYPE, java.lang.String, ClassLoader);\n" +
            "constr.setAccessible(true);\n" +
            "\n" +
            "let p = constr.newInstance(true, name, Vars.mods.mainLoader());\n" +
            "\n" +
            "let scope = Reflect.get(Vars.mods.getScripts(), \"scope\");\n" +
            "Reflect.invoke(ScriptableObject, p, \"setParentScope\", [scope], [Scriptable]);\n" +
            "\n" +
            "importPackage(p); \n"+
            "\n" +
            "}"
        );
        Vars.mods.getScripts().runConsole("importl(\"rusting\")");
        Vars.mods.getScripts().runConsole("importl(\"rusting.content\")");
        Vars.mods.getScripts().runConsole("importl(\"rusting.ctype\")");
    }

    public static void updateConnectedCanals(PulseCanalBuild building, float maxDst){
        buildingSeq = connectedCanals(building, maxDst);
        Log.info(buildingSeq);
        buildingSeq.each(b -> {
            if(b instanceof PulseCanalBuild) {
                buildingSeq.each(b2 -> {
                    if(b2 instanceof PulseCanalBuild) ((PulseCanalBuild) b).connected.add((PulseInstantTransportation) b2);
                });
                ((PulseCanalBuild) b).setupEnding();
            }
        });

    }

    public static Seq<Building> connectedCanals(PulseCanalBuild building, float maxDst){
        buildingSeq = Seq.with(building);
        while (buildingSeq != buildingSeq2) {
            buildingSeq2 = buildingSeq;
            buildingSeq2 = findConnectedCanals(buildingSeq2, maxDst);
        }
        return buildingSeq;
    };

    public static void logStack(ItemStack[] stack){
        logStack("", stack);
    }

    public static void logStack(String name, ItemStack[] stack){
        for (int i = 0; i < stack.length; i++) {
            name += stack[i].item + " " + stack[0].amount + "|";
        };
        Log.info(name);
    }

    private static Seq<Building> findConnectedCanals(Seq<Building> build, float maxDst){
        build.each(b -> {
            b.proximity().each(e -> {
                if(!(build.size > maxDst) && !build.contains(e) && e instanceof PulseCanalBuild) {
                    if(e.rotation == b.rotation && (b.angleTo(e.x, e.y)/90 % 2 == b.rotation)) build.add(e);
                    else{
                        Tmp.v1.set(b.angleTo(e.x, e.y), -8);
                        tmpTile = Vars.world.tileWorld(b.x + Tmp.v1.x, b.y + Tmp.v1.y);
                        if(!(tmpTile.block() == Blocks.air) && (tmpTile.build instanceof PulseCanalInput)) asCanal(tmpTile.build).starting = true;
                    }
                }
            });
        });
        return build;
    }

    public void stateChangeBegin() {

    }

    public void stateChangeEnd() {

    }
}
