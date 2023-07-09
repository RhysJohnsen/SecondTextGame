import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Spell{
	
	String name;
	char statChanged;
	boolean isPercent;
	int effect;
	
	public Spell(int idIn){
		String line;
		String readints;
		Scanner sc;
		Scanner scl;
		try{
			File items = new File("Spells.txt");
			sc = new Scanner(items);
			sc.nextLine();
			while(sc.hasNextLine()){
				if(sc.nextInt() == idIn){
					line = sc.nextLine();
					name = line.substring(line.indexOf('<') + 1, line.indexOf('>'));
					statChanged = line.charAt(line.indexOf('>') + 2);
					readints = line.substring(line.indexOf('>') + 3);
					scl = new Scanner(readints);
					//statChanged = scl.next().charAt(1);
					if(scl.nextInt() > 0)
						isPercent = true;
					else
						isPercent = false;
					effect = scl.nextInt();
				}
				else{
					sc.nextLine();
				}
			}
		}
		catch(FileNotFoundException e){
			System.out.println("File Spells.txt was not found.");
		}
	}
}