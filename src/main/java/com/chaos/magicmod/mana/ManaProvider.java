package com.chaos.magicmod.mana;

import com.chaos.magicmod.MagicMod;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.capabilities.ICapabilityProvider;

public class ManaProvider implements ICapabilityProvider<Player,Void, IMana> {
    private final IMana instance = new Mana();

    @Override
    public IMana getCapability(Player player, Void ctx) {
        return instance; // same instance for THIS player
    }
    public CompoundTag serializeNBT() {
        return instance.serializeNBT();
    }

    public void deserializeNBT(CompoundTag nbt) {
        instance.deserializeNBT(nbt);
    }

}
