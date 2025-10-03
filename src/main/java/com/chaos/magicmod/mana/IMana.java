package com.chaos.magicmod.mana;

import net.minecraft.nbt.CompoundTag;

public interface IMana {
    int getMana();
    int getMaxMana();
    void setMana(int mana);
    void setMaxMana(int maxMana);
    void addMana(int amount);
    boolean consume(int amount);

    // --- Correct serialization methods for entity capabilities ---
    CompoundTag serializeNBT();
    void deserializeNBT(CompoundTag tag);
}
