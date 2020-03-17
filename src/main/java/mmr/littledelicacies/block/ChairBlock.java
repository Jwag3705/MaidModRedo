package mmr.littledelicacies.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;

public class ChairBlock extends Block {
    private static final VoxelShape PART_BASE = Block.makeCuboidShape(2.0D, 9.0D, 2.0D, 14.0D, 11.0D, 14.0D);
    private static final VoxelShape LEG = Block.makeCuboidShape(2.0D, 0.0D, 2.0D, 4.0D, 9.0D, 4.0D);
    private static final VoxelShape LEG2 = Block.makeCuboidShape(2.0D, 0.0D, 12.0D, 4.0D, 9.0D, 14.0D);
    private static final VoxelShape LEG3 = Block.makeCuboidShape(12.0D, 0.0D, 2.0D, 14.0D, 9.0D, 4.0D);
    private static final VoxelShape LEG4 = Block.makeCuboidShape(12.0D, 0.0D, 12.0D, 14.0D, 9.0D, 14.0D);


    private static final VoxelShape AXIS_AABB = VoxelShapes.or(PART_BASE, LEG, LEG2, LEG3, LEG4);

    public ChairBlock(Properties properties) {
        super(properties);
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return AXIS_AABB;
    }
}
