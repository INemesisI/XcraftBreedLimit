package de.xcraft.INemesisI.BreedLimit.Manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Tameable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.event.player.PlayerInteractEntityEvent;

import de.xcraft.INemesisI.BreedLimit.XcraftBreedLimit;
import de.xcraft.INemesisI.Library.XcraftEventListener;

public class EventListener extends XcraftEventListener {

	private final Map<EntityType, List<Material>> breedItemList = new HashMap<EntityType, List<Material>>();
	private final ConfigManager cManager;

	public EventListener(XcraftBreedLimit plugin) {
		super(plugin);
		cManager = plugin.getConfigManager();
		List<Material> list = new ArrayList<Material>();
		list.add(Material.SEEDS);
		list.add(Material.MELON_SEEDS);
		list.add(Material.PUMPKIN_SEEDS);
		list.add(Material.NETHER_WARTS);
		breedItemList.put(EntityType.CHICKEN, new ArrayList<Material>(list));
		list.clear();
		list.add(Material.WHEAT);
		breedItemList.put(EntityType.COW, new ArrayList<Material>(list));
		list.clear();
		list.add(Material.RAW_FISH);
		breedItemList.put(EntityType.OCELOT, new ArrayList<Material>(list));
		list.clear();
		list.add(Material.CARROT);
		breedItemList.put(EntityType.PIG, new ArrayList<Material>(list));
		list.clear();
		list.add(Material.WHEAT);
		breedItemList.put(EntityType.SHEEP, new ArrayList<Material>(list));
		list.add(Material.RAW_BEEF);
		list.add(Material.COOKED_BEEF);
		list.add(Material.COOKED_CHICKEN);
		list.add(Material.RAW_CHICKEN);
		list.add(Material.ROTTEN_FLESH);
		list.add(Material.PORK);
		breedItemList.put(EntityType.WOLF, new ArrayList<Material>(list));
		list.clear();
		list.add(Material.GOLDEN_APPLE);
		list.add(Material.GOLDEN_CARROT);
		breedItemList.put(EntityType.HORSE, new ArrayList<Material>(list));
	}

	@EventHandler
	public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
		if (event.getPlayer().hasPermission("breedlimit.exempt")) {
			;// return;
		}
		if (event.isCancelled())
			return;
		Material item = event.getPlayer().getItemInHand().getType();
		Entity entity = event.getRightClicked();
		if (breedItemList.containsKey(entity.getType()) && breedItemList.get(entity.getType()).contains(item)) {
			if (entity instanceof Tameable && !((Tameable) entity).isTamed())
				return;
			event.setCancelled(checkBreeding(entity, event.getPlayer()));
		}
	}

	@EventHandler
	public void onEntitySpawn(CreatureSpawnEvent event) {
		if (event.getSpawnReason() == SpawnReason.EGG) {
			event.setCancelled(checkBreeding(event.getEntity(), null));
		}
	}

	public boolean checkBreeding(Entity entity, Player player) {
		// setup count
		Map<EntityType, Integer> count = new HashMap<EntityType, Integer>();
		for (EntityType key : cManager.limits.keySet()) {
			if (key.equals(entity.getType())) {
				count.put(key, 0);
			}
		}
		// count all entities
		int r = cManager.radius;
		Chunk c = entity.getLocation().getChunk();
		for (int x = c.getX() - r; x < c.getX() + r; x++) {
			for (int z = c.getZ() - r; z < c.getZ() + r; z++) {
				for (Entity e : c.getWorld().getChunkAt(x, z).getEntities()) {
					EntityType t = e.getType();
					if (count.containsKey(t)) {
						count.put(t, count.get(t) + 1);
					}
				}
			}
		}
		// check if we go over the limit
		for (EntityType key : count.keySet()) {
			if (count.get(key) > cManager.limits.get(key)) {
				if (player != null) {
					PluginManager pManager = (PluginManager) plugin.getPluginManager();
					if (pManager.hasLicence(player.getName(), entity.getType())) {
						pManager.removeLicence(player.getName(), entity.getType());
						// Messenger.sendInfo(player, cManager.messages.get(entity), false);
						return false;
					}
					plugin.getMessenger().sendInfo(player, cManager.getMessage(key), false);
				}
				return true;
			}
		}
		return false;
	}
}
