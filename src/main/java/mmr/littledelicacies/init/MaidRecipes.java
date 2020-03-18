package mmr.littledelicacies.init;

import mmr.littledelicacies.utils.recipes.BaristaRecipes;
import mmr.littledelicacies.utils.recipes.ScienceRecipes;
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

        ScienceRecipes.addRecipe(new ScienceRecipes(
                new ItemStack[]{new ItemStack(LittleItems.SUGAR_TOTEM, 1)},
                new ItemStack[]{
                        new ItemStack(Items.SUGAR),
                        new ItemStack(Items.GHAST_TEAR),
                        new ItemStack(Items.GOLD_NUGGET)
                }).setProbability(0.99F));

    }
}
