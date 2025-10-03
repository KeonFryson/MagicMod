package com.chaos.magicmod.mana;

import com.chaos.magicmod.MagicMod;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.neoforged.neoforge.common.util.INBTSerializable;


public class Mana implements IMana, INBTSerializable{
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
        MagicMod.LOGGER.debug("Setting mana: {} -> {}", this.mana, mana);
        this.mana = Math.max(0, Math.min(mana, maxMana));
    }

    @Override
    public void setMaxMana(int maxMana) {
        MagicMod.LOGGER.debug("Setting max mana: {} -> {}", this.maxMana, maxMana);
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

        CompoundTag tag = new CompoundTag();
        tag.putInt("Mana", mana);
        tag.putInt("MaxMana", maxMana);
        MagicMod.LOGGER.debug("Serializing mana: {}/{}", mana, maxMana);
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag tag) {
        this.mana = tag.getInt("Mana");
        this.maxMana = tag.getInt("MaxMana");
        MagicMod.LOGGER.debug("Deserialized mana: {}/{}", mana, maxMana);
    }

    @Override
    public Tag serializeNBT(HolderLookup.Provider provider) {
        return null;
    }

    @Override
    public void deserializeNBT(HolderLookup.Provider provider, Tag nbt) {

    }
}