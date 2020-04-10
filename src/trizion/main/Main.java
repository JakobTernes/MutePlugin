package trizion.main;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import trizion.commands.MutePlayerCommand;
import trizion.commands.UnmutePlayerCommand;
import trizion.listeners.MessageListener;
import trizion.mysql.Mysql;

public class Main extends JavaPlugin implements Listener{
	private static Main plugin;
	public void onEnable() {
		plugin = this;
		mysqlStartup();
		
		getCommand("mute").setExecutor(new MutePlayerCommand());	
		getCommand("unmute").setExecutor(new UnmutePlayerCommand());
		
		PluginManager pluginManager = Bukkit.getPluginManager();
		pluginManager.registerEvents(new MessageListener(), this);		
	}
	public static Main getPlugin() {
		return plugin;
	}
	public void mysqlStartup() {
		while(!Mysql.isConnected()) {
			Mysql.connect();
		}
	}
}
