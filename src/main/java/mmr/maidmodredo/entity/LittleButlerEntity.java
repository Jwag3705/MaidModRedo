package mmr.maidmodredo.entity;

import mmr.maidmodredo.client.maidmodel.ModelConfigCompound;
import mmr.maidmodredo.client.maidmodel.TextureBox;
import mmr.maidmodredo.utils.ModelManager;
import net.minecraft.entity.EntityType;
import net.minecraft.world.World;

public class LittleButlerEntity extends LittleMaidBaseEntity {
    public LittleButlerEntity(EntityType<? extends LittleMaidBaseEntity> p_i48575_1_, World p_i48575_2_) {
        super(p_i48575_1_, p_i48575_2_);
        textureData = new ModelConfigCompound(this, maidCaps);
        textureData.setColor((byte) 0xc);

        TextureBox ltb[] = new TextureBox[2];

        ltb[0] = ltb[1] = ModelManager.instance.getDefaultTexture(this);

        setTexturePackName(ltb);
    }
}
