package com.chaos.magicmod.spell;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public interface ISpell {
    ResourceLocation getIcon();
    String getName();
    int getManaCost();
    void cast(Level level, Player player, Vec3 pos);
    void cast(Level level, Player player, Entity target);
}
