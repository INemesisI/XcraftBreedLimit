package de.xcraft.INemesisI.BreedLimit.Command;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.xcraft.INemesisI.BreedLimit.Manager.PluginManager;
import de.xcraft.INemesisI.Utils.Command.XcraftCommand;
import de.xcraft.INemesisI.Utils.Manager.XcraftPluginManager;
import de.xcraft.INemesisI.Utils.Message.Messenger;

public class TpCommand extends XcraftCommand {

	public TpCommand() {
		super("bl", "tp", "t.*", "<#>", "Teleports you to entry <#>", "XcraftBreedLimit.Tp");
	}

	@Override
	public boolean execute(XcraftPluginManager manager, CommandSender sender, String[] args) {
		if (!(sender instanceof Player))
			return true;
		if (!args[1].matches("\\d*"))
			return false;
		PluginManager pmanager = (PluginManager) manager;
		Player player = (Player) sender;
		int entry = Integer.parseInt(args[1]) - 1;
		if (entry < 0 || pmanager.scan.size() <= entry) {
			Messenger.sendInfo(player, ChatColor.RED + "This Entry does not exist!", true);
			return true;
		}
		String[] split = pmanager.scan.get(entry).split(":")[0].replace("Chunk(", "").replace(")", "").split(", ");
		int x = Integer.parseInt(split[0]);
		int z = Integer.parseInt(split[1]);
		player.teleport(new Location(player.getWorld(), x * 16, 62, z * 16));
		return false;
	}

}
