package com.chaos.magicmod.client.screens;

import com.chaos.magicmod.item.ModItems;
import com.chaos.magicmod.item.custom.WandItem;
import com.chaos.magicmod.spell.ISpell;
import net.minecraft.ResourceLocationException;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderGuiEvent;

@EventBusSubscriber(modid = "magicmod", value = Dist.CLIENT)
public class SpellOverlay {
    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(
            "magicmod","textures/gui/spellbar.png");
    @SubscribeEvent
    public static void onRenderGUIOverlay(RenderGuiEvent.Pre event) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null) return;

        GuiGraphics gui = event.getGuiGraphics();

        // Slot frame coordinates in the texture (white frame)
        int frameU = 23;
        int frameV = 83;
        int frameWidth = 22;
        int frameHeight = 22;

        int slotCount = 5;
        int spacing = 2;
        int screenWidth = event.getGuiGraphics().guiWidth();
        int screenHeight = event.getGuiGraphics().guiHeight();

        // Right side, vertically centered
        int x = screenWidth - frameWidth - 10;
        int totalHeight = slotCount * frameHeight + (slotCount - 1) * spacing;
        int startY = (screenHeight - totalHeight) / 2;



        for (int i = 0; i < slotCount; i++) {
            int y = startY + i * (frameHeight + spacing);
            gui.blit(TEXTURE, x, y, frameU, frameV, frameWidth, frameHeight);
        }

        // Draw spell icon in first slot if holding wand
        if (mc.player.getMainHandItem().getItem() == ModItems.WAND.get()) {
            WandItem wand = (WandItem) mc.player.getMainHandItem().getItem();
            ISpell spell = wand.getSpell();
            ResourceLocation icon = spell.getIcon();
            int iconSize = 16;
            int slotX = x;
            int slotY = startY + 1;
            int iconX = slotX + (frameWidth - iconSize) / 3;
            int iconY = slotY +  (frameHeight - iconSize) / 2 ;
            gui.blit(icon, iconX, iconY, 0, 0, iconSize, iconSize, iconSize, iconSize);

            // Optionally, draw spell name and cost above the bar
            String spellName = spell.getName();
            int spellCost = spell.getManaCost();
            int spellTextX = x - 120;
            int spellTextY = startY - 24;
            gui.drawString(mc.font, "Spell: " + spellName + " (" + spellCost + " Mana)", spellTextX, spellTextY, 0xFFD700, true);
        }
    }
}
