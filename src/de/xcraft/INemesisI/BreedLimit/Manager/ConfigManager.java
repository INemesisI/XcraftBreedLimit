package de.xcraft.INemesisI.BreedLimit.Manager;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;

import de.xcraft.INemesisI.BreedLimit.XcraftBreedLimit;
import de.xcraft.INemesisI.Library.Manager.XcraftConfigManager;

public class ConfigManager extends XcraftConfigManager {

	private FileConfiguration config;

	public int radius;
	public final Map<EntityType, Integer> limits = new HashMap<EntityType, Integer>();
	public final Map<EntityType, Integer> licences = new HashMap<EntityType, Integer>();
	private final Map<EntityType, String> messages = new HashMap<EntityType, String>();

	public ConfigManager(XcraftBreedLimit plugin) {
		super(plugin);
	}

	@Override
	public void load() {
		config = plugin.getConfig();
		ConfigurationSection cs = config.getConfigurationSection("Breeding.Limits");
		for (String key : cs.getKeys(false)) {
			if (EntityType.valueOf(key) != null)
				limits.put(EntityType.valueOf(key), cs.getInt(key));
		}
		cs = config.getConfigurationSection("Breeding.Licence");
		for (String key : cs.getKeys(false)) {
			if (EntityType.valueOf(key) != null)
				licences.put(EntityType.valueOf(key), cs.getInt(key));
		}
		cs = config.getConfigurationSection("Breeding.Message");
		for (String key : cs.getKeys(false)) {
			if (key.equals("default") || EntityType.valueOf(key) != null) {
				EntityType type = key.equals("default") ? EntityType.UNKNOWN : EntityType.valueOf(key);
				messages.put(type, cs.getString(key));
			}
		}
		this.radius = config.getInt("Bredding.Radius", 2);
	}

	@Override
	public void save() {
	}

	public String getMessage(EntityType type) {
		if (messages.containsKey(type))
			return messages.get(type);
		else
			return messages.get("default");
	}
}
