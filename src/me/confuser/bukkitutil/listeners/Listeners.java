package me.confuser.bukkitutil.listeners;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;

import me.confuser.bukkitutil.BukkitPlugin;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;

public abstract class Listeners <T extends BukkitPlugin> implements Listener {
	private Class<T> clazz;
	protected T plugin;
	
	@SuppressWarnings("unchecked")
	public Listeners() {
		clazz = ((Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0]);
		
		Method method = null;
		try {
			method = clazz.getDeclaredMethod("getPlugin", null);
		} catch (NoSuchMethodException | SecurityException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			plugin =  (T) method.invoke(null, method.getParameterTypes());
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void register() {
		Bukkit.getServer().getPluginManager().registerEvents(this, plugin);
	}
}
