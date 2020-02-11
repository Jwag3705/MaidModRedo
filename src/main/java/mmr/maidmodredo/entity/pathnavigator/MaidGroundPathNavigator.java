package mmr.maidmodredo.entity.pathnavigator;

import net.minecraft.entity.MobEntity;
import net.minecraft.pathfinding.GroundPathNavigator;
import net.minecraft.pathfinding.PathFinder;
import net.minecraft.world.World;

public class MaidGroundPathNavigator extends GroundPathNavigator {
    public MaidGroundPathNavigator(MobEntity entity, World worldIn) {
        super(entity, worldIn);
    }

    protected PathFinder getPathFinder(int p_179679_1_) {
        this.nodeProcessor = new WalkAndAvoidLavaNodeProcessor();
        this.nodeProcessor.setCanEnterDoors(true);
        return new PathFinder(this.nodeProcessor, p_179679_1_);
    }
}
