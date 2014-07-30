package io.github.FiddyPercent.fiddycraft.Plant;

import io.github.FiddyPercent.fiddycraft.FiddyCraft;
import io.github.FiddyPercent.fiddycraft.Recipe;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
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
	private int plantExp;


	public Plants(FiddyCraft plugin, String uuid,Location loc){
		
		this.plugin = plugin;
		plantExp = plugin.getPlantInfo().getInt("Farmer."+ uuid + ".Plants." + plugin.getPlantLocationID(loc) + ".Plant EXP");
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
		plugin.savePlantInfo();
		}
	}
	
	public void addPlantQuailty(int q){
		int p = this.getPlantQuailty();
		int L = p + q;
		this.setPlantQuality(L);
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
		//Bukkit.broadcastMessage("plant cycle = " +cy + " array size = " + pnt.size() + " arraysize - cy " + cycle);
		String state = pnt.get(cycle);
		Block b = Bukkit.getWorld("world").getBlockAt(this.getPlantLocation());
		PlantUtil plant = new PlantUtil(plugin);
		Location loc = plant.getFirstPlantLocation(this.getPlantLocation());
		Block topblock = Bukkit.getWorld("world").getBlockAt(loc);
		double x =  pnt.size() / 100;
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
			plugin.addExpFarmer(Bukkit.getPlayer(this.getPlantOwner()), x);
			break;
		case "CT":
			b.setTypeIdAndData(141, (byte)6, true);
			plugin.addExpFarmer(Bukkit.getPlayer(this.getPlantOwner()), x);
			break;
		case "PT":
			b.setTypeIdAndData(142, (byte)6, true);
			plugin.addExpFarmer(Bukkit.getPlayer(this.getPlantOwner()), x);
			break;
		case "POTATO":
			b.setTypeIdAndData(142, (byte)7, true);
			plugin.addExpFarmer(Bukkit.getPlayer(this.getPlantOwner()), x);
			break;
		case "PUMPKIN":
			b.setType(Material.PUMPKIN);
			plugin.addExpFarmer(Bukkit.getPlayer(this.getPlantOwner()), x);
			break;
		case "MELON":
			b.setType(Material.MELON_BLOCK);
			plugin.addExpFarmer(Bukkit.getPlayer(this.getPlantOwner()), x);
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
			plugin.addExpFarmer(Bukkit.getPlayer(this.getPlantOwner()), x);
			break;
		case "LILAC":
			b.setTypeIdAndData(175, (byte)1, false);
			topblock.setTypeIdAndData(175, (byte) 8, true);
			plugin.addExpFarmer(Bukkit.getPlayer(this.getPlantOwner()), x);
			break;
		case "PEONY":
			b.setTypeIdAndData(175, (byte)5, false);
			topblock.setTypeIdAndData(175, (byte) 8, true);
			plugin.addExpFarmer(Bukkit.getPlayer(this.getPlantOwner()), x);
			break;
		case "SUNFLOWER":
			b.setTypeIdAndData(175, (byte)0, false);
			topblock.setTypeIdAndData(175, (byte) 8, true);
			plugin.addExpFarmer(Bukkit.getPlayer(this.getPlantOwner()), x);
			break;
		case "LF":
			b.setTypeIdAndData(175, (byte)3, true);
			topblock.setTypeIdAndData(175, (byte) 8, true);
			plugin.addExpFarmer(Bukkit.getPlayer(this.getPlantOwner()), x);
			break;
		case "F":
			b.setTypeIdAndData(31, (byte)2, true);
			plugin.addExpFarmer(Bukkit.getPlayer(this.getPlantOwner()), x);
			break;
		case "DANDELION":
			b.setTypeIdAndData(105, (byte)3, true);
			plugin.addExpFarmer(Bukkit.getPlayer(this.getPlantOwner()), x);
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
	Recipe r = new Recipe(plugin);
	String rank = r.setItemRank(this.getPlantQuailty());
	lore.add(rank);
	meta.setLore(lore);
	goods.setItemMeta(meta);
		return goods;
	}else{
		//Bukkit.broadcastMessage("dropped air");
		ItemStack d = new ItemStack(Material.AIR);
		return d;
	}
}
	
	@SuppressWarnings("deprecation")
	public void setFirstPlanting(){
		//Bukkit.broadcastMessage(ChatColor.RED + this.getPlantType());
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
			return "ST1~S~S~G~G~VSM~VSM~CT~CARROT";
		}else if(this.getPlantType().equalsIgnoreCase(cropSeed.POTATO.toString())){
			return "ST1~S~S~G~G~VSM~VSM~PT~POTATO";
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

	public int getPlantExp() {
		return plantExp;
	}

	public void addPlantExp(int add) {
		int  o = this.getPlantExp();
		int n = o + add;
		this.setPlantExp(n);
		this.checkGoodFarmingBonus();
	}

	public void setPlantExp(int exp) {
	
		plugin.getPlantInfo().set("Farmer."+ ownerUUID + ".Plants." + plugin.getPlantLocationID(plantLocation) + ".Plant EXP", exp);
		plugin.savePlantInfo();
	}
	
	public void checkGoodFarmingBonus(){
		ArrayList<String> p = new ArrayList<String>();
		this.getCropCycleString();
		String[] cys = this.getCropCycleString().split("~");
		for(String pc: cys){
			p.add(pc);
		}
		
		if((p.size() -2) == this.getPlantExp()){
			this.addPlantQuailty(1);
		}else{
			//Bukkit.broadcastMessage(p.size() + " = size & pnt exp = " +this.getPlantExp());
		}
	}
	
	public void checkbonus(){
		ArrayList<String> p = new ArrayList<String>();
		this.getCropCycleString();
		String[] cys = this.getCropCycleString().split("~");
		for(String pc: cys){
			p.add(pc);
		}
		
		if((p.size() -1) == this.getPlantExp()){
		Player player = Bukkit.getPlayer(this.getPlantOwner());
		player.sendMessage(ChatColor.GREEN + "GOOD FARMING BONUS");
		player.playSound(player.getLocation(), Sound.LEVEL_UP, 1, 1);
		}
	}
}
