package rusting.game;

import rusting.ctype.UnlockableAchievement;

public class RustingEvents {

    //events that occur very often
    public enum Trigger{
        shock,
        phaseDeflectHit,
        impactPower,
        thoriumReactorOverheat,
        fireExtinguish,
        acceleratorUse,
        newGame,
        tutorialComplete,
        flameAmmo,
        turretCool,
        enablePixelation,
        exclusionDeath,
        suicideBomb,
        openWiki,
        teamCoreDamage,
        socketConfigChanged,
        update,
        draw,
        preDraw,
        postDraw,
        uiDrawBegin,
        uiDrawEnd,
        //before/after bloom used, skybox or planets drawn
        universeDrawBegin,
        //skybox drawn and bloom is enabled - use Vars.renderer.planets
        universeDraw,
        //planets drawn and bloom disabled
        universeDrawEnd
    }

    /** Called when the player taps/clicks on an achievement while it's locked.*/
    public static class AchievementQuestionMarkClick{}
    /** Called when the player completes an achievement.*/
    public static class AchievementUnlockEvent{
        public final UnlockableAchievement achievement;

        public AchievementUnlockEvent(UnlockableAchievement achievement){
            this.achievement = achievement;
        }
    }
}
