package de.xcraft.inemesisi.breedlimit;

import java.io.File;
import java.io.IOException;
import org.bukkit.configuration.file.FileConfiguration;


public class ConfigManager {

	private BreedLimit			plugin;
	private FileConfiguration	config;
	private int					breedingCap;
	private int					chunkRadius;
	private int					wolfLimit;
	private int					ocelotLimit;
	private String				failMsg;

	public ConfigManager(BreedLimit plugin) {
		this.plugin = plugin;
	}

	public void load() {
		File check = new File(plugin.getDataFolder(), "config.yml");
		if (!check.exists()) {
			plugin.saveDefaultConfig();
			try {
				check.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		plugin.reloadConfig();
		config = plugin.getConfig();
		this.breedingCap = config.getInt("BreedingCap", 75);
		this.chunkRadius = config.getInt("ChunkRadius", 1);
		this.wolfLimit = config.getInt("WolfLimit", 5);
		this.ocelotLimit = config.getInt("OcelotLimit", 5);
		this.failMsg = config.getString("FailMsg", "The animal refuses the wheat - it is too crowded to breed!");
		failMsg = failMsg.replaceAll("&([a-f0-9])", "\u00A7$1");
	}

	public void save() {
		// TODO: Config save
	}

	public BreedLimit getPlugin() {
		return plugin;
	}

	public FileConfiguration getConfig() {
		return config;
	}

	public int getBreedingCap() {
		return breedingCap;
	}

	public int getChunkRadius() {
		return chunkRadius;
	}

	
	public int getWolfLimit() {
		return wolfLimit;
	}

	
	public int getOcelotLimit() {
		return ocelotLimit;
	}

	public String getFailMsg() {
		return failMsg;
	}
}
