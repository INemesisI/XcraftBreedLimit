package de.xcraft.INemesisI.BreedLimit.Command.Scan;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.xcraft.INemesisI.BreedLimit.Manager.PluginManager;
import de.xcraft.INemesisI.Library.Command.XcraftCommand;
import de.xcraft.INemesisI.Library.Manager.XcraftPluginManager;

public class TpCommand extends XcraftCommand {

	public TpCommand() {
		super("breedlimit", "tp", "t.*", "<#>", "Teleports you to entry <#>", "XcraftBreedLimit.Scan");
	}

	@Override
	public boolean execute(XcraftPluginManager manager, CommandSender sender, String[] args) {
		if (!(sender instanceof Player))
			return true;
		if (!args[0].matches("\\d*"))
			return false;
		PluginManager pmanager = (PluginManager) manager;
		Player player = (Player) sender;
		int entry = Integer.parseInt(args[0]) - 1;
		if (entry < 0 || pmanager.scan.size() <= entry) {
			manager.plugin.getMessenger().sendInfo(player, ChatColor.RED + "This Entry does not exist!", true);
			return true;
		}
		String[] split = pmanager.scan.get(entry).split(":")[0].replace("Chunk(", "").replace(")", "").split(", ");
		int x = Integer.parseInt(split[0]);
		int z = Integer.parseInt(split[1]);
		player.teleport(new Location(player.getWorld(), x * 16, 62, z * 16));
		return true;
	}

}
