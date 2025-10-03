package com.chaos.magicmod.spell;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class DamageBeamSpell implements ISpell{
    @Override
    public ResourceLocation getIcon() {
        return ResourceLocation.fromNamespaceAndPath("magicmod", "textures/gui/spells/beam.png");
    }

    @Override
    public String getName() {
        return "Arcane Beam";
    }

    @Override
    public int getManaCost() {
        return 20;
    }

    @Override
    public void cast(Level level, Player player, Vec3 pos) {
        if (level.isClientSide) return;

        Vec3 start = player.getEyePosition();
        Vec3 direction = player.getLookAngle();
        double maxDistance = 20.0;
        Vec3 end = start.add(direction.scale(maxDistance));

        Entity hitEntity = null;
        double closest = maxDistance;

        // Raycast for entity
        for (Entity entity : level.getEntities(player, player.getBoundingBox().expandTowards(direction.scale(maxDistance)).inflate(1.0D))) {
            if (entity == player || !entity.isPickable()) continue;
            var aabb = entity.getBoundingBox().inflate(0.3);
            var optionalHit = aabb.clip(start, end);
            if (optionalHit.isPresent()) {
                double distance = start.distanceTo(optionalHit.get());
                if (distance < closest) {
                    closest = distance;
                    hitEntity = entity;
                    end = optionalHit.get();
                }
            }
        }

        // Spawn particles along the beam
        if (level instanceof ServerLevel serverLevel) {
            int steps = 40;
            Vec3 step = end.subtract(start).scale(1.0 / steps);
            for (int i = 0; i < steps; i++) {
                Vec3 pos1 = start.add(step.scale(i));
                serverLevel.sendParticles(
                        net.minecraft.core.particles.ParticleTypes.END_ROD,
                        pos1.x, pos1.y, pos1.z,
                        1, 0, 0, 0, 0
                );
            }
        }

        // Damage entity if hit
        if (hitEntity != null) {
            hitEntity.hurt(level.damageSources().magic(), 10.0F);
        }
    }

    @Override
    public void cast(Level level, Player player, Entity target) {
        cast(level, player, target.position());
    }
}
