package io.github.FiddyPercent.fiddycraft;

import org.bukkit.plugin.java.JavaPlugin;

public class FiddyCraft extends JavaPlugin {
	

    public void onDisable() {
        this.getLogger().info("FiddyCraft is Disabled");
        saveConfig();
    }
	
    public void onEnable(){
    	loadConfig();
        this.getLogger().info("FiddyCraft is Enabled");
        getServer().getPluginManager().registerEvents(new FiddyCraftListener(), this);
        getCommand("test").setExecutor(new FiddyCraftCommands(this));
    }
 
    public void loadConfig(){
        getConfig().options().copyDefaults(true);
        saveConfig();
       
    }
    
    

}


