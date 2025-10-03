package com.chaos.magicmod.item.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.NotNull;

public class WandItem extends Item {
    public WandItem(Properties properties) {
        super(properties);
    }
    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(Level pLevel, @NotNull Player pPlayer, @NotNull InteractionHand pUsedHand) {
        ItemStack stack = pPlayer.getItemInHand(pUsedHand);

        if (!pLevel.isClientSide) {

            HitResult hitResult = pPlayer.pick(40, 0, false);

            if (hitResult.getType() == HitResult.Type.BLOCK) {
                BlockHitResult blockHitResult = (BlockHitResult) hitResult;
                BlockPos pos = blockHitResult.getBlockPos();

                LightningBolt lightningBolt = EntityType.LIGHTNING_BOLT.create(pLevel);
                if (lightningBolt != null) {
                    lightningBolt.moveTo(pos.getX(), pos.getY(), pos.getZ());
                    pLevel.addFreshEntity(lightningBolt);
                }
            }
        }

        return InteractionResultHolder.success(stack);
    }
}
