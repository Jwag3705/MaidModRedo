package mmr.maidmodredo.entity.pathnavigator;

import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.pathfinding.WalkNodeProcessor;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;

public class WalkAndAvoidLavaNodeProcessor extends WalkNodeProcessor {

    public PathNodeType getPathNodeType(IBlockReader blockaccessIn, int x, int y, int z) {
        return func_227480_b_(blockaccessIn, x, y, z);
    }

    public static PathNodeType func_227480_b_(IBlockReader p_227480_0_, int p_227480_1_, int p_227480_2_, int p_227480_3_) {
        PathNodeType pathnodetype = getPathNodeTypeRaw(p_227480_0_, p_227480_1_, p_227480_2_, p_227480_3_);
        if (pathnodetype == PathNodeType.OPEN && p_227480_2_ >= 1) {
            Block block = p_227480_0_.getBlockState(new BlockPos(p_227480_1_, p_227480_2_ - 1, p_227480_3_)).getBlock();
            BlockState blockstate = p_227480_0_.getBlockState(new BlockPos(p_227480_1_, p_227480_2_ - 1, p_227480_3_));
            PathNodeType pathnodetype1 = getPathNodeTypeRaw(p_227480_0_, p_227480_1_, p_227480_2_ - 1, p_227480_3_);

            Material material = blockstate.getMaterial();

            pathnodetype = pathnodetype1 != PathNodeType.WALKABLE && pathnodetype1 != PathNodeType.OPEN && pathnodetype1 != PathNodeType.WATER && pathnodetype1 != PathNodeType.LAVA ? PathNodeType.WALKABLE : PathNodeType.OPEN;

            if (pathnodetype1 == PathNodeType.DAMAGE_FIRE || block == Blocks.FIRE || block == Blocks.MAGMA_BLOCK || block == Blocks.CAMPFIRE) {
                pathnodetype = PathNodeType.DAMAGE_FIRE;
            }

            if (pathnodetype1 == PathNodeType.DAMAGE_CACTUS || block == Blocks.CACTUS) {
                pathnodetype = PathNodeType.DAMAGE_CACTUS;
            }

            if (pathnodetype1 == PathNodeType.DAMAGE_OTHER || block == Blocks.SWEET_BERRY_BUSH) {
                pathnodetype = PathNodeType.DAMAGE_OTHER;
            }

            if (pathnodetype1 == PathNodeType.STICKY_HONEY) {
                pathnodetype = PathNodeType.STICKY_HONEY;
            }

            if (pathnodetype1 == PathNodeType.LAVA || blockstate.getFluidState().isTagged(FluidTags.LAVA)) {
                pathnodetype = PathNodeType.LAVA;
            }

            if (block == Blocks.COCOA) {
                pathnodetype = PathNodeType.COCOA;
            } else if (block instanceof DoorBlock && material == Material.WOOD && !blockstate.get(DoorBlock.OPEN)) {
                pathnodetype = PathNodeType.DOOR_WOOD_CLOSED;
            } else if (block instanceof DoorBlock && material == Material.IRON && !blockstate.get(DoorBlock.OPEN)) {
                pathnodetype = PathNodeType.DOOR_IRON_CLOSED;
            } else if (block instanceof DoorBlock && blockstate.get(DoorBlock.OPEN)) {
                pathnodetype = PathNodeType.DOOR_OPEN;
            } else if (block instanceof AbstractRailBlock) {
                pathnodetype = PathNodeType.RAIL;
            } else if (block instanceof LeavesBlock) {
                pathnodetype = PathNodeType.LEAVES;
            }
        }

        if (pathnodetype == PathNodeType.WALKABLE) {
            pathnodetype = checkNeighborBlocks(p_227480_0_, p_227480_1_, p_227480_2_, p_227480_3_, pathnodetype);
        }

        return pathnodetype;
    }

    public static PathNodeType checkNeighborBlocks(IBlockReader p_193578_0_, int blockaccessIn, int x, int y, PathNodeType z) {
        try (BlockPos.PooledMutable blockpos$pooledmutable = BlockPos.PooledMutable.retain()) {
            for (int i = -1; i <= 1; ++i) {
                for (int j = -1; j <= 1; ++j) {
                    for (int k = -1; k <= 1; ++k) {
                        if (i != 0 || k != 0) {
                            PathNodeType type = getPathNodeTypeRaw(p_193578_0_, blockaccessIn, x, y);
                            if (type == PathNodeType.LAVA || type == PathNodeType.DAMAGE_CACTUS || type == PathNodeType.DAMAGE_FIRE || type == PathNodeType.DAMAGE_OTHER || type == PathNodeType.DANGER_CACTUS || type == PathNodeType.DANGER_FIRE || type == PathNodeType.DANGER_OTHER)
                                z = type;
                        }
                    }
                }
            }
        }

        return z;
    }
}
