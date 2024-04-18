package com.favouriteless.enchanted.common.items.poppets;

import com.favouriteless.enchanted.common.poppet.PoppetColour;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.damagesource.DamageSource;

import java.util.function.Predicate;

public class DeathPoppetItem extends AbstractDeathPoppetItem {

	public DeathPoppetItem(float failRate, int durability, PoppetColour colour, Predicate<DamageSource> sourcePredicate) {
		super(failRate, durability, colour, sourcePredicate);
	}

	@Override
	public boolean canProtect(Player player) {
		return true;
	}

	@Override
	public void protect(Player player) {
		player.setHealth(1);
		player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 100, 1));
	}

}
