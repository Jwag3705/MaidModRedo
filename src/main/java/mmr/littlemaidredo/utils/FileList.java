package mmr.littlemaidredo.utils;

import mmr.littlemaidredo.LittleMaidRedo;
import mmr.littlemaidredo.api.classutil.FileClassUtil;
import net.minecraft.client.Minecraft;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileList {

    public static File dirMinecraft;
    public static File dirMods;
    public static File developIncludeDirMods = null;
    public static List<File> filesMods;
    public static List<File> dirClasspath = new ArrayList<>();

    public static String dirMinecraftPath = "";

    static {
        //Object[] injectionData = FMLInjectionData.data();
        dirMinecraft = (File) Minecraft.getInstance().gameDir;
        dirMinecraftPath = FileClassUtil.getLinuxAntiDotName(dirMinecraft.getAbsolutePath());
        if (dirMinecraftPath.endsWith("/")) {
            dirMinecraftPath = dirMinecraftPath.substring(0, dirMinecraftPath.lastIndexOf("/"));
        }
        dirMods = new File(dirMinecraft, "mods");

        // Check if version directory exists
        //File dirModsVersion = new File(dirMods, (String)injectionData[4]);
        //if (dirModsVersion.isDirectory()) {
        //	dirMods = dirModsVersion;
        //}

        // List 'files' up in mods
        filesMods = new ArrayList<>();
        filesMods.addAll(Arrays.asList(dirMods.listFiles()));

        for (File child :
                new ArrayList<>(filesMods)) {
            if (!child.isFile()) {
                filesMods.remove(child);
            }
        }
        
        /*if (DevMode.DEVELOPMENT_DEBUG_MODE) {
            File devFile = new File(dirMinecraft, "../mods");
            if (devFile.isDirectory()) {
                developIncludeDirMods = devFile;
            }
        }*/

        LittleMaidRedo.debug("init FileManager.");
    }
}