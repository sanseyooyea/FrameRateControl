package work.microhand.frameratecontrol.common.network;

import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.function.Supplier;

/**
 * @author SanseYooyea
 */
public class PacketFrameRate {
    private final int framerate;
    private static final Logger LOGGER = LogManager.getLogger();

    public PacketFrameRate(PacketBuffer buffer) {
        framerate = buffer.readInt();
    }

    public PacketFrameRate(int framerate) {
        this.framerate = framerate;
    }

    public void toBytes(PacketBuffer buf) {
        buf.writeInt(this.framerate);
    }

    public void handler(Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> {
            Minecraft.getInstance().options.framerateLimit = this.framerate;
            Minecraft.getInstance().options.enableVsync = false;
            Minecraft.getInstance().options.save();
            Minecraft.getInstance().getWindow().setFramerateLimit(this.framerate);
        });
        context.get().setPacketHandled(true);
    }
}
