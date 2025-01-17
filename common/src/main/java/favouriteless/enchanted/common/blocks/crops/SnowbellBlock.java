package favouriteless.enchanted.common.blocks.crops;

import favouriteless.enchanted.common.init.registry.EnchantedItems;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;

public class SnowbellBlock extends CropsBlockAgeFive {

    public SnowbellBlock() {
        super(Properties.copy(Blocks.WHEAT));
    }

    protected ItemLike getBaseSeedId() {
        return EnchantedItems.SNOWBELL_SEEDS.get();
    }

}
