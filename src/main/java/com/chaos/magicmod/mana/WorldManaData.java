package com.chaos.magicmod.mana;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.saveddata.SavedData;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class WorldManaData extends SavedData {
    private final Map<UUID, Integer> manaMap = new HashMap<>();
    // Explicit no-arg constructor for new/empty instances (initializes defaults if needed)
    public WorldManaData() {
        // No initialization needed beyond the empty HashMap; defaults are handled in getMana()
    }
    public static WorldManaData get(ServerLevel serverLevel) {
        SavedData.Factory<WorldManaData> factory = new SavedData.Factory<>(
                WorldManaData::new,  // Supplier for new/empty instance
                WorldManaData::load  // Now matches BiFunction<CompoundTag, HolderLookup.Provider, WorldManaData>
        );
        return serverLevel.getDataStorage().computeIfAbsent(factory, "magicmod_man");
    }
    public int getMana(UUID playerId) {
        return manaMap.getOrDefault(playerId, 100); // default mana
    }
    public void setMana(UUID playerId, int mana) {
        manaMap.put(playerId, mana);
        setDirty();
    }
    @Override
    public CompoundTag save(CompoundTag tag, HolderLookup.@NotNull Provider registries) {
        ListTag list = new ListTag();
        for (Map.Entry<UUID, Integer> entry : manaMap.entrySet()) {
            CompoundTag playerTag = new CompoundTag();
            playerTag.putUUID("UUID", entry.getKey());
            playerTag.putInt("Mana", entry.getValue());
            list.add(playerTag);
        }
        tag.put("ManaMap", list);
        return tag;
    }
    // Updated to match SavedData.Factory<T> for 1.21 (BiFunction<CompoundTag, HolderLookup.Provider, T>)
    public static WorldManaData load(CompoundTag tag, HolderLookup.@NotNull Provider provider) {
        WorldManaData data = new WorldManaData();
        ListTag list = tag.getList("ManaMap", Tag.TAG_COMPOUND);
        for (Tag t : list) {
            CompoundTag playerTag = (CompoundTag) t;
            UUID uuid = playerTag.getUUID("UUID");
            int mana = playerTag.getInt("Mana");
            data.manaMap.put(uuid, mana);
        }
        return data;

    }
}
