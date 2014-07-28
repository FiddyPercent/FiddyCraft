package io.github.FiddyPercent.fiddycraft;

import java.util.ArrayList;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Chicken;
import org.bukkit.entity.Cow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Sheep;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Animals {
	private String animalName;
	private String ownerName;
	private String ownerUUID;
	private double heartPoints;
	private boolean isWashed;
	private boolean isHealthy;
	private boolean isHungry;
	private boolean isPet;
	private Set<String> allAnimals;
	private Set<String> allAnimalOwners;
	private String auuid;
	private String uuid;
	private boolean willDie;
	public final FiddyCraft plugin;
	

	public Animals(FiddyCraft plugin, String auuid, String uuid){
		this.plugin = plugin;
		ownerName = plugin.getAnimalData().getString("Farmer." + uuid + ".Animals." + auuid + ".Owner" );
		animalName = plugin.getAnimalData().getString("Farmer."+ uuid + ".Animals." +  auuid + ".Name");
		ownerUUID = plugin.getAnimalData().getString("Farmer."+ uuid + ".Animals." +  auuid + ".OwnerID");
		heartPoints = plugin.getAnimalData().getDouble("Farmer."+ uuid + ".Animals." +  auuid + ".HeartPoints");
		isWashed = plugin.getAnimalData().getBoolean("Farmer."+ uuid + ".Animals." +  auuid + ".Washed");
		isHealthy = plugin.getAnimalData().getBoolean("Farmer."+ uuid + ".Animals." +  auuid + ".Healthy");
		isPet =  plugin.getAnimalData().getBoolean("Farmer."+ uuid + ".Animals." +  auuid + ".Brushed");
		isHungry = plugin.getAnimalData().getBoolean("Farmer."+ uuid + ".Animals." +  auuid + ".Hunger");
		willDie = plugin.getAnimalData().getBoolean("Farmer."+ uuid + ".Animals." +  auuid + ".WillDie");
		allAnimalOwners = plugin.getAnimalData().getConfigurationSection("Farmer").getKeys(false);
		this.auuid = auuid;
		this.uuid = uuid;
	}
	
	
	public String getOwnerName(){
		return ownerName;
	}
	
	public boolean getIfShouldDie(){
		return willDie;
	}
	
	public String getOwnerUUID(){
		return ownerUUID;
	}
	
	public boolean getHunger(){
		return  isHungry;
	}
	
	public boolean getIsPet(){
		return isPet;
	}
	public double getHeartPoints(){
		return heartPoints;
	}
	
	public boolean getIsWashed(){
		return isWashed;
	}

	public boolean isHealthy(){
		return isHealthy;
	}
	
	public String getAnimalName(){
		return animalName;
	}
	
	public Set<String>  getAllAnimals(){
		return allAnimals;
	}
	
	public Set<String> getAllAnimalOwners(){
		return allAnimalOwners;
	}
	
	public void addHeartPoint(double  hp){
		double newhp = hp + this.getHeartPoints();
		double roundOff = Math.round(newhp * 100.0) / 100.0;
		this.setHeartPoints(roundOff);
	}
	
	public void setShouldDie(boolean b){
		plugin.getAnimalData().set("Farmer."+ uuid + ".Animals." +  auuid + ".WillDie", true);
		plugin.saveAnimalData();
	}
	
	public void removeAnimalData(){
		plugin.getAnimalData().set("Farmer." + uuid + ".Animals." + auuid ,null);
		plugin.saveAnimalData();
	}
	
	public void setIsWashed(boolean b){
		plugin.getAnimalData().set("Farmer."+ uuid + ".Animals." +  auuid + ".Brushed", b);
		plugin.saveAnimalData();
	}
	
	public void setHealth(boolean b){
		plugin.getAnimalData().getBoolean("Farmer."+ uuid + ".Animals." +  auuid + ".Healthy", b);
		plugin.saveAnimalData();
	}
	
	public void setHunger(boolean b){
		plugin.getAnimalData().set("Farmer."+ uuid + ".Animals." +  auuid + ".Hunger", b);
		plugin.saveAnimalData();
	}
	
	public void setAnimalName(String name){
		plugin.getAnimalData().set("Farmer."+ uuid + ".Animals." +  auuid + ".Name", name);
		 plugin.saveAnimalData();
	}
	
	public void setIsPet(boolean b){
		plugin.getAnimalData().set("Farmer."+ uuid + ".Animals." +  auuid + ".isPet", b);
		plugin.saveAnimalData();
	}
	
	public void setHeartPoints(double d){
		plugin.getAnimalData().set("Farmer."+ uuid + ".Animals." +  auuid + ".HeartPoints", d);
		 plugin.saveAnimalData();
	}
	
	public void setAnimalCoolDown(int time){
		plugin.getAnimalData().set("AnimalCoolDown." + auuid, time);
		plugin.saveAnimalData();
	}
	
	public boolean canDropProduce(Player p, Entity e){
		if(!plugin.getAnimalData().contains("AnimalCoolDown")){
					return true;
		}else{
			if(plugin.getAnimalData().contains("AnimalCoolDown." + e.getUniqueId().toString())){
					int timeLeft =plugin.getAnimalData().getInt("AnimalCoolDown." + e.getUniqueId().toString());
				if(timeLeft > 10){
					LivingEntity animal = (LivingEntity) e;
					p.sendMessage(ChatColor.YELLOW + animal.getCustomName() + " looks like it needs to rest a bit more.");
					return false;
					}else{
					plugin.getAnimalData().set("AnimalCoolDown." + e.getUniqueId().toString(), null);
					return true;
					}
					}else{
					return true;
					}
				}
			}
	
	
	public void petAnimal(Player p , Entity e){
		animalUtility au = new animalUtility(plugin);
		if(au.isAnimalOwner(p, e)){
			if(!this.isPet){
				au.playAnimalSound(e, p);
				p.sendMessage(ChatColor.YELLOW + "You pet " + this.getAnimalName());
				this.addHeartPoint(.01);
				this.setIsPet(false);
			}else{
				p.sendMessage(ChatColor.YELLOW + "Looks like "  + this.getAnimalName() +  " has been pet enough.");
			}
		}
	}
	
	public void washAnimal(Player p , Entity e){
		animalUtility au = new animalUtility(plugin);
		if(au.isAnimalOwner(p, e)){
		if((this.isWashed)){
			p.sendMessage(ChatColor.RED + this.getAnimalName() + " is already clean");
		}
		if(this.isWashed == false){
			Bukkit.getWorld("world").playSound(p.getLocation(), Sound.SWIM, 1, 1);
			Bukkit.getWorld("world").playEffect(e.getLocation(), Effect.POTION_BREAK, 0);
			p.sendMessage(ChatColor.YELLOW + this.getAnimalName() + " is all clean!");
			this.setIsWashed(true);
			this.addHeartPoint(.02);
			}
		}
	}
	
	public void dropProduce(Player p, Entity animal){
		String owner = p.getUniqueId().toString();
		String pet = animal.getUniqueId().toString();
		double hp = plugin.getAnimalData().getInt("Farmer." + owner + ".Animals." +  pet + ".HeartPoints" );
	//COW
		if(animal instanceof Cow){
			int drop = plugin.randomNumber(2);
			ItemStack milk = new ItemStack(Material.MILK_BUCKET, drop);
			ItemMeta meta = milk.getItemMeta();
			ArrayList<String> milkLore = new ArrayList<String>();
			if(hp <= 3){
				meta.setDisplayName("Small Milk");
				milkLore.add("Low Quailty Milk");
				milkLore.add("*");
			}else if(hp >= 4 && hp < 6){
				meta.setDisplayName("Medium Milk");
				milkLore.add("Good Quality Milk");
				milkLore.add("**");
			}else if(hp >= 7 && hp < 9){
				meta.setDisplayName("Large Milk");
				milkLore.add("Great Quailty Milk");
				milkLore.add("***");
			}else{
				meta.setDisplayName("Golden Milk");
				milkLore.add("The Highest Quality Milk");
				milkLore.add("****");;
			}
			meta.setLore(milkLore);
			milk.setItemMeta(meta);
			Bukkit.getWorld("world").dropItem(animal.getLocation(), milk);
			this.setAnimalCoolDown(6000);
	//CHICKEN	
		}else if(animal instanceof Chicken){
			ItemStack Egg = new ItemStack(Material.EGG);
			ItemMeta meta = Egg.getItemMeta();
			ArrayList<String> feathers = new ArrayList<String>();
			ArrayList<String> eggLore = new ArrayList<String>();
			int r = plugin.randomNumber(3);
			int rare = plugin.randomNumber(100);
			ItemStack Feather = new ItemStack(Material.FEATHER, r);
			ItemMeta fm = Feather.getItemMeta();
			
			if(rare == 100 && hp > 7){
				fm.setDisplayName("Golden Feather");
				feathers.add("A very rare feather that causes temporary flight");
			}else if(rare <= 95 && rare >= 85 && hp > 5){
				fm.setDisplayName("Grey Feather");
				feathers.add("A rare feather that causes temporary flight");
			}else{
				fm.setDisplayName("Normal Feather");
				feathers.add("A feather");
			}
			
			if(hp <= 1){
				meta.setDisplayName("Ugly Egg");
				eggLore.add("A warped egg not very good looking");
				eggLore.add("X");
			}else if(hp >= 2 && hp < 5){
				meta.setDisplayName("Normal Egg");
				eggLore.add("A normal looking egg");
				eggLore.add("*");
			}else if(hp >= 5 && hp < 6){
				meta.setDisplayName("Good Egg");
				eggLore.add("A good sized egg");
				eggLore.add("**");
			}else if(hp >= 7 && hp < 9){
				meta.setDisplayName("High Quality Egg");
				eggLore.add("A very good quality large egg");
				eggLore.add("***");
			}else{
				meta.setDisplayName("Golden Golden");
				eggLore.add("A rare Golden Egg the highest quality of egg");
				eggLore.add("****");
			}
			
		meta.setLore(eggLore);
		Egg.setItemMeta(meta);
		fm.setLore(feathers);
		Feather.setItemMeta(fm);
		
		Bukkit.getWorld("world").dropItem(animal.getLocation(), Egg);
		Bukkit.getWorld("world").dropItem(animal.getLocation(), Feather);
		this.setAnimalCoolDown(6000);
	//SHEEP
		}else if(animal instanceof Sheep){
			int drops = plugin.randomNumber(4);
			ItemStack wool = new ItemStack(Material.WOOL, drops);
			ArrayList<String> woolLore = new ArrayList<String>();
			ItemMeta meta = wool.getItemMeta();
			
			if(hp <= 3){
				meta.setDisplayName("Small Wool");
				woolLore.add("Low Quality wool");
			}else if(hp >= 4 && hp < 6){
				meta.setDisplayName("Medium Wool");
				woolLore.add("Good Quality wool");
			}else if(hp >= 7 && hp < 9){
				meta.setDisplayName("Large Wool");
				woolLore.add("Great Quality wool");
			}else{
				meta.setDisplayName("Golden Wool");
				woolLore.add("The Highest Quality of Wool");
			}
			
			Sheep sheep = (Sheep) animal;
			sheep.setSheared(true);
			meta.setLore(woolLore);
			wool.setItemMeta(meta);
			Bukkit.getWorld("world").dropItem(animal.getLocation(), wool);
			this.setAnimalCoolDown(6000);
			
		}
	}
}
