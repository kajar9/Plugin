package com.wolvencraft.yasp;

import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import com.wolvencraft.yasp.db.Database;
import com.wolvencraft.yasp.db.data.normal.Settings;
import com.wolvencraft.yasp.db.exceptions.DatabaseConnectionException;
import com.wolvencraft.yasp.listeners.*;
import com.wolvencraft.yasp.util.Message;

public class StatsPlugin extends JavaPlugin {
	private static StatsPlugin instance;
	private Database database;
	
	@Override
	public void onEnable() {
		instance = this;
		
		getConfig().options().copyDefaults(true);
		saveConfig();
		new Settings(this);
		
		if(!Database.testConnection()) {
			Message.log(Level.SEVERE, "Could not establish a connection to the database.");
			this.setEnabled(false);
			return;
		}
		
		try { database = new Database(); }
		catch (DatabaseConnectionException e) {
			Message.log(Level.SEVERE, e.getMessage());
			if (Settings.getDebug()) e.printStackTrace();
			this.setEnabled(false);
			return;
		} catch (Exception e) {
			Message.log(Level.SEVERE, e.getMessage());
			if (Settings.getDebug()) e.printStackTrace();
			this.setEnabled(false);
			return;
		}
		
		if (database == null) {
			instance = null;
			this.setEnabled(false);
			return;
		}

		Settings.retrieveData();
		
		new PlayerListener(this);
		new BlockListener(this);
		new EntityListener(this);
		
		Bukkit.getScheduler().runTaskTimerAsynchronously(this, new DataCollector(), 0L, Settings.getPing());
	}

	@Override
	public void onDisable() {
		Message.log("Plugin shutting down");
		try {
			DataCollector.pluginShutdown();
			DataCollector.pushAllData();
			instance = null;
			Bukkit.getScheduler().cancelAllTasks();
			DataCollector.clear();
		} catch (Exception e) { }
	}
	
	public static StatsPlugin getInstance() 		{ return instance; }
}
