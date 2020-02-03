package mmr.maidmodredo.item;

import com.google.common.collect.Lists;
import mmr.maidmodredo.entity.LittleMaidBaseEntity;
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

import java.util.List;

public class JobBookItem extends Item {
    public JobBookItem(Properties group) {
        super(group);
    }

    public ActionResultType onItemUse(ItemUseContext context) {
        World world = context.getWorld();
        BlockPos blockpos = context.getPos();
        if (context.getFace() != Direction.DOWN && world.getBlockState(blockpos.up()).isAir(world, blockpos.up()) && world.getBlockState(blockpos.up(2)).isAir(world, blockpos.up(2))) {

            PlayerEntity playerentity = context.getPlayer();
            world.playSound(playerentity, blockpos, SoundEvents.ITEM_BOOK_PAGE_TURN, SoundCategory.BLOCKS, 1.0F, 1.0F);
            CompoundNBT compoundNBT = context.getItem().getOrCreateTag();

            compoundNBT.putInt("JobDimension", context.getWorld().dimension.getType().getId());
            compoundNBT.putInt("JobPosX", context.getPos().getX());
            compoundNBT.putInt("JobPosY", context.getPos().getY());
            compoundNBT.putInt("JobPosZ", context.getPos().getZ());

            playerentity.sendStatusMessage(new TranslationTextComponent("item.maidmodredo.set_jobpostion", context.getPos().getX(), context.getPos().getY(), context.getPos().getZ()), true);

            return ActionResultType.SUCCESS;

        }

        return ActionResultType.PASS;
    }

    @Override
    public boolean itemInteractionForEntity(ItemStack stack, PlayerEntity playerIn, LivingEntity target, Hand hand) {


        if (target instanceof LittleMaidBaseEntity) {
            if (((LittleMaidBaseEntity) target).isTamed() && ((LittleMaidBaseEntity) target).isOwner(playerIn)) {
                CompoundNBT compound = stack.getTag();
                List<GlobalPos> list = Lists.newArrayList();
                if (compound != null && DimensionType.getById(compound.getInt("JobDimension")) != null && compound.contains("JobDimension") && compound.contains("JobPosX", 99) && compound.contains("JobPosY", 99) && compound.contains("JobPosZ", 99)) {
                    playerIn.world.playSound(playerIn, playerIn.getPosition(), SoundEvents.ITEM_BOOK_PAGE_TURN, SoundCategory.BLOCKS, 1.0F, 1.0F);
                    list.add(GlobalPos.of(DimensionType.getById(compound.getInt("JobDimension")), new BlockPos(compound.getInt("JobPosX"), compound.getInt("JobPosY"), compound.getInt("JobPosZ"))));
                    target.getBrain().setMemory(MemoryModuleType.JOB_SITE, GlobalPos.of(DimensionType.getById(compound.getInt("JobDimension")), new BlockPos(compound.getInt("JobPosX"), compound.getInt("JobPosY"), compound.getInt("JobPosZ"))));
                    target.getBrain().setMemory(MemoryModuleType.SECONDARY_JOB_SITE, list);
                    playerIn.sendStatusMessage(new TranslationTextComponent("item.maidmodredo.setmaid_jobpostion", compound.getInt("JobPosX"), compound.getInt("JobPosY"), compound.getInt("JobPosZ")), true);
                }

                return true;
            }
        }

        return super.itemInteractionForEntity(stack, playerIn, target, hand);
    }

    @Override
    public boolean hasEffect(ItemStack stack) {
        return true;
    }
}
