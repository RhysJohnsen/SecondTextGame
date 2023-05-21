import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Tile{
	int id;
	String baseDesc;
	String desc;
	Item[] items = new Item[10];
	int itemCount;
	Being[] beings = new Being[10];
	int beingCount;
	int exits;
	
	
	public Tile(int idIn){
		id = idIn;
		boolean looking = true;
		Scanner sc;
		Scanner scx;
		String line = "";
		String readints;
		Scanner scl;
		//String desc;
		//Item items;
		int itemID;
		try{
			File map = new File("Map.txt");
			File exMap = new File("tmp.txt");
			sc = new Scanner(map);
			scx = new Scanner(exMap);
			sc.nextLine();
			
			
			while(sc.hasNextLine() && looking){
				if(sc.nextInt() == idIn){
					looking = false;
					line = sc.nextLine();
					//scl = new Scanner(line);
					baseDesc = line.substring(0, line.indexOf(';'));
					if(line.indexOf('{') > 0){
						readints = line.substring(line.indexOf(';') + 1, line.indexOf('{'));
						scl = new Scanner(readints);
						exits = scl.nextInt();
						if(exits == 0){
							baseDesc += " Can't go anywhere from here.";
						}
						else{
							baseDesc += " Can go";
							if((exits & 8) > 0)
								baseDesc += " North,";
							if((exits & 4) > 0)
								baseDesc += " East,";
							if((exits & 2) > 0)
								baseDesc += " South,";
							if((exits & 1) > 0)
								baseDesc += " West,";
						}
						readints = line.substring(line.indexOf('{') + 1, line.indexOf('}'));
						scl = new Scanner(readints);
						itemCount = scl.nextInt();
						if(itemCount > 0)
							desc = baseDesc + " On the ground, you see: ";
						for(int i = 0; i < itemCount; i++){
							itemID = scl.nextInt();
							items[i] = new Item(itemID);
							desc += items[i].getName() + ", ";
						}
						readints = line.substring(line.indexOf('}') + 1);
						scl = new Scanner(readints);
						beingCount = scl.nextInt();
						if(beingCount > 0)
							desc += " Around you, you see: ";
						for(int i = 0; i < beingCount; i++){
							itemID = scl.nextInt();
							beings[i] = new Being(itemID);
							desc += beings[i].getName() + ", ";
						}
						readints = line.substring(line.indexOf('}') + 1);
					}
					/*
					if(line.indexOf('<') > 0){
						if(toPrint.substring(toPrint.indexOf('<') + 1, toPrint.indexOf('>')).equals("nam")){
							System.out.print(toPrint.substring(0, toPrint.indexOf('<')));
							System.out.print(player.getName());
						}
						System.out.println(toPrint.substring(toPrint.indexOf('>') + 1));
					}
					else{
						System.out.println(toPrint);
					}
					*/
				}
				else{
					sc.nextLine();
				}
			}
			
			
		}
		catch(FileNotFoundException e){
			System.out.println("File Map.txt was not found.");
		}
	}
	
	public void setDesc(){
		desc = baseDesc;
		if(itemCount > 0)
			desc += " On the ground, you see: ";
		for(int i = 0; i < itemCount; i++){
			desc += items[i].getName() + ", ";
		}
		if(beingCount > 0)
			desc += " Around you, you see: ";
		for(int i = 0; i < beingCount; i++){
			desc += beings[i].getName() + ", ";
		}
	}
	
	public String getDesc(){
		return desc;
	}
	
	public Item removeItem(String itemName){
		Item toReturn = null;
		boolean found = false;
		int index = 0;
		String beingName = "";
		if(itemName.contains(" from ")){
			index = itemName.indexOf(" from ");
			//System.out.println(index);
			beingName = itemName.substring(index + 6);
			itemName = itemName.substring(0, index);
			//System.out.println("A" + beingName + itemName + "A");
			Being toSearch = this.searchBeing(beingName);
			if(toSearch == null){
				return null;
			}
			else if(toSearch.health > 0)
				System.out.println(toSearch.name + " is still alive.");
			else
				toReturn = toSearch.removeItem(itemName);
		}
		else{
			for(int i = 0; i < itemCount; i++){
				if(items[i].getName().equals(itemName)){
					toReturn = items[i];
					itemCount--;
					found = true;
					this.setDesc();
				}
				if(found){
					items[i] = items[i + 1];
				}
			}
		}
		//this.setDesc();
		return toReturn;
	}
	
	public void addItem(Item newItem){
		items[itemCount] = newItem;
		itemCount++;
		this.setDesc();
	}
	
	public int getID(){
		return id;
	}
	
	public Being searchBeing(String beingName){
		Being toReturn = null;
		for(int i = 0; i < beingCount; i++){
			if(beings[i].getName().equals(beingName)){
				toReturn = beings[i];
				//toReturn.listItems();
			}
		}
		return toReturn;
	}
	
	public String getPrintable(){
		String toReturn = id + "\t" + baseDesc + "{ ";
		for(int i = 0; i < itemCount; i++){
			toReturn += items[i].getID() + " ";
		}
		toReturn += "}\n";
		return toReturn;
	}
	
	public int getExits(){
		return exits;
	}
	
	/*
	public spawnAI(){
		Runnable runnable = new runNPCs();
		Thread t = new Thread(runNPCs());
		t.start();
	}
	
	public runNPCs(){
		
		
	}
	*/
}