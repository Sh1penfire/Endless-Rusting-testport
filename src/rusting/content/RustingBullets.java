package rusting.content;

import arc.func.Cons;
import arc.graphics.Color;
import arc.math.Angles;
import arc.struct.Seq;
import arc.util.Time;
import arc.util.Tmp;
import mindustry.Vars;
import mindustry.content.*;
import mindustry.ctype.ContentList;
import mindustry.entities.Effect;
import mindustry.entities.Fires;
import mindustry.entities.bullet.*;
import mindustry.gen.*;
import mindustry.graphics.Pal;
import mindustry.world.blocks.defense.turrets.Turret.TurretBuild;
import rusting.EndlessRusting;
import rusting.entities.bullet.*;
import rusting.math.Mathr;

import static rusting.content.RustingStatusEffects.*;

public class RustingBullets implements ContentList{

    public static Cons<Bullet>
    homing, homingOwner;

    public static BulletType
        //basic bullets
        fossilShard, cloudyShard, craeShard, raehShard, mhemShard, fraeShard, paveShard, pavenShardling, darkShard, unhittableDarkShard, horizonShard, stingrayShard, spawnerGlass, spawnerGlassFrag, spawnerBulat, spawnerBulatFrag,
        //artillery
        mhemQuadStorm, craeQuadStorm, lightfractureTitanim, lightfractureBulat,
        //liquid
        melomaeShot, heavyMelomaeShot,
        //missile/weaving bullets
        craeWeaver, paveWeaver,
        //lightning bullets
        craeBolt, craeBoltKill,
        //laser bolt bullets
        paveBolt,
        //essentualy small nukes
        craeBalistorm, craeNukestorm,
        //boomerangs
        craeLightRoundaboutRight, craeLightRoundaboutLeft, saltyLightRoundaboutRight, saltyLightRoundaboutLeft, denseLightRoundaboutLeft, denseLightRoundaboutRight,
        //glaivs
        craeLightGlaive, craeLightGlaiveRight, craeLightGlaiveLeft, saltyLightGlaive,
        //instant bullets
        horizonInstalt, nummingInstalt, timelessInstalt, gunnersInstalt,
        //bullet spawning bullets
        nummingVortex, cloudyVortex, boltingVortex, flamstrikenVortex, gunnersVortex, guardianVortex,
        //flames
        longThorFlame, longPyraFlame,
        //harpoons
        cameoSmallHarpoon;
        ;

    @Override
    public void load(){

        homing = bullet -> {
            Fxr.singingFlame.at(bullet.x, bullet.y, bullet.rotation());
            if(bullet.fdata() != 1 && bullet.collided.size < 2){
                Tmp.v1.set(bullet.x, bullet.y);
                if(bullet.owner instanceof TurretBuild) {
                    Tmp.v1.set(((TurretBuild) bullet.owner).targetPos.x, ((TurretBuild) bullet.owner).targetPos.y);
                }
                else if (bullet.owner instanceof Unitc){
                    Tmp.v1.set(((Unitc) bullet.owner).aimX(), ((Unitc) bullet.owner).aimY());
                }
                bullet.vel.setAngle(Angles.moveToward(bullet.rotation(), bullet.angleTo(Tmp.v1.x, Tmp.v1.y), Time.delta * 261f * bullet.fin()));
                //stop homing in after reaching cursor
                if(bullet.within(Tmp.v1.x, Tmp.v1.y, bullet.hitSize)){
                    bullet.fdata = 1;
                }
            }
            //essentualy goes to owner aim pos.

        };

        homingOwner = bullet -> {
            Tmp.v1.set(bullet.x, bullet.y);
            if(bullet.owner instanceof Posc) {
                Tmp.v1.set(((Posc) bullet.owner).x(), ((Posc) bullet.owner).y());
            }
            bullet.vel.setAngle(Angles.moveToward(bullet.rotation(), bullet.angleTo(Tmp.v1.x, Tmp.v1.y), Time.delta));
            //essentualy goes to owner.

        };

        //low speed to make targeting work
        horizonInstalt = new InstantBounceBulletType(0.0001f, 22, "bullet"){{
            width = 7;
            height = 8;
            lifetime = 54;
            length = 132;
            buildingDamageMultiplier = 0.45f;
            shootEffect = Fx.shootSmall;
            hitEffect = Fx.hitFuse;
            bounceEffect = Fx.blockExplosionSmoke;
            status = shieldShatter;
            statusDuration = 450;
            knockback = 0.1f;
            drag = 0.005f;
            bounciness = 1.6;
            bounceCap = 3;
        }};

        nummingInstalt = new InstantBounceBulletType(0.0001f,  7.5f, "bullet"){{
            width = 7;
            height = 8;
            lifetime = 54;
            length = 152;
            buildingDamageMultiplier = 0.15f;
            shootEffect = Fx.shootSmall;
            hitEffect = Fx.hitFuse;
            bounceEffect = Fx.blockExplosionSmoke;
            trailColor = Palr.lightstriken;
            status = StatusEffects.shocked;
            statusDuration = 250;
            knockback = 0.3f;
            drag = 0.005f;
            bounciness = 1.2;
            bounceCap = 4;
        }};

        timelessInstalt = new InstantBounceBulletType(0.0001f, 38, "bullet"){{
            width = 7;
            height = 8;
            lifetime = 192;
            length = 166;
            buildingDamageMultiplier = 0.35f;
            shootEffect = Fx.shootSmall;
            hitEffect = Fx.hitFuse;
            bounceEffect = Fx.blockExplosionSmoke;
            trailColor = Palr.pulseBullet;
            status = StatusEffects.freezing;
            knockback = 0.3f;
            drag = 0.005f;
            bounciness = 1.2;
            bounceCap = 2;
        }};

        gunnersInstalt = new InstantBounceBulletType(0.0001f, 38, "bullet"){{
            width = 7;
            height = 8;
            lifetime = 192;
            length = 216;
            buildingDamageMultiplier = 0.55f;
            shootEffect = Fx.shootSmall;
            hitEffect = Fx.hitFuse;
            bounceEffect = Fx.blockExplosionSmoke;
            trailColor = Palr.dustriken;
            status = shieldShatter;
            knockback = 0.3f;
            drag = 0.005f;
            bounciness = 1.35f;
            bounceCap = 5;
        }};

        fossilShard = new BounceBulletType(4, 9, "bullet"){{
            width = 7;
            height = 8;
            lifetime = 54;
            hitEffect = Fx.hitFuse;
            despawnEffect = Fx.plasticburn;
            bounceEffect = Fx.blockExplosionSmoke;
            status = amberstriken;
            statusDuration = 45;
            knockback = 1;
            drag = 0.005f;
            bounciness = 0.6;
        }};

        cloudyShard = new BasicBulletType(6.5f, 3, "bullet"){{
            splashDamage = 6;
            splashDamageRadius = 22;
            width = 6;
            height = 12;
            lifetime = 18;
            hitEffect = Fx.plasticburn;
            despawnEffect = Fx.plasticburn;
            trailColor = Color.white;
            status = StatusEffects.corroded;
            frontColor = Color.white;
            backColor = Pal.gray;
            trailChance = 0.25f;
            knockback = 1.85f;
            homingPower = 0.15f;
        }};

        craeShard = new BounceBulletType(4, 5, "bullet"){{
            width = 7;
            height = 8;
            lifetime = 15;
            hitEffect = Fx.hitFuse;
            despawnEffect = Fx.plasticburn;
            bounceEffect = Fx.hitLancer;
            status = macrosis;
            frontColor = Palr.pulseChargeStart;
            backColor = Palr.pulseBullet;
            trailColor = frontColor;
            trailEffect = Fx.lightningShoot;
            knockback = 1;
            drag = 0.005f;
            bounciness = 0.6;
        }};

        raehShard = new BounceBulletType(6, 12, "bullet"){{
            width = 9;
            height = 10;
            lifetime = 45;
            hitEffect = Fx.hitFuse;
            despawnEffect = Fx.plasticburn;
            bounceEffect = Fx.blockExplosionSmoke;
            trailColor = Pal.darkPyraFlame;
            incendChance = 1;
            incendAmount = 3;
            status = StatusEffects.burning;
            statusDuration = 640;
            trailLength = 10;
            homingPower = 0.25F;
            knockback = 3;
            drag = 0.005f;
            bounciness = 0.85;
        }};



        mhemShard = new BounceBulletType(6, 22.5f, "bullet"){{
            consUpdate = homing;
            despawnEffect = Fx.fireSmoke;
            hitEffect = Fx.fire;
            bounceEffect = Fxr.shootMhemFlame;
            incendChance = 1;
            incendAmount = 10;
            status = StatusEffects.burning;
            statusDuration = 3600;
            maxRange = 156;
            width = 6;
            height = 8;
            hitSize = 12;
            lifetime = 35;
            hitEffect = Fx.hitFuse;
            trailLength = 0;
            drag = 0.015f;
            bounciness = 0.95;
            bounceCap = 2;
        }};

        fraeShard = new ConsBulletType(10, 25, "bullet"){{
            consUpdate = new Cons<Bullet>() {
                @Override
                public void get(Bullet bullet) {
                    darkShard.create(bullet.owner, bullet.team, bullet.x, bullet.y, bullet.rotation() - 90, Mathr.helix(7, 1, bullet.fin()));
                    darkShard.create(bullet.owner, bullet.team, bullet.x, bullet.y, bullet.rotation() + 90, Mathr.helix(7, 1, bullet.fin()));
                }
            };
            despawnEffect = Fx.plasticburn;
            hitEffect = Fx.plasticExplosion;
            status = StatusEffects.corroded;
            statusDuration = 7200;
            lightOpacity = 0;
            width = 10;
            height = 12;
            pierce = true;
            pierceBuilding = true;
            lifetime = 20;
            hitEffect = Fx.hitFuse;
        }};

        paveShard = new ConsBulletType(12, 115, "bullet"){{
            consUpdate = bullet -> {

                Fxr.singingFlame.at(bullet.x, bullet.y, bullet.rotation() + Mathr.helix(7, 45, bullet.fin()));
                Fxr.singingFlame.at(bullet.x, bullet.y, bullet.rotation() - Mathr.helix(7, 45, bullet.fin()));

                if(bullet.collided.size >= 1) {
                    Fires.create(Vars.world.tileWorld(bullet.x, bullet.y));
                    Fxr.paveFlame.at(bullet.x, bullet.y, bullet.rotation());
                }
            };
            despawnEffect = Fx.fireSmoke;
            hitEffect = Fxr.shootMhemFlame;
            incendChance = 1;
            incendAmount = 10;
            status = StatusEffects.melting;
            statusDuration = 3600;
            width = 8;
            height = 10;
            hitSize = 12;
            pierce = true;
            pierceBuilding = true;
            lifetime = 25;
            hitEffect = Fx.hitFuse;
        }};

        pavenShardling = new BounceBulletType(4, 12.5f, "bullet"){{
            consUpdate = homing;
            despawnEffect = Fx.fireSmoke;
            hitEffect = Fx.fire;
            bounceEffect = Fxr.shootMhemFlame;
            incendChance = 1;
            incendAmount = 10;
            status = StatusEffects.melting;
            statusDuration = 150;
            maxRange = 156;
            width = 6;
            height = 8;
            hitSize = 12;
            lifetime = 49f;
            hitEffect = Fx.hitFuse;
            trailLength = 0;
            drag = 0.005f;
            bounciness = 0.95;
            bounceCap = 2;
        }};

        darkShard = new BounceBulletType(4, 2.5f, "bullet"){{
            consUpdate = bullet -> {
                if(bullet.fin() % 0.04 < 0.01) Fxr.blackened.at(bullet.x, bullet.y, bullet.rotation());
            };
            despawnEffect = Fx.fireSmoke;
            hitEffect = Fx.casing3Double;
            bounceEffect = Fx.none;
            shootEffect = Fxr.blackened;
            frontColor = Color.darkGray;
            backColor = Palr.voidBullet;
            status = RustingStatusEffects.umbrafliction;
            statusDuration = 160;
            lightOpacity = 0;
            width = 10;
            height = 12;
            lifetime = 35;
            hitEffect = Fx.hitFuse;
            trailLength = 0;
            homingPower = 0.125f;
            drag = 0.015f;
            bounciness = 0.95;
            absorbable = false;
        }};

        //Spawned by Trumpedoot
        //God it's such a stupid name, but it's funny aswell
        unhittableDarkShard = new BounceBulletType(6.5f, 5, "bullet"){{
            consUpdate = bullet -> {
                if(bullet.fin() % 0.04 < 0.01) Fxr.blackened.at(bullet.x, bullet.y, bullet.rotation());
            };
            despawnEffect = Fx.fireSmoke;
            hitEffect = Fx.casing3Double;
            bounceEffect = Fx.none;
            shootEffect = Fxr.blackened;
            frontColor = Color.darkGray;
            backColor = Palr.voidBullet;
            status = RustingStatusEffects.umbrafliction;
            statusDuration = 160;
            lightOpacity = 0;
            width = 15;
            height = 21;
            lifetime = 17.5f;
            hitEffect = Fx.hitFuse;
            trailLength = 0;
            homingPower = 0.125f;
            drag = 0.015f;
            bounciness = 0.95;
            absorbable = true;
            hittable = false;
            reflectable = false;
        }};

        //essentualy a coppied Stingray Shard
        horizonShard = new BounceBulletType(8, 34, "bullet"){{
            despawnEffect = Fxr.corrodedEffect;
            bounceEffect = Fx.none;
            shootEffect = Fx.none;
            frontColor = Color.white;
            backColor = Palr.lightstriken;
            status = shieldShatter;
            statusDuration = 160;
            lightOpacity = 0;
            width = 7;
            height = 6;
            trailLength = 6;
            trailWidth = 4;
            lifetime = 26.5f;
            hitEffect = Fx.hitFuse;
            drag = 0.015f;
            bounciness = 0.95;
            buildingDamageMultiplier = 0.65f;
            reflectable = false;
            hittable = false;
            absorbable = true;
        }};

        stingrayShard = new BounceBulletType(8, 28f, "bullet"){{
            despawnEffect = Fxr.corrodedEffect;
            bounceEffect = Fx.none;
            shootEffect = Fx.none;
            frontColor = Color.white;
            backColor = Palr.lightstriken;
            trailColor = Palr.lightstriken;
            status = guardiansBlight;
            statusDuration = 160;
            lightOpacity = 0;
            width = 7;
            height = 6;
            trailLength = 6;
            trailWidth = 4;
            lifetime = 15.5f;
            hitEffect = Fx.hitFuse;
            drag = 0.015f;
            bounciness = 0.95;
            buildingDamageMultiplier = 0.5f;
            reflectable = false;
            hittable = false;
            absorbable = false;
        }};

        spawnerGlassFrag = new BasicBulletType(2.5f, 5, "bullet"){{
            width = 5f;
            height = 12f;
            shrinkY = 1f;
            lifetime = 55f;
            homingPower = 0.25f;
            backColor = Color.sky;
            frontColor = Color.white;
            despawnEffect = Fx.plasticburn;
            hitEffect = Fx.plasticburn;
            shootEffect = Fx.shootBig;
            pierce = true;
            pierceCap = 2;
            drag = 0.015f;
            reflectable = false;
        }};

        spawnerGlass = new BasicBulletType(1.25f, 13, "bullet"){{
            width = 10f;
            height = 22f;
            shrinkY = 0.8f;
            lifetime = 175;
            backColor = Pal.gray;
            frontColor = Color.white;
            despawnEffect = Fx.blastExplosion;
            despawnEffect = Fx.plasticExplosionFlak;
            hitSound = Sounds.explosion;
            fragBullet = spawnerGlassFrag;
            fragBullets = 11;
            fragVelocityMin = 0.85f;
            fragLifeMin = 0.75f;
            fragLifeMax = 1.15f;
            scaleVelocity = true;
            reflectable = false;
        }};

        spawnerBulatFrag = new BasicBulletType(2.5f, 3, "bullet"){{
            width = 5f;
            height = 12f;
            shrinkY = 1f;
            lifetime = 65f;
            knockback = -1;
            homingPower = 0.25f;
            splashDamage = 5;
            splashDamageRadius = 15;
            backColor = Color.sky;
            frontColor = Pal.plastaniumBack;
            despawnEffect = Fx.plasticburn;
            hitEffect = Fx.plasticburn;
            pierce = true;
            pierceCap = 2;
            reflectable = false;
            status = StatusEffects.corroded;
            statusDuration = 5;
            drag = 0.015f;
        }};

        spawnerBulat = new BasicBulletType(1.25f, 25, "bullet"){{
            width = 10f;
            height = 22f;
            shrinkY = 0.8f;
            lifetime = 175;
            knockback = -0.75f;
            frontColor = Pal.plastaniumBack;
            despawnEffect = Fx.plasticExplosionFlak;
            hitEffect = Fxr.spawnerBulatExplosion;
            shootEffect = Fx.shootBig;
            hitSound = Sounds.explosion;
            fragBullet = spawnerBulatFrag;
            fragBullets = 9;
            fragVelocityMin = 0.85f;
            fragLifeMin = 0.75f;
            fragLifeMax = 1.15f;
            scaleVelocity = true;
            status = StatusEffects.corroded;
            statusDuration = 360;
            reflectable = false;
        }};

        mhemQuadStorm = new ConsBulletType(2.85f, 3.5f, "shell"){{

            scaleVelocity = true;
            hitShake = 1f;
            frontColor = Palr.lightstriken;
            backColor = Pal.bulletYellowBack;
            knockback = 0.8f;
            lifetime = 105f;
            width = height = 11f;
            splashDamageRadius = 35f * 0.75f;
            splashDamage = 33f;
            shootEffect = Fx.shootBig;
            trailEffect = Fx.artilleryTrail;
            trailChance = 0.15f;
            fragBullet = nummingVortex;
            fragBullets = 1;

            shrinkX = 0.15f;
            shrinkY = 0.63f;

            drag = 0.13f;
        }};

        craeQuadStorm = new ConsBulletType(1.7f, 15.5f, "large-bomb"){{

            hitShake = 1f;
            frontColor = Palr.pulseBullet;
            backColor = Color.sky;
            trailColor = Palr.pulseChargeStart;
            hitSound = Sounds.explosion;
            hitSoundVolume = 0.35f;
            knockback = 4.5f;
            lifetime = 67f;
            width = height = 11f;
            splashDamageRadius = 65f * 0.75f;
            splashDamage = 99f;
            shootEffect = Fx.shootBig;
            trailEffect = Fx.artilleryTrail;
            hitEffect = Fxr.pulseExplosion;
            trailChance = 0.15f;
            spin = 2f;
            absorbable = false;
            reflectable = false;

            shrinkX = shrinkY = -2f;
        }};

        lightfractureTitanim = new RandspriteBulletType(7.5f, 37, "endless-rusting-lightsword", 4){{
            hitEffect = Fx.hitFuse;
            knockback = 0.15f;
            pierce = true;
            pierceBuilding = true;
            pierceCap = 3;
            lifetime = 40;
            width = 11;
            height = 16f;
            shrinkY = 0;
            frontColor = Items.titanium.color;
            backColor = Color.cyan;
            trailChance = 0.15f;
            trailEffect = Fxr.skyractureTrail;
            shootEffect = Fxr.skyractureShot;
            hitEffect = Fxr.skyractureBurst;
            despawnEffect = Fxr.skyfractureDespawn;
            ammoMultiplier = 6;
        }};

        lightfractureBulat = new RandspriteBulletType(6.6f, 28, "endless-rusting-lightsword", 4){{
            hitEffect = Fx.hitFuse;
            knockback = 1.35f;
            lifetime = 45;
            width = 11;
            height = 16f;
            shrinkY = 0;
            backColor = Pal.plastaniumFront;
            frontColor = Pal.plasticSmoke;
            trailChance = 0.1f;
            trailEffect = Fx.plasticburn;
            shootEffect = Fx.plasticExplosion;
            hitEffect = Fx.none;
            hitEffect = Fx.none;
            fragBullet = spawnerBulatFrag;
            fragBullets = 2;
            shootEffect = Fx.plasticburn;
            ammoMultiplier = 9;
        }};

        melomaeShot = new LiquidBulletType(RustingLiquids.melomae){{
            pierce = true;
            reflectable = false;
            absorbable = false;
            hittable = false;
            damage = 3;
            homingPower = 0.075f;
            knockback = 0.7f;
            drag = 0.01f;
        }};

        heavyMelomaeShot = new LiquidBulletType(RustingLiquids.melomae){{
            pierce = true;
            reflectable = false;
            absorbable = false;
            hittable = false;
            damage = 9.3f;
            homingPower = 0.075f;
            lifetime = 49f;
            speed = 4f;
            knockback = 1.5f;
            puddleSize = 8f;
            orbSize = 4f;
            drag = 0.001f;
            ammoMultiplier = 0.4f;
            statusDuration = 60f * 4f;
        }};

        craeWeaver = new BounceBulletType(3, 21, "bullet"){{
            width = 15;
            height = 18;
            lifetime = 45;
            shrinkX = 1;
            shootEffect = Fx.none;
            hitEffect = Fx.hitLancer;
            despawnEffect = Fx.plasticburn;
            bounceEffect = Fx.hitFuse;
            status = RustingStatusEffects.macotagus;
            statusDuration = 1440;
            frontColor = Palr.pulseChargeStart;
            backColor = Palr.pulseBullet;
            trailColor = frontColor;
            trailEffect = Fxr.craeWeaversResidue;
            trailChance = 0.15f;
            trailLength = 8;
            trailWidth = 5;
            weaveMag = 2;
            weaveScale = 5;
            homingPower = 0.125f;
            knockback = -0.15f;
            bounciness = 0.8;
        }};

        //duplicated bullet, only to be used for duoplys
        paveWeaver = new BounceBulletType(5, 11, "bullet"){{
            width = 15;
            height = 18;
            lifetime = 42;
            healPercent = 1f;
            shrinkX = 1;
            trailLength = 8;
            collidesTeam = true;
            shootEffect = Fx.heal;
            hitEffect = Fx.plasticburn;
            despawnEffect = Fx.plasticburn;
            backColor = Pal.heal;
            frontColor = Color.white;
            trailColor = frontColor;
            trailEffect = Fx.shootHeal;
            trailChance = 0.15f;
            trailWidth = 5;
            weaveMag = 2;
            weaveScale = 5;
            homingPower = 0.325f;
            knockback = 0.25f;
            bounciness = 0.57;
        }};

        craeBolt = new LightningBulletType(){{
            damage = 15;
            lightningDamage = 35f;
            lightningLength = 12;
            lightningColor = Palr.pulseChargeStart;
            status = macrosis;
        }};

        craeBoltKill = new LightningBulletType(){{
            damage = 19.5f;
            lightningDamage = 35f;
            lightningLength = 12;
            lightningColor = Palr.pulseChargeStart;
            status = macotagus;
            killShooter = true;
        }};

        paveBolt = new LaserBoltBulletType(5.2f, 16){{
            recoil = 1.25f;
            lifetime = 15f;
            healPercent = 2f;
            collidesTeam = true;
            backColor = Pal.heal;
            frontColor = Color.white;
        }};

        //let bullet = Vars.content.bullets().find(b => b.splashDamage == 175); UnitTypes.gamma.weapons.each(w => w.bullet = bullet);

        craeNukestorm = new BounceBulletType(9, 154, "endless-rusting-glave-large"){{
            hitShake = 12;
            hitEffect = Fxr.craeNukeHit;
            splashDamage = 175;
            splashDamageRadius = 125;

            shrinkX = 0;
            shrinkY = 0;
            spin = 19;
            width = 35;
            height = 35;
            lifetime = 112;
            status = RustingStatusEffects.macotagus;
            statusDuration = 1440;
            frontColor = Palr.pulseBullet;
            backColor = Palr.lightstriken;
            trailColor = frontColor;
            trailEffect = Fxr.craeWeaverShards;
            trailChance = 1f;
            weaveScale = 5;
            pierce = false;
            pierceBuilding = false;
            scaleVelocity = true;
            trailLength = 12;
            trailWidth = 8;
            hitSound = Sounds.explosion;
            knockback = -0.15f;
            fragBullets = 7;
            fragVelocityMin = 0.85f;
            fragBullet = craeWeaver;
        }};

        craeBalistorm = new BounceBulletType(3, 654, "endless-rusting-glave-large"){{

            consHit = (b) -> {
                RustingBullets.boltingVortex.create(b.owner, b.team, b.x, b.y, b.rotation());
            };

            consDespawned = consHit;

            spin = 18;
            hitShake = 56;
            hitEffect = Fxr.craeBigNukeHit;
            splashDamage = 775;
            splashDamageRadius = 225;

            shrinkX = 0;
            shrinkY = 0;
            width = 45;
            height = 45;
            lifetime = 336;
            status = RustingStatusEffects.macotagus;
            statusDuration = 1440;
            frontColor = Palr.lightstriken;
            backColor = Palr.pulseBullet;
            trailColor = frontColor;
            trailEffect = Fxr.craeWeaverShards;
            trailChance = 1f;
            weaveScale = 5;
            pierce = false;
            pierceBuilding = false;
            scaleVelocity = true;
            collides = false;
            trailLength = 9;
            trailWidth = 12;
            hitSound = Sounds.explosionbig;
            knockback = -0.15f;
            fragBullets = 16;
            fragVelocityMin = 1.85f;
            fragVelocityMax = 3.5f;
            fragBullet = craeShard;

        }};

        denseLightRoundaboutRight =  new BoomerangBulletType(1, 8, "endless-rusting-boomerang"){{

            other = denseLightRoundaboutLeft;
            width = 16;
            height = 19;
            lifetime = 120;
            homingPower = 0.05f;
            homingRange = 45f;
            trailWidth = 0;
            trailLength = 0;
            rotateMag = 1;
            rotateVisualMag = 0.6f;
            rotScaleMin = 0f;
            rotScaleMax = 0.7f;
            rotateRight = true;
            reverseBoomerangRotScale = true;
            hitEffect = Fx.hitFuse;
            despawnEffect = Fx.smeltsmoke;
            frontColor = Palr.pulseChargeStart;
            backColor = Palr.pulseChargeEnd;
            trailEffect = Fx.plasticburn;
            status = shieldShatter;
            trailChance = 0.35f;
            drag = -0.001f;
        }};

        saltyLightRoundaboutRight = new BoomerangBulletType(2, 7, "endless-rusting-boomerang"){{

            other = saltyLightRoundaboutLeft;

            reloadMultiplier = 0.85f;
            width = 14;
            height = 13;
            lifetime = 120;
            homingPower = 0.05f;
            homingRange = 45f;
            trailWidth = 0;
            trailLength = 0;
            rotateMag = 1;
            rotateVisualMag = 0.6f;
            rotScaleMin = 0.1f;
            rotScaleMax = 1f;
            rotateRight = true;
            reverseBoomerangRotScale = true;
            hitEffect = Fx.hitFuse;
            despawnEffect = Fx.smeltsmoke;
            frontColor = Color.white;
            backColor = Palr.dustriken;
            trailEffect = Fxr.salty;
            status = hailsalilty;
            trailChance = 0.15f;
            drag = 0.008f;
            pierceCap = 1;
            fragBullet = spawnerGlassFrag;
            fragBullets = 2;
        }};

        saltyLightRoundaboutLeft = new BoomerangBulletType(2, 7, "endless-rusting-boomerang"){{

            other = saltyLightRoundaboutRight;

            reloadMultiplier = 0.85f;
            width = 14;
            height = 13;
            lifetime = 120;
            homingPower = 0.05f;
            homingRange = 45f;
            trailWidth = 0;
            trailLength = 0;
            rotateMag = 1;
            rotateVisualMag = 0.6f;
            rotScaleMin = 0.1f;
            rotScaleMax = 1f;
            rotateRight = false;
            reverseBoomerangRotScale = false;
            hitEffect = Fx.hitFuse;
            despawnEffect = Fx.smeltsmoke;
            frontColor = Color.white;
            backColor = Palr.dustriken;
            trailEffect = Fxr.salty;
            status = hailsalilty;
            trailChance = 0.15f;
            drag = 0.008f;
            pierceCap = 1;
            fragBullet = spawnerGlassFrag;
            fragBullets = 2;
        }};

        denseLightRoundaboutLeft = new BoomerangBulletType(1, 11, "endless-rusting-boomerang"){{

            other = denseLightRoundaboutRight;

            width = 16;
            height = 19;
            lifetime = 120;
            homingPower = 0.05f;
            homingRange = 45f;
            trailWidth = 0;
            trailLength = 0;
            rotateMag = 1;
            rotateVisualMag = 0.6f;
            rotScaleMin = 0f;
            rotScaleMax = 0.7f;
            rotateRight = false;
            reverseBoomerangRotScale = true;
            hitEffect = Fx.hitFuse;
            despawnEffect = Fx.smeltsmoke;
            frontColor = Palr.pulseChargeStart;
            backColor = Palr.pulseChargeEnd;
            trailEffect = Fx.plasticburn;
            status = shieldShatter;
            trailChance = 0.35f;
            drag = -0.001f;
        }};

        craeLightRoundaboutRight = new BoomerangBulletType(2, 11, "endless-rusting-boomerang"){{

            other = craeLightRoundaboutLeft;

            reloadMultiplier = 1.35f;

            width = 12;
            height = 14;
            lifetime = 150;
            homingPower = 0.05f;
            homingRange = 45f;
            rotateMag = 3;
            rotScaleMin = 0f;
            rotScaleMax = 0.7f;
            rotateRight = true;
            reverseBoomerangRotScale = true;
            hitEffect = Fx.hitFuse;
            despawnEffect = Fx.plasticburn;
            frontColor = Palr.pulseChargeStart;
            backColor = Palr.pulseChargeStart;
            trailEffect = Fxr.whoosh;
            status = shieldShatter;
            trailChance = 0.35f;
            drag = -0.001f;
        }};

        craeLightRoundaboutLeft = new BoomerangBulletType(2, 8, "endless-rusting-boomerang"){{

            other = craeLightRoundaboutRight;

            reloadMultiplier = 1.35f;

            width = 12;
            height = 14;
            lifetime = 150;
            homingPower = 0.05f;
            homingRange = 45f;
            rotateMag = 3;
            rotScaleMin = 0f;
            rotScaleMax = 0.7f;
            rotateRight = false;
            reverseBoomerangRotScale = true;
            hitEffect = Fx.hitFuse;
            despawnEffect = Fx.plasticburn;
            frontColor = Palr.pulseChargeStart;
            backColor = Palr.pulseChargeEnd;
            trailEffect = Fxr.whoosh;
            status = shieldShatter;
            trailChance = 0.35f;
            drag = -0.001f;
        }};

        craeLightGlaive = new BoomerangBulletType(1, 25, "endless-rusting-glave"){{

            homingPower = 0.025f;

            consUpdate = bullet -> {
                if(bullet.owner instanceof TurretBuild) bullet.vel.setAngle(Angles.moveToward(bullet.rotation(), bullet.angleTo(((TurretBuild) bullet.owner).targetPos.x, ((TurretBuild) bullet.owner).targetPos.y), homingPower * Time.delta * 100f));
            };

            consHit = new Cons<Bullet>() {
                @Override
                public void get(Bullet bullet) {
                    for(int i = 0; i <  5; i++) {
                        ((BoomerangBulletType) craeLightRoundaboutLeft).createBoomerang(bullet.owner, bullet.team, bullet.x, bullet.y, i * 72 + bullet.rotation(), craeLightRoundaboutLeft.damage / 2, 0.85f, 1, 0);
                        ((BoomerangBulletType) craeLightRoundaboutRight).createBoomerang(bullet.owner, bullet.team, bullet.x, bullet.y, i * 72 + bullet.rotation(), craeLightRoundaboutRight.damage / 2, 0.45f, 1, 0);
                    }
                }
            };

            consDespawned = consHit;

            shrinkX = 1.5f;
            shrinkY = 1.5f;
            width = 20;
            height = 20;
            lifetime = 150;
            pierceCap = 1;
            rotateMag = 3;
            rotScaleMin = 0f;
            rotScaleMax = 0f;
            spin = 1;
            hitEffect = Fx.hitFuse;
            despawnEffect = Fx.plasticburn;
            frontColor = Palr.pulseChargeStart;
            backColor = Palr.pulseChargeEnd;
            status = shieldShatter;
            drag = -0.001f;

        }};

        craeLightGlaiveRight = new BoomerangBulletType(2, 15, "endless-rusting-glave"){{
            other = craeLightGlaiveLeft;

            width = 9;
            height = 9;
            lifetime = 165;
            homingPower = 0.025f;
            pierceCap = -1;
            shrinkX = 0;
            shrinkY = 0;
            bounceCap = 0;
            rotateMag = 5;
            rotScaleMin = 0.2f;
            rotScaleMax = 0.2f;
            rotateRight = true;
            stayInRange = true;
            hitEffect = Fx.hitFuse;
            despawnEffect = Fx.plasticburn;
            frontColor = Palr.pulseChargeStart;
            backColor = Palr.pulseBullet;
            status = shieldShatter;
            drag = -0.001f;
        }};

        craeLightGlaiveLeft = new BoomerangBulletType(2, 15, "endless-rusting-glave"){{
            other = craeLightGlaiveRight;

            width = 9;
            height = 9;
            lifetime = 165;
            homingPower = 0.025f;
            pierceCap = -1;
            shrinkX = 0;
            shrinkY = 0;
            bounceCap = 0;
            rotateMag = 5;
            rotScaleMin = 0.2f;
            rotScaleMax = 0.2f;
            rotateRight = false;
            stayInRange = true;
            hitEffect = Fx.hitFuse;
            despawnEffect = Fx.plasticburn;
            frontColor = Palr.pulseChargeStart;
            backColor = Palr.pulseBullet;
            status = shieldShatter;
            statusDuration = 60;
            drag = -0.001f;
        }};

        saltyLightGlaive = new BoomerangBulletType(3f, 45, "endless-rusting-glave-large"){{

            consDespawned = b -> {

                Sounds.explosion.at(b.x, b.y);
                Effect.shake(4, 36, b.x, b.y);

            };

            consHit = b -> {

                if(b.collided.size == pierceCap) consDespawned.get(b);

            };
            width = 22;
            height = 22;
            lifetime = 60;
            pierceCap = 4;
            hitShake = 2;
            shrinkX = 0;
            shrinkY = 0;
            bounceUnits = false;
            drawAlpha = 0.85f;
            spin = 0.25f;
            rotateMag = 0.25f;
            rotateVisualMag = 0.65f;
            rotScaleMin = 0f;
            rotScaleMax = 0f;
            hitEffect = Fx.smoke;
            despawnEffect = Fxr.instaltSummonerExplosion;
            frontColor = RustingItems.halsinte.color;
            backColor = Palr.lightstriken;
            status = hailsalilty;
            drag = -0.001f;
            fragBullet = saltyLightRoundaboutRight;
            fragBullets = 3;
            fragVelocityMin = 3;
            fragLifeMin = 0.15f;
            fragLifeMax = 0.15f;
            fragAngle = 180;
            fragCone = 35;
            trailColor = Color.white;
            trailEffect = Fxr.salty;
            trailWidth = 4;
            trailLength = 7;
        }};

        nummingVortex = new BulletSpawnBulletType(2f, 250, "none"){{

            bullets = Seq.with(
                new BulletSpawner(){{
                    bullet = nummingInstalt;
                    reloadTime = 5.5f;
                    manualAiming = true;
                    shootSound = Sounds.artillery;
                    intervalIn = 25;
                    intervalOut = 35;
                }},
                new BulletSpawner(){{
                    bullet = darkShard;
                    reloadTime = 7.5f;
                    manualAiming = true;
                    shootSound = Sounds.artillery;
                    intervalIn = 65;
                    intervalOut = 10;
                }}
            );
            hitSound = Sounds.release;
            finalFragBullet = fraeShard;
            finalFragBullets = 1;
            fragCone = 0;
            fragLifeMin = 1;
            fragLifeMax = 1;
            fragVelocityMin = 1;
            fragVelocityMax = 1;
            frontColor = Palr.lightstriken;
            backColor = Pal.bulletYellowBack;
            despawnEffect = Fxr.instaltSummonerExplosion;
            lifetime = 128f;
            width = height = 11f;
            splashDamageRadius = 35f * 0.75f;
            splashDamage = 33f;
            shootEffect = Fx.shootBig;

            shrinkX = 0.15f;
            shrinkY = 0.63f;

            drag = 0.085f;

            scaleDrawIn = 2.5f;
            scaleDrawOut = 7f;
        }};

        cloudyVortex = new BulletSpawnBulletType(0.35f, 250, "none"){{
            bullets = Seq.with(
                new BulletSpawner(){{
                    bullet = cloudyShard;
                    reloadTime = 3.5f;
                    manualAiming = true;
                    inaccuracy = 1;
                    intervalIn = 95;
                    intervalOut = 65;
                }},
                new BulletSpawner(){{
                    bullet = cloudyShard;
                    reloadTime = 9.5f;
                    manualAiming = false;
                    idleInaccuracy = 360;
                    inaccuracy = 5;
                    intervalIn = 95;
                    intervalOut = 65;
                }}
            );
            frontColor = Palr.lightstriken;
            backColor = Palr.dustriken;
            despawnEffect = Fx.sparkShoot;
            lifetime = 315f;
            width = height = 11f;
            splashDamageRadius = 35f * 0.75f;
            splashDamage = 33f;
            shootEffect = Fx.shootBig;

            shrinkX = 0.15f;
            shrinkY = 0.63f;

            scaleDrawIn = 4;
            scaleDrawOut = 9;
            drawSize = 6;

            homingPower = 0.02f;
            drag = 0.005f;

        }};

        //anyone know where this one is from? ;)
        boltingVortex = new BulletSpawnBulletType(0.099f, 560, "none"){{

            keepVelocity = false;

            bullets = Seq.with(
                new BulletSpawner(){{
                    bullet = craeBolt;
                    reloadTime = 2.5f;
                    shootSound = Sounds.spark;
                    intervalIn = 125;
                    intervalOut = 125;
                }}
            );
            frontColor = Palr.pulseChargeStart;
            backColor = Palr.pulseBullet;
            despawnEffect = Fx.sparkShoot;
            lifetime = 405f;
            width = height = 11f;
            splashDamage = 33f;
            homingPower = 0.02f;
            homingRange = 50;
            splashDamageRadius = 35f * 0.75f;
            shootEffect = Fx.shootBig;

            shrinkX = 0.15f;
            shrinkY = 0.63f;

            scaleDrawIn = 4;
            scaleDrawOut = 9;
            drawSize = 6;
            drag = -0.01f;
        }};

        flamstrikenVortex = new BulletSpawnBulletType(0.15f, 250, "none"){{
            bullets = Seq.with(
                new BulletSpawner(){{
                    bullet = pavenShardling;
                    manualAiming = false;
                    reloadTime = 10.5f;
                    shootSound = Sounds.flame2;
                    intervalIn = 65;
                    intervalOut = 65;
                    inaccuracy = 360;
                }},
                new BulletSpawner(){{
                    bullet = pavenShardling;
                    manualAiming = false;
                    reloadTime = 16.5f;
                    shootSound = Sounds.flame2;
                    intervalIn = 65;
                    intervalOut = 65;
                    inaccuracy = 360;
                }}
            );
            frontColor = Pal.lightPyraFlame;
            backColor = Palr.darkPyraBloom;
            despawnEffect = Fx.sparkShoot;
            lifetime = 405f;
            width = height = 11f;
            splashDamageRadius = 35f * 0.75f;
            splashDamage = 33f;
            shootEffect = Fx.shootBig;

            shrinkX = 0.15f;
            shrinkY = 0.63f;

            scaleDrawIn = 4;
            scaleDrawOut = 9;
            drawSize = 6;

        }};

        gunnersVortex = new BulletSpawnBulletType(0, 125, "none"){{
            useRange = true;
            recoil = 3;
            bullets = Seq.with(
                new BulletSpawner(){{
                    bullet = horizonShard;
                    reloadTime = 25;
                    shootSound = Sounds.artillery;
                    intervalIn = 10;
                    intervalOut = 10;
                }},
                new BulletSpawner(){{
                    bullet = horizonShard;
                    reloadTime = 15;
                    shootSound = Sounds.artillery;
                    intervalIn = 10;
                    intervalOut = 10;
                }}
            );
            hitSound = Sounds.release;
            frontColor = Palr.lightstriken;
            backColor = Pal.bulletYellowBack;
            despawnEffect = Fxr.instaltSummonerExplosion;
            lifetime = 158f;
            width = height = 11f;
            splashDamageRadius = 35f * 0.75f;
            splashDamage = 33f;
            shootEffect = Fx.shootBig;
            trueSpeed = 1.25f;

            shrinkX = 0.15f;
            shrinkY = 0.63f;

            scaleDrawIn = 2.5f;
            scaleDrawOut = 7f;
            absorbable = false;
            reflectable = false;
            hittable = true;
        }};

        guardianVortex = new BulletSpawnBulletType(0, 1550, "none"){{
            consUpdate = homingOwner;
            trueSpeed = 1;

            useRange = true;
            range = 210;
            keepVelocity = false;

            bullets = Seq.with(
                new BulletSpawner(){{
                    bullet = unhittableDarkShard;
                    reloadTime = 6.5f;
                    shootSound = Sounds.none;
                    intervalIn = 125;
                    intervalOut = 125;
                    lifetimeMultiplier = 2;
                }}
            );
            frontColor = Palr.voidBullet;
            backColor = Palr.voidBullet;
            despawnEffect = Fx.sparkShoot;
            lifetime = 1200;
            width = height = 11f;
            splashDamage = 33f;
            homingPower = 0.02f;
            homingRange = 50;
            splashDamageRadius = 35f * 0.75f;
            shootEffect = Fx.shootBig;

            shrinkX = 0.15f;
            shrinkY = 0.63f;

            scaleDrawIn = 15;
            scaleDrawOut = 15;
            drawSize = 9;
            absorbable = false;
            hittable = true;
            reflectable = false;
        }};

        longThorFlame = new BulletType(4.35f, 34.5f){{
            ammoMultiplier = 6f;
            hitSize = 9f;
            lifetime = 35f;
            pierce = true;
            pierceBuilding = true;
            statusDuration = 60f * 6;
            shootEffect = Fxr.shootLongThorFlame;
            hitEffect = Fx.hitFlameSmall;
            despawnEffect = Fx.none;
            status = StatusEffects.corroded;
            hittable = false;
            reflectable = false;
        }};

        longPyraFlame = new BulletType(4.35f, 16.5f){{
            ammoMultiplier = 4f;
            hitSize = 9f;
            lifetime = 35f;
            pierce = true;
            pierceBuilding = true;
            statusDuration = 60f * 6;
            shootEffect = Fxr.shootLongPyraFlame;
            hitEffect = Fx.hitFlameSmall;
            despawnEffect = Fx.none;
            status = StatusEffects.melting;
            hittable = false;
            reflectable = false;
        }};

        cameoSmallHarpoon = new BlockHarpoonBulletType(10.15f, 32, EndlessRusting.modname + "-cameo-small-harpoon") {{
            lifetime = 28.4f;
            homingPower = 0.15f;
            width = 32;
            height = 32;
            lightning = 4;
            lightningLength = 8;
            hitSound = Sounds.spark;
        }};
    }
}
