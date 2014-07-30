package io.github.FiddyPercent.fiddycraft;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.meta.ItemMeta;

public class RecipeBook {
	
	private FiddyCraft plugin;

	
	public RecipeBook(FiddyCraft plugin){
		this.plugin = plugin;
	}
	
	public void flour() {
		 ItemStack item = new ItemStack(Material.SUGAR);
		 ItemMeta imeta = item.getItemMeta();
	       ArrayList<String> Lore = new ArrayList<String>();
	       Lore.add("Flour for baking");
	       imeta.setDisplayName("Flour");
	       imeta.setLore(Lore);
	       item.setItemMeta(imeta);
		ShapelessRecipe flour = new ShapelessRecipe(item);
		flour.addIngredient(Material.WHEAT);
		Bukkit.addRecipe(flour);
		}
	
	public void hotRawChicken() {
		 ItemStack item = new ItemStack(Material.ROTTEN_FLESH); 
		ShapelessRecipe hotRawChicken = new ShapelessRecipe(item);
		hotRawChicken.addIngredient(Material.NETHER_STALK);
		hotRawChicken.addIngredient(Material.PUMPKIN_SEEDS);
		hotRawChicken.addIngredient(Material.MELON_SEEDS);
		hotRawChicken.addIngredient(Material.RAW_CHICKEN);
		Bukkit.addRecipe(hotRawChicken);
		}
	
	public void PreparedChicken() {
		 ItemStack item = new ItemStack(Material.ROTTEN_FLESH);
		ShapelessRecipe PreparedChicken = new ShapelessRecipe(item);
		PreparedChicken.addIngredient(Material.PUMPKIN_SEEDS);
		PreparedChicken.addIngredient(Material.MELON_SEEDS);
		PreparedChicken.addIngredient(Material.RAW_CHICKEN);
		Bukkit.addRecipe(PreparedChicken);
		}
//STEAK
	public void PreparedSteak() {
		 ItemStack item = new ItemStack(Material.RAW_BEEF);
		 ItemMeta imeta = item.getItemMeta();
	       ArrayList<String> Lore = new ArrayList<String>();
	       Lore.add("Ready for Cooking");
	       imeta.setDisplayName("Prepared Steak");
	       imeta.setLore(Lore);
	       item.setItemMeta(imeta);
	       
		ShapelessRecipe PreparedSteak = new ShapelessRecipe(item);
		PreparedSteak.addIngredient(Material.PUMPKIN_SEEDS);
		PreparedSteak.addIngredient(Material.MELON_SEEDS);
		PreparedSteak.addIngredient(Material.RAW_BEEF);
		Bukkit.addRecipe(PreparedSteak);
		}
//PORK CHOP
	public void PreparedPorkChop() {
		 ItemStack item = new ItemStack(Material.PORK);
		 ItemMeta imeta = item.getItemMeta();
	       ArrayList<String> Lore = new ArrayList<String>();
	       Lore.add("Ready for Cooking");
	       imeta.setDisplayName("Prepared Pork Chop");
	       imeta.setLore(Lore);
	       item.setItemMeta(imeta);
		ShapelessRecipe PreparedPorkChop = new ShapelessRecipe(item);
		PreparedPorkChop.addIngredient(Material.PUMPKIN_SEEDS);
		PreparedPorkChop.addIngredient(Material.MELON_SEEDS);
		PreparedPorkChop.addIngredient(Material.PORK);
		Bukkit.addRecipe(PreparedPorkChop);
		}
//PREPAIRED FISH
	public void PreparedFish() {
		 ItemStack item = new ItemStack(Material.RAW_FISH);
		 ItemMeta imeta = item.getItemMeta();
	       ArrayList<String> Lore = new ArrayList<String>();
	       Lore.add("Ready for Cooking");
	       imeta.setDisplayName("Prepared Fish");
	       imeta.setLore(Lore);
	       item.setItemMeta(imeta);
	       
		ShapelessRecipe PreparedFish = new ShapelessRecipe(item);
		PreparedFish.addIngredient(Material.PUMPKIN_SEEDS);
		PreparedFish.addIngredient(Material.MELON_SEEDS);
		PreparedFish.addIngredient(Material.RAW_FISH);
		Bukkit.addRecipe(PreparedFish);
		}
	

//BREAD DOUGH
	public void breadDough() {
		 ItemStack item = new ItemStack(Material.INK_SACK, 1, (short) 9);
		 ItemMeta imeta = item.getItemMeta();
	       ArrayList<String> Lore = new ArrayList<String>();
	       Lore.add("Bread Dough for making bread");
	       imeta.setDisplayName("Bread milDough");
	       imeta.setLore(Lore);
	       item.setItemMeta(imeta);
	       
		ShapelessRecipe breadDough = new ShapelessRecipe(item);
		breadDough.addIngredient(Material.PUMPKIN_SEEDS);
		breadDough.addIngredient(Material.SUGAR);
		breadDough.addIngredient(Material.SUGAR);
		breadDough.addIngredient(Material.MILK_BUCKET);
		breadDough.addIngredient(Material.EGG);
		
		Bukkit.addRecipe(breadDough);
		}
//BUTTER
	public void butter() {
		 ItemStack item = new ItemStack(Material.INK_SACK, 1,  (short) 11);
		 ItemMeta imeta = item.getItemMeta();
	       ArrayList<String> Lore = new ArrayList<String>();
	       Lore.add("Butter made from milk");
	       imeta.setDisplayName("Butter");
	       imeta.setLore(Lore);
	       item.setItemMeta(imeta);
	       
		ShapelessRecipe Butter = new ShapelessRecipe(item);
		Butter.addIngredient(Material.PUMPKIN_SEEDS);
		Butter.addIngredient(Material.MILK_BUCKET);
		Bukkit.addRecipe(Butter);
		}
//SWEET BREAD DOUGH
	public void sweetBreadDough() {
			ItemStack item = new ItemStack(Material.INK_SACK, 1,  (short) 13);
			ItemMeta imeta = item.getItemMeta();
		    ArrayList<String> Lore = new ArrayList<String>();
		    Lore.add("Bread Dough for making bread");
		    imeta.setDisplayName("Sweet Bread Dough");
		    imeta.setLore(Lore);
		    item.setItemMeta(imeta);
		       
			ShapelessRecipe breadDough = new ShapelessRecipe(item);
			breadDough.addIngredient(Material.PUMPKIN_SEEDS);
			breadDough.addIngredient(Material.SUGAR);
			breadDough.addIngredient(Material.SUGAR);
			breadDough.addIngredient(Material.SUGAR);
			breadDough.addIngredient(Material.MILK_BUCKET);
			breadDough.addIngredient(Material.EGG);
			
			Bukkit.addRecipe(breadDough);
		}

	@SuppressWarnings("deprecation")
	public void  sweetlBread(){
			ItemStack BoiledEgg = new ItemStack(Material.BREAD);
			ItemMeta meta = BoiledEgg.getItemMeta();
			ArrayList<String> Lore = new ArrayList<String>();
			meta.setDisplayName("Baked Bread");
			Lore.add("Bread, thats about it");
			meta.setLore(Lore);
			BoiledEgg.setItemMeta(meta);
			plugin.getServer().addRecipe(new FurnaceRecipe(new ItemStack(BoiledEgg), Material.INK_SACK, 9));
	}
	
	@SuppressWarnings("deprecation")
	public void  normalBread(){
			ItemStack BoiledEgg = new ItemStack(Material.BREAD);
			ItemMeta meta = BoiledEgg.getItemMeta();
			ArrayList<String> Lore = new ArrayList<String>();
			meta.setDisplayName("Baked Bread");
			Lore.add("Bread, thats about it");
			meta.setLore(Lore);
			BoiledEgg.setItemMeta(meta);
			plugin.getServer().addRecipe(new FurnaceRecipe(new ItemStack(BoiledEgg), Material.INK_SACK, 9));
	}
	
//BPOILED EGG
	public void  boiledEgg(){
			ItemStack BoiledEgg = new ItemStack(Material.EGG);
			ItemMeta meta = BoiledEgg.getItemMeta();
			ArrayList<String> Lore = new ArrayList<String>();
			meta.setDisplayName("Boiled Egg");
			Lore.add("An egg boiled in water");
			meta.setLore(Lore);
			BoiledEgg.setItemMeta(meta);
			plugin.getServer().addRecipe(new FurnaceRecipe(new ItemStack(BoiledEgg), Material.EGG));
		}
//HOT MILK
	public void  hotMilk(){
			ItemStack HotMilk = new ItemStack(Material.MILK_BUCKET);
			ItemMeta meta = HotMilk.getItemMeta();
			ArrayList<String> Lore = new ArrayList<String>();
			meta.setDisplayName("Hot Milk");
			Lore.add("Milk that has been heated");
			meta.setLore(Lore);
			HotMilk.setItemMeta(meta);
			plugin.getServer().addRecipe(new FurnaceRecipe(new ItemStack(HotMilk), Material.MILK_BUCKET));
			
	}
	
//BROTH POWDER
	public void dehydratedMeat(){
		   ItemStack Broth = new ItemStack(Material.INK_SACK, 1 , (short) 3);
	        ItemMeta broth = Broth.getItemMeta();
	        ArrayList<String> Lore = new ArrayList<String>();
	        Lore.add("Home made broth powder");
	        broth.setLore(Lore);
	        broth.setDisplayName("Broth Powder");
	        Broth.setItemMeta(broth);
	        plugin.getServer().addRecipe(new FurnaceRecipe(new ItemStack(Broth), Material.COOKED_CHICKEN));
	}
//SPICEY CHICKEN
	public void spiceyChicken(){
		   ItemStack item = new ItemStack(Material.COOKED_CHICKEN);
	        ItemMeta meta = item.getItemMeta();
	        ArrayList<String> Lore = new ArrayList<String>();
	        Lore.add("Spicey Chicken careful its hot");
	        meta.setLore(Lore);
	        meta.setDisplayName("Spicey Chicken");
	        item.setItemMeta(meta);
	        plugin.getServer().addRecipe(new FurnaceRecipe(new ItemStack(item), Material.RAW_CHICKEN));
	}
//NORMAL CHICKEN
	public void normalChicken(){
		   ItemStack item = new ItemStack(Material.COOKED_CHICKEN);
	        ItemMeta meta = item.getItemMeta();
	        ArrayList<String> Lore = new ArrayList<String>();
	        Lore.add("Chicken, everyone loves chicken");
	        meta.setLore(Lore);
	        meta.setDisplayName("Baked Chicken");
	        item.setItemMeta(meta);
	        plugin.getServer().addRecipe(new FurnaceRecipe(new ItemStack(item), Material.RAW_CHICKEN));
	}
	
	public void steak(){
		   ItemStack item = new ItemStack(Material.COOKED_BEEF);
	        ItemMeta meta = item.getItemMeta();
	        ArrayList<String> Lore = new ArrayList<String>();
	        Lore.add("Steak, mmmm meaty");
	        meta.setLore(Lore);
	        meta.setDisplayName("Steak");
	        item.setItemMeta(meta);
	        plugin.getServer().addRecipe(new FurnaceRecipe(new ItemStack(item), Material.RAW_BEEF));
	}

	public void porkchop(){
		   ItemStack item = new ItemStack(Material.GRILLED_PORK);
	        ItemMeta meta = item.getItemMeta();
	        ArrayList<String> Lore = new ArrayList<String>();
	        Lore.add("Pork in the big city");
	        meta.setLore(Lore);
	        meta.setDisplayName("Pork Chop");
	        item.setItemMeta(meta);
	        plugin.getServer().addRecipe(new FurnaceRecipe(new ItemStack(item), Material.PORK));
	}
	public void grilledfish(){
		   ItemStack item = new ItemStack(Material.COOKED_FISH);
	        ItemMeta meta = item.getItemMeta();
	        ArrayList<String> Lore = new ArrayList<String>();
	        Lore.add("Smells fishy");
	        meta.setLore(Lore);
	        meta.setDisplayName("Baked Fish");
	        item.setItemMeta(meta);
	        plugin.getServer().addRecipe(new FurnaceRecipe(new ItemStack(item), Material.RAW_FISH));
	}
	
//MUSHROOM SOUP
	@SuppressWarnings("deprecation")
	public void MushroomSoup(){
			ItemStack MushRoomSoup = new ItemStack(Material.MUSHROOM_SOUP);
			ItemMeta imeta = MushRoomSoup.getItemMeta();
			ArrayList<String> Lore = new ArrayList<String>();
			imeta.setDisplayName("Mushroom Soup");
			Lore.add("Home made mushroom soup");
			imeta.setLore(Lore);
			MushRoomSoup.setItemMeta(imeta);
			ShapedRecipe msoup = new ShapedRecipe(new ItemStack(MushRoomSoup));
	        msoup.shape(" B ","MWM"," K ");
		    msoup.setIngredient('M', Material.BROWN_MUSHROOM);
	        msoup.setIngredient('B', Material.INK_SACK, 3);
	        msoup.setIngredient('W', Material.WATER_BUCKET);
	        msoup.setIngredient('K', Material.BOWL);
	        plugin.getServer().addRecipe(msoup);
	}
	
	public void sweetMilk(){
		    ItemStack item = new ItemStack(Material.MILK_BUCKET);
	        ItemMeta imeta = item.getItemMeta();
	        ArrayList<String> Lore = new ArrayList<String>();
	        imeta.setDisplayName("Sweet Milk");
	        Lore.add("Sweetened Milk, may cause diabetes");
	        imeta.setLore(Lore);
	        item.setItemMeta(imeta);
	        ShapedRecipe sweetMilk = new ShapedRecipe(new ItemStack(item));
	        sweetMilk.shape("S","M");
	        sweetMilk.setIngredient('S', Material.SUGAR);
	        sweetMilk.setIngredient('M', Material.MILK_BUCKET);
	        plugin.getServer().addRecipe(sweetMilk);
	}
	
	
	
}
