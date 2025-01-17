package favouriteless.enchanted.common.items.poppets;

import favouriteless.enchanted.common.poppet.PoppetColour;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.damagesource.DamageSource;

import java.util.function.Predicate;

public class FirePoppetItem extends DeathPoppetItem {

	public FirePoppetItem(float failRate, int durability, PoppetColour colour, Predicate<DamageSource> sourcePredicate) {
		super(failRate, durability, colour, sourcePredicate);
	}

	@Override
	public void protect(Player player) {
		player.setHealth(1);
		player.clearFire();
	}

}
