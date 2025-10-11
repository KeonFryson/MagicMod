package com.chaos.magicmod.item;

import com.chaos.magicmod.MagicMod;
import com.chaos.magicmod.block.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TAB =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MagicMod.MODID);

    public static final Supplier<CreativeModeTab> MAGIC_MOD = CREATIVE_MODE_TAB.register("magic_mod_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.WAND.get()))
                    .title(Component.translatable("creativetab.magicmod.magic_mod"))
                    .displayItems((itemDisplayParameters, output) -> {
                        output.accept(ModItems.WAND);
                        output.accept(ModItems.RAW_BISMUTH);
                        output.accept(ModItems.BISMUTH);
                        output.accept(ModBlocks.BISMUTH_BLOCK);
                        output.accept(ModBlocks.BISMUTH_ORE);
                    }).build());




    public static void register(IEventBus eventBus){
        CREATIVE_MODE_TAB.register(eventBus);
    }
}
