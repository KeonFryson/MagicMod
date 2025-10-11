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

import java.util.List;

@EventBusSubscriber(modid = "magicmod", value = Dist.CLIENT)
public class SpellOverlay {
    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(
            "magicmod","textures/gui/spellbar.png");
    @SubscribeEvent
    public static void onRenderGUIOverlay(RenderGuiEvent.Pre event) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null) return;

        GuiGraphics gui = event.getGuiGraphics();

        int frameU = 23;
        int frameV = 83;
        int frameWidth = 22;
        int frameHeight = 22;

        int slotCount = 5;
        int spacing = 2;
        int screenWidth = event.getGuiGraphics().guiWidth();
        int screenHeight = event.getGuiGraphics().guiHeight();

        int x = screenWidth - frameWidth - 10;
        int totalHeight = slotCount * frameHeight + (slotCount - 1) * spacing;
        int startY = (screenHeight - totalHeight) / 2;

        for (int i = 0; i < slotCount; i++) {
            int y = startY + i * (frameHeight + spacing);
            gui.blit(TEXTURE, x, y, frameU, frameV, frameWidth, frameHeight);
        }


        // Draw spell icons if holding wand
        if (mc.player.getMainHandItem().getItem() == ModItems.WAND.get()) {
            WandItem wand = (WandItem) mc.player.getMainHandItem().getItem();
            List<ISpell> spells = wand.getSpells();
            int selected = wand.getSelectedSpellIndex();

            int iconSize = 16;
            for (int i = 0; i < slotCount && i < spells.size(); i++) {
                ISpell spell = spells.get(i);
                ResourceLocation icon = spell.getIcon();
                int slotX = x;
                int slotY = startY + i * (frameHeight + spacing) + 1;
                int iconX = slotX + (frameWidth - iconSize) / 3;
                int iconY = slotY + (frameHeight - iconSize) / 2;
                gui.blit(icon, iconX, iconY, 0, 0, iconSize, iconSize, iconSize, iconSize);

                // Highlight selected spell slot using the silver frame from the spellbar texture
                if (i == selected) {
                    int highlightU = 5;      // X position in texture
                    int highlightV = 110;    // Y position in texture
                    int highlightWidth = frameWidth;   // 22
                    int highlightHeight = frameHeight; // 22
                    int highlightX = slotX;
                    int highlightY = slotY;
                    gui.blit(TEXTURE, highlightX, highlightY, highlightU, highlightV, highlightWidth, highlightHeight);
                }
            }

            // Draw selected spell name and cost above the bar
            if (selected < spells.size()) {
                ISpell spell = spells.get(selected);
                String spellName = spell.getName();
                int spellCost = spell.getManaCost();
                int spellTextX = x - 120;
                int spellTextY = startY - 24;
                gui.drawString(mc.font, "Spell: " + spellName + " (" + spellCost + " Mana)", spellTextX, spellTextY, 0xFFD700, true);
            }
        }
    }
}