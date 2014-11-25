package me.confuser.bukkitutil.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.plugin.java.JavaPlugin;

public class BukkitSubCommandWrapper implements CommandExecutor {
	private String name;
	private SubCommand<?> command;

	public BukkitSubCommandWrapper(String name, SubCommand<?> command) {
		this.name = name;
		this.command = command;
	}

	public void register() {
		((JavaPlugin) command.plugin).getCommand(name).setExecutor(this);

		if (this instanceof TabCompleter) {
			((JavaPlugin) command.plugin).getCommand(name).setTabCompleter((TabCompleter) this);
		}
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String commandLabel, String args[]) {
		return this.command.onCommand(sender, args);
	}
}
