package io.github.FiddyPercent.fiddycraft;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.bukkit.entity.Player;

public class FcPlayers {
	public String Name;
	private int maxAnimals;
	private int maxCrops;
	private int maxFoodLevel;
	private boolean canLawyer;
	private boolean isWanted;
	private List<?> towersCompleted;
	private int playersKilled;
	private int timesConvected;
	private int timesArrested;
	private boolean isOnParole;
	private boolean pendingTrial;
	private String playerJob;
	private ArrayList<?> animalList;
	public final FiddyCraft plugin;
	private Player p;
	private String StartDate;
	private Set<String> allPlayers;
	private String recipeList;
	private String growableList;
	private String GovenmentJob;
	private Set<String> listAnimals;
	
	public FcPlayers(FiddyCraft plugin, Player P){ 
		this.plugin = plugin;
		Player p = P;
		allPlayers = plugin.getPlayerInfo().getConfigurationSection("Players").getKeys(false);
		Name = p.getName();
		StartDate = plugin.getPlayerInfo().getString("Players." +p.getUniqueId().toString()+ ".StartDate");
		maxAnimals =  plugin.getPlayerInfo().getInt("Players." +p.getUniqueId().toString()+ ".Max Animals");
		maxCrops = plugin.getPlayerInfo().getInt("Players." +p.getUniqueId().toString()+ ".Max Crops");
		maxFoodLevel = plugin.getPlayerInfo().getInt("Players." +p.getUniqueId().toString()+ ".Max Food Level");
		canLawyer = plugin.getPlayerInfo().getBoolean("Players." +p.getUniqueId().toString()+ ".Can Lawyer");
		isWanted = plugin.getPlayerInfo().getBoolean("Players." +p.getUniqueId().toString()+ ".Wanted");
		towersCompleted = plugin.getPlayerInfo().getList("Players." +p.getUniqueId().toString() + ".Towers Completed");
		playersKilled = plugin.getPlayerInfo().getInt("Players." +p.getUniqueId().toString() + ".Murders");
		timesConvected = plugin.getPlayerInfo().getInt("Players." +p.getUniqueId().toString() + ".Convictions");
		timesArrested = plugin.getPlayerInfo().getInt("Players." +p.getUniqueId().toString() + ".Arrests");
		isOnParole =  plugin.getPlayerInfo().getBoolean("Players." +p.getUniqueId().toString() + ".On Parole");
		playerJob = plugin.getPlayerInfo().getString("Players." +p.getUniqueId().toString()+ ".Job");
		animalList = (ArrayList<?>) plugin.getPlayerInfo().getList("Players." +p.getUniqueId().toString()+ ".Animals");
		pendingTrial = plugin.getPlayerInfo().getBoolean("Players." +p.getUniqueId().toString()+ ".Pending Trial");
		recipeList = plugin.getPlayerInfo().getString("Players."+ p.getUniqueId().toString() + ".Recipe List");
		growableList = plugin.getPlayerInfo().getString("Players."+ p.getUniqueId().toString() + ".Growable List");
		GovenmentJob = plugin.getPlayerInfo().getString("Players." + p.getUniqueId().toString() + ".Gov Job");
		listAnimals = plugin.getAnimalData().getConfigurationSection("Farmer." + p.getUniqueId() + ".Animals" ).getKeys(false);
	}
	public String getfcPlayerName(){
		return p.getName();
	}
	public String getStartDate(){
		return StartDate;
	}
	public int getMaxAnimals(){
		return maxAnimals;
	}
	public int getMaxCrops(){
		return maxCrops;
	}
	public int getMaxFoodLevel(){
		return maxFoodLevel;
	}
	public boolean canLawyer(){
		return canLawyer;
	}
	public boolean getIsWanted(){
		return isWanted;
	}
	public List<?> getTowersCompleted(){
		return towersCompleted;
	}
	public boolean getIsOnParol(){
		return isOnParole;
	}
	public int getPlayersKilled(){
		return playersKilled;
	}
	public int getTimesConvicted(){
		return timesConvected;
	}
	public int getTimesArrested(){
		return timesArrested;
	}
	public String getPlayerJob(){
		return playerJob;
	}
	public ArrayList<?> getAnimalList(){
		return animalList;
	}
	public boolean getisPendingTrial(){
		return pendingTrial;
	}
	public String getRecipeList(){
		return recipeList;
	}
	public String getGrowableList(){
		return growableList;
	}

	public void setMaxAnimals(int mAnimals){
		plugin.getPlayerInfo().set("Players." +p.getUniqueId().toString()+ ".Max Animals", mAnimals);
		plugin.savePlayerInfo();
	}
	public void setMaxCrops(int mCrops){
		plugin.getPlayerInfo().set("Players." +p.getUniqueId().toString()+ ".Max Crops", mCrops);
		plugin.savePlayerInfo();
	}
	public void setMaxFoodLevel(int mFoodLevel){
		plugin.getPlayerInfo().set("Players." +p.getUniqueId().toString()+ ".Max Food Level", mFoodLevel);
		plugin.savePlayerInfo();
	}
	public void setCanLawyer(boolean cLawyer){
		plugin.getPlayerInfo().set("Players." +p.getUniqueId().toString()+ ".Can Lawyer", cLawyer);
		plugin.savePlayerInfo();
	}
	public void setIsWanted(boolean iWanted){
		plugin.getPlayerInfo().set("Players." +p.getUniqueId().toString()+ ".Wanted", iWanted);
		plugin.savePlayerInfo();
	}
	public void setTowersCompleted(List<?> tower){
		plugin.getPlayerInfo().set("Players." +p.getUniqueId().toString()+ ".Max Animals", tower);
		plugin.savePlayerInfo();
	}
	public void setPlayersKilled(int pKilled){
		plugin.getPlayerInfo().set("Players." +p.getUniqueId().toString() + ".Murders", pKilled);
		plugin.savePlayerInfo();
	}
	public void setTimesConvicted(int tConvected){
		plugin.getPlayerInfo().set("Players." +p.getUniqueId().toString() + ".Convictions", tConvected);
		plugin.savePlayerInfo();
	}
	public void setTimesArrested(int tArrested){
		plugin.getPlayerInfo().set("Players." +p.getUniqueId().toString() + ".Arrests" , tArrested);
		plugin.savePlayerInfo();
	}
	public void setIsOnParol(boolean onParol){
		plugin.getPlayerInfo().set("Players." +p.getUniqueId().toString() + ".On Parol", onParol);
		plugin.savePlayerInfo();
	}
	public void setPlayerJob(String pJob){
		plugin.getPlayerInfo().set("Players." +p.getUniqueId().toString() + ".Job", pJob);
		plugin.savePlayerInfo();
	}
	public void setAnimalList(ArrayList<String> aList){
		plugin.getPlayerInfo().set("Players." +p.getUniqueId().toString() + ".AnimalList", aList);
		plugin.savePlayerInfo();
	}
	public void setFcPlayerName(String name){
		plugin.getPlayerInfo().set("Players." +p.getUniqueId().toString()+ ".Name", p.getName());
		plugin.savePlayerInfo();
	}
	public void setStartDate(){
		 plugin.getPlayerInfo().set("Players." +p.getUniqueId().toString(), plugin.getTimeStamp());
		 plugin.savePlayerInfo();
	}
	public void setPendingTrial(boolean pending){
		plugin.getPlayerInfo().set("Players." +p.getUniqueId().toString()+ ".Pending Trial", pending);
		 plugin.savePlayerInfo();
	}
	public void addRecipeToList(String recipe){
		String old = this.getRecipeList();
		String newlist = old  + ":" + recipe;
		plugin.getPlayerInfo().set("Players."+ p.getUniqueId().toString() + ".Recipe List", newlist);
		plugin.savePlayerInfo();
	}
	public void removeRecipeFromList(String recipe){
		String old = this.getRecipeList();
		String newlist = old.replace(":"+ recipe, "");
		plugin.getPlayerInfo().set("Players."+ p.getUniqueId().toString() + ".Recipe List", newlist);
		plugin.savePlayerInfo();
	}
	public void addToGrowableList(String plant){
		String old = this.getRecipeList();
		String newlist = old  + ":" + plant;
		plugin.getPlayerInfo().set("Players."+ p.getUniqueId().toString() + ".Growable List", newlist);
		plugin.savePlayerInfo();
	}
	public void removeFromGrowableList(String plant){
		String old = this.getRecipeList();
		String newlist = old.replace(":"+ plant, "");
		plugin.getPlayerInfo().set("Players."+ p.getUniqueId().toString() + ".Growable List", newlist);
		plugin.savePlayerInfo();
	}
	public String getGovenmentJob() {
		return GovenmentJob;
	}
	public void setGovenmentJob(String govjob) {
		plugin.getPlayerInfo().set("Players." + p.getUniqueId().toString() + ".Gov Job", govjob);
		plugin.savePlayerInfo();
	}
}
