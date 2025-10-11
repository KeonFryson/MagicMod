package com.chaos.magicmod.effect;

import com.chaos.magicmod.MagicMod;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModEffects {
    public static final DeferredRegister<MobEffect> MOB_EFFECT =
            DeferredRegister.create(BuiltInRegistries.MOB_EFFECT, MagicMod.MODID);


    public static final Holder<MobEffect> MANA_EFFECT = MOB_EFFECT.register("mana",
            ()->new ManaEffect(MobEffectCategory.NEUTRAL,0x0000FF));


    public static void register(IEventBus eventBus){
        MOB_EFFECT.register(eventBus);
    }
}
