package de.xcraft.INemesisI.BreedLimit.Command;

import org.bukkit.Chunk;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import de.xcraft.INemesisI.BreedLimit.Manager.PluginManager;
import de.xcraft.INemesisI.Utils.Command.XcraftCommand;
import de.xcraft.INemesisI.Utils.Manager.XcraftPluginManager;

public class ScanCommand extends XcraftCommand {

	public ScanCommand() {
		super("bl", "scan", "s.*", "<limit>", "Scans for a chunk with <limit> entities", "XcraftBreedLimit.Scan");
	}

	@Override
	public boolean execute(XcraftPluginManager manager, CommandSender sender, String[] args) {
		if (!(sender instanceof Player))
			return true;
		if (!args[1].matches("\\d*"))
			return false;
		int min = Integer.parseInt(args[1]);
		PluginManager pmanager = (PluginManager) manager;
		Player player = (Player) sender;
		pmanager.scan.clear();
		for (Chunk chunk : player.getWorld().getLoadedChunks()) {
			int a = 0;
			Entity[] list = chunk.getEntities();
			for (int i = 0; i < list.length; i++) {
				Entity e = list[i];
				if (e.getType() != EntityType.DROPPED_ITEM && e.getType() != EntityType.ITEM_FRAME)
					a++;
			}
			if (a > min) {
				pmanager.scan.add("Chunk(" + chunk.getX() + ", " + chunk.getZ() + "): " + a + " Entities");
			}
		}
		pmanager.showPage(player, 1);
		return false;
	}
}
