package work.microhand.frameratecontrol.common.network;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;
import work.microhand.frameratecontrol.FrameRateControl;

/**
 * @author SanseYooyea
 */
public class Networking {
    public static SimpleChannel INSTANCE;
    public static final String VERSION = "1.0";
    private static int ID = 0;

    public static int nextID() {
        return ID++;
    }

    public static void registerMessage() {
        INSTANCE = NetworkRegistry.newSimpleChannel(
                new ResourceLocation(FrameRateControl.MOD_ID, "frameratecontrol"),
                () -> VERSION,
                (version) -> version.equals(VERSION),
                (version) -> version.equals(VERSION)
        );
        INSTANCE.messageBuilder(PacketFrameRate.class, nextID())
                .encoder(PacketFrameRate::toBytes)
                .decoder(PacketFrameRate::new)
                .consumer(PacketFrameRate::handler)
                .add();
    }
}
