package mmr.maidmodredo.inventory;

import mmr.maidmodredo.entity.LittleMaidEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.SoundEvents;

public class InventoryMaidMain extends InventoryMaid {

    public InventoryMaidMain(LittleMaidEntity maid) {
        super(maid, (9 * 3));
    }

    @Override
    public void openInventory(PlayerEntity player) {
        super.markDirty();

        this.getContainerLittleMaid().playSound(SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0.5F, this.getContainerLittleMaid().getRNG().nextFloat() * 0.1F + 0.9F);
    }

    @Override
    public void closeInventory(PlayerEntity player) {
        super.markDirty();

        this.getContainerLittleMaid().playSound(SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0.5F, this.getContainerLittleMaid().getRNG().nextFloat() * 0.1F + 0.9F);
    }

}