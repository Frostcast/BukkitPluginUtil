package me.confuser.bukkitutil.commands;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Vector;

import me.confuser.bukkitutil.BukkitPlugin;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public abstract class MultiCommandHandler<T extends BukkitPlugin> extends BukkitCommand<T> {
	private HashMap<String, SubCommand<T>> commands = new HashMap<String, SubCommand<T>>();

	// Custom messages, allow whatever is using this to override
	private String commandMessage = ChatColor.GOLD + "" + ChatColor.BOLD + plugin.getPluginFriendlyName();
	private String commandTypeHelpMessage = ChatColor.GOLD + "Type /" + getName() + " help for help";
	private String commandNoExistMessage = ChatColor.RED + "Command doesn't exist.";
	private String errorOccuredMessage = ChatColor.RED + "An error occured while executing the command. Check the console";
	private String noPermissionMessage = ChatColor.RED + "You do not have permission for this command";

	public MultiCommandHandler(String name) {
		super(name);
		
		register();
	}
	
	public abstract void registerCommands();
	
	public void commandNotFound(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		sender.sendMessage(commandNoExistMessage);
		sender.sendMessage(commandTypeHelpMessage);
	}

	public void registerSubCommand(SubCommand<T> command) {
		commands.put(command.getName(), command);
	}

	public HashMap<String, SubCommand<T>> getCommands() {
		return commands;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if (args == null || args.length < 1) {
			sender.sendMessage(commandMessage);
			sender.sendMessage(commandTypeHelpMessage);
			return true;
		}

		if (args[0].equalsIgnoreCase("help")) {
			help(sender);
			return true;
		}

		String sub = args[0];

		// Remove sub from args
		Vector<String> vec = new Vector<String>();
		vec.addAll(Arrays.asList(args));
		vec.remove(0);
		String [] subArgs = vec.toArray(new String[0]);
		
		// Clean up
		vec = null;

		if (!commands.containsKey(sub)) {
			commandNotFound(sender, cmd, commandLabel, args);
			return true;
		}
		try {
			SubCommand<?> command = commands.get(sub);

			if (!hasPermission(sender, command))
				sender.sendMessage(noPermissionMessage);
			else {
				boolean showHelp = false;
				
				if (command instanceof PlayerSubCommand && sender instanceof Player) {
					showHelp = ((PlayerSubCommand<?>) command).onPlayerCommand((Player) sender, subArgs);
				} else {
					showHelp = command.onCommand(sender, subArgs);
				}
				
				
				if (!showHelp && command.getHelp() != null)
					sender.sendMessage(ChatColor.RED + "/" + getName() + " " + command.getHelp());
					
			}
		} catch (Exception e) {
			e.printStackTrace();
			sender.sendMessage(errorOccuredMessage);
			sender.sendMessage(commandTypeHelpMessage);
		}
		return true;
	}
	
	private boolean hasPermission(CommandSender sender, SubCommand<?> command) {
		return sender.hasPermission(plugin.getPermissionBase() + "." + command.getPermission());
	}

	public void help(CommandSender p) {
		p.sendMessage(ChatColor.GOLD + "/" + getName() + " <command> <args>");
		p.sendMessage(ChatColor.GOLD + "Commands are as follows:");

		for (SubCommand<?> v : commands.values()) {
			if (hasPermission(p, v) && v.getHelp() != null)
				p.sendMessage(ChatColor.AQUA + v.getName() + " " + v.getHelp());
		}
	}
}
