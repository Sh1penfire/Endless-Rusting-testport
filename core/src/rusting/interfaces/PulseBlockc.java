package rusting.interfaces;

import arc.graphics.Color;
import arc.util.Nullable;
import mindustry.gen.Building;
import rusting.world.modules.PulseModule;

public interface PulseBlockc{

    default float pulseEfficiency(){
        return 1;
    }

    default void customConsume(){}

    default boolean customConsumeValid(){
        return false;
    }

    default boolean allConsValid(){
        return false;
    }

    default boolean canRecievePulse(float charge){
        return false;
    }

    default boolean connectableTo(){
        return false;
    }

    default boolean receivePulse(float pulse, Building source){;
        pulseModule().pulse += pulse;
        return true;
    }

    default void addPulse(float pulse){
        addPulse(pulse, null);
    }

    default void addPulse(float pulse, @Nullable Building building){
        pulseModule().pulse += pulse;
    }

    default void removePulse(float pulse){
        removePulse(pulse, null);
    }

    default void removePulse(float pulse, @Nullable Building building){
        pulseModule().pulse -= pulse;
    }

    default void normalizePulse(){}

    default void overloadEffect(){}

    default boolean overloaded(){
        return false;
    }

    default float overloadChargef(){
        return 0;
    }

    default float chargef(boolean overloadaccount){
        return 0;
    }

    default float chargef(){
        return chargef(true);
    }

    default PulseModule pulseModule(){
        return null;
    }

    default void drawLaser(PulseBlockc building, float lerpPercent, Color laserCol, Color laserCol2) {

    }

    default float laserOffset(){
        return 8;
    }

}
