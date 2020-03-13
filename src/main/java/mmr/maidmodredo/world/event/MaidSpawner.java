package mmr.maidmodredo.world.event;

import mmr.maidmodredo.entity.LittleButlerEntity;
import mmr.maidmodredo.entity.LittleMaidBaseEntity;
import mmr.maidmodredo.entity.LittleMaidEntity;
import mmr.maidmodredo.init.LittleEntitys;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.village.PointOfInterestManager;
import net.minecraft.village.PointOfInterestType;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.server.ServerWorld;

import java.util.List;
import java.util.Random;

public class MaidSpawner {
    private int field_221125_a;

    public int tick(ServerWorld worldIn) {
        if (worldIn.isDaytime() && worldIn.getGameRules().getBoolean(GameRules.DO_MOB_SPAWNING)) {
            --this.field_221125_a;
            if (this.field_221125_a > 0) {
                return 0;
            } else {
                PlayerEntity playerentity = worldIn.getRandomPlayer();
                if (playerentity == null) {
                    return 0;
                } else {
                    Random random = worldIn.rand;
                    int i = playerentity.getPosition().getX() + (40 + random.nextInt(24)) * (random.nextBoolean() ? -1 : 1);
                    int j = playerentity.getPosition().getZ() + (40 + random.nextInt(24)) * (random.nextBoolean() ? -1 : 1);
                    int l = worldIn.getHeight(Heightmap.Type.WORLD_SURFACE, i, j);
                    BlockPos blockpos = new BlockPos(i, l, j);


                    if (worldIn.func_217471_a(playerentity.getPosition(), 3)) {
                        this.field_221125_a = 24000;
                        return this.func_221121_a(worldIn, blockpos, playerentity.getPosition());
                    } else if (worldIn.getPointOfInterestManager().func_219127_a(PointOfInterestType.MEETING.func_221045_c(), (p_221241_0_) -> {
                        return true;
                    }, playerentity.getPosition(), 48, PointOfInterestManager.Status.ANY).isPresent()) {

                        this.field_221125_a = 24000;
                        return this.spawnToNearMeetingPoint(worldIn, blockpos, playerentity.getPosition());
                    } else {
                        this.field_221125_a = 12000;
                    }

                }
                return 0;
            }
        } else {
            return 0;
        }
    }

    private int func_221121_a(ServerWorld worldIn, BlockPos p_221121_2_, BlockPos playerPos) {
        int i = 48;
        if (worldIn.getPointOfInterestManager().func_219145_a(PointOfInterestType.HOME.func_221045_c(), playerPos, 48, PointOfInterestManager.Status.IS_OCCUPIED) > 3L) {
            List<LittleMaidBaseEntity> list = worldIn.getEntitiesWithinAABB(LittleMaidBaseEntity.class, (new AxisAlignedBB(playerPos)).grow(48.0D, 12.0D, 48.0D));
            if (list.size() < 7) {
                if (worldIn.rand.nextBoolean()) {
                    return this.spawnMaid(p_221121_2_, worldIn, playerPos);
                } else {
                    return this.spawnButler(p_221121_2_, worldIn, playerPos);
                }
            }
        }

        return 0;
    }

    private int spawnToNearMeetingPoint(ServerWorld worldIn, BlockPos p_221121_2_, BlockPos playerPos) {
        List<LittleMaidBaseEntity> list = worldIn.getEntitiesWithinAABB(LittleMaidBaseEntity.class, (new AxisAlignedBB(playerPos)).grow(48.0D, 12.0D, 48.0D));
        if (list.size() < 7) {
            if (worldIn.rand.nextBoolean()) {
                return this.spawnMaid(p_221121_2_, worldIn, playerPos);
            } else {
                return this.spawnButler(p_221121_2_, worldIn, playerPos);
            }
        }

        return 0;
    }

    private int spawnMaid(BlockPos pos, World worldIn, BlockPos playerPos) {
        LittleMaidEntity littleentity = LittleEntitys.LITTLEMAID.create(worldIn);
        if (littleentity == null) {
            return 0;
        } else {
            littleentity.onInitialSpawn(worldIn, worldIn.getDifficultyForLocation(pos), SpawnReason.EVENT, (ILivingEntityData) null, (CompoundNBT) null);
            littleentity.moveToBlockPosAndAngles(pos, 0.0F, 0.0F);

            worldIn.addEntity(littleentity);
            return 1;
        }
    }

    private int spawnButler(BlockPos pos, World worldIn, BlockPos playerPos) {
        LittleButlerEntity littleentity = LittleEntitys.LITTLEBUTLER.create(worldIn);
        if (littleentity == null) {
            return 0;
        } else {
            littleentity.onInitialSpawn(worldIn, worldIn.getDifficultyForLocation(pos), SpawnReason.EVENT, (ILivingEntityData) null, (CompoundNBT) null);
            littleentity.moveToBlockPosAndAngles(pos, 0.0F, 0.0F);

            worldIn.addEntity(littleentity);
            return 1;
        }
    }
}