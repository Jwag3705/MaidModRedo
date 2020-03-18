package mmr.littledelicacies.addons.jei;

import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableStatic;
import mezz.jei.api.gui.ingredient.IGuiItemStackGroup;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.category.IRecipeCategory;
import mmr.littledelicacies.LittleDelicacies;
import mmr.littledelicacies.init.LittleItems;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class CategoryBarista implements IRecipeCategory<BaristaWrapper> {

    public static final ResourceLocation ID = new ResourceLocation(LittleDelicacies.MODID, "barista");

    private final IDrawable icon;
    private final IDrawableStatic background;
    private final IDrawableStatic slotDrawable;

    public CategoryBarista(IGuiHelper guiHelper) {

        this.icon = guiHelper.createDrawableIngredient(new ItemStack(LittleItems.APPLE_JUICE));
        this.background = guiHelper.createBlankDrawable(155, 57);
        this.slotDrawable = guiHelper.getSlotDrawable();
    }

    @Override
    public ResourceLocation getUid() {
        return ID;
    }

    @Override
    public Class<? extends BaristaWrapper> getRecipeClass() {
        return BaristaWrapper.class;
    }

    @Override
    public String getTitle() {

        return I18n.format("littledelicacies.barista.title");
    }

    @Override
    public IDrawable getBackground() {

        return this.background;
    }

    @Override
    public IDrawable getIcon() {

        return this.icon;
    }

    @Override
    public void setIngredients(BaristaWrapper recipe, IIngredients ingredients) {

        recipe.setIngredients(ingredients);
    }

    @Override
    public void draw(BaristaWrapper recipe, double mouseX, double mouseY) {

        for (int nextSlotId = 0; nextSlotId < 6; nextSlotId++) {
            final int relativeSlotId = nextSlotId;

            this.slotDrawable.draw(0 + 19 * (relativeSlotId % 3), 19 * (relativeSlotId / 3));
        }

        this.slotDrawable.draw(19 * 4, 19 * 1);
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, BaristaWrapper recipe, IIngredients ingredients) {

        final IGuiItemStackGroup stacks = recipeLayout.getItemStacks();

        int nextSlotId = 0;

        for (ItemStack entry : recipe.getInputItems()) {
            final int relativeSlotId = nextSlotId;
            stacks.init(nextSlotId, false, 0 + 19 * (relativeSlotId % 3), 19 * (relativeSlotId / 43));
            stacks.set(nextSlotId, entry);

            nextSlotId++;

        }

        // Soil Inputs
        stacks.init(nextSlotId, true, 19 * 4, 19 * 1);
        stacks.set(nextSlotId, recipe.getOutputItems());


        //stacks.addTooltipCallback(recipe::getTooltip);
    }
}