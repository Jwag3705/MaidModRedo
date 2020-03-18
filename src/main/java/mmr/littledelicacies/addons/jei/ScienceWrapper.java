package mmr.littledelicacies.addons.jei;

import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.category.extensions.IRecipeCategoryExtension;
import mmr.littledelicacies.utils.recipes.ScienceRecipes;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ScienceWrapper implements IRecipeCategoryExtension {

    private final ScienceRecipes baristaInfo;
    private final List<ItemStack> inputItems = NonNullList.create();
    private final List<ItemStack> outputItems = NonNullList.create();

    public ScienceWrapper(ScienceRecipes crop) {

        this.baristaInfo = crop;

        this.inputItems.addAll(crop.inputItems);
        this.outputItems.addAll(Arrays.asList(crop.getResultItemStack()));
    }

    public List<ItemStack> getInputItems() {

        return this.inputItems;
    }

    public List<ItemStack> getOutputItems() {

        return this.outputItems;
    }

    @Override
    public void setIngredients(IIngredients ingredients) {

        final List<ItemStack> inputs = new ArrayList<>();

        inputs.addAll(this.inputItems);

        ingredients.setInputs(VanillaTypes.ITEM, inputs);

        final List<ItemStack> outputs = new ArrayList<>();
        outputs.addAll(this.outputItems);

        ingredients.setOutputs(VanillaTypes.ITEM, outputs);
    }

  /*  public void getTooltip (int slotIndex, boolean input, ItemStack ingredient, List<String> tooltip) {

        if (!ingredient.isEmpty()) {

            if (input) {

                if (slotIndex == 0) {

                    final int growthTicks = this.baristaInfo.getGrowthTicks();
                    tooltip.add(TextFormatting.GRAY + I18n.format("botanypots.tooltip.jei.growthrate", growthTicks, ticksToTime(growthTicks, false)));
                }

                else if (slotIndex == 1) {

                    final SoilInfo output = BotanyPotHelper.getSoilForItem(RecipeUtils.getActiveRecipeManager(), ingredient);

                    if (output != null) {

                        final int difference = this.baristaInfo.getGrowthTicksForSoil(output) - this.baristaInfo.getGrowthTicks();
                        final TextFormatting formatting = difference < 0 ? TextFormatting.GREEN : difference > 0 ? TextFormatting.RED : TextFormatting.GRAY;
                        tooltip.add(formatting + I18n.format("botanypots.tooltip.jei.growthmodifier", output.getGrowthModifier(), ticksToTime(difference, true)));
                    }
                }
            }

            else {

                final int outputIndex = slotIndex - 2;

                if (outputIndex < this.drops.size()) {

                    final HarvestEntry entry = this.drops.get(outputIndex);
                    tooltip.add(TextFormatting.GRAY + I18n.format("botanypots.tooltip.jei.dropchance", entry.getChance() * 100f));

                    final int rollMin = entry.getMinRolls();
                    final int rollMax = entry.getMaxRolls();

                    if (rollMin == rollMax) {

                        tooltip.add(TextFormatting.GRAY + I18n.format("botanypots.tooltip.jei.rolls", rollMin));
                    }

                    else {

                        tooltip.add(TextFormatting.GRAY + I18n.format("botanypots.tooltip.jei.rollrange", rollMin, rollMax));
                    }
                }
            }
        }
    }*/

    private static String ticksToTime(int ticks, boolean prefix) {

        final boolean isPositive = ticks > 0;
        ticks = Math.abs(ticks);
        int i = ticks / 20;
        final int j = i / 60;
        i = i % 60;

        final String result = i < 10 ? j + ":0" + i : j + ":" + i;
        return prefix ? (isPositive ? "+" : "-") + result : result;
    }
}