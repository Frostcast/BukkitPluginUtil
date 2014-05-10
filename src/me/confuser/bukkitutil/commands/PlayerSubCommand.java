package me.confuser.bukkitutil.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public abstract class PlayerSubCommand extends SubCommand {

	public PlayerSubCommand(String name) {
		super(name);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, String[] args) {
		if (sender instanceof Player)
			return onPlayerCommand((Player) sender, args);
		
		return false;
	}
	
	public abstract boolean onPlayerCommand(Player player, String[] args);

}
