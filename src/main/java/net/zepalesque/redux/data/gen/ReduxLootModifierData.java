package net.zepalesque.redux.data.gen;

import com.aetherteam.aether.loot.AetherLoot;
import com.aetherteam.nitrogen.loot.modifiers.AddDungeonLootModifier;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.util.random.WeightedEntry;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.neoforged.neoforge.common.loot.LootTableIdCondition;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.block.ReduxBlocks;
import net.zepalesque.redux.config.ReduxConfig;
import net.zepalesque.redux.data.prov.ReduxLootModifierProvider;
import net.zepalesque.redux.item.ReduxItems;
import net.zepalesque.redux.loot.modifer.RawOreModifier;
import net.zepalesque.zenith.api.condition.ConfigCondition;
import net.zepalesque.zenith.loot.condition.ConditionLootModule;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ReduxLootModifierData extends ReduxLootModifierProvider {
    public ReduxLootModifierData(PackOutput output, CompletableFuture<HolderLookup.Provider> lookup) {
        super(output, Redux.MODID, lookup);
    }

    @Override
    protected void start() {

        HolderLookup.RegistryLookup<Enchantment> registrylookup = this.registries.lookupOrThrow(Registries.ENCHANTMENT);
        this.add("raw_veridium", new RawOreModifier(ReduxBlocks.VERIDIUM_ORE.get().asItem(), new ItemStack(ReduxItems.RAW_VERIDIUM.get()),
                new LootItemFunction[] {
                        SetItemCountFunction.setCount(ConstantValue.exactly(1.0F)).build(),
                        ApplyBonusCount.addOreBonusCount(registrylookup.getOrThrow(Enchantments.FORTUNE)).build()
                },
                new LootItemCondition[] {
                        new ConditionLootModule(new ConfigCondition(ReduxConfig.SERVER.serializerID(), ReduxConfig.SERVER.raw_ores)),
                        hasSilkTouch().invert().build(),
                        LootItemBlockStatePropertyCondition.hasBlockStateProperties(ReduxBlocks.VERIDIUM_ORE.get()).build()}));

        this.add("aerbound_cape", new AddDungeonLootModifier(
                new LootItemCondition[] {
                        LootTableIdCondition.builder(AetherLoot.BRONZE_DUNGEON_REWARD.location())/*.or(LootTableIdCondition.builder(AncientAetherLoot.CHESTS_DUNGEON_BRONZE_DUNGEON_REWARD))*/.build(),
                        LootItemRandomChanceCondition.randomChance(0.75F).build()
                },
                List.of(
                        WeightedEntry.wrap(new ItemStack(ReduxItems.AERBOUND_CAPE.get()), 1)
                ),
                ConstantInt.of(1)
        ));

    }


}
