public class Input{
	
	static Player player;
	public static void setPlayer(Player newPlayer){
		player = newPlayer;
	}
	
	
	
	public static boolean input(String in, Thread npcThread){
		boolean proceed = true;
		int wordIndex = in.indexOf(' ');
		
		if(wordIndex < 0)
			wordIndex = in.length();
		
		switch(in.substring(0, wordIndex)){
			case "look":
				System.out.println(player.curTile.getDesc());
				break;
			case "go":
				switch(in.substring(wordIndex + 1)){
					case "north":
						if((player.getCurTile().getExits() & 8) > 0){
							if(player.changeY(1))
								System.out.println("Went north");
						}
						else
							System.out.println("Could not go north");
						break;
					case "south":
						if((player.getCurTile().getExits() & 2) > 0){
							if(player.changeY(-1))
								System.out.println("Went south");
						}
						else
							System.out.println("Could not go south");
						break;
					case "east":
						if((player.getCurTile().getExits() & 4) > 0){
							if(player.changeX(1))
								System.out.println("Went east");
						}
						else
							System.out.println("Could not go east");
						break;
					case "west":
						if((player.getCurTile().getExits() & 1) > 0){
							if(player.changeX(-1))
								System.out.println("Went west");
						}
						else
							System.out.println("Could not go west");
						break;
				}
				break;
			case "take":
				Item toTake = player.getCurTile().removeItem(in.substring(wordIndex + 1));
				if(toTake != null){
					player.addItem(toTake);
					npcThread.resume();
				}
				else
					System.out.println("There is no " + in.substring(wordIndex + 1) + " around");
				break;
			case "drop":
				Item toDrop = player.removeItem(in.substring(wordIndex + 1));
				if(toDrop != null){
					player.getCurTile().addItem(toDrop);
					npcThread.resume();
				}
				break;
			case "inventory":
				player.listInven();
				break;
			case "equip":
				player.equip(in.substring(wordIndex + 1));
				break;
			case "unequip":
				player.unequip(in.substring(wordIndex + 1));
				break;
			case "attack":
				Being toAttack = player.curTile.searchBeing(in.substring(wordIndex + 1));
				if(toAttack != null){
					toAttack.changeHealth(-player.equiped[5].getEffectiveness());
					player.curTile.setDesc();
					npcThread.resume();
				}
				break;
			case "search":
				Being toSearch = player.curTile.searchBeing(in.substring(wordIndex + 1));
				if(toSearch != null){
					toSearch.listItems();
					npcThread.resume();
				}
				else
					System.out.println("There is no " + in.substring(wordIndex + 1) + " around");
				break;
			case "use":
				System.out.println("Not yet implemented");
				break;
			case "wait":
				npcThread.resume();
				break;
			case "cast":
				System.out.println("Not yet implemented");
				break;
			case "help":
				Printing.printHelp();
				break;
			case "quit":
				proceed = false;
				break;
			default:
				System.out.println("Not a valid action. Use 'Help' to list possible actions.");
		}
		return proceed;
	}
}