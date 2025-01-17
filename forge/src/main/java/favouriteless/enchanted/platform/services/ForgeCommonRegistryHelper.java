package favouriteless.enchanted.platform.services;

import favouriteless.enchanted.Enchanted;
import favouriteless.enchanted.common.items.ForgeNonAnimatedArmorItem;
import favouriteless.enchanted.common.items.NonAnimatedArmorItem;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ArmorItem.Type;
import net.minecraft.world.item.ArmorMaterials;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTab.DisplayItemsGenerator;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FireBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.common.util.ForgeSoundType;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.function.TriFunction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class ForgeCommonRegistryHelper implements ICommonRegistryHelper {

	public static final DeferredRegister<CreativeModeTab> TAB_REGISTRY = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Enchanted.MOD_ID);
	private static final RegistryMap registryMap = new RegistryMap();

	public static final List<SimpleJsonResourceReloadListener> dataLoaders = new ArrayList<>();


	@Override
	public <T> Supplier<T> register(Registry<? super T> registry, String name, Supplier<T> entry) {
		return registryMap.register(registry, name, entry);
	}

	@Override
	public <T extends AbstractContainerMenu> Supplier<MenuType<T>> registerMenu(String name, TriFunction<Integer, Inventory, FriendlyByteBuf, T> create) {
		return register(BuiltInRegistries.MENU, name, () -> IForgeMenuType.create(create::apply));
	}

	@Override
	public void register(ResourceLocation id, SimpleJsonResourceReloadListener loader) {
		dataLoaders.add(loader);
	}

	@Override
	public SoundType getSoundType(float volume, float pitch, Supplier<SoundEvent> breakSound, Supplier<SoundEvent> stepSound, Supplier<SoundEvent> placeSound, Supplier<SoundEvent> hitSound, Supplier<SoundEvent> fallSound) {
		return new ForgeSoundType(volume, pitch, breakSound, stepSound, placeSound, hitSound, fallSound);
	}

	@Override
	public Supplier<CreativeModeTab> registerCreativeTab(String name, Supplier<ItemStack> iconSupplier, DisplayItemsGenerator itemGenerator) {
		return TAB_REGISTRY.register(name, () -> CreativeModeTab.builder()
				.title(Component.translatable("tab." + Enchanted.MOD_ID + "." + name))
				.icon(iconSupplier)
				.displayItems(itemGenerator)
				.build());
	}

	@Override
	public void setFlammable(Block block, int igniteOdds, int burnOdds) {
		((FireBlock)Blocks.FIRE).setFlammable(block, igniteOdds, burnOdds);
	}

	@Override
	public Supplier<NonAnimatedArmorItem> registerNonAnimatedArmorItem(String name, ArmorMaterials material, Type type, String assetPath, Properties properties) {
		return register(BuiltInRegistries.ITEM, name, () -> new ForgeNonAnimatedArmorItem(material, type, assetPath, properties));
	}

	public static RegistryMap getRegistryMap() {
		return registryMap;
	}

	public static class RegistryMap {

		private final Map<ResourceLocation, DeferredRegister<?>> registries = new HashMap<>();

		private <T> RegistryObject<T> register(Registry<? super T> registry, String name, Supplier<T> entry) {
			DeferredRegister<T> reg = getDeferred(registry);
			return reg != null ? reg.register(name, entry) : null;
		}

		@SuppressWarnings({"unchecked"})
		public <T> DeferredRegister<T> getDeferred(Registry<? super T> registry) {
            return (DeferredRegister<T>)registries.computeIfAbsent(registry.key().location(), (key) -> {
				DeferredRegister<T> defReg = DeferredRegister.create(registry.key().location(), Enchanted.MOD_ID);
				defReg.register(FMLJavaModLoadingContext.get().getModEventBus());
				return defReg;
			});
		}

	}

}
