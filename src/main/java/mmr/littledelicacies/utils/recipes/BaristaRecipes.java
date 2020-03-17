package mmr.littledelicacies.utils.recipes;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.Iterator;

public class BaristaRecipes {
    public ItemStack[] resultItems = new ItemStack[]{};
    public ArrayList<ItemStack> inputItems = new ArrayList<ItemStack>();
    public boolean enchantment = false;
    public static ArrayList<BaristaRecipes> baristaRecipesList = new ArrayList<BaristaRecipes>();


    public BaristaRecipes() {
    }

    public BaristaRecipes(ItemStack[] result, ItemStack[] main) {
        this.setBaristaRecipes(result, main);
    }

    public void setBaristaRecipes(ItemStack[] result, ItemStack[] main) {
        this.clear();
        for (ItemStack o : main) {
            inputItems.add(o);
        }
        resultItems = result.clone();
    }

    /**
     * 初期化
     */
    public void clear() {
        resultItems = new ItemStack[]{};
        inputItems = new ArrayList<ItemStack>();
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

    public static void addRecipe(BaristaRecipes recipes) {
        baristaRecipesList.add(recipes);
    }

    public static void ClearRecipe(ItemStack[] inputs) {
        Iterator<BaristaRecipes> iter = baristaRecipesList.iterator();
        while (iter.hasNext()) {
            BaristaRecipes recipe = iter.next();
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
        baristaRecipesList.clear();
    }
}