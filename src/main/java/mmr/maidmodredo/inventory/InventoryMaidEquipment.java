package mmr.maidmodredo.inventory;

import mmr.maidmodredo.entity.LittleMaidEntity;
import net.minecraft.item.ItemStack;

public class InventoryMaidEquipment extends InventoryMaid {
    //slotCountでスロットの合計を入力
    public InventoryMaidEquipment(LittleMaidEntity maid) {
        super(maid, 6);
    }

    // TODO /* ======================================== MOD START =====================================*/
    public ItemStack getChestItem() {
        return this.getStackInSlot(0);
    }

    public ItemStack getbootItem() {
        return this.getStackInSlot(1);
    }

    public ItemStack getheadItem() {
        return this.getStackInSlot(2);
    }

    public ItemStack getLegItem() {
        return this.getStackInSlot(3);
    }

    public ItemStack getOffhandItem() {
        return this.getStackInSlot(4);
    }

    public ItemStack getMainhandItem() {
        return this.getStackInSlot(5);
    }

}