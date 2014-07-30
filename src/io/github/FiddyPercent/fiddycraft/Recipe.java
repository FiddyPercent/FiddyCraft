package io.github.FiddyPercent.fiddycraft;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Recipe {
	private FiddyCraft plugin;
	
	public Recipe(FiddyCraft plugin){
		this.plugin = plugin;
	}
	// NOTE: this gets recipe item names and puts them in an array.
	public ItemStack recipeCheck(ArrayList<ItemStack> recipeItems){
		ArrayList<String> holder = new ArrayList<String>();
		boolean firstItem = false;
		for(ItemStack rItems : recipeItems){
			String ingNames = this.getDisplayName(rItems);
			holder.add(ingNames);
		}
		
		
		ItemStack preResult = this.getResult(holder);
		ItemStack preFinalFood = this.setRecipeRank(preResult, recipeItems);
		ItemStack FinalFood = this.setFoodBuff(preFinalFood);
		
		return FinalFood;
	}
//NOTE this is where the recipe check goes	

	
	private ItemStack setRecipeRank(ItemStack preResult, ArrayList<ItemStack> recipeItems){
		ItemStack i = preResult;
		int total = 0;
		int nonTotal = 0;
		if(this.isaNonFoodItem(i.getType())){
			nonTotal = nonTotal + 1;
		}
			ItemMeta meta = i.getItemMeta();
		if(i.hasItemMeta()){
			if(meta.hasLore() && meta.getLore().size() >= 2){
				int l = this.getItemRankLevel(meta.getLore().get(1));
				total = l + total;
			}
		}
			int  rank = (total - nonTotal) / recipeItems.size();
			Bukkit.broadcastMessage("rank total " + rank);
			String r = this.setItemRank(rank);
			ArrayList<String> lore = (ArrayList<String>) meta.getLore();
			lore.add(r);
			meta.setLore(lore);
			i.setItemMeta(meta);
				return i;
	}
	
	private boolean isaNonFoodItem(Material item){
		Material i = item;
		if(i == Material.WATER_BUCKET || i == Material.BOWL || i == Material.POTION){
			return true;
		}else{
			return false;
		}
	}
	
	public int getItemRankLevel(String rank){
		String r = rank;
		if(r.equalsIgnoreCase("*")){
			return 1;
		}else if(r.equalsIgnoreCase("**")){
			return 2;
		}else if(r.equalsIgnoreCase("***")){
			return 3;
		}else if(r.equalsIgnoreCase("****")){
			return 4;
		}else{
			return 0;
		}
	}
	
	public String setItemRank(int number){
		int r = number;
		if(r == 1){
			return "*";
		}else if(r == 2){
			return "**";
		}else if(r == 3){
			return "***";
		}else if(r >= 4){
			return "****";
		}else{
			return "X";
		}
	}
	
	private String getDisplayName(ItemStack ri){
		ItemStack i = ri;
		ItemMeta meta = i.getItemMeta();
		if(i.hasItemMeta() && i.getItemMeta().hasDisplayName()){
			String result;
			// INFO: checks and set Generic food types (ranked food)
			result = this.checkGenerics(i);
			return  result;
		}else{
			Material mat = i.getType();
			String matName = mat.toString();
			return matName;
		}
	}
	
	
	private String checkGenerics(ItemStack i){
		Material mat = i.getType();
		ItemMeta meta = i.getItemMeta();
		String name = meta.getDisplayName();
		
		if(mat == Material.RAW_CHICKEN){
			if(name.equalsIgnoreCase("Good Chicken") || name.equalsIgnoreCase("Great Chicken") ||
					name.equalsIgnoreCase("OK Chicken") || name.equalsIgnoreCase("Legendary Chicken")){
				return "Generic Chicken";
			}
		}else if( mat == Material.RAW_BEEF){
			if(name.equalsIgnoreCase("Good Beef") || name.equalsIgnoreCase("Great Beef") ||
					name.equalsIgnoreCase("OK Beef") || name.equalsIgnoreCase("Legendary Beef")){
				return "Generic Beef";
			}
		}else if( mat == Material.PORK){
			if(name.equalsIgnoreCase("Good Pork") || name.equalsIgnoreCase("Great Pork") ||
					name.equalsIgnoreCase("OK Pork") || name.equalsIgnoreCase("Legendary Pork")){
				return "Generic Pork";
			}
		}else if( mat == Material.MILK_BUCKET){
			if(name.equalsIgnoreCase("Small Milk") || name.equalsIgnoreCase("Medium Milk") ||
					name.equalsIgnoreCase("Large Milk") || name.equalsIgnoreCase("Golden Milk")){
				return "Generic Milk";
			}
		}else if( mat == Material.EGG){
			if(name.equalsIgnoreCase("Small Egg") || name.equalsIgnoreCase("Normal Egg") ||
					name.equalsIgnoreCase("Medium Egg") || name.equalsIgnoreCase("Large Egg")
					|| name.equalsIgnoreCase("Golden Egg")){
				return "Generic Egg";
			}
		}else if( mat == Material.RAW_FISH){
			if(name.equalsIgnoreCase("Small Fish") || name.equalsIgnoreCase("Medium Fish") ||
					name.equalsIgnoreCase("Large Fish") || name.equalsIgnoreCase("King Fish")){
				return "Generic Fish";
			}
		}else{
			return name;
		}
			return name;
	}
	
	public ItemStack setFoodBuff(ItemStack mainIngredent){
		Material item = mainIngredent.getType();
		ItemMeta meta = mainIngredent.getItemMeta();
		ArrayList<String> lore = (ArrayList<String>) meta.getLore();
		lore.add("Bonus Effects");
		ArrayList<String> b = lore;
		String in = meta.getDisplayName();
		if(lore.size() ==3){
		if(in != null){
		if(in.contains("Spicey")){
			b.add("Heat Tolerance");
			}
		if(in.contains("Sweet")){
			b.add("Jumpy");
			}
		if(in.contains("Salty")){
			b.add("Saturation");
			}
		if(in.contains("Sour")){
			b.contains("Night Eyes");
			}
		if(in.contains("Bitter")){
			b.add("Healing");
			}
		if(in.contains("Miners")){
			b.add("Miners Bonus");
			}
	//MEAT
		if(item == Material.COOKED_BEEF || item == Material.COOKED_CHICKEN ||item == Material.COOKED_FISH){
			b.add("Strength");
		}
	//SOUP	
		if(item == Material.MUSHROOM_SOUP && mainIngredent.hasItemMeta() && meta.hasDisplayName()){
			if(meta.getDisplayName().equalsIgnoreCase("Mushroom Soup")){
				b.add("Health");
			}else{
				b.add("Regeneration");
			}
		}
	//SWEETS
		if(item == Material.COOKIE || item == Material.CAKE || item == Material.PUMPKIN_PIE){
			b.add("Speed");
		}
	//MILK
		if(item == Material.MILK_BUCKET){
			b.add("Strong Bones");
		}
	//VEGGIES
		if(item == Material.BAKED_POTATO || item == Material.GOLDEN_CARROT){
			b.add("Health");
			}
		}
		
		if(b.isEmpty()){
			b.add( "None");
		}
		meta.setLore(b);
		mainIngredent.setItemMeta(meta);
		return mainIngredent;
		}
		Bukkit.broadcastMessage("DID NOT PASS " + lore.size());
		return mainIngredent;
	}
	
	
	public void setFoodEffects(Player p,String effect, int starRank){
		int test = 2;
		int level = (int) (starRank +test) - 1;
		int time = (starRank + test)* 800;
		Bukkit.broadcastMessage("level = " + level + "time = " + time);
		Bukkit.broadcastMessage("in food effects + effect " + effect);
		if(effect.equalsIgnoreCase("Strength") && level != 0){
			p.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, time, level));
			p.sendMessage(ChatColor.GRAY + "You feel " + ChatColor.RED + "Stronger!");
		}
		
		if(effect.equalsIgnoreCase("Health") && level != 0){
			p.addPotionEffect(new PotionEffect(PotionEffectType.HEALTH_BOOST, time, level));
			p.sendMessage(ChatColor.GRAY + "You feel " + ChatColor.DARK_GREEN + "Healther!");
		}
		if(effect.equalsIgnoreCase("Regeneration") && level != 0){
			p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, time, level));
			p.sendMessage(ChatColor.GRAY + "You feel " + ChatColor.GREEN + "Like new!");
		}
		if(effect.equalsIgnoreCase("Speed") && level != 0){
			p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, time, level));
			p.sendMessage(ChatColor.GRAY + "You feel " + ChatColor.GREEN + "Fast!");
		}
		if(effect.equalsIgnoreCase("Heat Tolerance") && level != 0){
			p.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, time, level));
			p.sendMessage(ChatColor.GRAY + "You feel " + ChatColor.RED + "One with Fire!");
		}
		if(effect.equalsIgnoreCase("Jumpy") && level != 0){
			p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, time, level));
			p.sendMessage(ChatColor.GRAY + "You feel " + ChatColor.GREEN + "Like your bouncing off the walls!");
		}
		if(effect.equalsIgnoreCase("Saturation") && level != 0){
			p.addPotionEffect(new PotionEffect(PotionEffectType.SATURATION, time, level));
			p.sendMessage(ChatColor.GRAY + "You feel " + ChatColor.YELLOW + "Full!");
		}
		if(effect.equalsIgnoreCase("Healing") && level != 0){
			p.addPotionEffect(new PotionEffect(PotionEffectType.HEAL, time, level));
			p.sendMessage(ChatColor.GRAY + "You feel " + ChatColor.GREEN + "Restored!");
		}
		if(effect.equalsIgnoreCase("Miners Bonus") && level != 0){
			p.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, time, level));
			p.sendMessage(ChatColor.GRAY + "You feel " + ChatColor.YELLOW + "Like Mining!");
		}
		if(effect.equalsIgnoreCase("Strong Bones") && level != 0){
			p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, time, level));
			p.sendMessage(ChatColor.GRAY + "You feel " + ChatColor.GREEN + "Tough!");
		}
		
		if(effect.equalsIgnoreCase("None")){
			
		}
	}
	
	private ItemStack getResult(ArrayList<String> holder){
		ItemStack fail = new ItemStack(Material.SKULL);
		ItemStack dish = null;
	if(!this.recipeSpiceyChicken(holder,fail).isSimilar(fail)){
		return this.recipeSpiceyChicken(holder,fail);
	}
	if(!this.recipeSeasonedChicken(holder,fail).isSimilar(fail)){
		return this.recipeSeasonedChicken(holder,fail);
	}
	if(!this.recipeFurnaceSpiceyChicken(holder,fail).isSimilar(fail)){
		return this.recipeFurnaceSpiceyChicken(holder,fail);
	}
	if(!this.recipeFurnaceSeasonedChicken(holder,fail).isSimilar(fail)){
		return this.recipeFurnaceSeasonedChicken(holder,fail);
	}
		if(dish == null){
			dish = fail;
		}
		
	return dish;
}

	
	//CUSTOM RECIPES FROM THIS POINT ON
	
	private ItemStack recipeSeasonedChicken(ArrayList<String> recipe, ItemStack fail){

		ArrayList<String> foodrecipe = new ArrayList<String>();
		foodrecipe.add("Generic Chicken");
		foodrecipe.add("Salt");
		foodrecipe.add("Pepper");
		foodrecipe.add("AIR");
		foodrecipe.add("AIR");
		foodrecipe.add("AIR");
		foodrecipe.add("AIR");
		foodrecipe.add("AIR");
		foodrecipe.add("AIR");

		Material mat = Material.RAW_CHICKEN;
	if(recipe.containsAll(foodrecipe)){
		String FoodName = "Seasoned Raw Chicken";
		String description = "May cause food poisoning";
		ItemStack food = new ItemStack(mat);
		ItemMeta meta = food.getItemMeta();
		ArrayList<String> Lore = new ArrayList<String>();
		Lore.add(description);
		meta.setLore(Lore);
		meta.setDisplayName(FoodName);
		food.setItemMeta(meta);
		return food;
		}else{
		
			return fail;
			}
		}
	private ItemStack recipeSpiceyChicken(ArrayList<String> recipe, ItemStack fail){
		
		ArrayList<String> foodrecipe = new ArrayList<String>();
		foodrecipe.add("Generic Chicken");
		foodrecipe.add("Salt");
		foodrecipe.add("Pepper");
		foodrecipe.add("Cayenne");
		foodrecipe.add("AIR");
		foodrecipe.add("AIR");
		foodrecipe.add("AIR");
		foodrecipe.add("AIR");
		foodrecipe.add("AIR");

		Material mat = Material.RAW_CHICKEN;
	if(recipe.containsAll(foodrecipe)){	
		String FoodName = "Spicey Raw Chicken";
		String description = "Hot and Spicey and RAW!";
		ItemStack food = new ItemStack(mat);
		ItemMeta meta = food.getItemMeta();
		ArrayList<String> Lore = new ArrayList<String>();
		Lore.add(description);
		meta.setLore(Lore);
		meta.setDisplayName(FoodName);
		food.setItemMeta(meta);
		return food;
		}else{
			return fail;
			
		}
	
	}
	
	//FURNACE RECIPES ONLY
		
	private ItemStack recipeFurnaceSeasonedChicken(ArrayList<String> recipe, ItemStack fail){
			ArrayList<String> foodrecipe = new ArrayList<String>();
			foodrecipe.add("Seasoned Raw Chicken");
			Material mat = Material.COOKED_CHICKEN;
		if(recipe.containsAll(foodrecipe)){	
			String FoodName = "Seasoned Chicken";
			String description = "Tis the Season!";
			ItemStack food = new ItemStack(mat);
			ItemMeta meta = food.getItemMeta();
			ArrayList<String> Lore = new ArrayList<String>();
			Lore.add(description);
			meta.setLore(Lore);
			meta.setDisplayName(FoodName);
			food.setItemMeta(meta);
			return food;
			}else{
				return fail;
			}
		}
		
	private ItemStack recipeFurnaceSpiceyChicken(ArrayList<String> recipe, ItemStack fail){
		ArrayList<String> foodrecipe = new ArrayList<String>();
		foodrecipe.add("Seasoned Raw Chicken");
		Material mat = Material.COOKED_CHICKEN;
	if(recipe.containsAll(foodrecipe)){	
		String FoodName = "Seasoned Chicken";
		String description = "Tis the Season!";
		ItemStack food = new ItemStack(mat);
		ItemMeta meta = food.getItemMeta();
		ArrayList<String> Lore = new ArrayList<String>();
		Lore.add(description);
		meta.setLore(Lore);
		meta.setDisplayName(FoodName);
		food.setItemMeta(meta);
		return food;
		}else{
			return fail;
		}
	}
	
		
		
		
}
