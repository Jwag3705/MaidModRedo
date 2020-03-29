package mmr.littledelicacies.init;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class LittleItemGroups {
    public static final ItemGroup FOODS = new ItemGroup("littledelicacies_foods") {
        @OnlyIn(Dist.CLIENT)
        public ItemStack createIcon() {
            return new ItemStack(LittleItems.BIRTHDAY_CAKE);
        }
    };
    public static final ItemGroup MISC = new ItemGroup("littledelicacies_misc") {
        @OnlyIn(Dist.CLIENT)
        public ItemStack createIcon() {
            return new ItemStack(LittleItems.BROOM);
        }
    };
    public static final ItemGroup DECOR_FUNITURE = new ItemGroup("littledelicacies_spawnegg") {
        @OnlyIn(Dist.CLIENT)
        public ItemStack createIcon() {
            return new ItemStack(LittleBlocks.PINK_AWNING);
        }
    };

    public static final ItemGroup SPAWNEGG = new ItemGroup("littledelicacies_spawnegg") {
        @OnlyIn(Dist.CLIENT)
        public ItemStack createIcon() {
            return new ItemStack(LittleItems.LITTLEMAID_SPAWNEGG);
        }
    };

    public static final ItemGroup COMBAT = new ItemGroup("littledelicacies_combat") {
        @OnlyIn(Dist.CLIENT)
        public ItemStack createIcon() {
            return new ItemStack(LittleItems.MAGE_STUFF);
        }
    };
}
