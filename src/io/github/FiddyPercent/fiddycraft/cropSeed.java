package io.github.FiddyPercent.fiddycraft;

public enum cropSeed {

	CARROT("Carrot Seeds", 9),
	PUMPKIN("Pumpkin Seeds", 15),
	MELON("Melon Seeds", 12),
	POTATO("Potato Seeds", 10 ),
	WHEAT("Wheat Seeds", 7),
	ROSE("Rose Seeds", 8),
	LILAC("Lilac Seeds", 8),
	PEONY("Peony Seeds", 9),
	ALLIUM("Allium Seeds", 11),
	BLUE_ORCHID("Blue Orchid Seeds", 7),
	POPPY("Poppy Seeds", 9),
	DANDELION("Dandelion Seeds", 5),
	OXEYE_DAISY("Oxeye Dasiy Seeds", 6),
	PINK_TULIP("Pink Tulip Seeds", 5),
	WHITE_TULIP("White Tulip Seeds", 6),
	ORANGE_TULIP("Orange Tulip Seeds", 6),
	RED_TULIP("Red Tulip Seeds", 6),
	AZURE_BLUET("Azure Bluet Seeds", 8),
	PEPPER("Pepper Plant Seeds", 10); 
	
	 private String seedName;
	 private int cycles;
	 
     private cropSeed(String seedName, int cycles) {
             this.seedName = seedName;
             this.cycles = cycles;
     }
    
     public String getseedName() {
             return seedName;
     }

     public int getcycles() {
         return cycles;
     }
}
