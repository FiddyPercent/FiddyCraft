package io.github.FiddyPercent.fiddycraft;

import org.bukkit.Location;

public class Plants {
	private String plantType;
	private Location plantLocation;
	private boolean isWaterd;
	private int plantCycle;
	private int quality;
	private String plantOwner;
	public final FiddyCraft plugin;

	public Plants(FiddyCraft plugin, String uuid,Location loc){
		this.plugin = plugin;
		plantType = plugin.getPlantInfo().getString("Farmer."+uuid + "." + plugin.getPlantLocationID(loc) + ".Plant Type");
		plantLocation = plugin.getLocationFromString(plugin.getPlantLocationID(loc));
		isWaterd = plugin.getPlantInfo().getBoolean("Farmer."+uuid + "." + plugin.getPlantLocationID(loc) + ".Watered" );
		plantCycle = plugin.getPlantInfo().getInt("Farmer." +uuid+ "." +  plugin.getPlantLocationID(loc) + ".Plant Cycle");
		quality = plugin.getPlantInfo().getInt("Farmer." + uuid + "." + plugin.getPlantLocationID(loc) + ".Plant Quality");
		plantOwner = plugin.getPlantInfo().getString("Farmer." +uuid + "." + plugin.getPlantLocationID(loc) + ".Owner Name");
	}

	public String getPlantType(){
		return plantType;
		
	}
	
	public Location getPlantLocation(){
		return plantLocation;
	}
	
	public boolean getisWaterd(){
		return isWaterd;
	}
	
	public int getPlantCycle(){
		return plantCycle;
	}
	
	public int getPlantQuailty(){
		return quality;
	}
	
	public String getPlantOwner(){
		return plantOwner;
	}

	public void setPlantType(String type){
		plantType = type;
	}
	
	public void setPlantLocation(Location l){
		plantLocation = l;
	}
	
	public void setIsWatered(boolean b){
		isWaterd = b;
	}
	
	public void setPlantCycle(int pcl){
		plantCycle = pcl;
	}
	
	public void setPlantQuality(int q){
		quality = q;
	}
	
	public void setPlantOwner(String owner){
		plantOwner = owner;
	}
	

	
}
