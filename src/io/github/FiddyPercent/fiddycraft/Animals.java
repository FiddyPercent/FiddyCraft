package io.github.FiddyPercent.fiddycraft;

import java.util.ArrayList;
import java.util.List;
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
import org.bukkit.entity.Pig;
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
	private int age;
	private double weight;
	private boolean isHappy;
	public final FiddyCraft plugin;
	private int maxAge = 2;
	

	public Animals(FiddyCraft plugin, String auuid, String uuid){
		this.plugin = plugin;
		ownerName = plugin.getAnimalData().getString("Farmer." + uuid + ".Animals." + auuid + ".Owner" );
		animalName = plugin.getAnimalData().getString("Farmer."+ uuid + ".Animals." +  auuid + ".Name");
		ownerUUID = plugin.getAnimalData().getString("Farmer."+ uuid + ".Animals." +  auuid + ".OwnerID");
		heartPoints = plugin.getAnimalData().getDouble("Farmer."+ uuid + ".Animals." +  auuid + ".HeartPoints");
		isWashed = plugin.getAnimalData().getBoolean("Farmer."+ uuid + ".Animals." +  auuid + ".Washed");
		isHappy = plugin.getAnimalData().getBoolean("Farmer."+ uuid + ".Animals." +  auuid + ".Happy");
		isHealthy = plugin.getAnimalData().getBoolean("Farmer."+ uuid + ".Animals." +  auuid + ".Healthy");
		isPet =  plugin.getAnimalData().getBoolean("Farmer."+ uuid + ".Animals." +  auuid + ".isPet");
		isHungry = plugin.getAnimalData().getBoolean("Farmer."+ uuid + ".Animals." +  auuid + ".Hunger");
		willDie = plugin.getAnimalData().getBoolean("Farmer."+ uuid + ".Animals." +  auuid + ".WillDie");
		allAnimalOwners = plugin.getAnimalData().getConfigurationSection("Farmer").getKeys(false);
		age =  plugin.getAnimalData().getInt("Farmer."+ uuid + ".Animals." +  auuid + ".Age");
		weight =  plugin.getAnimalData().getDouble("Farmer."+ uuid + ".Animals." +  auuid + ".Weight");
		this.auuid = auuid;
		this.uuid = uuid;
	}
	
	public int getAge(){
		return age;
	}
	
	public boolean isHappy(){
		return isHappy;
	}
	
	public double getWeight(){
		return weight;
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
	
	public void setHappyness(boolean b){
		 plugin.getAnimalData().set("Farmer."+ uuid + ".Animals." +  auuid + ".Happy", b);
		 plugin.saveAnimalData();
	}
	
	public void addAge(int number){
		int n = number + this.getAge();
		plugin.getAnimalData().set("Farmer."+ uuid + ".Animals." +  auuid + ".Age", n);
		plugin.saveAnimalData();
	}
	
	public void addWeight(double number){
		double n = number + this.getWeight();
		double roundOff = Math.round(n * 100.0) / 100.0;
		plugin.getAnimalData().set("Farmer."+ uuid + ".Animals." +  auuid + ".Weight", roundOff);
		plugin.saveAnimalData();
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
		plugin.getAnimalData().set("Farmer."+ uuid + ".Animals." +  auuid + ".Washed", b);
		plugin.saveAnimalData();
	}
	
	public void setHealth(boolean b){
		plugin.getAnimalData().set("Farmer."+ uuid + ".Animals." +  auuid + ".Healthy", b);
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
			if(this.getHunger() == true){
				p.sendMessage(ChatColor.RED + this.getAnimalName() + " needs to eat first");
				return false;
			}else if(this.isHealthy == false){
				p.sendMessage(ChatColor.RED + this.getAnimalName() + " is too sick");
			}
			
			
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
			this.animalNoise(e, p);
			if(this.isPet == true){
				au.playAnimalSound(e, p);
				this.addHeartPoint(.02);
				if(e instanceof Pig){
					this.addHeartPoint(.03);
				}
				this.setIsPet(false);
			}else{
				
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
	
	public void deathDrop(Player p, Entity e){
		Bukkit.broadcastMessage("in death drop");
		if(e instanceof Cow){
			Bukkit.broadcastMessage("cow");
			int weight = (int) this.getWeight() / 10 ;
			int heartPoints = (int) ((int) this.getHeartPoints() * 1.5);
			int age = this.getAge()/ 10;
			int meatLoad = weight + age + heartPoints;
			ItemStack extra = new ItemStack(Material.LEATHER, meatLoad / 2);
			ItemStack meats = new ItemStack(Material.RAW_BEEF, meatLoad);
			ItemMeta meta = meats.getItemMeta();
			ArrayList<String> lore = new ArrayList<String>();
			ItemMeta meta2 = meats.getItemMeta();
			ArrayList<String> lore2 = new ArrayList<String>();
			int hp = (int) this.getHeartPoints();
			int rank =(int)  hp/2;
			if(hp < 3){
				meta.setDisplayName("OK Beef");
				lore.add("Beef that tastes OK");
				lore.add(plugin.setCookingRank(rank));
				meta2.setDisplayName("Low Quality Leather");
				lore2.add("Not the best Leather");
				lore2.add(plugin.setCookingRank(rank));
			}else if(hp > 2 && hp < 6){
				meta.setDisplayName("Good Beef");
				lore.add("Beef that tastes Good");
				lore.add(plugin.setCookingRank(rank));
				meta2.setDisplayName("Good Quality Leather");
				lore2.add("Good Leather");
				lore2.add(plugin.setCookingRank(rank));
			}else if(hp > 5 && hp <= 9){
				meta.setDisplayName("Great Beef");
				lore.add("Beef that tastes Great");
				lore.add(plugin.setCookingRank(rank));
				meta2.setDisplayName("Great Quality Leather");
				lore2.add("Great Quality");
				lore2.add(plugin.setCookingRank(rank));
			}else if(hp <= 10){
				meta.setDisplayName("Legendary Beef");
				lore.add("Beef that tastes Legendary");
				lore.add(plugin.setCookingRank(rank));
				meta2.setDisplayName("Highest Quality Leather");
				lore2.add("Perfect Quality");
				lore2.add(plugin.setCookingRank(rank));
			}
			meta.setLore(lore);
			meats.setItemMeta(meta);
			meta2.setLore(lore2);
			extra.setItemMeta(meta2);
			p.getWorld().dropItem(e.getLocation(), meats);
			p.getWorld().dropItem(e.getLocation(), extra);
		}else if(e instanceof Chicken){
			int weight = (int) this.getWeight() / 10 ;
			int heartPoints = (int) ((int) this.getHeartPoints() * 1.5);
			int age = this.getAge()/ 10;
			int meatLoad = weight + age + heartPoints;
			
			ItemStack meats = new ItemStack(Material.RAW_CHICKEN, meatLoad);
			ItemMeta meta = meats.getItemMeta();
			ArrayList<String> lore = new ArrayList<String>();
			int hp = (int) this.getHeartPoints();
			int rank =(int)  hp/2;
			if(hp < 3){
				meta.setDisplayName("OK Chicken");
				lore.add("Chicken that tastes OK");
				lore.add(plugin.setCookingRank(rank));
			}else if(hp > 2 && hp < 6){
				meta.setDisplayName("Good Chicken");
				lore.add("Chicken that tastes Good");
				lore.add(plugin.setCookingRank(rank));
			}else if(hp > 5 && hp <= 9){
				meta.setDisplayName("Great Chicken");
				lore.add("Chicken that tastes Great");
				lore.add(plugin.setCookingRank(rank));
			}else if(hp <= 10){
				meta.setDisplayName("Legendary Chicken");
				lore.add("Chicken that tastes Legendary");
				lore.add(plugin.setCookingRank(rank));
			}
			meta.setLore(lore);
			meats.setItemMeta(meta);
			p.getWorld().dropItem(e.getLocation(), meats);
		}else if(e instanceof Pig){
			int weight = (int) this.getWeight() / 10 ;
			int heartPoints = (int) ((int) this.getHeartPoints() * 2.5);
			int age = this.getAge()/ 10;
			int meatLoad = weight + age + heartPoints;
		
			ItemStack meats = new ItemStack(Material.PORK, meatLoad);
			ItemMeta meta = meats.getItemMeta();
			ArrayList<String> lore = new ArrayList<String>();
			int hp = (int) this.getHeartPoints();
			int rank =(int)  hp/2;
			if(hp < 3){
				meta.setDisplayName("OK Pork");
				lore.add("Pork that tastes OK");
				lore.add(plugin.setCookingRank(rank));
			}else if(hp > 2 && hp < 6){
				meta.setDisplayName("Good Pork");
				lore.add("Pork that tastes Good");
				lore.add(plugin.setCookingRank(rank));
			}else if(hp > 5 && hp <= 9){
				meta.setDisplayName("Great Pork");
				lore.add("Pork that tastes Great");
				lore.add(plugin.setCookingRank(rank));
			}else if(hp <= 10){
				meta.setDisplayName("Legendary Pork");
				lore.add("Pork that tastes Legendary");
				lore.add(plugin.setCookingRank(rank));
			}
			meta.setLore(lore);
			meats.setItemMeta(meta);
			
			p.getWorld().dropItem(e.getLocation(), meats);
		}
		
	}
	
	public void animalNoise(Entity e, Player p){
		String type = e.getType().toString();
		Location loc = p.getLocation();
		
		ArrayList<String> mood = new ArrayList<String>();
		boolean h = this.isHappy();
		boolean s = this.isHealthy();
		
		if(h == true){
			mood.add("happy");
		}else{
			if(s == false){
				mood.add("sick");
			}else{
				mood.add("mad");
			}
		}
		String m = mood.get(0);
		
		if(type.equalsIgnoreCase("Cow")){
			switch(m){
			case "happy":
				Bukkit.getWorld("world").playSound(loc, Sound.COW_IDLE, 100, 1);
				p.sendMessage(ChatColor.GRAY + this.getAnimalName() + " is " + ChatColor.GREEN  + m);
				break;
			case "sick":
				Bukkit.getWorld("world").playSound(loc, Sound.COW_HURT, 100, -10);
				p.sendMessage(ChatColor.GRAY + this.getAnimalName() + " is " + ChatColor.DARK_RED  + m);
				break;
			case "mad":
				Bukkit.getWorld("world").playSound(loc, Sound.COW_HURT, 100, -3);
				p.sendMessage(ChatColor.GRAY + this.getAnimalName() + " is " + ChatColor.RED  + m);
				break;
			}
			
			Bukkit.getWorld("world").playEffect(e.getLocation(), Effect.MOBSPAWNER_FLAMES, 5, 5);
		}else if(type.equalsIgnoreCase("Sheep")){
			switch(m){
			case "happy":
				Bukkit.getWorld("world").playSound(loc, Sound.SHEEP_IDLE, 100, 1);
				p.sendMessage(ChatColor.GRAY + this.getAnimalName() + " is " + ChatColor.GREEN  + m);
				break;
			case "sick":
				Bukkit.getWorld("world").playSound(loc, Sound.SHEEP_IDLE, 100, -10);
				p.sendMessage(ChatColor.GRAY + this.getAnimalName() + " is " + ChatColor.DARK_RED  + m);
				break;
			case "mad":
				Bukkit.getWorld("world").playSound(loc, Sound.SHEEP_IDLE, 100, -3);
				p.sendMessage(ChatColor.GRAY + this.getAnimalName() + " is " + ChatColor.RED  + m);
				break;
			}
			Bukkit.getWorld("world").playEffect(e.getLocation(), Effect.MOBSPAWNER_FLAMES, 5, 5);
		}else if(type.equalsIgnoreCase("Pig")){
			switch(m){
			case "happy":
				Bukkit.getWorld("world").playSound(loc, Sound.PIG_IDLE, 100, 1);
				p.sendMessage(ChatColor.GRAY + this.getAnimalName() + " is " + ChatColor.GREEN  + m);
				break;
			case "sick":
				Bukkit.getWorld("world").playSound(loc, Sound.PIG_DEATH, 100, -10);
				p.sendMessage(ChatColor.GRAY + this.getAnimalName() + " is " + ChatColor.DARK_RED  + m);
				break;
			case "mad":
				Bukkit.getWorld("world").playSound(loc, Sound.PIG_DEATH, 100, -3);
				p.sendMessage(ChatColor.GRAY + this.getAnimalName() + " is " + ChatColor.RED  + m);
				break;
			}
			Bukkit.getWorld("world").playEffect(e.getLocation(), Effect.MOBSPAWNER_FLAMES, 5, 5);
		}else if(type.equalsIgnoreCase("chicken")){
			switch(m){
			case "happy":
				Bukkit.getWorld("world").playSound(loc, Sound.CHICKEN_IDLE, 100, 1);
				p.sendMessage(ChatColor.GRAY + this.getAnimalName() + " is " + ChatColor.GREEN  + m);
				break;
			case "sick":
				Bukkit.getWorld("world").playSound(loc, Sound.CHICKEN_HURT, 100, -10);
				p.sendMessage(ChatColor.GRAY + this.getAnimalName() + " is " + ChatColor.DARK_RED  + m);
				break;
			case "mad":
				Bukkit.getWorld("world").playSound(loc, Sound.CHICKEN_HURT, 100, -3);
				p.sendMessage(ChatColor.GRAY + this.getAnimalName() + " is " + ChatColor.RED  + m);
				break;
			}
			Bukkit.getWorld("world").playEffect(e.getLocation(), Effect.MOBSPAWNER_FLAMES, 5, 5);
		}else if(type.equalsIgnoreCase("Horse")){
			switch(m){
			case "happy":
				Bukkit.getWorld("world").playSound(loc, Sound.HORSE_BREATHE, 100, 1);
				p.sendMessage(ChatColor.GRAY + this.getAnimalName() + " is " + ChatColor.GREEN  + m);
				break;
			case "sick":
				Bukkit.getWorld("world").playSound(loc, Sound.HORSE_ANGRY, 100, -10);
				p.sendMessage(ChatColor.GRAY + this.getAnimalName() + " is " + ChatColor.DARK_RED  + m);
				break;
			case "mad":
				Bukkit.getWorld("world").playSound(loc, Sound.HORSE_ANGRY, 100, -3);
				p.sendMessage(ChatColor.GRAY + this.getAnimalName() + " is " + ChatColor.RED  + m);
				break;
			}
			Bukkit.getWorld("world").playEffect(e.getLocation(), Effect.MOBSPAWNER_FLAMES, 5, 5);
		}else if(type.equalsIgnoreCase("Wolf")){
			switch(m){
			case "happy":
				Bukkit.getWorld("world").playSound(loc, Sound.WOLF_BARK, 100, 1);
				p.sendMessage(ChatColor.GRAY + this.getAnimalName() + " is " + ChatColor.GREEN  + m);
				break;
			case "sick":
				Bukkit.getWorld("world").playSound(loc, Sound.WOLF_HOWL, 50, 1);
				p.sendMessage(ChatColor.GRAY + this.getAnimalName() + " is " + ChatColor.DARK_RED  + m);
				break;
			case "mad":
				Bukkit.getWorld("world").playSound(loc, Sound.WOLF_GROWL, 50, 1);
				p.sendMessage(ChatColor.GRAY + this.getAnimalName() + " is " + ChatColor.RED  + m);
				break;
			}
			Bukkit.getWorld("world").playEffect(e.getLocation(), Effect.MOBSPAWNER_FLAMES, 5, 5);
		}
		
	}
	public void checkIfToOld(){
		if(this.getAge() < maxAge){
			this.setShouldDie(true);
			
		}
	}
	
	
	public void neglectDeath(Player p, Entity animal ){
		if(this.willDie && this.getAge() < 120){
			p.getWorld().createExplosion(animal.getLocation().getX(), animal.getLocation().getY(), animal.getLocation().getZ(), (float)0, false, false);
			animal.remove();
			animalUtility au = new animalUtility(plugin);
			this.animalNoise(animal, p);
			this.removeAnimalData();
			Bukkit.broadcastMessage(ChatColor.RED + p.getName() + " has had an animal die of neglect!");
			List<Entity> en = p.getNearbyEntities(10, 10, 10);
			for(Entity se : en){
			if(au.isAnimal(se)){
				if(au.isAnimalOwner(p, animal)){
				Animals news = new Animals(plugin, se.getUniqueId().toString(), p.getUniqueId().toString());
				news.addHeartPoint(-1);
				p.sendMessage(news.getAnimalName() + " doesn't look at you the same");
				}
			}
		}
	}
}
	
	public void oldAgeDeath(Player p, Entity animal ){
		if(this.willDie == true && this.getAge() > maxAge){
			p.getWorld().playEffect(animal.getLocation(), Effect.EXTINGUISH, 50);
			
			animalUtility au = new animalUtility(plugin);
			this.animalNoise(animal, p);
			this.removeAnimalData();
			Bukkit.broadcastMessage(ChatColor.AQUA + this.getAnimalName() + " has had a full life!");
			this.deathDrop(p, animal);
			
			List<Entity> en = p.getNearbyEntities(10, 10, 10);
			for(Entity se : en){
			if(au.isAnimal(se)){
				if(au.isAnimalOwner(p, animal)){
				Animals news = new Animals(plugin, se.getUniqueId().toString(), p.getUniqueId().toString());
				news.addHeartPoint(.1);
				int r = plugin.randomNumber(10);
				switch(r){
				case 1:
					p.sendMessage( ChatColor.GRAY + news.getAnimalName() + " thinks back on the good times with " + this.getAnimalName());
					break;
				case 2:
					p.sendMessage(ChatColor.GRAY + news.getAnimalName() + " is happy " + this.getAnimalName() + " is gone.");
					break;
				case 3:
					p.sendMessage(ChatColor.GRAY + news.getAnimalName() + " is contemplating life ");
					break;
				
				case 4:
					p.sendMessage(ChatColor.GRAY + news.getAnimalName() + " will miss " + this.getAnimalName());
					}
				}
			}
		}
			animal.remove();
	}
}
	
	public void dropProduce(Player p, Entity animal){
		double hp = this.getHeartPoints();
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
