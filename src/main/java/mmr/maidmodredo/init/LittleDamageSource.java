package mmr.maidmodredo.init;

import net.minecraft.entity.Entity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;

public class LittleDamageSource {
    public static DamageSource causeRushingDamage(Entity source) {
        return new EntityDamageSource("rush", source);
    }
}
