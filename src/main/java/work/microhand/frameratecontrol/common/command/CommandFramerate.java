package work.microhand.frameratecontrol.common.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.fml.network.PacketDistributor;
import work.microhand.frameratecontrol.common.network.Networking;
import work.microhand.frameratecontrol.common.network.PacketFrameRate;

/**
 * @author SanseYooyea
 */
public class CommandFramerate {
    public static void register(CommandDispatcher<CommandSource> dispatcher) {
        dispatcher.register(Commands.literal("framerate")
                .requires(source -> source.hasPermission(2))
                .then(Commands.literal("framerate")
                        .then(Commands.argument("player", EntityArgument.player())
                                .then(Commands.argument("framerate", IntegerArgumentType.integer(0, 2000))
                                        .executes(context -> {
                                            ServerPlayerEntity player = EntityArgument.getPlayer(context, "player");
                                            int framerate = IntegerArgumentType.getInteger(context, "framerate");
                                            setFramerate(player, framerate);
                                            return 0;
                                        })))));
    }

    private static void setFramerate(ServerPlayerEntity player, int framerate) {
        Networking.INSTANCE.send(
                PacketDistributor.PLAYER.with(
                        () -> player
                ),
                new PacketFrameRate(framerate));
    }

}
