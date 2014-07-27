package io.github.FiddyPercent.fiddycraft;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Plants  {
	private String plantType;
	private Location plantLocation;
	private boolean isWaterd;
	private int plantCycle;
	private int quality;
	private String plantOwner;
	public final FiddyCraft plugin;
	private String ownerUUID;
	private boolean isHealthy;
	private Boolean isFertilized;

	public Plants(FiddyCraft plugin, String uuid,Location loc){
		this.plugin = plugin;
		plantType = plugin.getPlantInfo().getString("Farmer."+ uuid + ".Plants." + plugin.getPlantLocationID(loc) + ".Plant Type");               
		plantLocation = plugin.getLocationFromString(plugin.getPlantLocationID(loc));
		isWaterd = plugin.getPlantInfo().getBoolean("Farmer."+uuid + ".Plants." + plugin.getPlantLocationID(loc) + ".Watered" );
		plantCycle = plugin.getPlantInfo().getInt("Farmer." +uuid+ ".Plants." +  plugin.getPlantLocationID(loc) + ".Plant Cycle");
		quality = plugin.getPlantInfo().getInt("Farmer." + uuid + ".Plants." + plugin.getPlantLocationID(loc) + ".Plant Quality");
		plantOwner = plugin.getPlantInfo().getString("Farmer." +uuid + ".Plants." + plugin.getPlantLocationID(loc) + ".Owner Name");
		isHealthy = plugin.getPlantInfo().getBoolean("Farmer." +uuid + ".Plants." + plugin.getPlantLocationID(loc) + ".Healthy");
		isFertilized = plugin.getPlantInfo().getBoolean("Farmer." +uuid + ".Plants." + plugin.getPlantLocationID(loc) + ".Fertilized");
		ownerUUID = uuid;
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
	public boolean isfertilized(){
		return isFertilized;
	}
	public boolean isHealthy(){
		return isHealthy;
	}
	public void setfertilized(boolean b){
		plugin.getPlantInfo().set("Farmer." +ownerUUID + ".Plants." + plugin.getPlantLocationID(plantLocation) + ".Fertilized", b);
		plugin.savePlantInfo();
	}
	public void setHealth(boolean b){
		plugin.getPlantInfo().set("Farmer." +ownerUUID + ".Plants." + plugin.getPlantLocationID(plantLocation) + ".Healthy", b);
		plugin.savePlantInfo();
	}
	
	public void setPlantType(String type){
		plugin.getPlantInfo().set("Farmer."+ownerUUID + ".Plants." + plugin.getPlantLocationID(plantLocation) + ".Plant Type", type.toUpperCase());
		plugin.savePlantInfo();
	}

	public void setIsWatered(boolean b){
		plugin.getPlantInfo().set("Farmer."+ ownerUUID + ".Plants." + plugin.getPlantLocationID(plantLocation) + ".Watered", b );
		plugin.savePlantInfo();
	}
	
	public void setPlantCycle(int pcl){
		if(pcl <= 0){
			plugin.getPlantInfo().set("Farmer." +ownerUUID+ ".Plants." +  plugin.getPlantLocationID(plantLocation) + ".Plant Cycle", 1);
		}else{
		plugin.getPlantInfo().set("Farmer." +ownerUUID+ ".Plants." +  plugin.getPlantLocationID(plantLocation) + ".Plant Cycle", pcl);
		}
		plugin.savePlantInfo();
	}
	
	public void setPlantQuality(int q){
		if(q < 0){
			plugin.getPlantInfo().set("Farmer." + ownerUUID + ".Plants." + plugin.getPlantLocationID(plantLocation) + ".Plant Quality", 0);
		}else{
		plugin.getPlantInfo().set("Farmer." + ownerUUID + ".Plants." + plugin.getPlantLocationID(plantLocation) + ".Plant Quality", q);
		}
		plugin.savePlantInfo();
	}
	
	@SuppressWarnings("deprecation")
	public void changePlantCycle(){
		ArrayList<String> pnt = new ArrayList<String>();
		String[] cys = this.getCropCycleString().split("~");
		for(String pc: cys){
			pnt.add(pc);
		}
		
		ArrayList<Integer> check = new ArrayList<Integer>();
		int cy = this.getPlantCycle() - 1;
		if(cy <= 0){
			check.add(1);
		}else{
			check.add(0);
		}
		int cycle = pnt.size() - cy - check.get(0);
		Bukkit.broadcastMessage("plant cycle = " +cy + " array size = " + pnt.size() + " arraysize - cy " + cycle);
		String state = pnt.get(cycle);
		Block b = Bukkit.getWorld("world").getBlockAt(this.getPlantLocation());
		Bukkit.broadcastMessage("Chaning state");
		Location loc = plugin.getFirstPlantLocation(this.getPlantLocation());
		Block topblock = Bukkit.getWorld("world").getBlockAt(loc);
		switch(state){
		case "ST1":
			b.setTypeIdAndData(105, (byte)0, true);
			break;
		case "ST2":
			b.setTypeIdAndData(105, (byte)1, true);
			break;
		case "ST3":
			b.setTypeIdAndData(105, (byte)2, true);
			break;
		case "ST4":
			b.setTypeIdAndData(105, (byte)3, true);
			break;
		case "ST5":
			b.setTypeIdAndData(105, (byte)4, true);
			break;
		case "ST6":
			b.setTypeIdAndData(105, (byte)5, true);
			break;
		case "ST7":
			b.setTypeIdAndData(105, (byte)6, true);
			break;
		case "ST8":
			b.setTypeIdAndData(105, (byte)7, true);
			break;
		case "S":
			b.setTypeIdAndData(59, (byte)0, true);
			break;
		case "G":
			b.setTypeIdAndData(59, (byte)1, true);
			break;
		case "VSM":
			b.setTypeIdAndData(59, (byte)2, true);
			break;
		case "SM":
			b.setTypeIdAndData(59, (byte)3, true);
			break;
		case "MED":
			b.setTypeIdAndData(59, (byte)4, true);
			break;
		case "T":
			b.setTypeIdAndData(59, (byte)5, true);
			break;
		case "VT":
			b.setTypeIdAndData(59, (byte)6, true);
			break;
		case "OXEYE":
			b.setTypeIdAndData(38, (byte)8, true);
			break;
		case "WHEAT":
			b.setTypeIdAndData(59, (byte)7, true);
			break;
		case "WHITE_TULIP":
			b.setTypeIdAndData(38, (byte)6, true);
			break;
		case "ORANGE_TULIP":
			b.setTypeIdAndData(38, (byte)5, true);
			break;
		case "PINK_TULIP":
			b.setTypeIdAndData(38, (byte)7, true);
			break;
		case "RED_TULIP":
			b.setTypeIdAndData(38, (byte)4, true);
			break;
		case "POPPY":
			b.setTypeIdAndData(38, (byte)0, true);
			break;
		case "BLUE_ORCHID":
			b.setTypeIdAndData(38, (byte)1, true);
			break;
		case "ALLIUM":
			b.setTypeIdAndData(38, (byte)2, true);
			break;
		case "AZURE_BLUET":
			b.setTypeIdAndData(38, (byte)3, true);
			break;
		case "CARROT":
			b.setTypeIdAndData(141, (byte)7, true);
			break;
		case "POTATO":
			b.setTypeIdAndData(142, (byte)7, true);
			break;
		case "PUMPKIN":
			b.setType(Material.PUMPKIN);
			break;
		case "MELON":
			b.setType(Material.MELON_BLOCK);
			break;
		case "TG":
			b.setTypeIdAndData(31, (byte) 1, true);
			topblock.setTypeIdAndData(175, (byte) 8, true);
			break;
		case "VTG":
			b.setTypeIdAndData(175, (byte)2, true);
			topblock.setTypeIdAndData(175, (byte) 8, true);
			break;
		case "ROSE":
			b.setTypeIdAndData(175, (byte) 4, false);
			topblock.setTypeIdAndData(175, (byte) 8, true);
			break;
		case "LILAC":
			b.setTypeIdAndData(175, (byte)1, false);
			topblock.setTypeIdAndData(175, (byte) 8, true);
			break;
		case "PEONY":
			b.setTypeIdAndData(175, (byte)5, false);
			topblock.setTypeIdAndData(175, (byte) 8, true);
			break;
		case "SUNFLOWER":
			b.setTypeIdAndData(175, (byte)0, false);
			topblock.setTypeIdAndData(175, (byte) 8, true);
			break;
		case "LF":
			b.setTypeIdAndData(175, (byte)3, true);
			topblock.setTypeIdAndData(175, (byte) 8, true);
			break;
		case "F":
			b.setTypeIdAndData(31, (byte)2, true);
			break;
		case "DANDELION":
			b.setTypeIdAndData(105, (byte)3, true);
			break;
		case "FAIL":
			this.killPlant();
			break;
		}
	} 
	public ItemStack dropProduce(){
		if(this.getPlantCycle() <= 1){
	ItemStack goods = this.getPlantItemStack();
	ItemMeta meta = goods.getItemMeta();
	ArrayList<String> lore = new ArrayList<String>();
	String rank = plugin.setCookingRank(this.getPlantQuailty());
	lore.add(rank);
	meta.setLore(lore);
	goods.setItemMeta(meta);
		return goods;
	}else{
		Bukkit.broadcastMessage("dropped air");
		ItemStack d = new ItemStack(Material.AIR);
		return d;
	}
}
	
	@SuppressWarnings("deprecation")
	public void setFirstPlanting(){
		Bukkit.broadcastMessage(ChatColor.RED + this.getPlantType());
		if(this.getPlantType().equalsIgnoreCase("MELON") || this.getPlantType().equalsIgnoreCase("PUMPKIN")){
			Block b = Bukkit.getWorld("world").getBlockAt(this.getPlantLocation());
			b.setTypeIdAndData(105, (byte)0, true);
		}else{
			Block b = Bukkit.getWorld("world").getBlockAt(this.getPlantLocation());
			b.setTypeIdAndData(59, (byte)0, true);
		}
	}

	
	public void killPlant(){
		Bukkit.getWorld("world").getBlockAt(this.getPlantLocation()).setType(Material.DEAD_BUSH);
		this.removePlantInfo();
	}
	public void growWeed(){
		Bukkit.getWorld("world").getBlockAt(this.getPlantLocation()).setTypeIdAndData(106, (byte) 0, false);
		this.removePlantInfo();
	}
	public void removePlantInfo(){
		plugin.getPlantInfo().set("Farmer." + ownerUUID + ".Plants." + plugin.getPlantLocationID(plantLocation), null); 
	}
	public String getCropCycleString( ){
		if(this.getPlantType().equalsIgnoreCase(cropSeed.OXEYE_DAISY.toString())){
			return "ST1~ST2~ST3~ST4~ST5~OXEYE";
		}
		else if(this.getPlantType().equalsIgnoreCase(cropSeed.WHEAT.toString())){
			return "S~G~VSM~SM~MED~T~TV~WHEAT";
		}
		else if(this.getPlantType().equalsIgnoreCase(cropSeed.WHITE_TULIP.toString())){
			return "ST1~ST2~ST3~ST4~WHITE_TULIP";
		}else if(this.getPlantType().equalsIgnoreCase(cropSeed.ORANGE_TULIP.toString())){
			return "ST1~ST2~ST3~ST4~ORANGE_TULIP";
		}else if( this.getPlantType().equalsIgnoreCase(cropSeed.PINK_TULIP.toString())){
			return "ST1~ST2~ST3~ST4~PINK_TULIP";
		}else if(this.getPlantType().equalsIgnoreCase(cropSeed.RED_TULIP.toString())){
			return "ST1~ST2~ST3~ST4~RED_TULIP";
		}else if(this.getPlantType().equalsIgnoreCase(cropSeed.CARROT.toString())){
			return "ST1~S~S~G~G~VSM~VSM~MED~CARROT";
		}else if(this.getPlantType().equalsIgnoreCase(cropSeed.POTATO.toString())){
			return "ST1~S~S~G~G~VSM~VSM~MED~POTATO";
		}else if(this.getPlantType().equalsIgnoreCase(cropSeed.PUMPKIN.toString())){
			return "ST1~STI~ST2~ST2~ST3~ST3~ST4~ST4~ST5~ST5~ST6~ST6~ST7~ST7~PUMPKIN";
		}else if(this.getPlantType().equalsIgnoreCase(cropSeed.MELON.toString())){
				return "STI~ST2~ST2~ST3~ST3~ST4~ST4~ST5~ST5~ST6~ST7~MELON";
		}else if(this.getPlantType().equalsIgnoreCase(cropSeed.ROSE.toString())){
			return "S~G~VSM~SM~TG~TG~VTG~ROSE";
		}else if(this.getPlantType().equalsIgnoreCase(cropSeed.LILAC.toString())){
			return "S~G~VSM~SM~TG~TG~VTG~LILIAC";
		}else if(this.getPlantType().equalsIgnoreCase(cropSeed.PEONY.toString())){
			return "S~G~VSM~SM~SM~TG~TG~VTG~PEONY";
		}else if(this.getPlantType().equalsIgnoreCase(cropSeed.ALLIUM.toString())){
			return "STI~ST2~ST2~ST3~ST3~ST4~ST4~ST5~ST5~ST6~ALLIUM";
		}else if(this.getPlantType().equalsIgnoreCase(cropSeed.BLUE_ORCHID.toString())){
			return "STI~ST2~ST3~ST4~ST5~ST6~BLUE_ORCHID";
		}else if(this.getPlantType().equalsIgnoreCase(cropSeed.POPPY.toString())){
			return "STI~ST2~ST2~ST3~ST3~ST4~ST4~ST5~POPPY";
		}else if(this.getPlantType().equalsIgnoreCase(cropSeed.DANDELION.toString())){
			return "STI~ST2~ST3~ST4~DANDELION";
		}else if(this.getPlantType().equalsIgnoreCase(cropSeed.AZURE_BLUET.toString())){
			return "STI~ST2~ST3~ST4~ST5~ST6~ST7~AZURE_BLUET";
		}else if(this.getPlantType().equalsIgnoreCase(cropSeed.PEPPER.toString())){
			return "STI~ST2~ST3~ST4~ST5~ST6~ST7~F~F~LF";
		}else{
			return "FAIL";
		}
	}
	
	private ItemStack getPlantItemStack(){
		if(this.getPlantType().equalsIgnoreCase(cropSeed.ALLIUM.toString())){
			ItemStack item = new ItemStack(Material.RED_ROSE, 1 , (short) 2);
			return item;
		}
		else if(this.getPlantType().equalsIgnoreCase(cropSeed.AZURE_BLUET.toString())){
			ItemStack item = new ItemStack(Material.RED_ROSE, 1 , (short) 3);
			return item;
		}
		else if(this.getPlantType().equalsIgnoreCase(cropSeed.BLUE_ORCHID.toString())){
			ItemStack item = new ItemStack(Material.RED_ROSE, 1 , (short) 1);
			return item;
		}
		else if(this.getPlantType().equalsIgnoreCase(cropSeed.CARROT.toString())){
			ItemStack item = new ItemStack(Material.CARROT_ITEM);
			return item;
		}
		else if(this.getPlantType().equalsIgnoreCase(cropSeed.DANDELION.toString())){
			ItemStack item = new ItemStack(Material.YELLOW_FLOWER);
			return item;
		}
		else if(this.getPlantType().equalsIgnoreCase(cropSeed.LILAC.toString())){
			ItemStack item = new ItemStack(Material.getMaterial(175), 1 , (short) 1);
			return item;
		}
		else if(this.getPlantType().equalsIgnoreCase(cropSeed.MELON.toString())){
			ItemStack item = new ItemStack(Material.MELON_BLOCK);
			return item;
		}
		else if(this.getPlantType().equalsIgnoreCase(cropSeed.ORANGE_TULIP.toString())){
			ItemStack item = new ItemStack(Material.RED_ROSE, 1 , (short) 5);
			return item;
		}
		else if(this.getPlantType().equalsIgnoreCase(cropSeed.OXEYE_DAISY.toString())){
			ItemStack item = new ItemStack(Material.RED_ROSE, 1 , (short) 8);
			return item;
		}
		else if(this.getPlantType().equalsIgnoreCase(cropSeed.PEONY.toString())){
			ItemStack item = new ItemStack(Material.getMaterial(175), 1 , (short) 5);
			return item;
		}
		else if(this.getPlantType().equalsIgnoreCase(cropSeed.PINK_TULIP.toString())){
			ItemStack item = new ItemStack(Material.RED_ROSE, 1 , (short) 7);
			return item;
		}
		else if(this.getPlantType().equalsIgnoreCase(cropSeed.POPPY.toString())){
			ItemStack item = new ItemStack(Material.RED_ROSE, 1 , (short) 0);
			return item;
		}
		else if(this.getPlantType().equalsIgnoreCase(cropSeed.POTATO.toString())){
			ItemStack item = new ItemStack(Material.POTATO_ITEM);
			return item;
		}
		else if(this.getPlantType().equalsIgnoreCase(cropSeed.PEPPER.toString())){
			ItemStack item = new ItemStack(Material.MELON_SEEDS, 3);
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName("Pepper");
			item.setItemMeta(meta);
			return item;
		}
		else if(this.getPlantType().equalsIgnoreCase(cropSeed.PUMPKIN.toString())){
			ItemStack item = new ItemStack(Material.PUMPKIN);
			return item;
		}else if(this.getPlantType().equalsIgnoreCase(cropSeed.RED_TULIP.toString())){
			ItemStack item = new ItemStack(Material.RED_ROSE, 1 , (short) 4);
			return item;
		}else if(this.getPlantType().equalsIgnoreCase(cropSeed.ROSE.toString())){
			ItemStack item = new ItemStack(Material.getMaterial(175), 1 , (short) 4);
			return item;
		}else if(this.getPlantType().equalsIgnoreCase(cropSeed.WHEAT.toString())){
			ItemStack item = new ItemStack(Material.WHEAT);
			return item;
		}else if(this.getPlantType().equalsIgnoreCase(cropSeed.WHITE_TULIP.toString())){
			ItemStack item = new ItemStack(Material.RED_ROSE, 1 , (short) 6);
			return item;
		}else{
				ItemStack item = new ItemStack(Material.DEAD_BUSH);
				
				return item;
			}
		}
	
	
}
