package de.xcraft.INemesisI.BreedLimit.Command;

import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;

import de.xcraft.INemesisI.Library.Command.XcraftUsage;

public class TypeUsage extends XcraftUsage {

	public TypeUsage() {
		super("TYPE", "Type");
	}

	@Override
	public boolean validate(String arg) {
		return EntityType.fromName(arg) != null;
	}

	@Override
	public String getFailMessage() {
		return "You need to provide a EntityType";
	}

	@Override
	public List<String> onTabComplete(List<String> list, CommandSender sender) {
		for (EntityType type : EntityType.values()) {
			list.add(type.toString());
		}
		return list;
	}
}