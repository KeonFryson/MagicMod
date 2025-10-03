package com.chaos.magicmod.spell;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class LightningSpell implements ISpell{
    @Override
    public ResourceLocation getIcon() {
        return ResourceLocation.fromNamespaceAndPath("magicmod", "textures/gui/spells/lightning.png");
    }

    @Override
    public String getName() {
        return "Lightning";
    }

    @Override
    public int getManaCost() {
        return 10;
    }

    @Override
    public void cast(Level level, Player player, Vec3 pos) {
        if (!level.isClientSide) {
            LightningBolt lightningBolt = EntityType.LIGHTNING_BOLT.create(level);
            if (lightningBolt != null) {
                lightningBolt.moveTo(pos.x, pos.y, pos.z);
                level.addFreshEntity(lightningBolt);
            }
        }
    }

    @Override
    public void cast(Level level, Player player, Entity target) {
        cast(level, player, target.position());
    }
}
