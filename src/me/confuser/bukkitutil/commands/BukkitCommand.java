package me.confuser.bukkitutil.commands;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;

import me.confuser.bukkitutil.BukkitPlugin;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.TabCompleter;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class BukkitCommand<T extends BukkitPlugin> implements CommandExecutor {
	private Class<T> clazz;
	protected T plugin;

	private String name;

	@SuppressWarnings("unchecked")
	public BukkitCommand(String name) {
		this.name = name;

		clazz = ((Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0]);

		Method method = null;
		try {
			method = clazz.getDeclaredMethod("getPlugin", null);
		} catch (NoSuchMethodException | SecurityException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			plugin = (T) method.invoke(null, method.getParameterTypes());
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void register() {
		((JavaPlugin) plugin).getCommand(name).setExecutor(this);
		
		if (this instanceof TabCompleter) {
			((JavaPlugin) plugin).getCommand(name).setTabCompleter((TabCompleter) this);
		}
	}

	public String getName() {
		return name;
	}
}
