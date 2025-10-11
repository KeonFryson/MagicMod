package com.chaos.magicmod.effect;

import com.chaos.magicmod.mana.IMana;
import com.chaos.magicmod.mana.Mana;
import com.chaos.magicmod.mana.ModCapabilities;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class ManaEffect extends MobEffect {
    public ManaEffect(MobEffectCategory category, int color) {
        super(category, color);
    }


    @Override
    public boolean applyEffectTick(LivingEntity livingEntity, int amplifier) {
        IMana mana = livingEntity.getCapability(ModCapabilities.MANA);
        if(mana != null){
            int amount = 1 + amplifier * 2;
            mana.addMana(amount);
        }
        return super.applyEffectTick(livingEntity, amplifier);
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return true;
    }
}
