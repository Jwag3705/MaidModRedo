package mmr.littledelicacies.entity.ai;

import mmr.littledelicacies.entity.boss.TrinityEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;

public class StartRushGoal extends Goal {
    protected final TrinityEntity attacker;

    public StartRushGoal(TrinityEntity trinityEntity) {
        this.attacker = trinityEntity;
    }

    @Override
    public boolean shouldExecute() {
        LivingEntity livingEntity = this.attacker.getAttackTarget();
        if (livingEntity != null && livingEntity.isAlive() && this.attacker.isAlive()) {
            double d0 = this.attacker.getDistanceSq(livingEntity);
            return this.attacker.getRNG().nextInt(220) == 0 && d0 > 42.0F;
        }
        return false;
    }

    @Override
    public void startExecuting() {
        super.startExecuting();
        this.attacker.setRushing(true);
    }
}
