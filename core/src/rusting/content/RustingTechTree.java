package rusting.content;

import arc.struct.Seq;
import mindustry.content.*;
import mindustry.content.TechTree.TechNode;
import mindustry.ctype.ContentList;
import mindustry.ctype.UnlockableContent;
import mindustry.type.ItemStack;
import rusting.Varsr;

import static mindustry.content.Blocks.*;
import static mindustry.content.SectorPresets.groundZero;
import static mindustry.game.Objectives.*;
import static rusting.content.RustingBlocks.*;
import static rusting.content.RustingSectorPresets.*;
import static rusting.game.RustedObjectives.SettingLockedObjective;

public class RustingTechTree implements ContentList {
    static TechTree.TechNode context = null;

    @Override
    public void load(){

        extendNode(coreShard, () -> {
            node(pulseResearchCenter, Seq.with(new SectorComplete(paileanCorridors)), () -> {
                node(pulseCollector, Seq.with(new SectorComplete(abystrikenCrevasse)), () -> {

                    node(pulseNode, () -> {
                        node(pulseResonator, () -> {
                            node(pulseSiphon, () -> {

                            });
                        });


                        node(pulseUpkeeper, Seq.with(new SectorComplete(pulsatingGroves)), () -> {
                            node(smallParticleSpawner, () -> {

                            });
                        });

                        node(pulseTesla, Seq.with(new SectorComplete(pulsatingGroves)), () -> {

                        });
                    });
                    node(pulseGenerator, Seq.with(new SectorComplete(crystallineCrags), new Produce(RustingItems.melonaleum)), () -> {

                    });
                });

                node(pulseCondensery, Seq.with(new SectorComplete(crystallineCrags), new Research(paileanCorridors), new Produce(melonaleum)), () -> {});

                node(archangel, Seq.with(new SectorComplete(abystrikenCrevasse)), () -> {

                });

                node(pulseFactory, Seq.with(new SectorComplete(pulsatingGroves)), () -> {

                    node(RustingUnits.duono, () -> {
                        node(RustingUnits.duoly, () -> {
                            node(RustingUnits.duanga, Seq.with(new SectorComplete(crystallineCrags)), () -> {

                            });
                        });
                    });

                    node(pulseDistributor, () -> {

                    });

                    node(enlightenmentReconstructor, () -> {
                        node(ascendanceReconstructor, () -> {

                        });
                    });
                });

                node(pulseLandmine, () -> {

                });

                node(pulseBarrier, () -> {
                    node(pulseBarrierLarge, () -> {});
                });

                node(RustingItems.melonaleum, Seq.with(new Produce(RustingItems.melonaleum)), () -> {

                });
            });
        });

        extendNode(conveyor, () -> {
            node(terraConveyor, Seq.with(new Produce(RustingItems.taconite)), () -> {
                node(terraPulveriser, () -> {

                });
            });
        });

        extendNode(copperWall, () -> {
            node(terraMound, Seq.with(new Produce(RustingItems.taconite)), () -> {
                node(terraMoundLarge, () -> {

                });
            });
        });

        extendNode(graphitePress, () -> {
            node(bulasteltForgery, Seq.with(new SectorComplete(plantaePresevereDomae)), () -> {
                node(desalinationMixer, Seq.with(new Produce(RustingItems.halsinte), new SectorComplete(volenChannels)), () -> {
                    node(cameoCrystallisingBasin, Seq.with(new Produce(RustingItems.melonaleum), new SectorComplete(sulphuricSea)), () -> {

                    });
                });
            });
        });

        extendNode(duo, () -> {
            node(prikend, Seq.with(new SectorComplete(plantaePresevereDomae)), () -> {
                node(prsimdeome, () -> {
                    node(prefraecon, Seq.with(new SectorComplete(pulsatingGroves), new Research(RustingStatusEffects.fragmentaein)), () -> {

                    });

                    node(rangi, Seq.with(new SectorComplete(volenChannels)), () -> {

                    });
                });
            });

            node(refract, Seq.with(new SectorComplete(plantaePresevereDomae)), () -> {
                node(diffract, () -> {
                    node(reflect, () -> {

                    });
                });
            });
        });

        extendNode(scatter, () -> {
            node(octain, Seq.with(new SectorComplete(volenChannels)), () -> {
                node(triagon, Seq.with(new SectorComplete(crystallineCrags)), () -> {

                });
            });
        });

        extendNode(mender, () -> {
            node(thrum, Seq.with(new SectorComplete(plantaePresevereDomae)), () -> {
                node(spikent, Seq.with(new SectorComplete(pulsatingGroves)), () -> {

                });
            });
        });

        extendNode(Items.copper, () -> {
            nodeProduce(RustingItems.taconite, () -> {

                nodeProduce(RustingItems.halsinte, () -> {
                    nodeProduce(RustingItems.cameoShardling, () -> {

                    });
                });

                nodeProduce(RustingItems.bulastelt, () -> {

                });
            });
        });

        extendNode(Liquids.water, () -> {
            nodeProduce(RustingLiquids.melomae, Seq.with(new Produce(RustingLiquids.melomae)), () -> {

            });
        });

        extendNode(coreShard, () -> {
            node(StatusEffects.wet, Seq.with(new Research(StatusEffects.wet)), () -> {
                node(StatusEffects.freezing, Seq.with(new Research(StatusEffects.freezing)), () -> {

                });
                node(StatusEffects.burning, Seq.with(new Research(StatusEffects.burning)), () -> {
                    node(StatusEffects.melting, Seq.with(new Research(StatusEffects.melting)), () -> {

                    });
                });
                node(StatusEffects.tarred, Seq.with(new Research(StatusEffects.tarred)), () -> {

                });
                node(StatusEffects.corroded, Seq.with(new Research(StatusEffects.corroded)), () -> {

                });
                node(RustingStatusEffects.macrosis, Seq.with(new Research(RustingStatusEffects.macrosis)), () -> {
                    node(RustingStatusEffects.macotagus, Seq.with(new Research(RustingStatusEffects.macrosis), new Research(RustingStatusEffects.macrosis)), () -> {

                    });
                });
                node(RustingStatusEffects.amberstriken, Seq.with(new Research(RustingStatusEffects.amberstriken)), () -> {
                    node(RustingStatusEffects.umbrafliction, Seq.with(new Research(RustingStatusEffects.umbrafliction), new Research(RustingStatusEffects.amberstriken)), () -> {

                    });
                });
                node(RustingStatusEffects.causticBurning, Seq.with(new Research(RustingStatusEffects.causticBurning)), () -> {

                });
                node(RustingStatusEffects.shieldShatter, Seq.with(new Research(RustingStatusEffects.shieldShatter)), () -> {
                    node(RustingStatusEffects.corruptShield, Seq.with(new Research(RustingStatusEffects.shieldShatter), new Research(RustingStatusEffects.corruptShield)), () -> {

                    });
                });
                node(RustingStatusEffects.hailsalilty, Seq.with(new Research(RustingStatusEffects.hailsalilty)), () -> {

                });
                node(RustingStatusEffects.fuesin, Seq.with(new Research(RustingStatusEffects.fuesin)), () -> {

                });
            });
        });

        extendNode(groundZero,  () -> {
            node(incipiensGrounds, () -> {
                    node(preservatory, Seq.with(new SectorComplete(paileanCorridors), new Research(terraConveyor)), () -> {
                        node(hangout, Seq.with(new SettingLockedObjective("settings.er.teleporterbuilt", "Finish a relic of the old times, the Pulse Teleporter.")), () -> {

                        });
                    });

                node(plantaePresevereDomae, Seq.with(new SectorComplete(incipiensGrounds), new Research(navalFactory)), () -> {
                    node(volenChannels, Seq.with(new SectorComplete(plantaePresevereDomae), new Research(hail), new Research(lancer), new Research(UnitTypes.horizon), new Produce(Items.pyratite)), () -> {
                        node(sulphuricSea, Seq.with(new SectorComplete(volenChannels), new SectorComplete(crystallineCrags), new SectorComplete(pulsatingGroves), new Research(triagon), new Research(steamGenerator), new Research(laserDrill), new Objective() {
                            @Override
                            public boolean complete() {
                                return true;//crystallineCrags.unlockedNow();
                            }

                            @Override
                            public String display() {
                                return Varsr.username + ", defeat and destroy the bases listed above, and your final campaign challenge (For now) in the mod awaits.";
                            }
                        }), () -> {

                        });
                    });
                });

                node(paileanCorridors, Seq.with(new SectorComplete(incipiensGrounds), new Research(pneumaticDrill), new Research(itemBridge), new Produce(Items.graphite), new Produce(Items.silicon)), () -> {
                    node(abystrikenCrevasse, Seq.with(new SectorComplete(paileanCorridors), new Research(pulseResearchCenter), new Research(ripple), new Research(titaniumConveyor)), () -> {
                        node(crystallineCrags, Seq.with(new SectorComplete(abystrikenCrevasse), new Research(octain), new Research(thermalGenerator), new Produce(melonaleum)), () -> {

                        });
                    });

                    node(pulsatingGroves, Seq.with(new SectorComplete(paileanCorridors), new SectorComplete(crystallineCrags), new Research(prsimdeome), new Research(waterBoilerGenerator)), () -> {

                    });
                });
            });
        });
    }

    //sets context to the node from the UnlockableContent
    private static void extendNode(UnlockableContent parent, Runnable children){
        TechNode parnode = TechTree.all.find(t -> t.content == parent);
        context = parnode;
        children.run();
    }

    private static void node(UnlockableContent content, ItemStack[] requirements, Seq<Objective> objectives, boolean debug, Runnable children){
        if(debug && !Varsr.debug) return;
        TechNode node = new TechNode(context, content, requirements);
        if(objectives != null) node.objectives = objectives;

        TechNode prev = context;
        context = node;
        children.run();
        context = prev;
    }

    private static void node(UnlockableContent content, ItemStack[] requirements, Runnable children){
        node(content, requirements, null, false, children);
    }

    private static void node(UnlockableContent content, Seq<Objective> objectives, Runnable children){
        node(content, content.researchRequirements(), objectives, false, children);
    }

    private static void node(UnlockableContent content, Runnable children){
        node(content, content.researchRequirements(), children);
    }

    private static void node(UnlockableContent block){
        node(block, () -> {});
    }

    private static void nodeProduce(UnlockableContent content, Seq<Objective> objectives, Runnable children){
        node(content, content.researchRequirements(), objectives.and(new Produce(content)), false, children);
    }

    private static void nodeProduce(UnlockableContent content, Runnable children){
        nodeProduce(content, Seq.with(), children);
    }

    private static void nodeProduce(UnlockableContent content){
        nodeProduce(content, Seq.with(), () -> {});
    }

    private static void debugNode(UnlockableContent content, Seq<Objective> objectives, Runnable children){
        node(content, ItemStack.with(), objectives, true, children);
    }

    private static void debugNode(UnlockableContent content, ItemStack[] requirements, Runnable children){
        node(content, requirements, null, true, children);
    }

    private static void debugNode(UnlockableContent content, Runnable children){
        node(content, ItemStack.with(), null, true, children);
    }
}
