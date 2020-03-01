package mmr.maidmodredo.maidjob;

import mmr.maidmodredo.entity.LittleMaidBaseEntity;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.SoundEvents;

public class EffectCasterMaidJob extends CasterMaidJob {
    private Effect effect;

    public EffectCasterMaidJob(String nameIn, ItemStack stack, Effect effect) {
        super(nameIn, stack);
        this.effect = effect;
    }

    @Override
    public void castMagic(LittleMaidBaseEntity owner, Entity target) {
        if (owner.getOwner() != null && owner.getOwner().isAlive()) {
            if (owner.getOwner().getActivePotionEffect(effect) == null) {
                owner.getOwner().addPotionEffect(new EffectInstance(effect, 200));
                owner.playSound(SoundEvents.ENTITY_EVOKER_CAST_SPELL, 1.0F, 1.0F);

                for (int i = 0; i < 6; ++i) {
                    owner.getOwner().world.addParticle(ParticleTypes.ENCHANT, owner.getOwner().getPosXRandom(0.5D), owner.getOwner().getPosYRandom() + 0.5D, owner.getOwner().getPosZRandom(0.5D), (owner.getOwner().getRNG().nextDouble() - 0.5D) * 2.0D, -owner.getOwner().getRNG().nextDouble(), (owner.getOwner().getRNG().nextDouble() - 0.5D) * 2.0D);
                }
            }
        }
    }
}
