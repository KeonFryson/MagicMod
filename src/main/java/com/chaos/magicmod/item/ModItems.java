package com.chaos.magicmod.item;

import com.chaos.magicmod.MagicMod;
import com.chaos.magicmod.item.custom.WandItem;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MagicMod.MODID);

    public static final DeferredItem<Item> WAND = ITEMS.register("wand",
            ()->new WandItem(new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> BISMUTH = ITEMS.register("bismuth",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> RAW_BISMUTH = ITEMS.register("raw_bismuth",
            () -> new Item(new Item.Properties()));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
