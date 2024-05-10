package favouriteless.enchanted.common.init;

import favouriteless.enchanted.Enchanted;
import favouriteless.enchanted.common.init.registry.AltarUpgradeRegistry;
import favouriteless.enchanted.common.init.registry.PowerProviderRegistry;
import favouriteless.enchanted.common.reloadlisteners.altar.AltarUpgradeReloadListener;
import favouriteless.enchanted.common.reloadlisteners.altar.PowerProviderReloadListener;
import favouriteless.enchanted.platform.CommonServices;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

public class EnchantedData {

    public static final PowerProviderRegistry<Block> POWER_BLOCKS = new PowerProviderRegistry<>();
    public static final PowerProviderRegistry<TagKey<Block>> POWER_TAGS = new PowerProviderRegistry<>();
    public static final AltarUpgradeRegistry ALTAR_UPGRADES = new AltarUpgradeRegistry();



    static {
        register("altar_blocks", new PowerProviderReloadListener<>("altar/blocks", EnchantedData::createBlockKey, EnchantedData.POWER_BLOCKS));
        register("altar_tags", new PowerProviderReloadListener<>("altar/tags", EnchantedData::createBlockTagKey, EnchantedData.POWER_TAGS));
        register("altar_upgrade", new AltarUpgradeReloadListener());
    }

    public static void register(String id, SimpleJsonResourceReloadListener reloadListener) {
        CommonServices.COMMON_REGISTRY.register(Enchanted.id(id), reloadListener);
    }

    private static Block createBlockKey(ResourceLocation key) {
        Block block = BuiltInRegistries.BLOCK.get(key);
        if(block != Blocks.AIR)
            return block;
        else
            return null;
    }

    private static TagKey<Block> createBlockTagKey(ResourceLocation key) {
        return TagKey.create(Registries.BLOCK, key);
    }

    public static void load() {} // Method which exists purely to load the class.

}
