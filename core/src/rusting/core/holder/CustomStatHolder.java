package rusting.core.holder;

import arc.struct.Seq;

public class CustomStatHolder {
    public StatHolder pulseStorage = new StatHolder("pulsestorage"),

    //Generic stats
    resistance = new StatHolder("resistance"),
    powerLoss = new StatHolder("powerloss"),
    connectable = new StatHolder("connectable"),
    canOverload = new StatHolder("canoverload"),

    //Overload related stats
    requiresOverload = new StatHolder("requiresoverload"),
    minRequiredPercent = new StatHolder("overloadpercent", "percent"),
    overloadCapacity = new StatHolder("overloadcapacity"),
    projectileChanceModifier = new StatHolder("projectilechancemodifier"),
    projectileRange = new StatHolder("projectilerange", "blocks"),

    //Transmission related stats
    connections = new StatHolder("connections"),
    energyTransmission = new StatHolder("energytransmission"),
    siphonAmount = new StatHolder("siphonamount"),
    laserRange = new StatHolder("laserrange", "blocks"),
    pulseReloadTime = new StatHolder("pulsereloadtime"),
    pulseBursts = new StatHolder("pulsebursts"),
    pulseBurstSpacing = new StatHolder("pulseburstspacing", "seconds"),

    //utility related stats
    healPercent = new StatHolder("healpercent", "seconds"),
    overdrivePercent = new StatHolder("overdrivepercent"),
    healPercentFalloff = new StatHolder("healpercentfalloff"),

    //generation related stats
    pulseProduced = new StatHolder("pulseproduced"),
    pulseProductionInterval = new StatHolder("pulseproductioninterval", "seconds")
    ;

    public StatGroup allStats = new StatGroup(Seq.with(
            pulseStorage,
            resistance,
            powerLoss,
            connectable,
            canOverload,
            requiresOverload,
            minRequiredPercent,
            overloadCapacity,
            projectileChanceModifier,
            projectileRange,
            laserRange,
            energyTransmission,
            siphonAmount,
            pulseReloadTime,
            pulseBursts,
            pulseBurstSpacing,
            healPercent,
            healPercentFalloff,
            pulseProduced,
            pulseProductionInterval
    ), true);

    //generic stats which are affected by or affect no other stats. Excludes canOverload as overload group comes directly after generic stats
    public Seq<StatHolder> genericStats = Seq.with(
            pulseStorage,
            resistance,
            powerLoss,
            connectable,
            canOverload
    );

    //shown if block can overload
    public Seq<StatHolder> conditionalOverloadStats = Seq.with(
            requiresOverload,
            minRequiredPercent,
            overloadCapacity,
            projectileChanceModifier,
            projectileRange
    );

    //shown if the block has been assigned a pulse reload time stat
    public Seq<StatHolder> conditionalRangeStats = Seq.with(
            connections,
            laserRange,
            energyTransmission,
            siphonAmount,
            pulseReloadTime,
            pulseBursts,
            pulseBurstSpacing
    );

    public Seq<StatHolder> conditionalUtilityStats = Seq.with(
            healPercent,
            overdrivePercent,
            healPercentFalloff
    );

    //shown if a block has been assigned a pulse produced stat
    public Seq<StatHolder> conditionalProductionStats = Seq.with(
            pulseProduced,
            pulseProductionInterval
    );

    //all stats
    public Seq<StatGroup> loadableGroups = Seq.with(
            new StatGroup(
                    genericStats, true, "generalstats"
            ),
            new StatGroup(
                    conditionalOverloadStats, canOverload, "overloadstats"
            ),
            new StatGroup(
                    conditionalRangeStats, connections, "transmissionstats"
            ),
            new StatGroup(
                    conditionalUtilityStats, healPercent, "utilitystats"
            ),
            new StatGroup(
                    conditionalProductionStats, pulseProduced, "productionstats"
            )
    );

}
