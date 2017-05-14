import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioSystem;


public class Main {

	// sort by name, size, ranking/popularity

	final static int coreCount = Runtime.getRuntime().availableProcessors();

	public static void main(String[] args) throws Exception {

		AudioFileFormat format = AudioSystem.getAudioFileFormat(new File("08 - Uprise.mp3"));

		System.out.println("Welcome to EMPShell\nhttps://github.com/CezaryRJ");
		String currentPath = System.getProperty("user.dir");

		Scanner inn;
		
		ArrayList<String> tmpArr = null;
		Manager manager = new Manager(System.getProperty("user.dir"));

		HashMap<String, Boolean> database;
		HashMap<String, String> userSettings = new HashMap<String, String>();
		try {
			// read file database
			int counter = 0;
			database = new HashMap<String, Boolean>();
			inn = new Scanner(new File("empdb.txt"));
			while (inn.hasNextLine()) {

				database.put(inn.nextLine(), true);
				counter++;

			}
			System.out.println(counter + " files in databse");

		} catch (FileNotFoundException e) {
			// scan all subfolders on first boot, or if db file is not found
			
			tmpArr = new ArrayList<>();
			tmpArr.add(currentPath);
			manager.runVoid.get("index").run(tmpArr);

		}

		try {
			//load user setting if present
			inn = new Scanner(new File("userSettings.txt"));
			String tmp;

			String tmp2 = "";
			int counter = 0;

			while (inn.hasNext()) {
				tmp = inn.nextLine();

				while (!tmp.substring(counter, counter + 1).equals(" ")) {
					counter++;
				}
				tmp2 = tmp.substring(counter + 1);
				tmp = tmp.substring(0, counter);

				userSettings.put(tmp, tmp2);

			}

			System.out.println("User settings loaded");
		} catch (FileNotFoundException e) {
			
			System.out.println("No user setting found");

		}

		//Start loop
		ArrayList<String> tokens;
		String tmp;
		while (true) {

			inn = new Scanner(System.in);
			tokens = manager.tokenize(inn.nextLine(), " ");
			tmp = tokens.get(0);

			if (manager.runVoid.get(tmp) != null) {
				tokens.remove(0);
				
				manager.runVoid.get(tmp).run(tokens);
			}

			else {
				System.out.println("No such Command");
			}

		}
	}
}
