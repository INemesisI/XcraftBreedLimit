package de.xcraft.INemesisI.BreedLimit;

import net.milkbowl.vault.economy.Economy;

import org.bukkit.plugin.RegisteredServiceProvider;

import de.xcraft.INemesisI.BreedLimit.Manager.CommandManager;
import de.xcraft.INemesisI.BreedLimit.Manager.ConfigManager;
import de.xcraft.INemesisI.BreedLimit.Manager.EventListener;
import de.xcraft.INemesisI.BreedLimit.Manager.PluginManager;
import de.xcraft.INemesisI.Utils.XcraftPlugin;

public class XcraftBreedLimit extends XcraftPlugin {
	public Economy economy;

	private boolean setupEconomy() {
		RegisteredServiceProvider<Economy> economyProvider = this.getServer().getServicesManager()
				.getRegistration(net.milkbowl.vault.economy.Economy.class);
		if (economyProvider != null) {
			economy = economyProvider.getProvider();
		}
		return economy != null;
	}

	@Override
	protected void setup() {
		this.pluginManager = new PluginManager(this);
		this.configManager = new ConfigManager(this);
		this.eventListener = new EventListener(this);
		this.commandManager = new CommandManager(this);
		this.setupEconomy();
		configManager.load();
	}
}
