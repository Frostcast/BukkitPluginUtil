package me.confuser.bukkitutil.commands;

import me.confuser.bukkitutil.BukkitPlugin;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public abstract class PlayerSubCommand<T extends BukkitPlugin> extends SubCommand<T> {

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
