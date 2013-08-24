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
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.event.player.PlayerInteractEntityEvent;

import de.xcraft.INemesisI.BreedLimit.XcraftBreedLimit;
import de.xcraft.INemesisI.Utils.XcraftEventListener;

public class EventListener extends XcraftEventListener {

	private final Map<EntityType, List<Material>> breedItemList = new HashMap<EntityType, List<Material>>();
	private final ConfigManager cManager;

	public EventListener(XcraftBreedLimit plugin) {
		super(plugin);
		cManager = (ConfigManager) plugin.configManager;
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
		if (event.getPlayer().hasPermission("breedlimit.exempt"))
			;//return;
		if (event.isCancelled())
			return;
		Material item = event.getPlayer().getItemInHand().getType();
		Entity entity = event.getRightClicked();
		if (breedItemList.containsKey(entity.getType()) && breedItemList.get(entity.getType()).contains(item)) {
			event.setCancelled(checkBreeding(entity, event.getPlayer()));
		}
	}

	public void onEntitySpawn(CreatureSpawnEvent event) {
		if (event.getSpawnReason() == SpawnReason.EGG)
			event.setCancelled(checkBreeding(event.getEntity(), null));
	}

	public boolean checkBreeding(Entity entity, Player player) {
		Map<String, Integer> count = new HashMap<String, Integer>();
		for (String key : cManager.limits.keySet()) {
			if ((cManager.groups.containsKey(key))) {
					if(cManager.groups.get(key).contains(entity.getType()) && !count.containsKey(key)) {
						count.put(key, 0);	
					}
			} else if (EntityType.valueOf(key).equals(entity.getType())) {
				count.put(key, 0);
			}
		}
		int r = cManager.radius;
		List<EntityType> entities = new ArrayList<EntityType>();
		Chunk c = entity.getLocation().getChunk();
		for (int x = c.getX()-r ; x < c.getX()+r; x++) {
			for (int z = c.getZ()-r ; z < c.getZ()+r; z++) {
				for (Entity e : c.getWorld().getChunkAt(x, z).getEntities())
					entities.add(e.getType());
			}
		}
		for (EntityType e : entities) {
			for (String key : count.keySet()) {
				if (!cManager.groups.keySet().contains(key)) {
					if (EntityType.valueOf(key).equals(e))
						count.put(key, count.get(key) + 1);
				} else {
					if (cManager.groups.get(key).contains(e)) {
						count.put(key, count.get(key) + 1);
					}
				}
			}
		}
		for (String key : count.keySet()) {
			if (count.get(key) > cManager.limits.get(key)) {
				if (player != null) {
					PluginManager pManager = (PluginManager) plugin.pluginManager;
					if (pManager.hasLicence(player.getName(), entity.getType())) {
						pManager.removeLicence(player.getName(), entity.getType());
						// Messenger.sendInfo(player, cManager.messages.get(entity), false);
						return true;
					}
					plugin.messenger.sendInfo(player, cManager.messages.get(key), false);
				}
				return false;
			}
		}
		return true;
	}

}
