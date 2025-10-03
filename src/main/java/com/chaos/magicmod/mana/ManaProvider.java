package com.chaos.magicmod.mana;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.capabilities.ICapabilityProvider;

public class ManaProvider implements ICapabilityProvider<Player,Void, IMana> {
    private  final  Mana mana = new Mana();

    @Override
    public IMana getCapability(Player player, Void ctx) {
        return mana;
    }

    public void write(IMana instance, CompoundTag tag) {
        tag.merge(instance.serializeNBT());
    }

    public void read(IMana instance, CompoundTag tag) {
        instance.deserializeNBT(tag);
    }
}
