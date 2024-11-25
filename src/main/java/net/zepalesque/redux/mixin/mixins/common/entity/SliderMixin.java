package net.zepalesque.redux.mixin.mixins.common.entity;

import com.aetherteam.aether.block.AetherBlocks;
import com.aetherteam.aether.entity.monster.dungeon.boss.Slider;
import com.google.common.collect.ImmutableMap;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.util.Lazy;
import net.zepalesque.redux.block.ReduxBlocks;
import net.zepalesque.redux.blockset.stone.ReduxStoneSets;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Map;

@Mixin(Slider.class)
public class SliderMixin {

    @Unique
    private static final Lazy<Map<Block, Block>> REDUX$CONVERSIONS = Lazy.of(() -> new ImmutableMap.Builder<Block, Block>()
            .put(ReduxBlocks.LOCKED_CARVED_BASE.get(), ReduxBlocks.CARVED_BASE.get())
            .put(ReduxBlocks.LOCKED_SENTRY_BASE.get(), ReduxBlocks.SENTRY_BASE.get())
            .put(ReduxBlocks.LOCKED_CARVED_PILLAR.get(), ReduxBlocks.CARVED_PILLAR.get())
            .put(ReduxBlocks.LOCKED_RUNELIGHT.get(), ReduxBlocks.RUNELIGHT.get())
            .put(ReduxBlocks.LOCKED_SENTRITE_BRICKS.get(), ReduxStoneSets.SENTRITE_BRICKS.block().get())
            .build()
    );

    // TODO: use mod's system when it is fixed
    @Inject(method = "convertBlock", at = @At("HEAD"), cancellable = true, remap = false)
    protected void redux$convertBlock(BlockState state, CallbackInfoReturnable<BlockState> cir) {
        if (REDUX$CONVERSIONS.get().containsKey(state.getBlock())) {
            cir.setReturnValue(REDUX$CONVERSIONS.get().get(state.getBlock()).withPropertiesOf(state));
        }
    }
}
