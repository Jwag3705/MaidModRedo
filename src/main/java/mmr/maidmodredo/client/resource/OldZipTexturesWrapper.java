package mmr.maidmodredo.client.resource;

import com.google.common.collect.ImmutableSet;
import mmr.maidmodredo.MaidModRedo;
import net.minecraft.resources.IResourcePack;
import net.minecraft.resources.ResourcePackType;
import net.minecraft.resources.data.IMetadataSectionSerializer;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;
import java.util.function.Predicate;

public class OldZipTexturesWrapper implements IResourcePack {

    public static ArrayList<String> keys = new ArrayList<String>();

    @Override
    public InputStream getRootResourceStream(String fileName) throws IOException {
        return null;
    }

    @Override
    public InputStream getResourceStream(ResourcePackType type, ResourceLocation location) throws IOException {
        if(resourceExists(type,location)){
            String key = texturesResourcePath(location);
            key = containsKey(key);
            return MaidModRedo.class.getClassLoader().getResourceAsStream(key);
        }
        return null;
    }

    @Override
    public Collection<ResourceLocation> getAllResourceLocations(ResourcePackType type, String pathIn, int maxDepth, Predicate<String> filter) {
        return null;
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
                || key.toLowerCase().startsWith("mob/littlemaid")) {
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