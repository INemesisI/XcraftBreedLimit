package de.xcraft.INemesisI.BreedLimit.Command;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.xcraft.INemesisI.BreedLimit.Manager.PluginManager;
import de.xcraft.INemesisI.Library.Command.XcraftCommand;
import de.xcraft.INemesisI.Library.Manager.XcraftPluginManager;

public class PageCommand extends XcraftCommand {

	public PageCommand() {
		super("breedlimit", "page", "p.*", "<page>", "Shows page <page>", "XcraftBreedLimit.Scan");
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
