package me.confuser.bukkitutil.commands;

import me.confuser.bukkitutil.BukkitPlugin;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class BukkitSubCommandWrapper <T extends BukkitPlugin> extends BukkitCommand<T> {
	private SubCommand<T> command;
	
	public BukkitSubCommandWrapper(String name, SubCommand<T> command) {
		super(name);
		
		this.command = command;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String commandLabel, String args[]) {
		return this.command.onCommand(sender, args);
	}
}
