package io.github.FiddyPercent.fiddycraft;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

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
		return this.getResult(holder);
	}
//NOTE this is where the recipe check goes	
	private ItemStack getResult(ArrayList<String> holder){
		ItemStack fail = new ItemStack(Material.SKULL);
		ItemStack dish = null;
		
		
		if(!this.recipeSpiceyChicken(holder,fail).isSimilar(fail)){
			Bukkit.broadcastMessage("spiceyChicken is true");
			return this.recipeSpiceyChicken(holder,fail);
			
		}
		if(!this.recipeSeasonedChicken(holder,fail).isSimilar(fail)){
			Bukkit.broadcastMessage("seasoned chicken is true");
			return this.recipeSeasonedChicken(holder,fail);
		}
		
		if(dish == null){
			dish = fail;
		}
		return dish;

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

		Material mat = Material.COOKED_CHICKEN;
		Bukkit.broadcastMessage("RF:"+ ChatColor.BLUE + foodrecipe.toString());
		Bukkit.broadcastMessage("R:" + ChatColor.GREEN + recipe.toString());
	if(recipe.containsAll(foodrecipe)){
		Bukkit.broadcastMessage(ChatColor.GOLD + "cooked chicken");
		String FoodName = "Cooked Chicken";
		String description = "Everybody loves chicken";
		ItemStack food = new ItemStack(mat);
		ItemMeta meta = food.getItemMeta();
		ArrayList<String> Lore = new ArrayList<String>();
		Lore.add(description);
		meta.setLore(Lore);
		meta.setDisplayName(FoodName);
		food.setItemMeta(meta);
		Bukkit.broadcastMessage(ChatColor.DARK_GREEN + "normal returning food");
		return food;
		}else{
		//Bukkit.broadcastMessage(ChatColor.RED +foodrecipe.toString() + "Foodrecipe <-- recipeoutput --> " + recipe.toString());
			Bukkit.broadcastMessage(foodrecipe.size() + " FR <=== SEASONDED R===> " + recipe.size());
			
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

		Material mat = Material.COOKED_CHICKEN;
		Bukkit.broadcastMessage("RF:"+ ChatColor.BLUE + foodrecipe.toString());
		Bukkit.broadcastMessage("R:" + ChatColor.GREEN + recipe.toString());
	if(recipe.containsAll(foodrecipe)){	
		Bukkit.broadcastMessage(ChatColor.GOLD + "HOT CHICKEN");
		String FoodName = "Spicey Cooked Chicken";
		String description = "Hot and Spicey!";
		ItemStack food = new ItemStack(mat);
		ItemMeta meta = food.getItemMeta();
		ArrayList<String> Lore = new ArrayList<String>();
		Lore.add(description);
		meta.setLore(Lore);
		meta.setDisplayName(FoodName);
		food.setItemMeta(meta);
		Bukkit.broadcastMessage(ChatColor.DARK_GREEN + "hot returning food");
		return food;
		}else{
		//Bukkit.broadcastMessage(foodrecipe.toString()+ ChatColor.BLUE + "Foodrecipe <-- recipeoutput --> " + recipe.toString());
			Bukkit.broadcastMessage(foodrecipe.size() + " FR <=== HOT R===> " + recipe.size());
			return fail;
			
		}
	
	}
	
	
	
	
	
	
	
	
	
}
