package favouriteless.enchanted.common.rites.binding;

import favouriteless.enchanted.api.rites.AbstractCreateItemRite;
import favouriteless.enchanted.common.init.registry.EnchantedBlocks;
import favouriteless.enchanted.common.init.registry.EnchantedItems;
import favouriteless.enchanted.common.rites.CirclePart;
import favouriteless.enchanted.common.rites.RiteType;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.UUID;

public class RiteBindingWaystoneDuplicate extends AbstractCreateItemRite {

    protected RiteBindingWaystoneDuplicate(RiteType<?> type, ServerLevel level, BlockPos pos, UUID caster, int power) {
        super(type, level, pos, caster, power, SoundEvents.ZOMBIE_VILLAGER_CURE, new ItemStack(EnchantedItems.BOUND_WAYSTONE.get(), 2));
    }

    public RiteBindingWaystoneDuplicate(RiteType<?> type, ServerLevel level, BlockPos pos, UUID caster) {
        this(type, level, pos, caster, 500); // Power, power per tick
        CIRCLES_REQUIRED.put(CirclePart.SMALL, EnchantedBlocks.RITUAL_CHALK.get());
        ITEMS_REQUIRED.put(EnchantedItems.BOUND_WAYSTONE.get(), 1);
        ITEMS_REQUIRED.put(EnchantedItems.WAYSTONE.get(), 1);
        ITEMS_REQUIRED.put(EnchantedItems.ENDER_DEW.get(), 1);
        ITEMS_REQUIRED.put(Items.REDSTONE, 1);
    }

    @Override
    public void setupItemNbt(int index, ItemStack stack) {
        if(index == 0) {
            for(ItemStack item : itemsConsumed) {
                if(item.getItem() == EnchantedItems.BOUND_WAYSTONE.get()) {
                    stack.setTag(item.getOrCreateTag());
                }
            }
        }
    }

}
