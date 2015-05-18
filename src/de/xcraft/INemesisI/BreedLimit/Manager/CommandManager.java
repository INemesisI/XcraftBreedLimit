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

		registerCommand(new ScanCommand(this, "breedlimit", "scan", "s.*", "<KEY> ...", "Scans for chunks with # entities", "XcraftBreedLimit.Scan"));
		registerCommand(new TpCommand(this, "breedlimit", "tp", "t.*", "<#>", "Teleports you to entry <#>", "XcraftBreedLimit.Scan"));
		registerCommand(new PageCommand(this, "breedlimit", "page", "p.*", "<#>", "Shows page <page>", "XcraftBreedLimit.Scan"));
		registerCommand(new ListLicencesCommand(this, "licence", "list", "l.*", "", "lists all current licences", "XcraftBreedLimit.Licence"));
		registerCommand(new BuyLicenceCommand(this, "licence", "buy", "b.*", "<Animal> [quantity]", "buy a licence for an animal",
				"XcraftBreedLimit.Licence"));

		registerUsage(new TypeUsage());

	}

}
