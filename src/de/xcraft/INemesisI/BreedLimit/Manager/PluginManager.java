package de.xcraft.INemesisI.BreedLimit.Manager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import de.xcraft.INemesisI.BreedLimit.XcraftBreedLimit;
import de.xcraft.INemesisI.Library.Manager.XcraftPluginManager;

public class PluginManager extends XcraftPluginManager {
	public final List<String> scan = new ArrayList<String>();
	private Map<String, Map<EntityType, Integer>> licences;

	public PluginManager(XcraftBreedLimit plugin) {
		super(plugin);
	}

	@Override
	public XcraftBreedLimit getPlugin() {
		return (XcraftBreedLimit) plugin;
	}

	public Map<String, Map<EntityType, Integer>> getLicences() {
		return licences;
	}

	public void setLicences(Map<String, Map<EntityType, Integer>> licences) {
		this.licences = licences;
	}

	public Map<EntityType, Integer> getLicence(String player) {
		return licences.get(player);
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
