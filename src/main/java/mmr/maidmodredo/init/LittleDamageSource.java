package mmr.maidmodredo.init;

import net.minecraft.entity.Entity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;

public class LittleDamageSource {
    public static DamageSource causeRushingDamage(Entity source) {
        return new EntityDamageSource("rush", source);
    }

    public static DamageSource causeRotationAttackDamage(Entity source) {
        return new EntityDamageSource("rotation_attack", source);
    }

    public static DamageSource causeShieldBushDamage(Entity source) {
        return new EntityDamageSource("shield_bush", source);
    }
}
