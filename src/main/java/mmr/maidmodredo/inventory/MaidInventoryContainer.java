package mmr.maidmodredo.inventory;

import mmr.maidmodredo.entity.LittleMaidBaseEntity;
import mmr.maidmodredo.init.LittleContainers;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.*;
import net.minecraft.network.PacketBuffer;

public class MaidInventoryContainer extends Container {
    private LittleMaidBaseEntity friendEntity;
    private PlayerInventory playerInventory;

    public MaidInventoryContainer(int windowId, PlayerInventory playerInventory, PacketBuffer extraData) {
        this(windowId, playerInventory, (LittleMaidBaseEntity) (playerInventory.player.world.getEntityByID(extraData.readVarInt())));
    }

    public MaidInventoryContainer(int id, PlayerInventory playerInventory, final LittleMaidBaseEntity entityFriend) {
        super(LittleContainers.MAID_INVENTORY, id);
        int column;
        int row;
        int index;

        EquipmentSlotType slotType;

        //フレンズの装備スロットを追加する
        for (index = 0; index < 6; ++index) {


            int x = 0, y = 0;
            switch (index) {
                case 0:
                    slotType = EquipmentSlotType.CHEST;
                    x = 8;
                    y = 36;
                    break;
                case 1:
                    slotType = EquipmentSlotType.FEET;
                    x = 80;
                    y = 36;
                    break;
                case 2:
                    slotType = EquipmentSlotType.HEAD;
                    x = 8;
                    y = 18;
                    break;
                case 3:
                    slotType = EquipmentSlotType.LEGS;
                    x = 80;
                    y = 18;
                    break;
                case 4:
                    slotType = EquipmentSlotType.OFFHAND;
                    x = 80;
                    y = 54;
                    break;
                case 5:
                    slotType = EquipmentSlotType.MAINHAND;
                    x = 8;
                    y = 54;
                    break;
                default:
                    slotType = null;
            }
            EquipmentSlotType finalSlotType = slotType;
            this.addSlot(new Slot(entityFriend.getInventoryMaidEquipment(), index, x, y) {
                public int getSlotStackLimit() {
                    if (this.getSlotIndex() == 4 || this.getSlotIndex() == 5) {
                        return 64;
                    } else {
                        return 1;
                    }
                }

                @Override
                public boolean isItemValid(ItemStack stack) {
                    if (finalSlotType == null)
                        return false;
                    Item item = stack.getItem();
                    if (item instanceof SwordItem) {
                        return this.getSlotIndex() == 4 || this.getSlotIndex() == 5;
                    }
                    if (item instanceof ToolItem) {
                        return this.getSlotIndex() == 4 || this.getSlotIndex() == 5;
                    }
                    if (item instanceof ArmorItem) {
                        if (this.getSlotIndex() < 4) {
                            if (stack.canEquip(finalSlotType, entityFriend)) {
                                return true;
                            }
                        }
                    }
                    return this.getSlotIndex() == 4 || this.getSlotIndex() == 5;
                }
            });
        }

        for (column = 0; column < 3; ++column) {
            for (row = 0; row < 9; ++row) {
                index = (row + column * 9);

                this.addSlot(new Slot(entityFriend.getInventoryMaidMain(), index, (row * 18) + 8, (column * 18) + 74));
            }
        }

        for (column = 0; column < 3; ++column) {
            for (row = 0; row < 9; ++row) {
                index = (row + column * 9 + 9);

                this.addSlot(new Slot(playerInventory, index, (row * 18) + 8, (column * 18) + 140));
            }
        }

        for (row = 0; row < 9; ++row) {
            index = row;

            this.addSlot(new Slot(playerInventory, index, (row * 18) + 8, 198));
        }

        this.friendEntity = entityFriend;
        this.playerInventory = playerInventory;
    }

    public LittleMaidBaseEntity getLittleMaidEntity() {
        return friendEntity;
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return this.friendEntity.isAlive() && (playerIn.getDistanceSq(this.friendEntity) < 64.0D);
    }

    @Override
    public ItemStack transferStackInSlot(PlayerEntity player, int index) {
        ItemStack stackEmpty = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);

        if (slot == null || !slot.getHasStack()) {
            return stackEmpty;
        }

        ItemStack srcItemStack = slot.getStack();
        ItemStack dstItemStack = srcItemStack.copy();

        // mergeItemStack(移動するItemStack, 移動先の最小スロット番号, 移動先の最大スロット番号, 昇順or降順)

        // int indexEquipment = 5;
        // int indexMain = 27;

        if (index < this.getLittleMaidEntity().getInventoryMaidEquipment().getSizeInventory() + this.getLittleMaidEntity().getInventoryMaidMain().getSizeInventory()) {
            if (!this.mergeItemStack(dstItemStack, this.getLittleMaidEntity().getInventoryMaidEquipment().getSizeInventory() + this.getLittleMaidEntity().getInventoryMaidMain().getSizeInventory(), this.inventorySlots.size(), true)) {
                return stackEmpty;
            }
        } else {
            if (!this.mergeItemStack(dstItemStack, 0, this.getLittleMaidEntity().getInventoryMaidEquipment().getSizeInventory() + this.getLittleMaidEntity().getInventoryMaidMain().getSizeInventory(), false)) {
                return stackEmpty;
            }
        }

        if (dstItemStack.isEmpty()) {
            slot.putStack(stackEmpty);
        } else {
            slot.onSlotChanged();
        }

        if (dstItemStack.getCount() == srcItemStack.getCount()) {
            return stackEmpty;
        }

        slot.onTake(player, dstItemStack);

        return srcItemStack;
    }


    @Override
    public void onContainerClosed(PlayerEntity playerIn) {
        this.friendEntity.getInventoryMaidMain().closeInventory(playerIn);
        this.friendEntity.getInventoryMaidEquipment().closeInventory(playerIn);
        playerInventory.openInventory(playerInventory.player);

        super.onContainerClosed(playerIn);
    }


}