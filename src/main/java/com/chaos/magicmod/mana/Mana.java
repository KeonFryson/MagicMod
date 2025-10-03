package com.chaos.magicmod.mana;

import com.chaos.magicmod.MagicMod;
import net.minecraft.nbt.CompoundTag;
import net.neoforged.neoforge.common.util.INBTSerializable;


public class Mana implements IMana {
    private int mana;
    private int maxMana;

    public Mana() {
        this.maxMana = 100;
        this.mana = maxMana;
        //MagicMod.LOGGER.debug("Mana capability constructed: {}/{}", this.mana, this.maxMana);
    }

    @Override
    public int getMana() {
        return mana;
    }

    @Override
    public int getMaxMana() {
        return maxMana;
    }

    @Override
    public void setMana(int mana) {
        this.mana = Math.max(0, Math.min(mana, maxMana));
    }

    @Override
    public void setMaxMana(int maxMana) {
        this.maxMana = maxMana;
        if (this.mana > maxMana) {
            this.mana = maxMana;
        }
    }

    @Override
    public void addMana(int amount) {
        setMana(this.mana + amount);
    }

    @Override
    public boolean consume(int amount) {
        if (this.mana >= amount) {
            setMana(this.mana - amount);
            return true;
        }
        return false;
    }

    @Override
    public CompoundTag serializeNBT() {
        MagicMod.LOGGER.debug("Serializing Mana: " + mana + "/" + maxMana);
        CompoundTag tag = new CompoundTag();
        tag.putInt("Mana", mana);
        tag.putInt("MaxMana", maxMana);
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag tag) {
        if (tag.contains("Mana")) {
            this.mana = tag.getInt("Mana");
        } else {
            this.mana = this.maxMana; // default if missing
        }
        if (tag.contains("MaxMana")) {
            this.maxMana = tag.getInt("MaxMana");
        } else {
            this.maxMana = 100; // default if missing
        }
    }
}