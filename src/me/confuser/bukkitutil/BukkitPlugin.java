package me.confuser.bukkitutil;

import org.bukkit.plugin.java.JavaPlugin;

public abstract class BukkitPlugin extends JavaPlugin {
	protected static BukkitPlugin statPlugin;
	
	public BukkitPlugin() {
		statPlugin = this;
	}
	
	public abstract void onEnable();
	
	public abstract String getPluginFriendlyName();

	public abstract String getPermissionBase();
	
	public abstract void setupConfigs();

	public abstract void setupCommands();

	public abstract void setupListeners();
	
	public abstract void setupRunnables();

	public static BukkitPlugin getBukkitPlugin() {
		return statPlugin;
	}
}
