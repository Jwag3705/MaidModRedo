package mmr.littledelicacies.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;

import java.util.UUID;

public class CommonHelper {
    public static final boolean isClient;
    public static final Minecraft mc;
    static {
        Minecraft lm = null;
        try {
            lm =  Minecraft.getInstance();// ModLoader.getMinecraftInstance();
        } catch (Exception e) {
//			e.printStackTrace();
        } catch (Error e) {
//			e.printStackTrace();
        }

        mc = lm;
        isClient = mc != null;
    }

    public static boolean isLocalPlay() {

        return isClient && mc.isIntegratedServerRunning();

    }

    public static UUID getPlayerUUID(PlayerEntity par1EntityPlayer) {
        return par1EntityPlayer.getUniqueID();
    }
}
