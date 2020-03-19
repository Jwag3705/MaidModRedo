package mmr.littledelicacies.client.resource;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import mmr.littledelicacies.LittleDelicacies;
import net.minecraft.resources.IResourcePack;
import net.minecraft.resources.ResourcePackType;
import net.minecraft.resources.data.IMetadataSectionSerializer;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OldZipTexturesWrapper implements IResourcePack {

    public static ArrayList<String> keys = new ArrayList<String>();
    public static String[] searchKeys = new String[]{};
    protected static String defNames[] = {
            "mob_littlemaid0.png", "mob_littlemaid1.png",
            "mob_littlemaid2.png", "mob_littlemaid3.png",
            "mob_littlemaid4.png", "mob_littlemaid5.png",
            "mob_littlemaid6.png", "mob_littlemaid7.png",
            "mob_littlemaid8.png", "mob_littlemaid9.png",
            "mob_littlemaida.png", "mob_littlemaidb.png",
            "mob_littlemaidc.png", "mob_littlemaidd.png",
            "mob_littlemaide.png", "mob_littlemaidf.png",
            "mob_littlemaidw.png",
            "mob_littlemaid_a00.png", "mob_littlemaid_a01.png"
    };

    @Override
    public InputStream getRootResourceStream(String fileName) throws IOException {
        ResourceLocation location = new ResourceLocation(fileName);
        if (resourceExists(ResourcePackType.CLIENT_RESOURCES, location)) {
            String key = texturesResourcePath(location);

            key = containsKey(key);
            return LittleDelicacies.class.getClassLoader().getResourceAsStream(key);
        }
        return null;
    }

    public InputStream getInputStream(ResourcePackType type, ResourceLocation arg0) throws IOException {
        if (resourceExists(type, arg0)) {
            String key = texturesResourcePath(arg0);
            key = containsKey(key);
            return LittleDelicacies.class.getClassLoader().getResourceAsStream(key);
        }
        return null;
    }

    @Override
    public InputStream getResourceStream(ResourcePackType type, ResourceLocation location) throws IOException {
        return this.getInputStream(type, location);
    }

    @Override
    public Collection<ResourceLocation> getAllResourceLocations(ResourcePackType type, String namespaceIn, String pathIn, int maxDepthIn, Predicate<String> filterIn) {
        List<ResourceLocation> list = Lists.newArrayList();

        for (String key : keys) {
            ResourceLocation location = addTextureName(key, searchKeys);
            if (location != null) {
                list.add(location);
            }
        }

        Collections.sort(list);
        return list;
    }

    protected ResourceLocation addTextureName(String fname, String[] pSearch) {
        // パッケージにテクスチャを登録
        if (!fname.startsWith("/")) {
            fname = (new StringBuilder()).append("/").append(fname).toString();
        }

//		LittleMaidRedo.LOGGER.debug("MMM_TextureManager.addTextureName : %s # %s # %s # %s", fname, pSearch[0], pSearch[1], pSearch[2]);
        if (fname.toLowerCase().startsWith(pSearch[1].toLowerCase())) {
            int i = fname.lastIndexOf("/");
            if (pSearch[1].length() < i) {
                String pn = fname.substring(pSearch[1].length(), i);
                pn = pn.replace('/', '.');
                String fn = fname.substring(i);
                int lindex = getIndex(fn);
                if (lindex > -1) {
                    //lts.addTexture(lindex, fname);
                    if (fname.contains("littledelicacies")) {
                        return addTexture(lindex, "littledelicacies:" + fname);
                    } else {
                        return addTexture(lindex, fname);
                    }
                }
            }
        }
        return null;
    }

    public ResourceLocation addTexture(int pIndex, String pLocation) {
        String ls = "/assets/minecraft/";
        String ls2 = "/assets/littledelicacies/";
        if (pLocation.startsWith(ls)) {
            pLocation = pLocation.substring(ls.length());
        } else if (pLocation.contains(ls2)) {
            pLocation = pLocation.substring("littledelicacies:".length() + ls2.length());

            pLocation = "littledelicacies:" + pLocation;

            ls = "/assets/littledelicacies/";
        }

        return new ResourceLocation(pLocation);

    }

    protected int getIndex(String name) {
        // 名前からインデックスを取り出す
        for (int i = 0; i < defNames.length; i++) {
            if (name.endsWith(defNames[i])) {
                return i;
            }
        }

        Pattern p = Pattern.compile("_([0-9a-f]+).png");
        Matcher m = p.matcher(name);
        if (m.find()) {
            return Integer.decode("0x" + m.group(1));
        }

        return -1;
    }

    @Nullable
    @Override
    public <T> T getMetadata(IMetadataSectionSerializer<T> deserializer) throws IOException {
        return null;
    }


    @Override
    public String getName() {
        return "OldTexturesLoader";
    }

    @Override
    public Set<String> getResourceNamespaces(ResourcePackType type) {
        return ImmutableSet.of("minecraft");
    }

    @Override
    public boolean resourceExists(ResourcePackType type, ResourceLocation arg0) {

        String key = texturesResourcePath(arg0);

        return containsKey(key) == null ? false : true;
    }

    /**
     * change to texturePack resource path
     * @param path
     * @return
     */
    public String texturesResourcePath(ResourceLocation path) {

        String key = path.getPath();
        if(key.startsWith("/")) key = key.substring(1);

        //旧式用の判定処理
        if (key.toLowerCase().startsWith("mob/modelmulti")
                || key.toLowerCase().startsWith("mob/littlemaid") || key.toLowerCase().startsWith("mob/littlebutler")) {
            //old type is not change
        } else {
            key = "assets/minecraft/" + key;
        }

        return key;
    }

    /**
     * テクスチャリストの中に対象テクスチャが含まれるかチェックする
     * 大文字小文字は区別しない
     * @param path
     * @return
     */
    public String containsKey(String path) {

        String ret = null;

        for (String key : keys) {
            if (key.toLowerCase().equals(path.toLowerCase())) {
                ret = key;
                break;
            }
        }

        return ret;
    }

    @Override
    public void close() throws IOException {

    }
}