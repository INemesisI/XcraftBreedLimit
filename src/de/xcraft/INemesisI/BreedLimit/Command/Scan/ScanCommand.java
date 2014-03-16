package de.xcraft.INemesisI.BreedLimit.Command.Scan;

import org.bukkit.Chunk;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import de.xcraft.INemesisI.BreedLimit.Manager.PluginManager;
import de.xcraft.INemesisI.Library.Command.XcraftCommand;
import de.xcraft.INemesisI.Library.Manager.XcraftCommandManager;
import de.xcraft.INemesisI.Library.Manager.XcraftPluginManager;

public class ScanCommand extends XcraftCommand {

	public ScanCommand(XcraftCommandManager cManager, String command, String name, String pattern, String usage, String desc, String permission) {
		super(cManager, command, name, pattern, usage, desc, permission);
	}

	@Override
	public boolean execute(XcraftPluginManager manager, CommandSender sender, String[] args) {
		if (!(sender instanceof Player))
			return true;
		boolean loaded;
		int radius = -1;
		int limit = -1;
		EntityType type = null;
		for (String arg : args) {
			if (arg.startsWith("r:")) {
				String r = arg.replace("r:", "");
				if (r.matches("\\d.*")) {
					radius = Integer.parseInt(r) / 16;
				} else
					return false;
			} else if (arg.startsWith("l:")) {
				String l = arg.replace("l:", "");
				if (l.matches("\\d.*")) {
					limit = Integer.parseInt(l);
				} else
					return false;
			} else if (arg.startsWith("t:")) {
				type = EntityType.valueOf(arg.replace("t:", ""));
			}
		}
		PluginManager pmanager = (PluginManager) manager;
		Player player = (Player) sender;
		pmanager.scan.clear();
		if (radius == -1) {
			for (Chunk chunk : player.getWorld().getLoadedChunks()) {
				String scan = scanChunk(chunk, type, limit);
				if (scan != null)
					pmanager.scan.add(scan);
			}
			pmanager.showPage(player, 1);
			return true;
		} else {
			Chunk pc = player.getLocation().getChunk();
			for (int x = pc.getX() - radius; x < pc.getX() + radius; x++) {
				for (int z = pc.getZ() - radius; z < pc.getZ() + radius; z++) {
					Chunk chunk = player.getWorld().getChunkAt(x, z);
					if (!chunk.isLoaded()) {
						chunk.load(false);
						loaded = false;
					} else
						loaded = true;
					String scan = scanChunk(chunk, type, limit);
					if (scan != null)
						pmanager.scan.add(scan);
					if (!loaded)
						chunk.unload(false, false);
				}
			}
		}
		pmanager.showPage(player, 1);
		return true;
	}

	public String scanChunk(Chunk chunk, EntityType type, int limit) {
		int a = 0;
		for (Entity e : chunk.getEntities()) {
			if (type != null) {
				if (e.getType().equals(type)) {
					a++;
				}
			} else if (e.getType() != EntityType.DROPPED_ITEM && e.getType() != EntityType.ITEM_FRAME) {
				a++;
			}
		}
		if (a > 0 && (limit == -1 || a >= limit)) {
			return "Chunk(" + chunk.getX() + ", " + chunk.getZ() + "): " + a + " Entities";
		} else
			return null;
	}
}
