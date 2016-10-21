package es.uvigo.ei.sing.s2p.core.io;


import static es.uvigo.ei.sing.s2p.core.operations.MascotIdentificationsOperations.filter;
import static es.uvigo.ei.sing.s2p.core.operations.MascotIdentificationsOperations.removeDuplicates;
import static es.uvigo.ei.sing.commons.csv.io.CsvReader.CsvReaderBuilder.newCsvReaderBuilder;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;

import es.uvigo.ei.sing.commons.csv.entities.CsvData;
import es.uvigo.ei.sing.commons.csv.entities.CsvEntry;
import es.uvigo.ei.sing.commons.csv.entities.CsvFormat;
import es.uvigo.ei.sing.s2p.core.entities.MascotEntry;
import es.uvigo.ei.sing.s2p.core.entities.MascotIdentifications;

public class MascotCsvLoader {

	public static MascotIdentifications load(File file, CsvFormat csvFormat,
		int minScore, boolean removeDuplicates) throws IOException 
	{
		MascotIdentifications entries = filter(load(file, csvFormat), minScore);

		return removeDuplicates ? removeDuplicates(entries) : entries;
	}

	public static MascotIdentifications load(File file, CsvFormat csvFormat)
		throws IOException 
	{
		CsvData data = 	newCsvReaderBuilder()
						.withFormat(csvFormat).withHeader()
						.build().read(file);

		return parseEntries(data);
	}

	private static MascotIdentifications parseEntries(CsvData data) 
		throws IOException 
	{
		MascotIdentifications entries = new MascotIdentifications();
		for (CsvEntry e : data) {
			entries.add(parseEntry(e, data.getFormat()));
		}

		return entries;
	}
	
	private static MascotEntry parseEntry(CsvEntry entry, CsvFormat format)
		throws IOException 
	{
		String title 			= entry.get(0);
		String platePosition 	= entry.get(1);
		int mascotScore 		= asInt(entry.get(2));
		int difference 			= asInt(entry.get(3));
		int msCoverage 			= asInt(entry.get(4));
		double proteinMW 		= asDouble(entry.get(5), format);
		String method 			= entry.get(6);
		double pIValue 			= asDouble(entry.get(7), format);
		String accession 		= entry.get(8);
		
		return new MascotEntry(title, platePosition, mascotScore, difference,
			msCoverage, proteinMW, method, pIValue, accession);
	}
	
	private static double asDouble(String string, CsvFormat format)
		throws IOException 
	{
		try {
			return format.getDecimalFormatter().parse(string).doubleValue();
		} catch (ParseException e) {
			throw new IOException("Can't parse number " + string, e);
		}
	}

	private static int asInt(String string) throws IOException {
		try {
			return Integer.valueOf(string);
		} catch(NumberFormatException e) {
			throw new IOException("Can't parse number " + string, e);
		}
	}
}
