package de.xcraft.INemesisI.BreedLimit.Manager;

import de.xcraft.INemesisI.BreedLimit.Command.TypeUsage;
import de.xcraft.INemesisI.BreedLimit.Command.Licence.BuyLicenceCommand;
import de.xcraft.INemesisI.BreedLimit.Command.Licence.ListLicencesCommand;
import de.xcraft.INemesisI.BreedLimit.Command.Scan.PageCommand;
import de.xcraft.INemesisI.BreedLimit.Command.Scan.ScanCommand;
import de.xcraft.INemesisI.BreedLimit.Command.Scan.TpCommand;
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
		registerCommand(new BuyLicenceCommand());

		registerUsage(new TypeUsage());

	}

}
