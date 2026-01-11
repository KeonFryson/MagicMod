package com.chaos.magicmod.client.screens;

import com.chaos.magicmod.item.custom.WandItem;
import com.chaos.magicmod.spell.DamageBeamSpell;
import com.chaos.magicmod.spell.ISpell;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class WandSpellScreen extends Screen {
    private final ItemStack wandStack;
    private final WandItem wandItem;
    private List<ISpell> spells;

    public WandSpellScreen(ItemStack wandStack) {
        super(Component.literal("Wand Spell Management"));
        this.wandStack = wandStack;
        this.wandItem = (WandItem) wandStack.getItem();
        this.spells = wandItem.getSpells();
    }

    @Override
    protected void init() {
        this.clearWidgets();
        int y = 40;

        // Remove spell buttons
        for (int i = 0; i < spells.size(); i++) {
            ISpell spell = spells.get(i);
            this.addRenderableWidget(Button.builder(
                            Component.literal("Remove " + spell.getName()),
                            btn -> {
                                wandItem.removeSpell(spell);
                                wandItem.serializeNBT();
                                this.spells = wandItem.getSpells();
                                this.init();
                            })
                    .pos(this.width / 2 - 100, y)
                    .size(120, 20)
                    .build()
            );
            y += 24;
        }

        // Add spell button (example: DamageBeamSpell)
        this.addRenderableWidget(Button.builder(
                        Component.literal("Add Arcane Beam"),
                        btn -> {
                            wandItem.addSpell(new DamageBeamSpell());
                            wandItem.serializeNBT();
                            this.spells = wandItem.getSpells();
                            this.init();
                        })
                .pos(this.width / 2 + 20, 40)
                .size(120, 20)
                .build()
        );

        // You can add more buttons for other spells here
    }

    @Override
    public void render(GuiGraphics gui, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(gui, mouseX, mouseY, partialTicks);
        gui.drawCenteredString(this.font, this.title.getString(), this.width / 2, 15, 0xFFFFFF);
        super.render(gui, mouseX, mouseY, partialTicks);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}