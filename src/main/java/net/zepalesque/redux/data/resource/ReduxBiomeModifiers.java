package net.zepalesque.redux.data.resource;

import com.aetherteam.aether.AetherTags;
import com.aetherteam.aether.data.resources.registries.AetherBiomes;
import com.aetherteam.aether_genesis.data.resources.registries.GenesisBiomes;
import com.google.common.collect.ImmutableMap;
import net.builderdog.ancient_aether.AncientAether;
import net.builderdog.ancient_aether.AncientAetherTags;
import net.builderdog.ancient_aether.data.resources.registries.AncientAetherBiomes;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ForgeBiomeModifiers;
import net.minecraftforge.registries.ForgeRegistries;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.api.condition.Conditions;
import net.zepalesque.redux.api.predicate.MusicPredicate;
import net.zepalesque.redux.client.audio.ReduxMusic;
import net.zepalesque.redux.data.resource.biome.registry.ReduxBiomes;
import net.zepalesque.redux.entity.ReduxEntityTypes;
import net.zepalesque.redux.misc.ReduxTags;
import net.zepalesque.redux.world.biome.modifier.*;
import teamrazor.deepaether.world.biomes.DABiomes;

import java.util.List;
import java.util.Optional;

public class ReduxBiomeModifiers {
    public static String FEATURE = "feature/";
    public static String MOB = "mob/";
    public static String MODIFY = "modify/";
    public static String AETHER_GRASS_COLOR = "aether_grass_colors/";
    public static String GRASS_COLOR_BASE = "grass_color_base/";
    public static String OVERWORLD_AETHER_GRASS = "overworld_aether_grass/";

    public static final ResourceKey<BiomeModifier> ADD_AETHER_CAVES = createKey(FEATURE + "aether_caves");
    public static final ResourceKey<BiomeModifier> ADD_BLIGHTED_CAVES = createKey(FEATURE + "blighted_caves");
    public static final ResourceKey<BiomeModifier> ADD_FUNGAL_CAVES = createKey(FEATURE + "fungal_caves");
    public static final ResourceKey<BiomeModifier> ADD_MOSSY_HOLYSTONE_ORE = createKey(FEATURE + "mossy_holystone_ore");
    public static final ResourceKey<BiomeModifier> ADD_MOSSY_ROCKS = createKey(FEATURE + "mossy_rocks");
    public static final ResourceKey<BiomeModifier> ADD_WYNDSPROUTS = createKey(FEATURE + "wyndsprouts");
    public static final ResourceKey<BiomeModifier> ADD_GENESIS_SPROUTS = createKey(FEATURE + "genesis_sprouts");
    public static final ResourceKey<BiomeModifier> ADD_VANILLA_SWET = createKey(MOB + "vanilla_swet");
    public static final ResourceKey<BiomeModifier> ADD_ENDERMAN = createKey(MOB + "enderman");
    public static final ResourceKey<BiomeModifier> ADD_VERIDIUM = createKey(FEATURE + "veridium_ore");
    public static final ResourceKey<BiomeModifier> ADD_DIVINITE = createKey(FEATURE + "divinite");
    public static final ResourceKey<BiomeModifier> ADD_SENTRITE = createKey(FEATURE + "sentrite");
    public static final ResourceKey<BiomeModifier> ADD_SNOW = createKey(FEATURE + "snow");
    public static final ResourceKey<BiomeModifier> WATER_COLOR_AETHER = createKey(MODIFY + "water_color");
    public static final ResourceKey<BiomeModifier> SKY_COLOR_AETHER = createKey(MODIFY + "alt_sky_color");
    public static final ResourceKey<BiomeModifier> VANILLA_GRASS_OVERRIDE = createKey(MODIFY + "aether_color_override");
    public static final ResourceKey<BiomeModifier> MUSIC_MODIFY = createKey(MODIFY + "music_modify");
    public static final ResourceKey<BiomeModifier> AETHER_GRASS_COLORS = createKey(MODIFY + "aether_grass_colors");

    private static ResourceKey<BiomeModifier> createKey(String name) {
        return ResourceKey.create(ForgeRegistries.Keys.BIOME_MODIFIERS, Redux.locate(name));
    }

    public static void bootstrap(BootstapContext<BiomeModifier> context) {
        HolderGetter<Biome> biomes = context.lookup(Registries.BIOME);
        HolderGetter<ConfiguredWorldCarver<?>> carvers = context.lookup(Registries.CONFIGURED_CARVER);
        HolderGetter<PlacedFeature> features = context.lookup(Registries.PLACED_FEATURE);
        context.register(ADD_AETHER_CAVES, new CarverModifier(biomes.getOrThrow(ReduxTags.Biomes.HAS_AETHER_CAVES), carvers.getOrThrow(ReduxConfiguredCarvers.AETHER_CAVES)));

        context.register(ADD_BLIGHTED_CAVES, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(ReduxTags.Biomes.HAS_BLIGHTED_CAVES), HolderSet.direct(features.getOrThrow(ReduxPlacements.BLIGHTMOSS_SPARSE_VEGETATION)),
                GenerationStep.Decoration.VEGETAL_DECORATION));

        context.register(ADD_FUNGAL_CAVES, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(ReduxTags.Biomes.HAS_FUNGAL_CAVES), HolderSet.direct(features.getOrThrow(ReduxPlacements.FUNGAL_SPARSE_VEGETATION)),
                GenerationStep.Decoration.VEGETAL_DECORATION));

        context.register(ADD_MOSSY_HOLYSTONE_ORE, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(ReduxTags.Biomes.HAS_MOSSY_HOLYSTONE_ORE), HolderSet.direct(features.getOrThrow(ReduxPlacements.MOSSY_HOLYSTONE_ORE)),
                GenerationStep.Decoration.UNDERGROUND_ORES));

        context.register(ADD_MOSSY_ROCKS, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(ReduxTags.Biomes.HAS_MOSSY_ROCKS), HolderSet.direct(features.getOrThrow(ReduxPlacements.MOSSY_ROCK)),
                GenerationStep.Decoration.TOP_LAYER_MODIFICATION));

        context.register(ADD_SNOW, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(AetherTags.Biomes.IS_AETHER), HolderSet.direct(features.getOrThrow(ReduxPlacements.AETHER_SNOW_LAYER)),
                GenerationStep.Decoration.TOP_LAYER_MODIFICATION));

        context.register(ADD_WYNDSPROUTS, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(ReduxTags.Biomes.HAS_WYNDSPROUTS), HolderSet.direct(features.getOrThrow(ReduxPlacements.WYNDSPROUTS_PATCH)),
                GenerationStep.Decoration.VEGETAL_DECORATION));

        context.register(ADD_GENESIS_SPROUTS, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(ReduxTags.Biomes.HAS_BOTH_SPROUTS), HolderSet.direct(features.getOrThrow(ReduxPlacements.GENESIS_SKYSPROUTS_PATCH), features.getOrThrow(ReduxPlacements.GENESIS_WYNDSPROUTS_PATCH)),
                GenerationStep.Decoration.VEGETAL_DECORATION));

        context.register(ADD_VANILLA_SWET, new ReduxSpawnsModifier(
                biomes.getOrThrow(ReduxTags.Biomes.HAS_VANILLA_SWET), List.of(new MobSpawnSettings.SpawnerData(ReduxEntityTypes.VANILLA_SWET.get(), 5, 1, 2)), Conditions.VANILLA_SWETS, 0.5, 0.1));

        context.register(ADD_ENDERMAN, new ReduxSpawnsModifier(
                biomes.getOrThrow(ReduxTags.Biomes.HAS_ENDERMAN), List.of(new MobSpawnSettings.SpawnerData(EntityType.ENDERMAN, 1, 1, 4)), Conditions.ENDERMEN, 1.0, 0.12));

        context.register(ADD_VERIDIUM, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(ReduxTags.Biomes.HAS_VERIDIUM_ORE), HolderSet.direct(features.getOrThrow(ReduxPlacements.VERIDIUM_ORE)),
                GenerationStep.Decoration.UNDERGROUND_ORES));

        context.register(ADD_DIVINITE, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(ReduxTags.Biomes.HAS_DIVINITE), HolderSet.direct(features.getOrThrow(ReduxPlacements.DIVINITE_ORE)),
                GenerationStep.Decoration.UNDERGROUND_ORES));

        context.register(ADD_SENTRITE, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(ReduxTags.Biomes.HAS_SENTRITE), HolderSet.direct(features.getOrThrow(ReduxPlacements.SENTRITE_ORE)),
                GenerationStep.Decoration.UNDERGROUND_ORES));

        BiomeModifier water = new WaterModifier(
                Optional.of(new WaterModifier.DefaultWaterSettings(biomes.getOrThrow(ReduxTags.Biomes.MODIFY_WATER_COLOR), Optional.of(0x85BDD1), Optional.of(0x182226))),
                ImmutableMap.<ResourceKey<Biome>, Integer>builder() // water
                        .put(AetherBiomes.SKYROOT_MEADOW, 0x91C8D8)
                        .put(AetherBiomes.SKYROOT_FOREST, 0x79A8C4)
                        .put(AetherBiomes.SKYROOT_WOODLAND, 0x6A94B5)
                        .put(ReduxBiomes.GILDED_GROVES, 0x89C1C6)
                        .build(),
                ImmutableMap.<ResourceKey<Biome>, Integer>builder() // fog
                        .put(AetherBiomes.SKYROOT_MEADOW, 0x1B2528)
                        .put(AetherBiomes.SKYROOT_FOREST, 0x141C21)
                        .put(AetherBiomes.SKYROOT_WOODLAND, 0x10171C)
                        .put(ReduxBiomes.GILDED_GROVES, 0x1E2A2B)
                        .build());
        context.register(WATER_COLOR_AETHER, new ConditionalBiomeModifier(Holder.direct(water), Conditions.WATER));

        BiomeModifier sky = new SkiesModifier(
                Optional.of(new SkiesModifier.DefaultSkySettings(biomes.getOrThrow(ReduxTags.Biomes.HAS_REDUX_SKY_COLOR), Optional.of(0x9FA4DD), Optional.of(0xBEC4E5))),
                ImmutableMap.<ResourceKey<Biome>, Integer>builder() // sky
                        .put(ReduxBiomes.THE_BLIGHT, 0x9994D1)
                        .put(ReduxBiomes.FROSTED_FORESTS, 0xB3B3E5)
                        .put(ReduxBiomes.GLACIAL_TUNDRA, 0xB3B3E5)
                        .put(ReduxBiomes.SKYROOT_SHRUBLANDS, 0xACC9E6)
                        .put(ReduxBiomes.SKYFIELDS, 0xACBAE6)
                        .put(ReduxBiomes.GILDED_GROVES, 0xC4BDAA)
                        .put(ReduxBiomes.GILDED_GRASSLANDS, 0xC4BDAA)
                        .build(),
                ImmutableMap.<ResourceKey<Biome>, Integer>builder() // fog
                        .put(ReduxBiomes.THE_BLIGHT, 0xC0B1DB)
                        .put(ReduxBiomes.FROSTED_FORESTS, 0xD0D2E5)
                        .put(ReduxBiomes.GLACIAL_TUNDRA, 0xD0D2E5)
                        .put(ReduxBiomes.SKYROOT_SHRUBLANDS, 0xCEDDEB)
                        .put(ReduxBiomes.SKYFIELDS, 0xCED5EB)
                        .put(ReduxBiomes.GILDED_GROVES, 0xDDD9DA)
                        .put(ReduxBiomes.GILDED_GRASSLANDS, 0xDDD9DA)
                        .build());
        context.register(SKY_COLOR_AETHER, new ConditionalBiomeModifier(Holder.direct(sky), Conditions.SKY));

        // TODO: Look into, maybe somehow convert the map so it has a fake map with something like a TagEntry, and then it computes the actual map on creation (alternatively on fire, only if it is null) In fact, maybe even a builder wrapper class to add them easier
        BiomeModifier vanillaGrass = new FoliageModifier(
                Optional.of(new FoliageModifier.DefaultFoliageSettings(biomes.getOrThrow(ReduxTags.Biomes.CHANGE_VANILLA_GRASS_COLORS), Optional.of(0x91BD59), Optional.of(0x77AB2F))),
                ImmutableMap.<ResourceKey<Biome>, Integer>builder() // grass
                        .build(),
                ImmutableMap.<ResourceKey<Biome>, Integer>builder() // foliage
                        .build());
        context.register(VANILLA_GRASS_OVERRIDE, vanillaGrass);

        context.register(MUSIC_MODIFY, new MusicModifier(biomes.getOrThrow(ReduxTags.Biomes.MODIFY_MUSIC),
                new MusicModifier.MusicOperator(Optional.empty(), Optional.of(ReduxMusic.MUSIC_MIN), Optional.of(ReduxMusic.MUSIC_MAX), Optional.empty()),
                Optional.of(new MusicPredicate(Optional.empty(), Optional.of(List.of(12000)), Optional.of(List.of(24000)), Optional.empty()))));



        BiomeModifier aetherGrass = new AetherGrassColors(ImmutableMap.<ResourceKey<Biome>, Integer>builder()
                .put(AetherBiomes.SKYROOT_FOREST, 0xA2F2BC)
                .put(AetherBiomes.SKYROOT_WOODLAND, 0x96E8B0)
                .put(AetherBiomes.SKYROOT_MEADOW, 0xBAFFCB)
                .put(ReduxBiomes.THE_BLIGHT, ReduxBiomes.BLIGHT_GRASS_COLOR)
                .put(ReduxBiomes.FROSTED_FORESTS, ReduxBiomes.FROSTED_GRASS_COLOR)
                .put(ReduxBiomes.GLACIAL_TUNDRA, ReduxBiomes.FROSTED_GRASS_COLOR)
                .put(ReduxBiomes.GILDED_GROVES, ReduxBiomes.GILDED_GRASS_COLOR)
                .put(ReduxBiomes.GILDED_GRASSLANDS, ReduxBiomes.GILDED_GRASSLANDS_COLOR)
                .put(ReduxBiomes.SKYFIELDS, ReduxBiomes.SKYFIELDS_GRASS_COLOR)
                .put(ReduxBiomes.CLOUDCAPS, ReduxBiomes.CLOUDCAP_GRASS_COLOR)
                .put(ReduxBiomes.SKYROOT_SHRUBLANDS, ReduxBiomes.SHRUBLANDS_GRASS_COLOR)
                .put(AncientAetherBiomes.WYNDCAP_HIGHLAND, ReduxBiomes.FROZEN_GRASS_COLOR)
                .put(AncientAetherBiomes.WYNDCAP_TAIGA, ReduxBiomes.FROZEN_GRASS_COLOR)
                .put(AncientAetherBiomes.FESTIVE_WYNDCAP_TAIGA, ReduxBiomes.FROZEN_GRASS_COLOR)
                .put(AncientAetherBiomes.WYNDCAP_PEAKS, ReduxBiomes.FROZEN_GRASS_COLOR)
                .put(AncientAetherBiomes.FROZEN_CAVERNS, ReduxBiomes.FROZEN_GRASS_COLOR)
                .put(AncientAetherBiomes.ELEVATED_FOREST, ReduxBiomes.PALE_GRASS_COLOR)
                .put(AncientAetherBiomes.ELEVATED_CLEARING, ReduxBiomes.PALE_GRASS_COLOR)
                .put(AncientAetherBiomes.ELEVATED_CAVERNS, ReduxBiomes.PALE_GRASS_COLOR)
                .put(DABiomes.AERGLOW_FOREST, ReduxBiomes.AERGLOW_GRASS_COLOR)
                .put(DABiomes.AERLAVENDER_FIELDS, ReduxBiomes.AERLAVENDER_GRASS_COLOR)
                .put(DABiomes.BLUE_AERGLOW_FOREST, ReduxBiomes.BLUE_AERGLOW_GRASS_COLOR)
                .put(DABiomes.GOLDEN_GROVE, ReduxBiomes.GOLDEN_GRASS_COLOR)
                .put(DABiomes.GOLDEN_HEIGHTS, ReduxBiomes.GOLDEN_GRASS_COLOR)
                .put(DABiomes.MYSTIC_AERGLOW_FOREST, ReduxBiomes.MYSTIC_AERGLOW_GRASS_COLOR)
                .put(GenesisBiomes.VIBRANT_FOREST, 0xA2F2BC)
                .put(GenesisBiomes.VIBRANT_WOODLAND, 0x96E8B0)
                .put(GenesisBiomes.VIBRANT_MEADOW, 0xBAFFCB)
                .build());
        context.register(AETHER_GRASS_COLORS, aetherGrass);
    }
}
