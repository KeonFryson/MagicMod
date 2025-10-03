package com.chaos.magicmod.mana;

import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

@EventBusSubscriber(modid = "magicmod")
public class PlayerManaEvents {

    @SubscribeEvent
    public static void onPlayerClone(PlayerEvent.Clone event){
        if(!event.isWasDeath()) return;

        Player oldPlayer = event.getOriginal();
        Player newPlayer = event.getEntity();

        IMana oldMana = oldPlayer.getCapability(ModCapabilities.MANA);
        IMana newMana = newPlayer.getCapability(ModCapabilities.MANA);

        if(oldMana != null && newMana != null){
            newMana.setMana(oldMana.getMana());
            newMana.setMaxMana(oldMana.getMaxMana());
        }
    }



}
