package de.xcraft.INemesisI.BreedLimit.Manager;

import de.xcraft.INemesisI.BreedLimit.Command.BuyLicenceCommand;
import de.xcraft.INemesisI.BreedLimit.Command.GiveLicenceCommand;
import de.xcraft.INemesisI.BreedLimit.Command.ListLicencesCommand;
import de.xcraft.INemesisI.BreedLimit.Command.PageCommand;
import de.xcraft.INemesisI.BreedLimit.Command.ScanCommand;
import de.xcraft.INemesisI.BreedLimit.Command.TpCommand;
import de.xcraft.INemesisI.Library.XcraftPlugin;
import de.xcraft.INemesisI.Library.Manager.XcraftCommandManager;

public class CommandManager extends XcraftCommandManager {

	public CommandManager(XcraftPlugin plugin) {
		super(plugin);
	}

	@Override
	protected void registerCommands() {
		registerBukkitCommand("breedlimit");
		registerBukkitCommand("licence");
		
		registerCommand(new ScanCommand());
		registerCommand(new TpCommand());
		registerCommand(new PageCommand());
		registerCommand(new ListLicencesCommand());
		registerCommand(new GiveLicenceCommand());
		registerCommand(new BuyLicenceCommand());

	}

}
