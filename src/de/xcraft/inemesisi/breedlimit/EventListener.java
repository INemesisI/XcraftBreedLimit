package de.xcraft.inemesisi.breedlimit;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.event.player.PlayerInteractEntityEvent;

public class EventListener implements Listener {

	private BreedLimit plugin;
	private ConfigManager cfg;
	private List<EntityType> allowedEntities = new ArrayList<EntityType>();

	public EventListener(BreedLimit instance) {
		plugin = instance;
		cfg = plugin.getCfg();
		allowedEntities.add(EntityType.CHICKEN);
		allowedEntities.add(EntityType.COW);
		allowedEntities.add(EntityType.OCELOT);
		allowedEntities.add(EntityType.PIG);
		allowedEntities.add(EntityType.SHEEP);
		allowedEntities.add(EntityType.WOLF);
	}

	@EventHandler
	public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
		if (event.getPlayer().hasPermission("breedlimit.exempt"))
			return;
		if (event.isCancelled())
			return;
		Material item = event.getPlayer().getItemInHand().getType();
		switch (event.getRightClicked().getType()) {
			case CHICKEN :
				if (item == Material.SEEDS || item == Material.MELON_SEEDS || item == Material.PUMPKIN_SEEDS
						|| item == Material.NETHER_WARTS) {
					event.setCancelled(checkBreeding(event.getRightClicked()));
				}
			case COW :
				if (item == Material.WHEAT)
					event.setCancelled(checkBreeding(event.getRightClicked()));
			case OCELOT :
				if (item == Material.RAW_FISH)
					event.setCancelled(checkBreeding(event.getRightClicked()));
			case PIG :
				if (item == Material.CARROT)
					event.setCancelled(checkBreeding(event.getRightClicked()));
			case SHEEP :
				if (item == Material.WHEAT)
					event.setCancelled(checkBreeding(event.getRightClicked()));
			case WOLF :
				if (item == Material.RAW_BEEF || item == Material.COOKED_BEEF || item == Material.COOKED_CHICKEN
						|| item == Material.RAW_CHICKEN || item == Material.ROTTEN_FLESH || item == Material.PORK) {
					event.setCancelled(checkBreeding(event.getRightClicked()));
				}
			default :
				break;
		}
		if (event.isCancelled())
			event.getPlayer().sendMessage(cfg.getFailMsg());
	}

	public void onEntitySpawn(CreatureSpawnEvent event) {
		if (event.getSpawnReason() == SpawnReason.EGG)
			event.setCancelled(checkBreeding(event.getEntity()));
	}

	public boolean checkBreeding(Entity entity) {
		int animals = 0, wolfs = 0, ocelots = 0;
		int r = cfg.getChunkRadius();
		List<Entity> entities = entity.getNearbyEntities(r * 16, r * 16, 5);
		for (Entity e : entities) {
			if (allowedEntities.contains(e.getType()))
				animals++;
			if (entity.getType() == EntityType.WOLF && e.getType() == EntityType.WOLF)
				wolfs++;
			if (entity.getType() == EntityType.OCELOT && e.getType() == EntityType.OCELOT)
				ocelots++;
		}
		if (animals > cfg.getBreedingCap() || wolfs > cfg.getWolfLimit() || ocelots > cfg.getOcelotLimit()) {
			return true;
		}
		return false;
	}
}
