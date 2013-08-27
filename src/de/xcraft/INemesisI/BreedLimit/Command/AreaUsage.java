package de.xcraft.INemesisI.BreedLimit.Command;

import java.util.List;

import org.bukkit.command.CommandSender;

import de.xcraft.INemesisI.Library.Command.XcraftUsage;

public class AreaUsage extends XcraftUsage {

	public AreaUsage() {
		super("AREA", "Area");
	}

	@Override
	public boolean validate(String arg) {
		return arg.matches("\\d.*");
	}

	@Override
	public String getFailMessage() {
		return "The area must be a number!";
	}

	@Override
	public List<String> onTabComplete(List<String> list, CommandSender sender) {
		list.add(String.valueOf(5));
		list.add(String.valueOf(10));
		list.add(String.valueOf(20));
		return list;
	}
}