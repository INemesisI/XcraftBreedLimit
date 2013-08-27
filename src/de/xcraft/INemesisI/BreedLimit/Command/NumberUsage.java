package de.xcraft.INemesisI.BreedLimit.Command;

import java.util.List;

import org.bukkit.command.CommandSender;

import de.xcraft.INemesisI.Library.Command.XcraftUsage;

public class NumberUsage extends XcraftUsage {

	public NumberUsage() {
		super("#", "#");
	}

	@Override
	public boolean validate(String arg) {
		return arg.matches("\\d.*");
	}

	@Override
	public String getFailMessage() {
		return "# must be a number!";
	}

	@Override
	public List<String> onTabComplete(List<String> list, CommandSender sender) {
		list.add(String.valueOf(10));
		list.add(String.valueOf(50));
		list.add(String.valueOf(100));
		return list;
	}
}
