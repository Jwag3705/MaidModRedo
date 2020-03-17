package mmr.littledelicacies.maidjob;

import mmr.littledelicacies.entity.LittleMaidBaseEntity;
import mmr.littledelicacies.init.MaidJob;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;

public abstract class CasterMaidJob extends MaidJob {
    public CasterMaidJob(String nameIn, ItemStack stack) {
        super(nameIn, stack);
    }

    public abstract void castMagic(LittleMaidBaseEntity owner, Entity target);
}
