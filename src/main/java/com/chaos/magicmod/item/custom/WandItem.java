package com.chaos.magicmod.item.custom;

import com.chaos.magicmod.MagicMod;
import com.chaos.magicmod.mana.IMana;
import com.chaos.magicmod.mana.ModCapabilities;
import com.chaos.magicmod.mana.WorldManaData;
import com.chaos.magicmod.spell.DamageBeamSpell;
import com.chaos.magicmod.spell.ISpell;
import com.chaos.magicmod.spell.LightningSpell;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.NotNull;

public class WandItem extends Item {

    private final ISpell spell = new DamageBeamSpell();

    public WandItem(Properties properties) {
        super(properties);
    }

    public ISpell getSpell(){
        return spell;
    }




    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(Level pLevel, @NotNull Player pPlayer, @NotNull InteractionHand pUsedHand) {
        ItemStack stack = pPlayer.getItemInHand(pUsedHand);

        if (!pLevel.isClientSide) {

            IMana mana = pPlayer.getCapability(ModCapabilities.MANA);
            if (mana == null) {
                MagicMod.LOGGER.debug("No mana capability found for player: {}", pPlayer.getName().getString());
                return InteractionResultHolder.fail(stack);
            }
            int manaCost = spell.getManaCost();
            MagicMod.LOGGER.debug("Player {} mana before: {}/{}", pPlayer.getName().getString(), mana.getMana(), mana.getMaxMana());
            if (!mana.consume(manaCost)) {
                pPlayer.displayClientMessage(Component.literal("Not enough mana!"), true);
                MagicMod.LOGGER.debug("Player {} tried to cast but had insufficient mana: {}/{}", pPlayer.getName().getString(), mana.getMana(), mana.getMaxMana());
                return InteractionResultHolder.fail(stack);
            }
            MagicMod.LOGGER.debug("Player {} mana after: {}/{}", pPlayer.getName().getString(), mana.getMana(), mana.getMaxMana());

// Sync to WorldManaData
            if (pLevel instanceof ServerLevel serverLevel) {
                WorldManaData data = WorldManaData.get(serverLevel);
                data.setMana(pPlayer.getUUID(), mana.getMana());
            }


            // Raycast for entity
            double reach = 40.0D;
            EntityHitResult entityHitResult = raycastEntity(pLevel, pPlayer, reach);

            if (entityHitResult != null && entityHitResult.getEntity() != pPlayer) {
                spell.cast(pLevel, pPlayer, entityHitResult.getEntity());
            } else {
                // Fallback to block hit
                HitResult hitResult = pPlayer.pick(reach, 0, false);
                if (hitResult.getType() == HitResult.Type.BLOCK) {
                    BlockHitResult blockHitResult = (BlockHitResult) hitResult;
                    spell.cast(pLevel, pPlayer, blockHitResult.getLocation());
                }
            }
        }

        return InteractionResultHolder.success(stack);
    }
    // Raycast from player's eyes to hit an entity (ignoring the player)
    private EntityHitResult raycastEntity(Level level, Player player, double reach) {
        // Start and end points of the ray
        var eyePos = player.getEyePosition();
        var lookVec = player.getLookAngle();
        var endPos = eyePos.add(lookVec.x * reach, lookVec.y * reach, lookVec.z * reach);

        EntityHitResult closestResult = null;
        double closestDistance = reach;

        for (Entity entity : level.getEntities(player, player.getBoundingBox().expandTowards(lookVec.scale(reach)).inflate(1.0D))) {
            if (entity == player || !entity.isPickable()) continue;

            var aabb = entity.getBoundingBox().inflate(0.3); // Slightly larger for easier targeting
            var optionalHit = aabb.clip(eyePos, endPos);

            if (optionalHit.isPresent()) {
                double distance = eyePos.distanceTo(optionalHit.get());
                if (distance < closestDistance) {
                    closestDistance = distance;
                    closestResult = new EntityHitResult(entity, optionalHit.get());
                }
            }
        }
        return closestResult;
    }
}
