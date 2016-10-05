package es.uvigo.ei.sing.s2p.core.io;

import static es.uvigo.ei.sing.commons.csv.io.CsvReader.CsvReaderBuilder.newCsvReaderBuilder;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import es.uvigo.ei.sing.commons.csv.entities.CsvEntry;
import es.uvigo.ei.sing.commons.csv.entities.CsvFormat;
import es.uvigo.ei.sing.commons.csv.io.CsvReader;

public class MaldiPlateLoader {
	private static final String SEP = ",";
	private static final CsvFormat FORMAT = new CsvFormat(SEP, '.', false, "\n");

	public static Map<String, String> load(File file) throws IOException {
		return parseLines(getCsvReader().read(file));
	}

	private static CsvReader getCsvReader() {
		return newCsvReaderBuilder().withFormat(FORMAT).build();
	}

	private static Map<String, String> parseLines(List<CsvEntry> lines) {
		Map<String, String> toret = new HashMap<String, String>();
		CsvEntry header = lines.remove(0);
		
		lines.forEach(line -> {
			String rowName = line.get(0);
			for(int i = 1; i < line.size(); i++) {
				String cellValue = line.get(i);
				if (!cellValue.equals("")) {
					toret.put(rowName + header.get(i), cellValue);
				}
			}
		});
		
		return toret;
	}
}
