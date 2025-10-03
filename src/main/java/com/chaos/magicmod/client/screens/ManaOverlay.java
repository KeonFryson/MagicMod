package com.chaos.magicmod.client.screens;
import com.chaos.magicmod.mana.IMana;
import com.chaos.magicmod.mana.Mana;
import com.chaos.magicmod.mana.ModCapabilities;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderGuiEvent;

@EventBusSubscriber(modid ="magicmod", value = net.neoforged.api.distmarker.Dist.CLIENT)
public class ManaOverlay {
    @SubscribeEvent
    public static void onRenderGUIOverlay(RenderGuiEvent.Pre event){
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null) return;

        IMana mana = mc.player.getCapability(ModCapabilities.MANA);
        if(mana == null)return;

        GuiGraphics gui = event.getGuiGraphics();

        String text = "Mana: " + mana.getMana() + "/" + mana.getMaxMana();
        gui.drawString(mc.font, Component.literal(text), 5, 5, 0x00BFFF, true);
    }
}
