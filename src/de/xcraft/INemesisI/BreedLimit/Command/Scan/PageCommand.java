package de.xcraft.INemesisI.BreedLimit.Command.Scan;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.xcraft.INemesisI.BreedLimit.Manager.PluginManager;
import de.xcraft.INemesisI.Library.Command.XcraftCommand;
import de.xcraft.INemesisI.Library.Manager.XcraftCommandManager;
import de.xcraft.INemesisI.Library.Manager.XcraftPluginManager;

public class PageCommand extends XcraftCommand {

	public PageCommand(XcraftCommandManager cManager, String command, String name, String pattern, String usage, String desc, String permission) {
		super(cManager, command, name, pattern, usage, desc, permission);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean execute(XcraftPluginManager manager, CommandSender sender, String[] args) {

		if (!(sender instanceof Player))
			return true;
		if (!args[0].matches("\\d*"))
			return false;
		Player player = (Player) sender;
		PluginManager pmanager = (PluginManager) manager;
		pmanager.showPage(player, 1);
		return true;
	}

}
