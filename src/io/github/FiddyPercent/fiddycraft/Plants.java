package io.github.FiddyPercent.fiddycraft;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class Plants {
	private String plantType;
	private Location plantLocation;
	private boolean isWaterd;
	private int plantCycle;
	private int quality;
	private Player plantOwner;
	
	public final FiddyCraft plugin;

	public Plants(FiddyCraft plugin, String Type, Location plantLoc, boolean iswaterd, int pCycle, int qual, Player po){
		this.plugin = plugin;
		plantType = Type;
		plantLocation = plantLoc;
		isWaterd = iswaterd;
		plantCycle = pCycle;
		quality = qual;
		plantOwner = po;
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
	
	public Player getPlantOwner(){
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
	
	public void setPlantOwner(Player owner){
		plantOwner = owner;
	}
	
	public int getPlantLocationX(Location loc){
		return loc.getBlockX();
	}
	
	public int getPlantLocationY(Location loc){
		return loc.getBlockY();
	}
	
	public int getPlantLocationZ(Location loc){
		return loc.getBlockZ();
	}
}
