import java.util.HashMap;

public class Player{
	
	String name;
	Item inventory[] = new Item[10];
	Item equiped[] = new Item[6]; //0:head, 1:torso, 2:legs, 3:feet, 4:hands, 5:weapon
	int itemCount;
	int maxWeight;
	int weight;
	int armourpts;
	int dmgpts;
	int health;
	int maxHealth;
	Tile curTile;
	int posX;
	int posY;
	HashMap<Integer, Tile> explored;
	Spell spellList[] = new Spell[10];
	int spellCount;
	
	public Player(){
		name = "Prisoner";
		posX = 50;
		posY = 50;
		curTile = new Tile(5050);
		//itemCount = 2;
		itemCount = 3;
		inventory[0] = new Item(4);
		inventory[1] = new Item(5);
		inventory[2] = new Item(2);
		maxWeight = 30;
		maxHealth = 30;
		health = 30;
		weight = inventory[0].getWeight() + inventory[1].getWeight();
		armourpts = 0;
		this.equip(inventory[0].getName());
		this.equip(inventory[1].getName());
		this.equip(inventory[2].getName());
		spellList[0] = new Spell(0);
		spellList[1] = new Spell(1);
		spellCount = 2;
		}
	
	public void setExplored(HashMap<Integer, Tile> exploredIn){
		explored = exploredIn;
	}
	
	public boolean changeX(int moved){
		posX += moved;
		curTile = explored.get((posX * 100) + posY);
		if(curTile == null)
			curTile = new Tile((posX * 100) + posY);
		return true;
	}
	
	public boolean changeY(int moved){
		posY += moved;
		curTile = explored.get((posX * 100) + posY);
		if(curTile == null)
			curTile = new Tile((posX * 100) + posY);
		return true;
	}
	
	public int getCoords(){
		return (posX * 100) + posY;
	}
	
	public String getName(){
		return name;
	}
	
	public Tile getCurTile(){
		return curTile;
	}
	
	public void setName(String newName){
		name = newName;
	}
	
	
	public void listInven(){
		System.out.println("Weight: " + weight + "/" + maxWeight);
		System.out.println("Armour: " + armourpts);
		for(int i = 0; i < itemCount; i++){
			if(inventory[i].getIsEquipped())
				System.out.print("E: ");
			System.out.println(inventory[i].getName() + "\tWeight: " + inventory[i].getWeight());
		}
	}
	
	public Item removeItem(String itemName){
		Item toReturn = null;
		boolean found = false;
		for(int i = 0; i < itemCount; i++){
			if(inventory[i].getName().equals(itemName)){
				toReturn = inventory[i];
				weight -= toReturn.getWeight();
				itemCount--;
				found = true;
				if(toReturn.getIsEquipped())
					this.unequip(toReturn.getName());
			}
			if(found){
				inventory[i] = inventory[i + 1];
			}
		}
		if(found)
			System.out.println("Dropped " + itemName);
		else
			System.out.println("You do not have " + itemName);
		return toReturn;
	}
	
	public void addItem(Item newItem){
		inventory[itemCount] = newItem;
		itemCount++;
		weight += newItem.getWeight();
		System.out.println("Took " + newItem.getName());
	}
	
	public boolean equip(String itemName){
		boolean found = false;
		Item toEquip = null;
		for(int i = 0; i < itemCount && !found; i++){
			if(inventory[i].getName().equals(itemName) && inventory[i].getEquipSlot() >= 0){
				found = true;
				toEquip = inventory[i];
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
			System.out.println("Equipped " + itemName);
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
				System.out.println("Unequipped " + itemName);
			}
		}
		return found;
	}
	
	public void changeHealth(double dmg, String source){
		if(dmg < 0 && health > 0){
			dmg = dmg * (1 - ((double)armourpts / 100));
			health += (int)dmg;
			System.out.println("took " + (-(int)dmg) + " damage from " + source);
			if(health <= 0){
				System.out.println("You died");
				health = 0;
			}
		}
		else if(health > 0){
			health += (int)dmg;
			if(health > maxHealth){
				dmg = dmg - (health - maxHealth);
				health = maxHealth;
			}
			System.out.println("Healed " + (int)dmg);
		}
		System.out.println(health + " health remaining.");
	}
	
	public void applySpell(Spell toApply){
		switch(toApply.statChanged){
			case 'h':
				if(toApply.isPercent){
					//health *= 1 + (toApply.effect / 100);
					this.changeHealth(health * (toApply.effect / 100), "Self");
				}
				else{
					//health += toApply.effect;
					this.changeHealth(toApply.effect, "Self");
				}
			break;
			default:
				System.out.println(toApply.statChanged + "is not a valid spell stat");
		}
	}
}