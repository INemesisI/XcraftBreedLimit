package de.xcraft.INemesisI.BreedLimit.Command;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.xcraft.INemesisI.BreedLimit.Manager.PluginManager;
import de.xcraft.INemesisI.Utils.Command.XcraftCommand;
import de.xcraft.INemesisI.Utils.Manager.XcraftPluginManager;

public class PageCommand extends XcraftCommand {

	public PageCommand() {
		super("bl", "page", "p.*", "<page>", "Shows page <page>", "XcraftBreedLimit.Page");
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
