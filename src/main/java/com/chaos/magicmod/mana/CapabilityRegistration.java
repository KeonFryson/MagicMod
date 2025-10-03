package com.chaos.magicmod.mana;


import net.minecraft.world.entity.EntityType;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.minecraft.nbt.CompoundTag;

@EventBusSubscriber(modid = "magicmod")
public class CapabilityRegistration {

    @SubscribeEvent
    public static void onRegisterCapabilities(RegisterCapabilitiesEvent event){
        event.registerEntity(
                ModCapabilities.MANA,
                EntityType.PLAYER,
                new ManaProvider()
        );
    }

}
