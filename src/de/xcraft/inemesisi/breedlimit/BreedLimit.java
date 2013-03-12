package de.xcraft.inemesisi.breedlimit;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;


public class BreedLimit extends JavaPlugin {

	private EventListener	eventlistener;
	private ConfigManager	config;
	private List<String>	scan	= new ArrayList<String>();

	@Override
	public void onDisable() {
	}

	@Override
	public void onEnable() {
		config = new ConfigManager(this);
		eventlistener = new EventListener(this);
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(eventlistener, this);
		config.load();
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String cmdLabel, String[] args) {
		if (args.length == 0) return true;
		if (args[0].equals("reload") && sender.hasPermission("breedlimit.reload")) {
			config.load();
			sendMsg((Player) sender, "config reloaded!");
		}
		if (args[0].equals("scan") && sender.hasPermission("beedlimit.scan")) {
			if (!(sender instanceof Player)) return true;
			if (!args[1].matches("\\d*")) return false;
			int min = Integer.parseInt(args[1]);
			Player player = (Player) sender;
			scan.clear();
			for (Chunk chunk : player.getWorld().getLoadedChunks()) {
				int a = 0;
				Entity[] list = chunk.getEntities();
				for (int i=0;i<list.length;i++) {
					Entity e = list[i];
					if (e.getType() != EntityType.DROPPED_ITEM && e.getType() != EntityType.ITEM_FRAME)
						a++;
				}
				if (a > min) {
					scan.add("Chunk(" + chunk.getX() + ", " + chunk.getZ() + "): " + a + " Entities");
				}
			}
			showPage(player, 1);
		}
		if (args[0].equals("page") && sender.hasPermission("beedlimit.scan")) {
			if (!(sender instanceof Player)) return true;
			if (!args[1].matches("\\d*")) return false;
			Player player = (Player) sender;
			showPage(player, Integer.parseInt(args[1]));
		}
		if (args[0].equals("tp") && sender.hasPermission("beedlimit.scan")) {
			if (!(sender instanceof Player)) return true;
			if (!args[1].matches("\\d*")) return false;
			Player player = (Player) sender;
			int entry = Integer.parseInt(args[1]) - 1;
			if (entry < 0 || scan.size() <= entry) {
				sendMsg(player, ChatColor.RED + "This Entry does not exist!");
				return true;
			}
			String[] split = scan.get(entry).split(":")[0].replace("Chunk(", "").replace(")", "").split(", ");
			int x = Integer.parseInt(split[0]);
			int z = Integer.parseInt(split[1]);
			player.teleport(new Location(player.getWorld(), x * 16, 62, z * 16));
		}
		return true;
	}

	public void showPage(Player player, int page) {
		sendMsg(player,
				ChatColor.YELLOW + "Scan Data page" + ChatColor.DARK_GRAY + "[" + ChatColor.GOLD + page + ChatColor.DARK_GRAY + "/" + (scan
						.size() / 10 + 1) + "]");
		page = page - 1;
		for (int i = (page * 10); i < (page * 10) + 10; i++) {
			if (i < scan.size()) {
				player.sendMessage(ChatColor.GOLD + "" + (i + 1) + ChatColor.WHITE + ": " + ChatColor.AQUA + scan
						.get(i));
			} else break;
		}
	}

	public void sendMsg(Player player, String string) {
		player.sendMessage("[" + ChatColor.DARK_AQUA + getDescription().getName() + ChatColor.WHITE + "] " + ChatColor.GRAY + string);
	}

	public ConfigManager getCfg() {
		return config;
	}
}
