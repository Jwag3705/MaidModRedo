package mmr.littledelicacies.item;

import mmr.littledelicacies.entity.LittleMaidBaseEntity;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.GlobalPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;

import javax.annotation.Nullable;
import java.util.List;

public class HouseWandItem extends Item {
    public HouseWandItem(Properties group) {
        super(group);
    }

    public ActionResultType onItemUse(ItemUseContext context) {
        World world = context.getWorld();
        BlockPos blockpos = context.getPos();
        if (context.getFace() != Direction.DOWN && world.getBlockState(blockpos).isIn(BlockTags.BEDS) && world.getBlockState(blockpos.up()).isAir(world, blockpos.up()) && world.getBlockState(blockpos.up(2)).isAir(world, blockpos.up(2))) {

            PlayerEntity playerentity = context.getPlayer();
            world.playSound(playerentity, blockpos, SoundEvents.ITEM_BOOK_PAGE_TURN, SoundCategory.BLOCKS, 1.0F, 1.0F);
            CompoundNBT compoundNBT = context.getItem().getOrCreateTag();

            compoundNBT.remove("RestDimension");
            compoundNBT.remove("RestPosX");
            compoundNBT.remove("RestPosY");
            compoundNBT.remove("RestPosZ");

            compoundNBT.putInt("BedDimension", context.getWorld().dimension.getType().getId());
            compoundNBT.putInt("BedPosX", context.getPos().getX());
            compoundNBT.putInt("BedPosY", context.getPos().getY());
            compoundNBT.putInt("BedPosZ", context.getPos().getZ());

            playerentity.sendStatusMessage(new TranslationTextComponent("item.littledelicacies.setbed", context.getPos().getX(), context.getPos().getY(), context.getPos().getZ()), true);

            return ActionResultType.SUCCESS;
        }

        if (context.getFace() != Direction.DOWN && world.getBlockState(blockpos.up()).isAir(world, blockpos.up()) && world.getBlockState(blockpos.up(2)).isAir(world, blockpos.up(2))) {

            PlayerEntity playerentity = context.getPlayer();
            world.playSound(playerentity, blockpos, SoundEvents.ITEM_BOOK_PAGE_TURN, SoundCategory.BLOCKS, 1.0F, 1.0F);
            CompoundNBT compoundNBT = context.getItem().getOrCreateTag();

            compoundNBT.remove("BedDimension");
            compoundNBT.remove("BedPosX");
            compoundNBT.remove("BedPosY");
            compoundNBT.remove("BedPosZ");

            compoundNBT.putInt("RestDimension", context.getWorld().dimension.getType().getId());
            compoundNBT.putInt("RestPosX", context.getPos().getX());
            compoundNBT.putInt("RestPosY", context.getPos().getY());
            compoundNBT.putInt("RestPosZ", context.getPos().getZ());

            playerentity.sendStatusMessage(new TranslationTextComponent("item.littledelicacies.setrest", context.getPos().getX(), context.getPos().getY(), context.getPos().getZ()), true);

            return ActionResultType.SUCCESS;
        }

        return ActionResultType.PASS;
    }

    @Override
    public boolean itemInteractionForEntity(ItemStack stack, PlayerEntity playerIn, LivingEntity target, Hand hand) {


        if (target instanceof LittleMaidBaseEntity) {
            if (((LittleMaidBaseEntity) target).isTamed() && ((LittleMaidBaseEntity) target).isOwner(playerIn)) {
                CompoundNBT compound = stack.getTag();
                if (compound != null && compound.contains("BedDimension") && compound.contains("BedDimension") && compound.contains("BedPosX", 99) && compound.contains("BedPosY", 99) && compound.contains("BedPosZ", 99)) {
                    target.getBrain().setMemory(MemoryModuleType.HOME, GlobalPos.of(DimensionType.getById(compound.getInt("BedDimension")), new BlockPos(compound.getInt("BedPosX"), compound.getInt("BedPosY"), compound.getInt("BedPosZ"))));
                    playerIn.sendStatusMessage(new TranslationTextComponent("item.littledelicacies.setmaidbed", compound.getInt("BedPosX"), compound.getInt("BedPosY"), compound.getInt("BedPosZ")), true);
                    compound.remove("BedDimension");
                    compound.remove("BedPosX");
                    compound.remove("BedPosY");
                    compound.remove("BedPosZ");

                    return true;
                }

                if (compound != null && compound.contains("RestDimension") && compound.contains("RestDimension") && compound.contains("RestPosX", 99) && compound.contains("RestPosY", 99) && compound.contains("RestPosZ", 99)) {
                    target.getBrain().setMemory(MemoryModuleType.MEETING_POINT, GlobalPos.of(DimensionType.getById(compound.getInt("RestDimension")), new BlockPos(compound.getInt("RestPosX"), compound.getInt("RestPosY"), compound.getInt("RestPosZ"))));
                    playerIn.sendStatusMessage(new TranslationTextComponent("item.littledelicacies.setmaidrest", compound.getInt("RestPosX"), compound.getInt("RestPosY"), compound.getInt("RestPosZ")), true);
                    compound.remove("RestDimension");
                    compound.remove("RestPosX");
                    compound.remove("RestPosY");
                    compound.remove("RestPosZ");

                    return true;
                }
            }
        }

        return super.itemInteractionForEntity(stack, playerIn, target, hand);
    }

    @Override
    public boolean hasEffect(ItemStack stack) {
        return true;
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        CompoundNBT compound = stack.getTag();
        if (compound != null && compound.contains("BedDimension")) {
            tooltip.add((new TranslationTextComponent("item.littledelicacies.housewand.bed", compound.getInt("BedPosX"), compound.getInt("BedPosY"), compound.getInt("BedPosZ"))));
        }
        if (compound != null && compound.contains("RestDimension")) {
            tooltip.add((new TranslationTextComponent("item.littledelicacies.housewand.rest", compound.getInt("RestPosX"), compound.getInt("RestPosY"), compound.getInt("RestPosZ"))));
        }
    }
}
