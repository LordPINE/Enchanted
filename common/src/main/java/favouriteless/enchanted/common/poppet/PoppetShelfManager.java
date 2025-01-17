package favouriteless.enchanted.common.poppet;

import favouriteless.enchanted.common.blocks.entity.PoppetShelfBlockEntity;
import favouriteless.enchanted.common.poppet.PoppetShelfSavedData.PoppetEntry;
import net.minecraft.world.entity.player.Player;
import net.minecraft.server.level.ServerLevel;

import java.util.ArrayList;
import java.util.List;

public class PoppetShelfManager {

	public static PoppetShelfInventory getInventoryFor(PoppetShelfBlockEntity shelf) {
		if(shelf.getLevel() instanceof ServerLevel) {
			PoppetShelfSavedData data = PoppetShelfSavedData.get(shelf.getLevel());
			resolveAbsentShelf(data, shelf);
			return data.SHELF_STORAGE.get(PoppetShelfSavedData.getShelfIdentifier(shelf));
		}
		return null;
	}

	public static void removeShelf(PoppetShelfBlockEntity shelf) {
		if(shelf.getLevel() instanceof ServerLevel) {
			PoppetShelfSavedData data = PoppetShelfSavedData.get(shelf.getLevel());
			String identifier = PoppetShelfSavedData.getShelfIdentifier(shelf);
			data.removePoppetUUIDs(identifier, data.SHELF_STORAGE.get(identifier));
			data.SHELF_STORAGE.remove(identifier);
		}
	}

	public static void resolveAbsentShelf(PoppetShelfSavedData data, PoppetShelfBlockEntity shelf) {
		String identifier = PoppetShelfSavedData.getShelfIdentifier(shelf);
		if(!data.SHELF_STORAGE.containsKey(identifier)) {
			data.SHELF_STORAGE.put(identifier, new PoppetShelfInventory(shelf.getLevel(), shelf.getBlockPos()));
		}
	}

	public static List<PoppetEntry> getEntriesFor(Player player) {
		if(!player.level().isClientSide) {
			PoppetShelfSavedData data = PoppetShelfSavedData.get(player.level());
			if(data.PLAYER_POPPETS.containsKey(player.getUUID())) {
				List<PoppetEntry> list = data.PLAYER_POPPETS.get(player.getUUID());
				if(list != null)
					return list;
			}
		}
		return new ArrayList<>();
	}

}
