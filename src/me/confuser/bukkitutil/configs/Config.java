package me.confuser.bukkitutil.configs;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import me.confuser.bukkitutil.BukkitPlugin;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

/**
 * Inspired by md_5
 *
 * An awesome super-duper-lazy Config lib! Just extend it, set some (non-static)
 * variables
 *
 * Originally by:
 *
 * @author codename_B
 * @version 2.2
 *
 *          Modified by:
 * @author confuser
 *
 */
public abstract class Config {

	protected File file = null;
	public YamlConfiguration conf = new YamlConfiguration();
	public BukkitPlugin plugin = BukkitPlugin.getBukkitPlugin();

	public Config(Object file) {
		setFile(file);
	}

	/**
	 * Must be called before using config.load() or config.save();
	 *
	 * @param input
	 * @return (Config) instance
	 */
	public Config setFile(Object input) {
		// handle the File
		if (input == null) {
			new InvalidConfigurationException("File cannot be null!").printStackTrace();
		} else if (input instanceof File) {
			// the file, directly
			file = (File) input;
		} else if (input instanceof Plugin) {
			// the config.yml of the plugin
			file = getFile((Plugin) input);
		} else if (input instanceof String) {
			// the literal file from the string
			file = new File(plugin.getDataFolder(), (String) input);
		}
		return this;
	}

	/**
	 * Lazy load
	 */
	public void load() {
		if (file != null) {
			try {
				onLoad(file);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			new InvalidConfigurationException("File cannot be null!").printStackTrace();
		}

		afterLoad();
	}

	public abstract void afterLoad();

	public void setDefaults() {
		plugin.saveResource(file.getName(), false);
	};

	/**
	 * Lazy save
	 *
	 * @throws Exception
	 */
	public abstract void onSave();

	public void onLoad(File file) throws Exception {
		if (!file.exists()) {
			if (file.getParentFile() != null)
				file.getParentFile().mkdirs();

			// Set the defaults first
			setDefaults();
		}

		conf.load(file);

		// Look for defaults in the jar
		InputStream defConfigStream = plugin.getResource(file.getName());
		if (defConfigStream != null) {
			YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
			conf.setDefaults(defConfig);
			conf.options().copyDefaults(true);
		}

		save();
	}

	public void save() {
		onSave();

		try {
			conf.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/*
	 * Utility methods
	 */

	private File getFile(Plugin plugin) {
		return new File(plugin.getDataFolder(), "config.yml");
	}

}
