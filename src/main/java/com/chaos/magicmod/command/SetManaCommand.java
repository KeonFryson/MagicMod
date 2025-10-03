package com.chaos.magicmod.command;

import com.chaos.magicmod.mana.IMana;
import com.chaos.magicmod.mana.ModCapabilities;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

public class SetManaCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(
                Commands.literal("setmana")
                        .requires(cs -> cs.hasPermission(2)) // Requires operator level 2
                        .then(Commands.argument("player", net.minecraft.commands.arguments.EntityArgument.player())
                                .then(Commands.argument("mana", IntegerArgumentType.integer(0))
                                        .then(Commands.argument("maxMana", IntegerArgumentType.integer(1))
                                                .executes(ctx -> {
                                                    ServerPlayer player = net.minecraft.commands.arguments.EntityArgument.getPlayer(ctx, "player");
                                                    int mana = IntegerArgumentType.getInteger(ctx, "mana");
                                                    int maxMana = IntegerArgumentType.getInteger(ctx, "maxMana");

                                                    IMana manaCap = player.getCapability(ModCapabilities.MANA);
                                                    if (manaCap != null) {
                                                        manaCap.setMaxMana(maxMana);
                                                        manaCap.setMana(mana);
                                                        ctx.getSource().sendSuccess(() ->
                                                                Component.literal("Set mana for " + player.getName().getString() +
                                                                        ": " + mana + "/" + maxMana), true);
                                                        return 1;
                                                    } else {
                                                        ctx.getSource().sendFailure(Component.literal("Player does not have mana capability!"));
                                                        return 0;
                                                    }
                                                })
                                        )
                                )
                        )
        );
    }
}
