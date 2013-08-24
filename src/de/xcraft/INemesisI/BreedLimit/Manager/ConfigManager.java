package de.xcraft.INemesisI.BreedLimit.Manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;

import de.xcraft.INemesisI.BreedLimit.XcraftBreedLimit;
import de.xcraft.INemesisI.Utils.Manager.XcraftConfigManager;

public class ConfigManager extends XcraftConfigManager {

	private FileConfiguration config;

	public int radius;
	public final Map<String, Integer> limits = new HashMap<String, Integer>();
	public final Map<String, List<EntityType>> groups = new HashMap<String, List<EntityType>>();
	public final Map<String, Integer> licences = new HashMap<String, Integer>();
	public final Map<String, String> messages = new HashMap<String, String>();

	public ConfigManager(XcraftBreedLimit plugin) {
		super(plugin);
	}

	@Override
	public void load() {
		config = plugin.getConfig();
		ConfigurationSection cs = config.getConfigurationSection("Breeding.Groups");
		for (String key : cs.getKeys(false)) {
			List<EntityType> list = new ArrayList<EntityType>();
			for (String type : cs.getString(key).split(", ")) {
				list.add(EntityType.valueOf(type));
			}
			groups.put(key, list);
		}
		cs = config.getConfigurationSection("Breeding.Limits");
		for (String key : cs.getKeys(false)) {
			if (groups.containsKey(key) || EntityType.valueOf(key) != null)
			limits.put(key, cs.getInt(key));
		}
		cs = config.getConfigurationSection("Breeding.Licence");
		for (String key : cs.getKeys(false)) {
			if (groups.containsKey(key) || EntityType.valueOf(key) != null)
			licences.put(key, cs.getInt(key));
		}
		cs = config.getConfigurationSection("Breeding.Message");
		for (String key : cs.getKeys(false)) {
			messages.put(key, cs.getString(key));
		}
		this.radius = config.getInt("Bredding.Radius", 2);
	}
	@Override
	public void save() {
	}
}
