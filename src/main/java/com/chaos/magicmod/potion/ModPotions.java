package com.chaos.magicmod.potion;

import com.chaos.magicmod.MagicMod;
import com.chaos.magicmod.effect.ModEffects;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.alchemy.Potion;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModPotions {
    public static final DeferredRegister<Potion> POTIONS =
            DeferredRegister.create(BuiltInRegistries.POTION, MagicMod.MODID);

    public static final Holder<Potion> MANA_POTION = POTIONS.register("mana_potion",
            ()->new Potion(new MobEffectInstance(ModEffects.MANA_EFFECT, 200,0)));

    public static void register(IEventBus eventBus){
        POTIONS.register(eventBus);
    }
}
