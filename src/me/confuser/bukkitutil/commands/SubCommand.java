package me.confuser.bukkitutil.commands;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;

import me.confuser.bukkitutil.BukkitPlugin;

import org.bukkit.command.CommandSender;

public abstract class SubCommand <T extends BukkitPlugin> {
	private Class<T> clazz;
	protected T plugin;

	private String name;
	
	@SuppressWarnings("unchecked")
	public SubCommand(String name) {
		this.name = name.toLowerCase();
		
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

	public abstract boolean onCommand(CommandSender sender, String[] args);

	public abstract String getHelp();

	public abstract String getPermission();
	
	public String getName() {
		return name;
	}

}
