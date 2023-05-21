import java.io.File;
import java.io.FileWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.util.HashMap;

public class Driver{
	public static void main(String args[]){
		/** Will make it so files can be saved and loaded. not yet implemented
		Scanner sc = new Scanner(System.in);
		System.out.println("Hello Adventurer!\nType 'New' to Start a new game.\nType 'Load <SaveName>' to load a previous save.");
		String in = sc.nextLine();
		in.toLowerCase();
		if(in.equals("load")){
		}
		else if(in.equals("new")){
			startGame();
		}
		**/
		startGame();
	}
	
	public static void startGame(){
		Scanner sc = new Scanner(System.in);
		Player player = new Player();
		Printing.setPlayer(player);
		Input.setPlayer(player);
		//File dia = new File("Dialogue.txt");
		//Scanner scd = new Scanner(dia);
		System.out.println("You wake up on a cold hard floor. Type 'Look' to look around.");
		String in = sc.nextLine();
		in.toLowerCase();
		while(!in.equals("look")){
			System.out.println("Type 'Look' to look around");
			in = sc.nextLine();
		}
		Printing.printDia(1.1);
		in = sc.nextLine();
		player.setName(in);
		Tile test = new Tile(player.getCoords());
		Printing.printDesc(test);
		System.out.println("Type 'help' for actions");
		runGame(player);
		/*
		Printing.printDia(1.2);
		System.out.println("Type 'Take Prison Key' to take Prison Key");
		*/
		
		/*
		System.out.println("You are inside of a cold, damp prison cell, it looks like it was abandoned. Old tatters of clothes hang from your body.\nYour moving around catches the attention of another prisoner.");
		System.out.println("The old man looks at you, and says: 'It's been awhile since anyone new came through here, what's your name?'");
		System.out.println("You reply: ");
		in = sc.nextLine();
		System.out.println(in + "? A rather fitting name");
		*/
	}
	
	public static void runGame (Player player){
		
		HashMap<Integer, Tile> explored = new HashMap<Integer, Tile>();
		player.setExplored(explored);
		NpcControl npcs = new NpcControl(player);
		Thread npcThread = null;
		try{
			npcThread = npcs.runNPC();
		}
		catch(InterruptedException e){
			System.out.println("InterruptedException");
		}
		
		Scanner sc = new Scanner(System.in);
		boolean running = true;
		while (running){
			if(!explored.containsKey(player.getCoords())){
				explored.put(player.getCoords(), player.getCurTile());
			}
			
			
			String in = sc.nextLine();
			in.toLowerCase();
			running = Input.input(in, npcThread);
		}
		npcThread.stop();
	}
}