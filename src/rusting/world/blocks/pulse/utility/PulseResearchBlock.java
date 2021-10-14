package rusting.world.blocks.pulse.utility;

import arc.Core;
import arc.scene.ui.layout.Table;
import arc.struct.Seq;
import arc.util.Log;
import arc.util.io.Reads;
import arc.util.io.Writes;
import mindustry.Vars;
import mindustry.ctype.UnlockableContent;
import mindustry.game.Team;
import mindustry.gen.Icon;
import mindustry.world.Block;
import mindustry.world.Tile;
import rusting.Varsr;
import rusting.interfaces.ResearchCenter;
import rusting.world.blocks.pulse.PulseBlock;

import static mindustry.Vars.player;
import static mindustry.Vars.state;

public class PulseResearchBlock extends PulseBlock implements ResearchCenter{

    public int threshold = 2;
    public Seq<Block> blocks = new Seq();
    public Seq<String> fieldNames = new Seq();

    public PulseResearchBlock(String name) {
        super(name);
        configurable = true;
        destructible = false;

        config(int.class, (PulseResearchBuild entity, Integer id) -> {
            Varsr.research.unlock(entity.team, id);
            Log.info("HAI!");
        });

    }

    @Override
    public void load(){
        super.load();
    }

    @Override
    public boolean canBreak(Tile tile) {
        return super.canBreak(tile) && tile.block() instanceof PulseResearchBlock && ((PulseResearchBuild) Vars.world.buildWorld(tile.worldx(), tile.worldy())).researchedBlocks.size == 0;
    }

    @Override
    public boolean isHidden(){
        return PulseBlock.validCenter(player.team()) || super.isHidden();
    }

    @Override
    public void drawPlace(int x, int y, int rotation, boolean valid){
        super.drawPlace(x, y, rotation, valid);
        if(getCenterTeam(player.team()) != null){
            drawPlaceText(Core.bundle.get("bar.centeralreadybuilt"), x, y, valid);
        }
    }

    @Override
    public boolean canPlaceOn(Tile tile, Team team){
        //must have been researched, but for now checks if research center exists
        if(getCenterTeam(team) != null) return false;
        return super.canPlaceOn(tile, team);
    }

    public static boolean researched(UnlockableContent content, Team team){
            return getCenterTeam(team) != null && getCenterTeam(team).researchedBlocks.contains(content.localizedName) || content instanceof PulseBlock && !((PulseBlock) content).getResearchModule().needsResearching || state.rules.infiniteResources;
    }

    public static boolean researched(UnlockableContent content, PulseResearchBuild building){
        boolean returnBool = false;
        returnBool = building.researchedBlocks.contains(content.localizedName) || state.rules.infiniteResources;
        return returnBool;
    }

    public void buildDialog(Tile tile){
        Vars.control.input.frag.config.hideConfig();
        if(!(tile.build instanceof PulseResearchBuild)) return;
        Varsr.ui.blocklist.show(tile);
    }

    public class PulseResearchBuild extends PulseBlockBuild{
        public Seq<String> researchedBlocks = new Seq<>();

        @Override
        public void created() {
            super.created();
        }

        public void buildConfiguration(Table table){
            super.buildConfiguration(table);
            table.button(Icon.pencil, () -> {
                buildDialog(tile);
            }).size(40f);
            table.button(Icon.eraser, () -> {
                buildDialog(tile);
            }).size(40f);
        }

        @Override
        public void write(Writes w) {
            super.write(w);
            w.d(researchedBlocks.size);
            researchedBlocks.each(block -> {
                w.str(block);
            });
        }

        @Override
        public void read(Reads r, byte revision) {
            super.read(r, revision);
            //might mess up any classes which extend off this, only keep temporarily or have one block
            double index = r.d();
            for(int i = 0; i < index; i++){
                configure(r.str());
            }
        }
    }
}
