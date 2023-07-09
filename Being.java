import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Random;
public class Being{
	
	int id;
	String name;
	int health;
	int armourpts;
	int dmgpts;
	Item[] items = new Item[10];
	int itemCount;
	Random rand = new Random();
	Item equiped[] = new Item[6]; //0:head, 1:torso, 2:legs, 3:feet, 4:hands, 5:weapon
	boolean aggressive;
	
	public Being(int idIn){
		Scanner sc;
		Scanner scl;
		String line = "";
		String readints;
		int itemID;
		boolean looking = true;
		id = idIn;
		int possibleItems;
		itemCount = 0;
		armourpts = 0;
		try{
			File list = new File("Beings.txt");
			sc = new Scanner(list);
			sc.nextLine();
			
			
			while(sc.hasNextLine() && looking){
				if(sc.nextInt() == idIn){
					looking = false;
					line = sc.nextLine();
					//scl = new Scanner(line);
					name = line.substring(line.indexOf('<') + 1, line.indexOf('>'));
					if(line.indexOf('{') > 0){
						readints = line.substring(line.indexOf('>') + 1, line.indexOf('{'));
						scl = new Scanner(readints);
						health = scl.nextInt();
						if(scl.nextInt() == 0){
							aggressive = false;
						}
						else{
							aggressive = true;
						}
						readints = line.substring(line.indexOf('{') + 1, line.indexOf('}'));
						scl = new Scanner(readints);
						possibleItems = scl.nextInt();
						for(int i = 0; i < possibleItems; i++){
							if(rand.nextInt(100) < scl.nextInt()){
								itemID = scl.nextInt();
								items[itemCount] = new Item(itemID);
								if(equiped[items[itemCount].equipSlot] == null)
									this.equip(items[itemCount].name);
								itemCount++;
							}
							else
								scl.nextInt();
						}
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
			System.out.println("File Beings.txt was not found.");
		}
	}
	
	public void changeHealth(double dmg){
		if(dmg < 0 && health > 0){
			if(!aggressive){
				System.out.println(name + " is now aggressive.");
				aggressive = true;
			}
			dmg = dmg * (1 - ((double)armourpts / 100));
			health += (int)dmg;
			System.out.println("Dealt " + (-(int)dmg) + " damage to " + name);
			if(health <= 0){
				System.out.println("Killed " + name);
				health = 0;
				name = "dead " + name;
			}
		}
		else if(health > 0){
			health += dmg;
			System.out.println("Healed " + name + " by " + dmg);
		}
	}
	
	public String getName(){
		return name;
	}
	
	public void listItems(){
		System.out.println("Items from " + name);
		for(int i = 0; i < itemCount; i++){
			if(items[i].getIsEquipped())
				System.out.print("E: ");
			System.out.println(items[i].getName() + "\tWeight: " + items[i].getWeight());
		}
	}
	
	public boolean equip(String itemName){
		boolean found = false;
		Item toEquip = null;
		for(int i = 0; i <= itemCount && !found; i++){
			if(items[i].getName().equals(itemName) && items[i].getEquipSlot() >= 0){
				found = true;
				toEquip = items[i];
			}
		}
		if(found){
			if(equiped[toEquip.getEquipSlot()] != null){
				this.unequip(equiped[toEquip.getEquipSlot()].getName());
			}
			equiped[toEquip.getEquipSlot()] = toEquip;
			toEquip.changeIsEquipped();
			if(toEquip.equipSlot == 5)
				dmgpts = toEquip.effect;
			else
				armourpts += toEquip.getEffectiveness();
		}
		else
			System.out.println("Could not equip " + itemName);
		return found;
	}
	
	public boolean unequip(String itemName){
		boolean found = false;
		for(int i = 0; i < 6 && !found; i++){
			if(equiped[i] != null && equiped[i].getName().equals(itemName)){
				found =  true;
				equiped[i].changeIsEquipped();
				if(equiped[i].equipSlot == 5)
					dmgpts = 0;
				else
					armourpts -= equiped[i].getEffectiveness();
				equiped[i] = null;
			}
		}
		return found;
	}
	
	public Item removeItem(String itemName){
		Item toReturn = null;
		boolean found = false;
		for(int i = 0; i < itemCount; i++){
			if(items[i].getName().equals(itemName)){
				toReturn = items[i];
				//weight -= toReturn.getWeight();
				itemCount--;
				found = true;
				if(toReturn.getIsEquipped())
					this.unequip(toReturn.getName());
			}
			if(found){
				items[i] = items[i + 1];
			}
		}
		if(found)
			System.out.println("Took " + itemName);
		else
			System.out.println(name + " did not have " + itemName);
		return toReturn;
	}
	
	public void applySpell(Spell toApply){
		switch(toApply.statChanged){
			case 'h':
				if(toApply.isPercent){
					//health *= 1 + (toApply.effect / 100);
					this.changeHealth(health * (toApply.effect / 100));
				}
				else{
					//health += toApply.effect;
					this.changeHealth(toApply.effect);
				}
			break;
			default:
				System.out.println(toApply.statChanged + "is not a valid spell stat");
		}
	}
}