package me.confuser.bukkitutil.commands;

import me.confuser.bukkitutil.BukkitPlugin;

import org.bukkit.command.CommandSender;

public abstract class SubCommand {
	private String name;
	
	public SubCommand(String name) {
		this.name = name;
	}

	public BukkitPlugin plugin = BukkitPlugin.getBukkitPlugin();

	public abstract boolean onCommand(CommandSender sender, String[] args);

	public abstract String getHelp();

	public abstract String getPermission();
	
	public String getName() {
		return name;
	}

}
