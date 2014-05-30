package io.github.FiddyPercent.fiddycraft;

import org.bukkit.permissions.Permission;

public class FcPerm {

	public final FiddyCraft plugin;
	 
	public FcPerm(FiddyCraft plugin){
	this.plugin = plugin;
	}
	
	public void setPerms(){
		
	
		new Permission ("FiddyCraft.noob");
		new Permission ("FiddyCraft.police");
		new Permission ("FiddyCraft.assasin");
		new Permission ("FiddyCraft.thug");
		new Permission ("FiddyCraft.judge");
		new Permission ("FiddyCraft.detective");
		new Permission ("FiddyCraft.rich");
		new Permission ("FiddyCraft.common");
		new Permission ("FiddyCraft.poor ");

	}
	

	
}
