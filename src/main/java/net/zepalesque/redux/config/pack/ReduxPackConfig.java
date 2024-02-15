package net.zepalesque.redux.config.pack;

import net.minecraft.SharedConstants;
import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.metadata.pack.PackMetadataSection;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.api.Conditional;
import net.zepalesque.redux.api.packconfig.Category;
import net.zepalesque.redux.api.packconfig.PackConfig;
import net.zepalesque.redux.builtin.BuiltinPackUtils;
import net.zepalesque.redux.client.resource.ReduxOverridesPackResources;
import net.zepalesque.redux.config.enums.pack.*;

import java.util.List;

public class ReduxPackConfig {


    public final Category base;

    public final PackConfig<Boolean> better_aechor_plants;
    public final PackConfig<Boolean> blighted_cockatrices;
    public final PackConfig<SwetBallType> swet_ball_type;
    public final PackConfig<VeridiumType> veridium_type;
    public final PackConfig<Boolean> use_jappafied_textures;
    public final PackConfig<Boolean> menu_panorama;

    public final PackConfig<Boolean> auto_apply;

    public final PackConfig<Boolean> better_aechor_sounds;
    public final PackConfig<Boolean> better_aerwhale_sounds;
    public final PackConfig<Boolean> better_cockatrice_sounds;
    public final PackConfig<Boolean> better_mimic_awaken_sound;
    public final PackConfig<Boolean> better_moa_sounds;
    public final PackConfig<Boolean> better_sentry_sounds;
    public final PackConfig<Boolean> better_slider_sounds;
    public final PackConfig<Boolean> better_tempest_sounds;

    public final PackConfig<Boolean> tintable_grass;

    public ReduxPackConfig(Category base) {
        this.base = base;
        PackConfig.Builder builder = base.builder();
        builder.push("mob");
        // Self-explanatory
        this.better_aechor_plants = builder.comment().cfg("better_aechor_plants", true);
        // Purple redux cockatrices
        this.blighted_cockatrices = builder.comment().cfg("blighted_cockatrices", true);
        builder.pop();
        // Various sound improvements
        builder.push("audio");
        this.better_aechor_sounds = builder.comment().cfg("better_aechor_sounds", true);
        this.better_aerwhale_sounds = builder.comment().cfg("better_aerwhale_sounds", true);
        this.better_cockatrice_sounds = builder.comment().cfg("better_cockatrice_sounds", true);
        this.better_mimic_awaken_sound = builder.comment().cfg("better_mimic_awaken_sound", true);
        this.better_moa_sounds = builder.comment().cfg("better_moa_sounds", true);
        this.better_sentry_sounds = builder.comment().cfg("better_sentry_sounds", true);
        this.better_slider_sounds = builder.comment().cfg("better_slider_sounds", true);
        this.better_tempest_sounds = builder.comment().cfg("better_tempest_sounds", true);
        builder.pop();
        builder.push("item");
        // Which behavior for swet balls to use
        this.swet_ball_type = builder.comment().cfg("swet_ball_type", SwetBallType.gel, SwetBallType.MAPPER);
        this.veridium_type = builder.comment().cfg("veridium_type", VeridiumType.modern, VeridiumType.MAPPER);
        builder.pop();
        builder.push("gui");
        // Use Redux menu panorama
        this.menu_panorama = builder.comment().cfg("menu_panorama", true);
        builder.pop();
        // Jappafied Aethers compat
        this.use_jappafied_textures = builder.comment().cfg("use_jappafied_textures", false);
        // Auto-apply the resource pack
        this.auto_apply = builder.comment().cfg("auto_apply", true);
        // Allows for tinting grass along with some flower stems
        this.tintable_grass = builder.comment().cfg("tintable_grass", true);
    }

    public static ReduxOverridesPackResources generate(String id, String title, Component desc) {
        ReduxPackConfig config = Redux.packConfig;
        List<Conditional<PackResources>> packs = List.of();
        if (config != null) {
            packs = List.of(
                    Conditional.of(BuiltinPackUtils.createPack("resource/tintable_grass"), config.tintable_grass),
                    Conditional.of(BuiltinPackUtils.createPack("resource/mob/better_aechor_plants"), config.better_aechor_plants),
                    Conditional.of(BuiltinPackUtils.createPack("resource/mob/blighted_cockatrices"), config.blighted_cockatrices::get),
                    Conditional.of(BuiltinPackUtils.createPack("resource/item/swet_ball"), () -> config.swet_ball_type.get() == SwetBallType.consistent_name),
                    Conditional.of(BuiltinPackUtils.createPack("resource/item/swet_gel"), () -> config.swet_ball_type.get() == SwetBallType.gel),
                    Conditional.of(BuiltinPackUtils.createPack("resource/gui/menu_panorama"), config.menu_panorama),
                    Conditional.of(BuiltinPackUtils.createPack("resource/jappafied_textures"), config.use_jappafied_textures),
                    Conditional.of(BuiltinPackUtils.createPack("resource/item/genesis_jelly"), Redux::aetherGenesisCompat),
                    Conditional.of(BuiltinPackUtils.createPack("resource/item/shadow_veridium"), () -> config.veridium_type.get() == VeridiumType.shadow),
                    Conditional.of(BuiltinPackUtils.createPack("resource/item/classic_veridium"), () -> config.veridium_type.get() == VeridiumType.classic),
                    Conditional.of(BuiltinPackUtils.createPack("resource/sounds/aechor"), config.better_aechor_sounds),
                    Conditional.of(BuiltinPackUtils.createPack("resource/sounds/aerwhale"), config.better_aerwhale_sounds),
                    Conditional.of(BuiltinPackUtils.createPack("resource/sounds/cockatrice"), config.better_cockatrice_sounds),
                    Conditional.of(BuiltinPackUtils.createPack("resource/sounds/mimic_awaken"), config.better_mimic_awaken_sound),
                    Conditional.of(BuiltinPackUtils.createPack("resource/sounds/moa"), config.better_moa_sounds),
                    Conditional.of(BuiltinPackUtils.createPack("resource/sounds/sentry"), config.better_sentry_sounds),
                    Conditional.of(BuiltinPackUtils.createPack("resource/sounds/slider"), config.better_slider_sounds),
                    Conditional.of(BuiltinPackUtils.createPack("resource/sounds/tempest"), config.better_tempest_sounds)
            );
        }
        return new ReduxOverridesPackResources(id, true, new PackMetadataSection(
                desc, SharedConstants.getCurrentVersion().getPackVersion(PackType.CLIENT_RESOURCES)), packs);

    }
}
