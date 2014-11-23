package me.confuser.bukkitutil;

import java.util.HashMap;
import java.util.UUID;

import me.confuser.bukkitutil.configs.Config;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class Message {
	private static HashMap<String, String> messages = new HashMap<String, String>(10);

	private String message;

	public Message(String key) {
		this.message = messages.get(key);
	}

	public Message replace(CharSequence oldChar, CharSequence newChar) {
		message = this.message.replace(oldChar, newChar);

		return this;
	}

	public Message set(String token, String replace) {
		return replace("[" + token + "]", replace);
	}

	public Message set(String token, Integer replace) {
		return replace("[" + token + "]", replace.toString());
	}
	
	public Message set(String token, Double replace) {
		return replace("[" + token + "]", replace.toString());
	}
	
	public Message set(String token, Long replace) {
		return replace("[" + token + "]", replace.toString());
	}
	
	public Message set(String token, Float replace) {
		return replace("[" + token + "]", replace.toString());
	}

	public boolean sendTo(UUID uuid) {
		Player player = Bukkit.getPlayer(uuid);

		if (player == null)
			return false;

		player.sendMessage(message);

		return true;
	}

	public void sendTo(CommandSender sender) {
		sender.sendMessage(message);
	}

	@Override
	public String toString() {
		return message;
	}

	public static Message get(String key) {
		return new Message(key);
	}

	public static String getString(String key) {
		return messages.get(key);
	}

	public static void load(YamlConfiguration config) {
		messages.clear();

		for (String key : config.getConfigurationSection("messages").getKeys(false)) {
			messages.put(key, ChatColor.translateAlternateColorCodes('&', config.getString("messages." + key).replace("\\n", "\n")));
		}
	}

	public static void load(Config<?> config) {
		load(config.conf);
	}
}
