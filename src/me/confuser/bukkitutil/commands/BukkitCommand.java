package me.confuser.bukkitutil.commands;

import me.confuser.bukkitutil.BukkitPlugin;

import org.bukkit.command.CommandExecutor;

public abstract class BukkitCommand implements CommandExecutor {
	private BukkitPlugin plugin = BukkitPlugin.getBukkitPlugin();
	
	private String name;
	
	public BukkitCommand(String name) {
		this.name = name;
	}
	
	public void register() {
		plugin.getCommand(name).setExecutor(this);
	}
	
	public String getName() {
		return name;
	}
}
