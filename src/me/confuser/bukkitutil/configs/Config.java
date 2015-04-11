package me.confuser.bukkitutil.configs;

import me.confuser.bukkitutil.BukkitPlugin;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Inspired by md_5
 * <p/>
 * An awesome super-duper-lazy Config lib! Just extend it, set some (non-static)
 * variables
 * <p/>
 * Originally by:
 *
 * @author codename_B
 * @author confuser
 * @version 2.2
 *          <p/>
 *          Modified by:
 */
public abstract class Config<T extends BukkitPlugin> {

  private Class<T> clazz;
  protected T plugin;

  protected File file = null;
  public YamlConfiguration conf = new YamlConfiguration();

  @SuppressWarnings("unchecked")
  public Config(Object file) {
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

    setFile(file);
  }

  /**
   * Must be called before using config.load() or config.save();
   *
   * @param input
   *
   * @return (Config) instance
   */
  public Config<T> setFile(Object input) {
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
  }

  ;

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

    try {
      conf.load(file);
    } catch (InvalidConfigurationException e) {
      plugin.getLogger().severe("Invalid yaml file " + file.getName());
      e.printStackTrace();

      plugin.getLogger().severe("Attempting to load default " + file.getName());

      file.renameTo(new File(plugin.getDataFolder(), file.getName().replace(".yml", "") + new SimpleDateFormat
              ("yyyy-MM-dd_HH-mm-ss").format(new Date()) + ".yml.invalid"));
      plugin.saveResource(file.getName(), false);

      onLoad(file);
    }

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
