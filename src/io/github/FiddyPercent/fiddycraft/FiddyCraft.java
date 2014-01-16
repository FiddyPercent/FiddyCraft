package io.github.FiddyPercent.fiddycraft;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class FiddyCraft extends JavaPlugin {
	  private  FileConfiguration VillagerNames;
	  private File VillagerNameFile;

    public void onDisable() {
        this.getLogger().info("FiddyCraft is Disabled");
        saveConfig();
        saveVillagerNames();
    }
	
    public void onEnable(){
    	loadConfig();
    	reloadVillagerNames();
        this.getLogger().info("FiddyCraft is Enabled");
        getServer().getPluginManager().registerEvents(new FiddyCraftListener(), this);
        getCommand("vfname").setExecutor(new FiddyCraftCommands(this));
    }
 
    public void loadConfig(){
        getConfig().options().copyDefaults(true);
        saveConfig();
       
    }
    
    public void reloadVillagerNames() {
        if (VillagerNames == null) {
        	VillagerNameFile = new File(getDataFolder(), "VillagerNames.yml");
        }
        VillagerNames = YamlConfiguration.loadConfiguration(VillagerNameFile);
        InputStream defConfigStream = this.getResource("VillagerNames.yml");
        if (defConfigStream != null) {
            YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
            VillagerNames.setDefaults(defConfig);
        }
    }
    public FileConfiguration getVillagerNames() {
        if (VillagerNames == null) {
            this.reloadVillagerNames();
        }
        return VillagerNames;
    }
    
    public void saveVillagerNames() {
        if (VillagerNames == null || VillagerNameFile == null) {
        return;
        }
        try {
            getVillagerNames().save(VillagerNameFile);
            Bukkit.broadcastMessage("saving");
        } catch (IOException ex) {
            this.getLogger().log(Level.SEVERE, "Could not save config to " + VillagerNameFile, ex);
        }
    }
}


