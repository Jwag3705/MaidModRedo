package mmr.maidmodredo.item;

import mmr.maidmodredo.entity.LittleMaidEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.GlobalPos;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;

public class HouseWandItem extends Item {
    public HouseWandItem(Properties group) {
        super(group);
    }

    public ActionResultType onItemUse(ItemUseContext context) {
        World world = context.getWorld();
        BlockPos blockpos = context.getPos();
        if (context.getFace() != Direction.DOWN && world.getBlockState(blockpos.up()).isAir(world, blockpos.up()) && world.getBlockState(blockpos.up(2)).isAir(world, blockpos.up(2))) {

            PlayerEntity playerentity = context.getPlayer();
            world.playSound(playerentity, blockpos, SoundEvents.ITEM_BOOK_PAGE_TURN, SoundCategory.BLOCKS, 1.0F, 1.0F);
            CompoundNBT compoundNBT = context.getItem().getOrCreateTag();

            compoundNBT.putInt("BedDimension", context.getWorld().dimension.getType().getId());
            compoundNBT.putInt("BedPosX", context.getPos().getX());
            compoundNBT.putInt("BedPosY", context.getPos().getY());
            compoundNBT.putInt("BedPosZ", context.getPos().getZ());

            playerentity.sendStatusMessage(new TranslationTextComponent("item.maidmodredo.setbed", context.getPos().getX(), context.getPos().getY(), context.getPos().getZ()), true);

            return ActionResultType.SUCCESS;

        }

        return ActionResultType.PASS;
    }

    @Override
    public boolean itemInteractionForEntity(ItemStack stack, PlayerEntity playerIn, LivingEntity target, Hand hand) {


        if (target instanceof LittleMaidEntity) {
            CompoundNBT compound = stack.getTag();
            if (compound != null && DimensionType.getById(compound.getInt("BedDimension")) != null && compound.contains("BedDimension") && compound.contains("BedPosX", 99) && compound.contains("BedPosY", 99) && compound.contains("BedPosZ", 99)) {
                target.getBrain().setMemory(MemoryModuleType.HOME, GlobalPos.of(DimensionType.getById(compound.getInt("BedDimension")), new BlockPos(compound.getInt("BedPosX"), compound.getInt("BedPosY"), compound.getInt("BedPosZ"))));
                playerIn.sendStatusMessage(new TranslationTextComponent("item.maidmodredo.setmaidbed", compound.getInt("BedPosX"), compound.getInt("BedPosY"), compound.getInt("BedPosZ")), true);
            }

            return true;
        }

        return super.itemInteractionForEntity(stack, playerIn, target, hand);
    }

    @Override
    public boolean hasEffect(ItemStack stack) {
        return true;
    }
}
