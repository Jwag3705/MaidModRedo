package mmr.littledelicacies.inventory;

import mmr.littledelicacies.entity.LittleMaidBaseEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.text.ITextComponent;

public class InventoryMaid extends Inventory implements INamedContainerProvider {

    public int slot;
    private LittleMaidBaseEntity entityLittleMaid;

    public InventoryMaid(LittleMaidBaseEntity entityLittleMaid, int slotCount) {
        super(slotCount);

        this.slot = slotCount;
        this.entityLittleMaid = entityLittleMaid;
    }

    @Override
    public boolean isUsableByPlayer(PlayerEntity player) {
        return this.entityLittleMaid.isAlive() && (player.getDistanceSq(this.entityLittleMaid) < 64.0D);

    }

    // TODO /* ======================================== MOD START =====================================*/

    public LittleMaidBaseEntity getContainerLittleMaid() {
        return this.entityLittleMaid;
    }

    public void readInventoryFromNBT(ListNBT nbtList) {
        for (int i = 0; i < nbtList.size(); ++i) {
            CompoundNBT compoundnbt = nbtList.getCompound(i);
            int j = compoundnbt.getByte("Slot");

            ItemStack itemstack = ItemStack.read(nbtList.getCompound(i));
            if (!itemstack.isEmpty()) {
                this.setInventorySlotContents(j, itemstack);
            }
        }

    }

    public ListNBT writeInventoryToNBT() {
        ListNBT nbtList = new ListNBT();

        for (int slot = 0; slot < this.getSizeInventory(); ++slot) {
            ItemStack stackSlot = this.getStackInSlot(slot);

            if (InventoryMaidHelper.isNotEmptyItemStack(stackSlot)) {
                CompoundNBT nbt = new CompoundNBT();
                nbt.putByte("Slot", (byte) slot);
                stackSlot.write(nbt);
                nbtList.add(nbt);
            }
        }

        return nbtList;
    }

    @Override
    public Container createMenu(int windowId, PlayerInventory inventory, PlayerEntity player) {
        return new MaidInventoryContainer(windowId, inventory, this.entityLittleMaid);
    }

    @Override
    public void openInventory(PlayerEntity player) {
        entityLittleMaid.setOpenInventory(true);
    }

    @Override
    public void closeInventory(PlayerEntity player) {
        entityLittleMaid.setOpenInventory(false);
    }

    @Override
    public ITextComponent getDisplayName() {
        return this.entityLittleMaid.getName();
    }
}