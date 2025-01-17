package favouriteless.enchanted.common.effects;

import favouriteless.enchanted.common.init.registry.EnchantedEffects;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.LivingEntity;

public class EffectEvents {

	public static boolean onLivingHurt(LivingEntity entity, DamageSource source, float amount) {
		if(entity.hasEffect(EnchantedEffects.FALL_RESISTANCE.get()))
			return source.is(DamageTypeTags.IS_FALL) || source.is(DamageTypes.FLY_INTO_WALL);
		if(entity.hasEffect(EnchantedEffects.DROWN_RESISTANCE.get()))
			return source.is(DamageTypeTags.IS_DROWNING);
		if(entity.hasEffect(EnchantedEffects.MAGIC_RESISTANCE.get()))
			return EnchantedEffects.isMagic(source);

		return false;
	}

}
