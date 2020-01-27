package mmr.littlemaidredo.init;

import net.minecraft.block.Blocks;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class LittleItemGroups {
    public static final ItemGroup LITTLEMAID = new ItemGroup("littlemaid") {
        @OnlyIn(Dist.CLIENT)
        public ItemStack createIcon() {
            return new ItemStack(LittleItems.BROOM);
        }
    };
}
