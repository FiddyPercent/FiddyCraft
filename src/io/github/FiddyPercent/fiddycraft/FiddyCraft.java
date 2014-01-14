package io.github.FiddyPercent.fiddycraft;

import org.bukkit.plugin.java.JavaPlugin;

public class FiddyCraft extends JavaPlugin {
	
    @Override
    public void onEnable(){
        this.getLogger().info("FiddyCraft is Enabled");
    }
 
    @Override
    public void onDisable() {
        this.getLogger().info("FiddyCraft is Disabled");
    }
}


