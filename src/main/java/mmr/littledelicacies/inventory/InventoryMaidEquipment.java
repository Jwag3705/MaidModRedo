package mmr.littledelicacies.inventory;

import mmr.littledelicacies.entity.LittleMaidBaseEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;

public class InventoryMaidEquipment extends InventoryMaid {
    //slotCountでスロットの合計を入力
    public InventoryMaidEquipment(LittleMaidBaseEntity maid) {
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

    public void damageArmor(float damage) {
        if (!(damage <= 0.0F)) {
            damage = damage / 4.0F;
            if (damage < 1.0F) {
                damage = 1.0F;
            }

            for (int i = 0; i < 6; ++i) {
                ItemStack itemstack = this.getStackInSlot(i);
                if (itemstack.getItem() instanceof ArmorItem) {
                    int i_f = i;
                    itemstack.damageItem((int) damage, this.getContainerLittleMaid(), (p_214023_1_) -> {
                        p_214023_1_.sendBreakAnimation(EquipmentSlotType.fromSlotTypeAndIndex(EquipmentSlotType.Group.ARMOR, i_f));
                    });
                }
            }

        }
    }
}