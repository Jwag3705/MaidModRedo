package mmr.maidmodredo.init;

import mmr.maidmodredo.utils.recipes.BaristaRecipes;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class MaidRecipes {

    public static void registerRecipe() {
        BaristaRecipes.addRecipe(new BaristaRecipes(
                new ItemStack[]{new ItemStack(LittleItems.APPLE_JUICE, 1)},
                new ItemStack[]{
                        new ItemStack(Items.APPLE),
                        new ItemStack(Items.GLASS_BOTTLE)
                })

        );
    }
}
