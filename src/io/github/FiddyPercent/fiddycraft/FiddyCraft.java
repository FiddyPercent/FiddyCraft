package io.github.FiddyPercent.fiddycraft;

import org.bukkit.plugin.java.JavaPlugin;

public class FiddyCraft extends JavaPlugin {
	

    public void onEnable(){
        this.getLogger().info("FiddyCraft is Enabled");
        getServer().getPluginManager().registerEvents(new FiddyCraftListener(), this);
    }
 

    public void onDisable() {
        this.getLogger().info("FiddyCraft is Disabled");
    }
}


