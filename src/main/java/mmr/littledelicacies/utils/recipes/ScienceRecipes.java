package mmr.littledelicacies.utils.recipes;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.Iterator;

public class ScienceRecipes {
    public ItemStack[] resultItems = new ItemStack[]{};
    public ArrayList<ItemStack> inputItems = new ArrayList<ItemStack>();
    public float probability = 1;
    public static ArrayList<ScienceRecipes> scienceRecipeList = new ArrayList<ScienceRecipes>();


    public ScienceRecipes() {
    }

    public ScienceRecipes(ItemStack[] result, ItemStack[] main) {
        this.setScienceRecipe(result, main);
    }

    public void setScienceRecipe(ItemStack[] result, ItemStack[] main) {
        this.clear();
        for (ItemStack o : main) {
            inputItems.add(o);
        }
        resultItems = result.clone();
    }

    public ScienceRecipes setProbability(float probability) {
        this.probability = probability;
        return this;
    }

    public float getProbability() {
        return probability;
    }

    /**
     * 初期化
     */
    public void clear() {
        resultItems = new ItemStack[]{};
        inputItems = new ArrayList<ItemStack>();
        probability = 1;
    }

    public ItemStack[] getResultItemStack() {
        return resultItems.clone();
    }


    public ItemStack[] getResult(IInventory inventory, boolean simulator) {
        ItemStack[] retStack = new ItemStack[]{};

        ArrayList<ItemStack> inventoryList = new ArrayList<ItemStack>();
        for (int i = 0; i < inventory.getSizeInventory(); i++) {
            if (!inventory.getStackInSlot(i).isEmpty()) {
                inventoryList.add(inventory.getStackInSlot(i).copy());
            }
        }


        boolean flg1 = true;
        for (ItemStack obj1 : inputItems) {
            boolean flg2 = false;
            for (int i = 0; i < inventoryList.size(); i++) {

                ItemStack stack1 = (ItemStack) obj1;
                if (ItemStack.areItemsEqual(stack1, inventoryList.get(i))) {
                    if (!simulator) {
                        inventory.getStackInSlot(i).shrink(1);
                    }

                    inventoryList.remove(obj1);
                    flg2 = true;
                    break;
                }

            }
            if (!flg2) {
                flg1 = false;
                break;
            }
        }

        if (!flg1) {
            return retStack;
        }

        return resultItems.clone();
    }

    public static void addRecipe(ScienceRecipes recipes) {
        scienceRecipeList.add(recipes);
    }

    public static void ClearRecipe(ItemStack[] inputs) {
        Iterator<ScienceRecipes> iter = scienceRecipeList.iterator();
        while (iter.hasNext()) {
            ScienceRecipes recipe = iter.next();
            if (check(inputs[0], recipe.resultItems[0])
                    && check(inputs[1], recipe.resultItems[1]))
                iter.remove();
        }
    }

    private static boolean check(ItemStack a, ItemStack b) {

        if (ItemStack.areItemStacksEqual((ItemStack) a, b)) {
            return true;
        }
        return false;
    }

    public static void ClearAllRecipe() {
        scienceRecipeList.clear();
    }
}