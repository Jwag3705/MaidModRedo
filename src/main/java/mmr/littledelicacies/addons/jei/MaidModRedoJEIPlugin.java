package mmr.littledelicacies.addons.jei;


import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import mmr.littledelicacies.LittleDelicacies;
import mmr.littledelicacies.utils.recipes.BaristaRecipes;
import mmr.littledelicacies.utils.recipes.ScienceRecipes;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import java.util.Collection;
import java.util.stream.Collectors;

@JeiPlugin
public class MaidModRedoJEIPlugin implements IModPlugin {

    @Override
    public ResourceLocation getPluginUid() {

        return new ResourceLocation(LittleDelicacies.MODID, "jei");
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {

        registration.addRecipeCatalyst(new ItemStack(Blocks.SMOOTH_STONE), CategoryScience.ID);
        registration.addRecipeCatalyst(new ItemStack(Blocks.SMOOTH_STONE_SLAB), CategoryScience.ID);
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {

        final Collection<BaristaRecipes> barista = BaristaRecipes.baristaRecipesList;
        final Collection<ScienceRecipes> science = ScienceRecipes.scienceRecipeList;
        registration.addRecipes(barista.stream().map(BaristaWrapper::new).collect(Collectors.toList()), CategoryBarista.ID);
        registration.addRecipes(science.stream().map(ScienceWrapper::new).collect(Collectors.toList()), CategoryScience.ID);
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {

        registration.addRecipeCategories(new CategoryBarista(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new CategoryScience(registration.getJeiHelpers().getGuiHelper()));
    }
}