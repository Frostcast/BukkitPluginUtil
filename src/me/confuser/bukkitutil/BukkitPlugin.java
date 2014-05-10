package me.confuser.bukkitutil;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class BukkitPlugin extends JavaPlugin {
	private static BukkitPlugin statPlugin;
	
	public BukkitPlugin() {
		statPlugin = this;
	}

	public abstract String getPluginFriendlyName();

	public abstract String getPermissionBase();
	
	public abstract void setupConfigs();

	public abstract void setupCommands();

	public abstract void setupListeners();
	
	public abstract void setupRunnables();

	public void registerListener(Listener listener) {
		getServer().getPluginManager().registerEvents(listener, this);
	}
	
	public static BukkitPlugin getBukkitPlugin() {
		return statPlugin;
	}
}
