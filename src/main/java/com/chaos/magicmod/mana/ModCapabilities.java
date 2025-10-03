package com.chaos.magicmod.mana;

import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.capabilities.EntityCapability;

public class ModCapabilities {
    public static  final EntityCapability<IMana, Void> MANA =
            EntityCapability.create(
                    ResourceLocation.fromNamespaceAndPath("magicmod", "mana"),
                    IMana.class,
                    Void.class
            );
}
