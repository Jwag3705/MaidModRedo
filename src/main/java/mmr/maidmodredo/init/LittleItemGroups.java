package mmr.maidmodredo.init;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class LittleItemGroups {
    public static final ItemGroup FOODS = new ItemGroup("maidmodredo_foods") {
        @OnlyIn(Dist.CLIENT)
        public ItemStack createIcon() {
            return new ItemStack(LittleItems.BIRTHDAY_CAKE);
        }
    };
    public static final ItemGroup MISC = new ItemGroup("maidmodredo_misc") {
        @OnlyIn(Dist.CLIENT)
        public ItemStack createIcon() {
            return new ItemStack(LittleItems.BROOM);
        }
    };
}
