package de.xcraft.INemesisI.BreedLimit.Command;

import org.bukkit.Chunk;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import de.xcraft.INemesisI.BreedLimit.Manager.PluginManager;
import de.xcraft.INemesisI.Library.Command.XcraftCommand;
import de.xcraft.INemesisI.Library.Manager.XcraftPluginManager;

public class ScanCommand extends XcraftCommand {

	public ScanCommand() {
		super("breedlimit", "scan", "s.*", "<Limit> [Type]",
				"Scans for chunks with <limit> entities", "XcraftBreedLimit.Scan");
	}

	@Override
	public boolean execute(XcraftPluginManager manager, CommandSender sender, String[] args) {
		if (!(sender instanceof Player))
			return true;
		if (!args[0].matches("\\d*"))
			return false;
		int min = Integer.parseInt(args[0]);
		EntityType type = null;
		if (args.length > 1)
			type = EntityType.valueOf(args[1]);
		boolean etype = type != null;
		PluginManager pmanager = (PluginManager) manager;
		Player player = (Player) sender;
		pmanager.scan.clear();
		for (Chunk chunk : player.getWorld().getLoadedChunks()) {
			int a = 0;
			Entity[] list = chunk.getEntities();
			for (int i = 0; i < list.length; i++) {
				Entity e = list[i];
				if (etype) {
					if (e.getType().equals(type))
						a++;
				} else if (e.getType() != EntityType.DROPPED_ITEM
						&& e.getType() != EntityType.ITEM_FRAME)
					a++;

			}
			if (a > min) {
				pmanager.scan.add("Chunk(" + chunk.getX() + ", " + chunk.getZ() + "): " + a
						+ " Entities");
			}
		}
		pmanager.showPage(player, 1);
		return true;
	}
}
