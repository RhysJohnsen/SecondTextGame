import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Item{
	int id;
	String name;
	int weight;
	char type;
	int stat;
	int dura;
	int equipSlot;
	int effect;
	boolean isEquipped;
	
	public Item (String nameIn, char typeIn, int weightIn, int statIn, int duraIn){
		name = nameIn;
		type = typeIn;
		weight = weightIn;
		stat = statIn;
		dura = duraIn;
		isEquipped = false;
	}
	
	public Item(int idIn){
		isEquipped = false;
		id = idIn;
		String line;
		String readints;
		Scanner sc;
		Scanner scl;
		try{
			File items = new File("Items.txt");
			sc = new Scanner(items);
			sc.nextLine();
			while(sc.hasNextLine()){
				if(sc.nextInt() == idIn){
					line = sc.nextLine();
					name = line.substring(line.indexOf('<') + 1, line.indexOf('>'));
					readints = line.substring(line.indexOf('>') + 1);
					scl = new Scanner(readints);
					weight = scl.nextInt();
					stat = scl.nextInt();
					dura = scl.nextInt();
					equipSlot = scl.nextInt();
					effect = scl.nextInt();
				}
				else{
					sc.nextLine();
				}
			}
		}
		catch(FileNotFoundException e){
			System.out.println("File Items.txt was not found.");
		}
	}
	
	void use(int useBy){
		this.dura -= useBy;
		if(dura < 0)
			this.dura = 0;
	}
	
	String getName(){
		return name;
	}
	
	int getWeight(){
		return weight;
	}
	
	int getID(){
		return id;
	}
	
	int getEquipSlot(){
		return equipSlot;
	}
	
	int getEffectiveness(){
		return effect;
	}
	
	void changeIsEquipped(){
		isEquipped = !isEquipped;
	}
	
	boolean getIsEquipped(){
		return isEquipped;
	}
}