package com.chaos.magicmod.mana;

import com.chaos.magicmod.MagicMod;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

@EventBusSubscriber(modid = "magicmod")
public class PlayerManaEvents {

    @SubscribeEvent
    public static void onPlayerClone(PlayerEvent.Clone event) {
        Player oldPlayer = event.getOriginal();
        Player newPlayer = event.getEntity();

        IMana oldMana = oldPlayer.getCapability(ModCapabilities.MANA);
        IMana newMana = newPlayer.getCapability(ModCapabilities.MANA);

        MagicMod.LOGGER.debug("Player clone: death={}", event.isWasDeath());

        if (oldMana != null && newMana != null) {
            if (event.isWasDeath()) {
                // Copy on death
                newMana.setMana(oldMana.getMana());
                newMana.setMaxMana(oldMana.getMaxMana());
                MagicMod.LOGGER.debug("Copied mana {}/{} to respawned player",
                        oldMana.getMana(), oldMana.getMaxMana());
            } else {
                // Fresh login/new world → reset to defaults
                newMana.setMaxMana(100);
                newMana.setMana(100);
                MagicMod.LOGGER.debug("New world or login → reset to defaults {}/{}",
                        newMana.getMana(), newMana.getMaxMana());
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        Player player = event.getEntity();
        IMana mana = player.getCapability(ModCapabilities.MANA);
        if (mana != null && player instanceof ServerPlayer serverPlayer) {
            CompoundTag persistentData = serverPlayer.getPersistentData();
            if (persistentData.contains("Mana") && persistentData.contains("MaxMana")) {
                mana.setMana(persistentData.getInt("Mana"));
                mana.setMaxMana(persistentData.getInt("MaxMana"));
                MagicMod.LOGGER.debug("Loaded mana {}/{} for player {}", mana.getMana(), mana.getMaxMana(), player.getName().getString());
            } else {
                // If not loaded, set to max
                mana.setMaxMana(100);
                mana.setMana(100);
                MagicMod.LOGGER.debug("No saved mana found, set to default {}/{} for player {}", mana.getMana(), mana.getMaxMana(), player.getName().getString());
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerLoggedOut(PlayerEvent.PlayerLoggedOutEvent event) {
        Player player = event.getEntity();
        IMana mana = player.getCapability(ModCapabilities.MANA);
        if (mana != null && player instanceof ServerPlayer serverPlayer) {
            CompoundTag persistentData = serverPlayer.getPersistentData();
            persistentData.putInt("Mana", mana.getMana());
            persistentData.putInt("MaxMana", mana.getMaxMana());
            MagicMod.LOGGER.debug("Saved mana {}/{} for player {}", mana.getMana(), mana.getMaxMana(), player.getName().getString());
        }
    }

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        Player player = event.getEntity();
        IMana mana = player.getCapability(ModCapabilities.MANA);
        if (mana != null && !player.level().isClientSide) {
            // Regenerate mana every 5 seconds (400 ticks)
            if (player.tickCount % 100 == 0) {
                if (mana.getMana() < mana.getMaxMana()) {
                    mana.addMana(5);
                }
            }
        }
    }







}
