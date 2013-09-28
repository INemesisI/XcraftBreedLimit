package de.xcraft.INemesisI.BreedLimit.Command;

import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;

import de.xcraft.INemesisI.Library.Command.XcraftUsage;

public class TypeUsage extends XcraftUsage {

	public TypeUsage() {
		super("KEY", "Key");
	}

	@Override
	public boolean validate(String arg) {
		if (arg.matches("t:.*")) {
			try {
				EntityType.valueOf(arg.replace("t:", ""));
			} catch (IllegalArgumentException e) {
				return false;
			}
			return true;
		} else
			return (arg.matches("[l|r]:\\d.*"));
	}

	@Override
	public String getFailMessage() {
		return "&cWrong Usage. example: /bl scan t:CHICKEN l:10 r:20 searches for more than 10 chickens in a 20 block radius!";
	}

	@Override
	public List<String> onTabComplete(List<String> list, CommandSender sender) {
		for (EntityType type : EntityType.values()) {
			list.add("t:" + type.toString());
		}
		return list;
	}
}