package mmr.littledelicacies.item;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.UseAction;
import net.minecraft.stats.Stats;
import net.minecraft.world.World;

public class DrinkItem extends Item {
    public DrinkItem(Properties group) {
        super(group);
    }

    public ItemStack onItemUseFinish(ItemStack stack, World worldIn, LivingEntity entityLiving) {
        PlayerEntity playerentity = entityLiving instanceof PlayerEntity ? (PlayerEntity) entityLiving : null;
        if (playerentity == null || !playerentity.abilities.isCreativeMode) {
            stack.shrink(1);
        }

        if (playerentity instanceof ServerPlayerEntity) {
            CriteriaTriggers.CONSUME_ITEM.trigger((ServerPlayerEntity) playerentity, stack);
        }

        if (playerentity != null) {
            playerentity.addStat(Stats.ITEM_USED.get(this));
        }

        if (playerentity == null || !playerentity.abilities.isCreativeMode) {
            if (stack.isEmpty()) {
                return new ItemStack(Items.GLASS_BOTTLE);
            }

            if (playerentity != null) {
                playerentity.inventory.addItemStackToInventory(new ItemStack(Items.GLASS_BOTTLE));
            }
        }

        return stack;
    }

    /**
     * How long it takes to use or consume an item
     */
    public int getUseDuration(ItemStack stack) {
        return 30;
    }

    /**
     * returns the action that specifies what animation to play when the items is being used
     */
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.DRINK;
    }
}
