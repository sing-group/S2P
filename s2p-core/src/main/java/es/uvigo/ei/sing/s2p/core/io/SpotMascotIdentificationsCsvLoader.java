/*
 * #%L
 * S2P Core
 * %%
 * Copyright (C) 2016 - 2017 José Luis Capelo Martínez, José Eduardo Araújo, Florentino Fdez-Riverola, Miguel
 * 			Reboiro-Jato, Hugo López-Fernández, and Daniel Glez-Peña
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */
package es.uvigo.ei.sing.s2p.core.io;

import static es.uvigo.ei.sing.commons.csv.io.CsvReader.CsvReaderBuilder.newCsvReaderBuilder;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import es.uvigo.ei.sing.commons.csv.entities.CsvData;
import es.uvigo.ei.sing.commons.csv.entities.CsvEntry;
import es.uvigo.ei.sing.commons.csv.entities.CsvFormat;
import es.uvigo.ei.sing.s2p.core.entities.MascotEntry;
import es.uvigo.ei.sing.s2p.core.entities.MascotIdentifications;
import es.uvigo.ei.sing.s2p.core.entities.SpotMascotIdentifications;

public class SpotMascotIdentificationsCsvLoader {

	public static SpotMascotIdentifications load(File file, CsvFormat csvFormat)
		throws IOException 
	{
		CsvData data = 	newCsvReaderBuilder()
						.withFormat(csvFormat)
						.withHeader()
						.build().read(file);

		return parseEntries(data, file);
	}

	private static SpotMascotIdentifications parseEntries(CsvData data, File file) 
		throws IOException 
	{
		Map<String, MascotIdentifications> spotToIdentifications = new HashMap<>();
		for (CsvEntry e : data) {
			MascotEntry entry = parseEntry(e, data.getFormat(), spotToIdentifications);
			
			String spot = e.get(0);
			spotToIdentifications.putIfAbsent(spot, new MascotIdentifications());
			spotToIdentifications.get(spot).add(entry);
		}

		return new SpotMascotIdentifications(spotToIdentifications);
	}
	
	private static MascotEntry parseEntry(CsvEntry entry, CsvFormat format, 
		Map<String, MascotIdentifications> spotToIdentifications) throws IOException 
	{
		String title 			= entry.get(1);
		String platePosition 	= entry.get(2);
		int mascotScore 		= asInt(entry.get(3));
		int difference 			= asInt(entry.get(4));
		int msCoverage 			= asInt(entry.get(5));
		double proteinMW 		= asDouble(entry.get(6), format);
		String method 			= entry.get(7);
		double pIValue 			= asDouble(entry.get(8), format);
		String accession 		= entry.get(9);
		String source 			= entry.get(10);
		
		return new MascotEntry(title, platePosition, mascotScore, difference,
			msCoverage, proteinMW, method, pIValue, accession, new File(source));
	}
	
	private static double asDouble(String string, CsvFormat format)
		throws IOException 
	{
		try {
			return format.asDouble(string);
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
