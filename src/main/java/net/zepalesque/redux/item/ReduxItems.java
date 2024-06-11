package net.zepalesque.redux.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemNameBlockItem;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.block.ReduxBlocks;

public class ReduxItems {

    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Redux.MODID);

    public static final DeferredItem<Item> WYND_OATS = ITEMS.register("wynd_oats", () -> new ItemNameBlockItem(ReduxBlocks.WYNDOATS.get(), new Item.Properties().food(ReduxFoods.WYND_OATS)));
    public static final DeferredItem<Item> WYND_OAT_PANICLE = ITEMS.register("wynd_oat_panicle", () -> new Item(new Item.Properties()));

}
