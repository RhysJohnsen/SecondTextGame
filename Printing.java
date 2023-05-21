import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Printing{
	//Scanner scd;
	//File dia = new File("Dialogue.txt");
	static Player player;
	public static void setPlayer(Player newPlayer){
		player = newPlayer;
	}
	
	public static void printDia(double phraseNum){
		Scanner scd;
		String toPrint;
		//File dia = new File("Dialogue.txt");
		//scd = new Scanner(dia);
		try{
			File dia = new File("Dialogue.txt");
			scd = new Scanner(dia);
			while(scd.hasNextLine()){
				if(scd.nextDouble() == phraseNum){
					toPrint = scd.nextLine();
					if(toPrint.indexOf('<') > 0){
						if(toPrint.substring(toPrint.indexOf('<') + 1, toPrint.indexOf('>')).equals("nam")){
							System.out.print(toPrint.substring(0, toPrint.indexOf('<')));
							System.out.print(player.getName());
						}
						System.out.println(toPrint.substring(toPrint.indexOf('>') + 1));
					}
					else{
						System.out.println(toPrint);
					}
				}
				else{
					scd.nextLine();
				}
			}
		}
		catch(FileNotFoundException e){
			System.out.println("File Dialogue.txt was not found.");
		}
	}
	
	public static void printDesc(Tile tile){
		System.out.println(tile.getDesc());
	}
	
	public static void printHelp(){
		System.out.println("Available actions:");
		System.out.println("look|\tGet a description of your surroundings");
		System.out.println("inventory|\tCheck what you are holding");
		System.out.println("go <north/east/south/west>|\tTravel to one of these directions");
		System.out.println("take <item name>|\tTake an item from the ground");
		System.out.println("drop <item name>|\tDrop an item from your inventory");
		System.out.println("take <item name> from <being>|\tTake an item from a non-alive being. (Note! dead beings have the word 'dead' added to their name)");
		System.out.println("search <being>|\tSee what a being is holding");
		System.out.println("attack <being>|\tAttack a being with your equipped weapon");
		System.out.println("equip <item name>|\tEquip an item in your inventory");
		System.out.println("unequip <item name>|\tUnequip an equipped item");
		System.out.println("wait|\tDo nothing");
		System.out.println("quit|\tleave the game");
	}
	
}