package es.uvigo.ei.sing.s2p.core.io;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MaldiPlateLoader {
	private static final String SEP = ",";

	public static Map<String, String> load(File file) throws IOException {
		List<String> lines = Files.readAllLines(file.toPath());
		return parseLines(lines);
	}

	private static Map<String, String> parseLines(List<String> lines) {
		Map<String, String> toret = new HashMap<String, String>();
		String[] header = lines.get(0).split(SEP);
		lines.subList(1, lines.size()).forEach(l -> {
			String[] line = l.concat(" ").split(SEP);
			String row = line[0];
			for(int i = 1; i < line.length; i++) {
				String current = line[i].trim();
				if (!current.equals("")) {
					toret.put(row + header[i], current);
				}
			}
		});
		return toret;
	}
}
