package io.github.FiddyPercent.fiddycraft.Plant;

import io.github.FiddyPercent.fiddycraft.FcPlayers;
import io.github.FiddyPercent.fiddycraft.FiddyCraft;
import io.github.FiddyPercent.fiddycraft.Jobs.FcFarmers;

import java.util.ArrayList;
import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class PlantUtil {
	private final FiddyCraft plugin;
	
	public PlantUtil(FiddyCraft plugin){
		this.plugin = plugin;
	}
	
    public Location getFirstPlantLocation(Location loc){
    	int x = (int) loc.getBlockX();
		int y = (int) loc.getBlockY() + 1;
		int z = (int) loc.getBlockZ();
		Location l = new Location(Bukkit.getWorld("world"), x,y,z);
    	return l;
    }

    public boolean isPlayerPlantOwner(Player p, Location loc){
    	if(plugin.getPlantInfo().contains("Farmer." + p.getUniqueId().toString() + ".Plants." 
    			+ plugin.getPlantLocationID(loc))){
    		return true;
    	}else{
    		return false;
    	}
    }
	
    public boolean isASeed(ItemStack item){
    	Material m = item.getType();
    	if(m == Material.SEEDS ||	
    		m == Material.PUMPKIN_SEEDS||
    		m == Material.CARROT_ITEM ||
    		m == Material.POTATO_ITEM ||
    		m == Material.MELON_SEEDS){
    		return true;
    	}else{
    		return false;
    	}
    }
    
    public boolean matchesSeedType(String seedType){
		 String dn = seedType;
		 ArrayList<cropSeed> test = new ArrayList<cropSeed>(Arrays.asList(cropSeed.values()));
		 String meh = dn.toUpperCase().replace(" ", "_");
		 if(test.toString().contains(meh)){
					return true;
			}else{
				return false;
		}
    }
    
    public ItemStack getFertilizerItem(){
    	ItemStack fertilized = new ItemStack(Material.DIRT, 1 , (short) 2);
		ItemMeta meta = fertilized.getItemMeta();
		meta.setDisplayName("fertilizer");
		ArrayList<String> lore = new ArrayList<String>();
		lore.add("Good Old Fresh Steaming Manure");
		meta.setLore(lore);
		fertilized.setItemMeta(meta);
		return fertilized;
    }
    

    public boolean canPlantThis(Player p,String seedName){
    	String list = this.getPlayerPlantAbleList(p);
    	ArrayList<String> l = new ArrayList<String>();
    	String[] li = list.split("~");
    	for(String i : li){
    		l.add(i);
    	}
    		String sn = seedName.replace(" Seeds", "").replace(" ", "_").toUpperCase();
    	if(l.toString().contains(sn)){
    		return true;
    	}else{
    		return false;
    	}
    }
 
    public boolean isGrowableBlock(Block b){
    	Material bl = b.getType();
    	if(bl == Material.GRASS ||
    	   bl == Material.CROPS ||
    	   bl == Material.LONG_GRASS ||
    	   bl == Material.DEAD_BUSH ||
    	   bl == Material.YELLOW_FLOWER ||
    	   bl == Material.RED_ROSE ||
    	   bl == Material.WHEAT ||
    	   bl ==Material.PUMPKIN_STEM||
    	   bl == Material.MELON_STEM ||
    	   bl == Material.PUMPKIN ||
    	   bl == Material.MELON_BLOCK ||
    	   bl == Material.COCOA||
    	   bl == Material.CARROT ||
    	   bl == Material.POTATO ||
    	   bl == Material.SUGAR_CANE_BLOCK ||
    	   bl == Material.NETHER_WARTS ||
    	   bl == Material.getMaterial(175) ){
    		return true;
    	}else{
    		return false;
    	}
    }
    

    public String getPlayerPlantAbleList(Player p){
    	FcPlayers fc = new FcPlayers(plugin, p);
    	if(fc.getPlayerJob().equalsIgnoreCase("Farmer")){
    		FcFarmers fm = new FcFarmers(plugin, p);
    		String rank = fm.getRank();
    		if(rank.equalsIgnoreCase("Farmer")){
    			String base = "OXEYE_DAISY:AZURE_BLUET:POTATO:CARROT:WHEAT:RED_TULIP:ORANGE_TULIP:PINK_TULIP:WHITE_TULIP:DANDELION";
    	    	String extra = fc.getGrowableList();
    	    	return base+extra;
    		}else if(rank.equalsIgnoreCase("Great Farmer")){
    			String base = "OXEYE_DAISY:AZURE_BLUET:POTATO:CARROT:POPPY:ALLIUM:MELON:RED_TULIP:ORANGE_TULIP:PINK_TULIP:WHITE_TULIP:WHEAT";
    	    	String extra = fc.getGrowableList();
    	    	return base+extra;
    		}else if(rank.equalsIgnoreCase("Legendary Farmer")){
    			String base = "OXEYE_DAISY:AZURE_BLUET:POTATO:CARROT:POPPY:ALLIUM:MELON:PUMPKIN;PEPPER:RED_TULIP:ORANGE_TULIP:PINK_TULIP:WHITE_TULIP:WHEAT:ROSE:BLUE_ORCHID";
    	    	String extra = fc.getGrowableList();
    	    	return base+extra;
    		}else{
    			String base = "WHEAT:RED_TULIP:ORANGE_TULIP:PINK_TULIP:WHITE_TULIP:DANDELION:";
    	    	String extra = fc.getGrowableList();
    	    	return base+extra;
    		}
    	}else{
    		String base = "WHEAT:RED_TULIP:ORANGE_TULIP:PINK_TULIP:WHITE_TULIP:DANDELION:";
    		String extra = fc.getGrowableList();
    			return base+extra;
    	}
    }
    

    public void plantNewSeed(Player p, Block e, String plantType, ItemStack Seeds){
        if(!plugin.getPlantInfo().contains("Farmer")){
        	plugin.getPlantInfo().set("Farmer.", p.getUniqueId().toString());
        	plugin.savePlantInfo();
    		}
        	ItemMeta meta = Seeds.getItemMeta();
        	cropSeed cs = cropSeed.valueOf(plantType);
        	int cyc = cs.getcycles();
        	int x = (int) e.getLocation().getBlockX();
    		int y = (int) e.getLocation().getBlockY() +1;
    		int z = (int) e.getLocation().getBlockZ();
    		String l = x+"~"+y+"~"+z;
        	String ownerUUID = p.getUniqueId().toString();
        	plugin.getPlantInfo().set("Farmer." + ownerUUID + ".Plants." + l + ".Plant Location" + ".X", x);
        	plugin.getPlantInfo().set("Farmer." + ownerUUID + ".Plants." + l + ".Plant Location" + ".Y", y);
        	plugin.getPlantInfo().set("Farmer." + ownerUUID + ".Plants." + l + ".Plant Location" + ".Z",z);
        	plugin.getPlantInfo().set("Farmer." + ownerUUID + ".Plants." + l + ".Owner Name" , p.getName());
        	plugin.getPlantInfo().set("Farmer." + ownerUUID + ".Plants." + l + ".Plant Type",plantType);
        	plugin.getPlantInfo().set("Farmer." + ownerUUID + ".Plants." + l + ".Watered",false);
        	plugin.getPlantInfo().set("Farmer." + ownerUUID + ".Plants." + l + ".Plant Cycle",cyc);
        	plugin.getPlantInfo().set("Farmer." + ownerUUID + ".Plants." + l + ".Plant Quality", plugin.getcookingRankLevel(meta.getLore().get(1)));
        	plugin.getPlantInfo().set("Farmer." + ownerUUID + ".Plants." + l + ".Healthy", true);
    		plugin.getPlantInfo().set("Farmer." + ownerUUID + ".Plants." + l + ".Fertilized", false);
    		plugin.getPlantInfo().set("Farmer." + ownerUUID + ".Plants." + l + ".Plant EXP", 0);
    		plugin.savePlantInfo();
        }
    
    
}
