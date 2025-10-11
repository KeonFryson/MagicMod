package com.chaos.magicmod.client;

import com.chaos.magicmod.item.ModItems;
import com.chaos.magicmod.item.custom.WandItem;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.InputEvent;
import org.lwjgl.glfw.GLFW;

@EventBusSubscriber(modid = "magicmod", value = Dist.CLIENT)
public class KeyBindings {
    public static final KeyMapping CYCLE_SPELL = new KeyMapping(
            "key.magicmod.cycle_spell",
            GLFW.GLFW_KEY_R,
            "key.categories.magicmod"
    );

    @SubscribeEvent
    public static void onKeyInput(InputEvent.Key event) {
        if (CYCLE_SPELL.consumeClick()) {
            Minecraft mc = Minecraft.getInstance();
            LocalPlayer player = mc.player;
            if (player != null && player.getMainHandItem().getItem() == ModItems.WAND.get()) {
                WandItem wand = (WandItem) player.getMainHandItem().getItem();
                wand.cycleSpell();
            }
        }
    }
}