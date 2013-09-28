package de.xcraft.INemesisI.BreedLimit;

import net.milkbowl.vault.economy.Economy;

import org.bukkit.plugin.RegisteredServiceProvider;

import de.xcraft.INemesisI.BreedLimit.Manager.CommandManager;
import de.xcraft.INemesisI.BreedLimit.Manager.ConfigManager;
import de.xcraft.INemesisI.BreedLimit.Manager.EventListener;
import de.xcraft.INemesisI.BreedLimit.Manager.PluginManager;
import de.xcraft.INemesisI.Library.XcraftPlugin;
import de.xcraft.INemesisI.Library.Message.Messenger;

//@formatter:off
/***
* @author INemesisI
*     by _____   __                         _      ____
*       /  _/ | / /__  ____ ___  ___  _____(_)____/  _/ 
*       / //  |/ / _ \/ __ `__ \/ _ \/ ___/ / ___// /  
*     _/ // /|  /  __/ / / / / /  __(__  ) (__  )/ /   
*    /___/_/ |_/\___/_/ /_/ /_/\___/____/_/____/___/                                              
*/ 
//@formatter:on

public class XcraftBreedLimit extends XcraftPlugin {

	private PluginManager pluginManager = null;
	private ConfigManager configManager = null;
	private CommandManager commandManager = null;
	private EventListener eventListener = null;
	private Messenger messenger = null;
	private Economy economy;

	@Override
	protected void setup() {
		this.messenger = Messenger.getInstance(this);
		this.pluginManager = new PluginManager(this);
		this.configManager = new ConfigManager(this);
		this.eventListener = new EventListener(this);
		this.commandManager = new CommandManager(this);
		this.setupEconomy();
		configManager.load();
	}

	@Override
	public PluginManager getPluginManager() {
		return pluginManager;
	}

	@Override
	public ConfigManager getConfigManager() {
		return configManager;
	}

	@Override
	public CommandManager getCommandManager() {
		return commandManager;
	}

	@Override
	public EventListener getEventListener() {
		return eventListener;
	}

	@Override
	public Messenger getMessenger() {
		return messenger;
	}

	public Economy getEconomy() {
		return economy;
	}

	private boolean setupEconomy() {
		RegisteredServiceProvider<Economy> economyProvider = this.getServer().getServicesManager()
				.getRegistration(net.milkbowl.vault.economy.Economy.class);
		if (economyProvider != null) {
			economy = economyProvider.getProvider();
		}
		return economy != null;
	}
}
