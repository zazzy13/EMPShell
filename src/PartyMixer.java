import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;

public class PartyMixer implements runVoid {

	Manager manager;

	PartyMixer(Manager manager) {
		this.manager = manager;
	}

	public void run(ArrayList<String> inn) throws Exception {

		// inn 0 = number of tracs

		File playlist = new File("EMPplaylist.m3u");
		File tmp = new File(manager.path);
		File[] fileList = tmp.listFiles();
		ArrayList<String> tokens;

		int filesWanted = Integer.parseInt(inn.get(0));
		int filesFound = 0;

		PrintWriter writer = new PrintWriter(playlist);
		writer.println("#EXTM3U");

		for (int i = 0; i < fileList.length && filesFound != filesWanted; i++) {
			tokens = manager.tokenize(fileList[i].getName(), ".");
			if (tokens.get(tokens.size() - 1).equals("mp3")) {
				writer.println("#EXTINF:0," + fileList[i].getName());
				writer.println(fileList[i].getAbsolutePath());
				filesFound++;
			}

		}
		writer.close();
		inn.clear();
		inn.add("EMPplaylist.m3u");
		manager.runVoid.get("start").run(inn);

	}

	@Override
	public void help() {
		System.out.println("mix (number) - This method will generate a playlist consisting of X requested sound files in the database");

	}

}
