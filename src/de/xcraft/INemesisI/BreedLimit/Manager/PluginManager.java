package de.xcraft.INemesisI.BreedLimit.Manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import de.xcraft.INemesisI.BreedLimit.XcraftBreedLimit;
import de.xcraft.INemesisI.Library.Manager.XcraftPluginManager;

public class PluginManager extends XcraftPluginManager {
	public final List<String> scan = new ArrayList<String>();
	private final Map<String, List<EntityType>> licences = new HashMap<String, List<EntityType>>();

	// private final Map<EntityType, Integer> prices = new HashMap<EntityType, Integer>();

	public PluginManager(XcraftBreedLimit plugin) {
		super(plugin);
	}

	@Override
	public XcraftBreedLimit getPlugin() {
		return (XcraftBreedLimit) plugin;
	}

	public void addLicence(String player, EntityType type) {
		licences.get(type).add(type);
	}

	public boolean removeLicence(String player, EntityType type) {
		return licences.get(player).remove(type);
	}

	public boolean hasLicence(String player, EntityType type) {
		return licences.containsKey(player) && licences.get(player).contains(type);
	}

	public void showPage(Player player, int page) {
		plugin.getMessenger().sendInfo(
				player,
				ChatColor.YELLOW + "Scan Data page" + ChatColor.DARK_GRAY + "[" + ChatColor.GOLD + page + ChatColor.DARK_GRAY + "/"
						+ (scan.size() / 10 + 1) + "]", true);
		page = page - 1;
		for (int i = (page * 10); i < (page * 10) + 10; i++) {
			if (i < scan.size()) {
				plugin.getMessenger().sendInfo(player, ChatColor.GOLD + "" + (i + 1) + ChatColor.WHITE + ": " + ChatColor.AQUA + scan.get(i), true);
			} else {
				break;
			}
		}
	}

}
