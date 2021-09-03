package rusting.content;

import arc.func.Prov;
import arc.graphics.Color;
import arc.struct.ObjectIntMap;
import arc.struct.ObjectMap.Entry;
import mindustry.ai.types.MinerAI;
import mindustry.content.*;
import mindustry.core.Version;
import mindustry.ctype.ContentList;
import mindustry.entities.abilities.StatusFieldAbility;
import mindustry.entities.bullet.BombBulletType;
import mindustry.entities.bullet.LaserBulletType;
import mindustry.gen.*;
import mindustry.graphics.Pal;
import mindustry.type.UnitType;
import mindustry.type.Weapon;
import rusting.ai.types.BossStingrayAI;
import rusting.ai.types.MultiSupportAI;
import rusting.entities.abilities.*;
import rusting.entities.units.*;

public class RustingUnits implements ContentList{
    //Steal from BetaMindy
    private static Entry<Class<? extends Entityc>, Prov<? extends Entityc>>[] types = new Entry[]{
            prov(StingrayUnitEntity.class, StingrayUnitEntity::new),
            prov(CraeUnitEntity.class, CraeUnitEntity::new),
            prov(AcriUnitEntity.class, AcriUnitEntity::new),
            prov(BaseUnit.class, BaseUnit::new)
    };

    private static ObjectIntMap<Class<? extends Entityc>> idMap = new ObjectIntMap<>();

    /**
     * Internal function to flatmap {@code Class -> Prov} into an {@link Entry}.
     * @author GlennFolker
     */
    private static <T extends Entityc> Entry<Class<T>, Prov<T>> prov(Class<T> type, Prov<T> prov){
        Entry<Class<T>, Prov<T>> entry = new Entry<>();
        entry.key = type;
        entry.value = prov;
        return entry;
    }

    /**
     * Setups all entity IDs and maps them into {@link EntityMapping}.
     * @author GlennFolker
     */

    private static void setupID(){
        for(
                int i = 0,
                j = 0,
                len = EntityMapping.idMap.length;

                i < len;

                i++
        ){
            if(EntityMapping.idMap[i] == null){
                idMap.put(types[j].key, i);
                EntityMapping.idMap[i] = types[j].value;

                if(++j >= types.length) break;
            }
        }
    }

    /**
     * Retrieves the class ID for a certain entity type.
     * @author GlennFolker
     */
    public static <T extends Entityc> int classID(Class<T> type){
        return idMap.get(type, -1);
    }

    public static CraeUnitType
            duono, duoly, duanga;
    public static UnitType
            marrow, metaphys, ribigen, spinascene, trumpedoot,
            fahrenheit, celsius;
    public static UnitType
        stingray;
    //Acrimynal's drone army
    public static AcriUnitType
            observantly, kindling;

    @Override
    public void load() {
        setupID();

        EntityMapping.nameMap.put("duono", CraeUnitEntity::new);
        duono = new CraeUnitType("duono"){{

            defaultController = MultiSupportAI::new;

            flying = true;
            hitSize = 7;
            itemCapacity = 50;
            health = 110;
            speed = 1.2f;
            accel = 0.045f;
            drag = 0.025f;
            isCounted = false;

            mineTier = 3;
            mineSpeed = 1.5f;

            pulseStorage = 25;
            repairRange = 40;

            projectileDeathSpawnInterval = 3;

            constructor = CraeUnitEntity::new;

            abilities.add(
                new UpkeeperFieldAbility(4.35f, 135, 45, 4f)
            );
            weapons.addAll(
                new Weapon("none") {{
                    x = 0;
                    y = 0;
                    mirror = false;
                    bullet = RustingBullets.paveBolt;
                    reload = 75;
                    shootCone = 35;
                    shots = 2;
                    shotDelay = 1;
                    inaccuracy = 1;
                }}
            );
        }};

        EntityMapping.nameMap.put("duoly", CraeUnitEntity::new);
        duoly = new CraeUnitType("duoly"){{
            defaultController = MultiSupportAI::new;

            flying = true;
            hitSize = 10;
            itemCapacity = 20;
            health = 320;
            speed = 2.3f;
            accel = 0.0225f;
            drag = 0.0185f;
            rotateSpeed = 2.85f;
            isCounted = false;

            mineTier = 3;
            mineSpeed = 2.3f;

            pulseStorage = 65;
            repairRange = 50;


            projectileDeathSpawnInterval = 5;

            constructor = CraeUnitEntity::new;

            abilities.add(
                    new UpkeeperFieldAbility(5.5f, 175, 55, 7f)
            );
            weapons.add(
                new Weapon("none") {{
                    x = 1;
                    y = 3;
                    bullet = RustingBullets.paveWeaver;
                    reload = 22.5f;
                }}
            );
        }};

        EntityMapping.nameMap.put("duanga", CraeUnitEntity::new);
        duanga = new CraeUnitType("duanga"){{
            defaultController = MultiSupportAI::new;

            range = 155;
            flying = true;
            hitSize = 15;
            itemCapacity = 35;
            health = 435;
            armor = 2;
            speed = 1.8f;
            accel = 0.0525f;
            drag = 0.0385f;
            rotateSpeed = 4.85f;
            buildSpeed = 0.75f;

            rotateShooting = false;

            isCounted = false;

            pulseStorage = 85;
            repairRange = 90;
            pulseAmount = 3.25f;
            pulseGenRange = 120;

            projectileDeathSpawnInterval = 8;

            constructor = CraeUnitEntity::new;

            abilities.add(
                new HealthEqualizerAbility(){{
                    health = 7.5f;
                    reload = 22.5f;
                    range = 95;
                    lineThickness = 7;
                    laserOffset = 2.35f;
                    maxWidth = 2;
                    y = -2;
                    x = 3.75f;
                    mirror = true;
                }},
                new PulseGeneratorAbility(){{
                    pulse = 3.25f;
                    reload = 135;
                    range = 120;
                    laserOffset = 2.35f;
                    x = 0;
                    y = -6.25f;
                }},
                new UpkeeperFieldAbility(7.5f, 235, 65, 6),
                new RegenerationAbility(3.5f/60)
            );
        }};


        EntityMapping.nameMap.put("fahrenheit", BaseUnit::new);
        fahrenheit = new UnitType("fahrenheit"){{

            flying = false;
            hitSize = 5;
            health = 110;
            speed = 1.2f;
            accel = 0.045f;
            drag = 0.025f;

            constructor = BaseUnit::new;
            weapons.add(
                new Weapon("none") {{
                    x = 0;
                    y = 0;
                    shootCone = 360;
                    mirror = false;
                    bullet = new BombBulletType(0f, 0f, "clear"){{
                        hitEffect = Fx.pulverize;
                        lifetime = 10f;
                        speed = 1.3f;
                        splashDamageRadius = 58f;
                        instantDisappear = true;
                        splashDamage = 45f;
                        killShooter = true;
                        hittable = false;
                        collidesAir = true;
                        fragBullets = 13;
                        fragBullet = RustingBullets.craeBoltKill;
                    }};
                }}
            );
        }};

        EntityMapping.nameMap.put("celsius", BaseUnit::new);
        celsius = new UnitType("celsius"){{

            flying = false;
            hitSize = 9;
            health = 110;
            speed = 1.2f;
            accel = 0.045f;
            drag = 0.025f;

            constructor = BaseUnit::new;
            weapons.add(
                    new Weapon("none") {{
                        x = 0;
                        y = 0;
                        shootCone = 360;
                        mirror = false;
                        bullet = new BombBulletType(0f, 0f, "clear"){{
                            hitEffect = Fx.pulverize;
                            lifetime = 10f;
                            speed = 1.3f;
                            splashDamageRadius = 58f;
                            instantDisappear = true;
                            splashDamage = 45f;
                            killShooter = true;
                            hittable = false;
                            collidesAir = true;
                            fragBullets = 13;
                            fragBullet = RustingBullets.craeBoltKill;
                        }};
                    }}
            );
        }};

        EntityMapping.nameMap.put("marrow", BaseUnit::new);
        marrow = new UnitType("marrow"){{
            hitSize = 8;
            health = 335;
            armor = 1;
            speed = 0.5225f;
            accel = 0.5f;
            drag = 0.25f;
            lightRadius = hitSize * 2.5f;
            lightColor = Palr.dustriken;
            lightOpacity = 0.15f;
            itemCapacity = 15;
            commandLimit = 4;
            mechLegColor = Palr.dustriken;
            //v7 compatability
            constructor = BaseUnit::new;
            abilities.add(
                    new RegenerationAbility(0.1f)
            );

            immunities.addAll(
                StatusEffects.wet,
                StatusEffects.burning,
                StatusEffects.sporeSlowed,
                StatusEffects.sapped,
                RustingStatusEffects.shieldShatter
            );

            weapons.add(
                new Weapon("none") {{
                    x = 4;
                    y = 4.25f;
                    shots = 3;
                    spacing = 3;
                    shotDelay = 5;
                    bullet = RustingBullets.horizonInstalt;
                    shootSound = Sounds.bang;
                    reload = 125;
                }}
            );

        }};

        EntityMapping.nameMap.put("metaphys", BaseUnit::new);
        metaphys = new UnitType("metaphys"){{
            hitSize = 10;
            health = 830;
            armor = 4;
            speed = 0.45f;
            accel = 0.15f;
            drag = 0.05f;
            lightRadius = hitSize * 2.5f;
            lightColor = Palr.dustriken;
            lightOpacity = 0.35f;
            itemCapacity = 35;
            commandLimit = 3;
            mechLegColor = Palr.dustriken;
            //v7 compatability
            constructor = BaseUnit::new;
            abilities.add(
                    new RegenerationAbility(0.1f)
            );

            immunities.addAll(
                StatusEffects.wet,
                StatusEffects.burning,
                StatusEffects.sporeSlowed,
                StatusEffects.sapped,
                RustingStatusEffects.shieldShatter
            );

            weapons.addAll(
                new Weapon("endless-rusting-metaphys-sidearms"){{
                    top = false;
                    alternate = true;
                    x = 7.25f;
                    y = 1.25f;
                    recoil = 2;
                    shots = 3;
                    spacing = 25;
                    inaccuracy = 3;
                    bullet = RustingBullets.pavenShardling;
                    shootSound = Sounds.flame2;
                    reload = 77.5f;
                }}
            );

        }};

        EntityMapping.nameMap.put("ribigen", BaseUnit::new);
        ribigen = new UnitType("ribigen"){{
            hitSize = 13;
            health = 1235;
            armor = 10;
            speed = 0.35f;
            accel = 0.35f;
            drag = 0.15f;
            lightRadius = hitSize * 3.5f;
            lightColor = Palr.dustriken;
            lightOpacity = 0.05f;
            itemCapacity = 75;
            commandLimit = 2;
            mechLegColor = Palr.dustriken;
            //v7 compatability
            constructor = BaseUnit::new;
            abilities.add(
                    new RegenerationAbility(0.3f)
            );

            immunities.addAll(
                    StatusEffects.wet,
                    StatusEffects.burning,
                    StatusEffects.sporeSlowed,
                    StatusEffects.sapped,
                    RustingStatusEffects.shieldShatter,
                    RustingStatusEffects.amberstriken,
                    RustingStatusEffects.umbrafliction
            );

            weapons.addAll(
                new Weapon("endless-rusting-ribigen-instalt-launcher"){{
                    x = 13.25f;
                    y = 6.25f;
                    reload = 120;
                    recoil = 3;
                    shots = 4;
                    spacing = 4;
                    shootSound = Sounds.artillery;
                    bullet = RustingBullets.timelessInstalt;
                    top = false;
                }},
                new Weapon("endless-rusting-ribigen-weapon"){{
                    x = 0;
                    y = 0;
                    shootY = 7.75f;
                    shots = 5;
                    spacing = 1;
                    inaccuracy = 0.25f;
                    shotDelay = 1;
                    recoil = 0;
                    reload = 150;
                    heatColor = new Color(Pal.turretHeat).a(0.54f);
                    bullet = Bullets.heavyOilShot;
                    shootSound = Sounds.release;
                    mirror = false;
                }},
                new Weapon("none"){{
                    x = 0;
                    y = 0;
                    shots = 25;
                    spacing = 2;
                    inaccuracy = 1;
                    shotDelay = 0.35f;
                    reload = 150;
                    bullet = Bullets.oilShot;
                    shootSound = Sounds.none;
                }}
            );
        }};

        EntityMapping.nameMap.put("spinascene", BaseUnit::new);
        spinascene = new UnitType("spinascene"){{
            hitSize = 24;
            health = 9760;
            armor = 13;
            speed = 0.25f;
            accel = 0.35f;
            drag = 0.25f;
            rotateSpeed = 0.55f;
            lightRadius = hitSize * 4.5f;
            lightColor = Palr.dustriken;
            lightOpacity = 0.125f;
            itemCapacity = 125;
            commandLimit = 2;
            mechLegColor = Palr.dustriken;
            //v7 compatability
            constructor = BaseUnit::new;
            abilities.add(
                new RegenerationAbility(0.7f),
                new StatusFieldAbility(RustingStatusEffects.corruptShield, 1440, 600, 45)
            );

            immunities.addAll(
                StatusEffects.wet,
                StatusEffects.burning,
                StatusEffects.sporeSlowed,
                StatusEffects.sapped,
                RustingStatusEffects.shieldShatter,
                RustingStatusEffects.amberstriken,
                RustingStatusEffects.umbrafliction
            );

            weapons.addAll(
                new Weapon("endless-rusting-spinascene-branches"){{
                    x = 16.5f;
                    y = -0.35f;
                    shootY = 19.45f;
                    reload = 430;
                    shots = 9;
                    spacing = 3;
                    shootSound = Sounds.none;
                    recoil = 3;
                    bullet = RustingBullets.timelessInstalt;
                    shootCone = 90;
                    top = false;
                }},
                new Weapon("endless-rusting-spinascene-beam"){{
                    x = 19.17f;
                    y = 3.45f;
                    bullet = RustingBullets.nummingVortex;
                    shootSound = Sounds.bang;
                    shootCone = 90;
                    reload = 430;
                    inaccuracy = 4;
                    recoil = 3;
                }},
                new Weapon("none"){{
                    x = 0;
                    y = 0;
                    bullet = RustingBullets.darkShard;
                    shootSound = Sounds.none;
                    shots = 15;
                    reload = 45 * 7.5f;
                    inaccuracy = 360;
                    shotDelay = 22.5f;
                    shootCone = 360;
                    rotate = true;
                }}
            );
        }};

        EntityMapping.nameMap.put("trumpedoot", BaseUnit::new);
        trumpedoot = new UnitType("trumpedoot"){{
            hitSize = 28;
            health = 29500;
            armor = 19;
            speed = 0.35f;
            accel = 0.65f;
            drag = 0.45f;
            rotateSpeed = 0.95f;
            lightRadius = hitSize * 4.5f;
            lightColor = Palr.dustriken;
            lightOpacity = 0.013f;
            itemCapacity = 125;
            commandLimit = 5;
            mechLegColor = Palr.dustriken;

            constructor = BaseUnit::new;

            abilities.addAll(
                new RegenerationAbility(0.21f),
                new SpeedupAbility(){{
                    range = 115;
                    lineThickness = 7;
                    laserOffset = 2.35f;
                    maxWidth = 2;
                    x = 15.5f;
                    mirror = true;
                }}
            );

            weapons.addAll(
                new Weapon("endless-rusting-triumpedoot-weapon"){{
                    bullet = RustingBullets.gunnersVortex;
                    reload = 195;
                    shootX = 21;
                    shootY = 6;
                    shots = 4;
                    x = 0;
                    shootCone = 360;
                    spacing = 15;
                    alternate = false;
                    top = false;
                    recoil = 15;
                    shake = 5;
                }},
                new Weapon("none"){{
                    x = 0;
                    mirror = false;
                    shots = 3;
                    spacing = 120;
                    shootCone = 360;
                    reload = 1200;
                    bullet = RustingBullets.guardianVortex;
                    shotDelay = 5;
                }}
            );
            immunities.addAll(
                StatusEffects.wet,
                StatusEffects.burning,
                StatusEffects.sporeSlowed,
                StatusEffects.sapped,
                RustingStatusEffects.amberstriken,
                RustingStatusEffects.umbrafliction
            );
        }};

        EntityMapping.nameMap.put("guardian-sulphur-stingray", StingrayUnitEntity::new);
        stingray = new UnitType("guardian-sulphur-stingray"){{
            health = 8750;
            armor = 3;
            rotateSpeed = 3.25f;
            lightOpacity = 0.35f;
            lightColor = Palr.pulseBullet;
            hitSize = 18;
            drag = 0.05f;
            accel = 0.055f;
            speed = 3.75f;
            flying = true;
            circleTarget = true;
            faceTarget = false;
            omniMovement = false;
            constructor = StingrayUnitEntity::new;
            if(Version.number < 7) defaultController = BossStingrayAI::new;
            abilities.addAll(
                new RegenerationAbility(0.75f)
            );
            weapons.addAll(
                new Weapon("none"){{
                    bullet = RustingBullets.saltyLightGlaive;
                    shootCone = 360;
                    shots = 5;
                    shotDelay = 5;
                    spacing = -15;
                    reload = 450;
                    singleTarget = true;
                    mirror = false;
                }},
                new Weapon("none"){{
                    bullet = RustingBullets.saltyLightGlaive;
                    shootCone = 360;
                    shots = 5;
                    shotDelay = 5;
                    spacing = 15;
                    reload = 450;
                    singleTarget = true;
                    mirror = false;
                }},
                new Weapon("none"){{
                    bullet = RustingBullets.stingrayShard;
                    shots = 2;
                    spacing = 5;
                    inaccuracy = 2;
                    shootCone = 360;
                    x = 11.5f;
                    y = 5.25f;
                    reload = 4.65f;
                    shootSound = Sounds.bang;
                    singleTarget = true;
                }},
                new Weapon("none"){{
                    bullet = RustingBullets.melomaeShot;
                    shots = 5;
                    shotDelay = 4;
                    shootCone = 360;
                    x = 17.25f;
                    y = 4.25f;
                    reload = 45.65f;
                    shootSound = Sounds.explosion;
                    singleTarget = true;
                }},
                new Weapon("none") {{
                    bullet = RustingBullets.mhemShard;
                    shootCone = 360;
                    x = 8.75f;
                    y = -7.5f;
                    reload = 15;
                    singleTarget = true;
                }}
            );
            immunities.addAll(
                StatusEffects.wet,
                StatusEffects.shocked,
                StatusEffects.freezing,
                StatusEffects.blasted,
                StatusEffects.sporeSlowed,
                StatusEffects.sapped,
                StatusEffects.burning,
                StatusEffects.unmoving,
                RustingStatusEffects.amberstriken,
                RustingStatusEffects.umbrafliction,
                RustingStatusEffects.macrosis,
                RustingStatusEffects.macotagus,
                RustingStatusEffects.hailsalilty
            );
        }};

        EntityMapping.nameMap.put("observantly", AcriUnitEntity::new);
        observantly = new AcriUnitType("observantly"){{

            flying = true;
            drag = 0.025f;
            accel = 0.0525f;
            speed = 1.75f;
            health = 850f;
            armor = 10;
            rotateSpeed = 3.5f;
            hitSize = 19;
            itemCapacity = 14;

            constructor = AcriUnitEntity::new;


            weapons.addAll(
                new Weapon("none"){{
                    bullet = RustingBullets.melomaeShot;
                    shots = 15;
                    x = 0;
                    y = 0;
                    spacing = 72;
                    reload = 125;
                    shotDelay = 5;
                    shootCone = 360;
                    shootSound = Sounds.none;
                }},
                new Weapon("none"){{
                    bullet = RustingBullets.melomaeShot;
                    shots = 15;
                    x = 0;
                    y = 0;
                    spacing = -72;
                    reload = 125;
                    shotDelay = 5;
                    shootCone = 360;
                    shootSound = Sounds.none;
                }},
                new Weapon("none"){{
                    bullet = new LaserBulletType(25){{
                        recoil = 5;
                        length = 166;
                    }};
                    shots = 3;
                    x = 0;
                    y = 0;
                    spacing = 3;
                    reload = 135;
                    shootSound = Sounds.laser;
                    soundPitchMax = 0.35f;
                    soundPitchMin = 0.25f;
                }}
            );
        }};

        EntityMapping.nameMap.put("kindling", AcriUnitEntity::new);
        kindling = new AcriUnitType("kindling"){{
            flying = true;

            accel = 0.025f;
            drag = 0.001f;
            speed = 2.35f;
            rotateSpeed = 4.5f;
            itemCapacity = 75;
            mineTier = 3;
            mineSpeed = 6.5f;
            health = 635f;
            armor = 13;
            hitSize = 14;
            defaultController = MinerAI::new;
            constructor = AcriUnitEntity::new;
            abilities.addAll(
                new HealthEqualizerAbility(){{
                    mountName = "none";
                    mirror = false;
                }},
                new RegenerationAbility(0.95f)
            );
        }};
    }
}
