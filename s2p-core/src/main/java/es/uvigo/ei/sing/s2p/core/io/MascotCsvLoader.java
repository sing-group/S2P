/*
 * #%L
 * S2P Core
 * %%
 * Copyright (C) 2016 José Luis Capelo Martínez, José Eduardo Araújo, Florentino Fdez-Riverola, Miguel
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

		return parseEntries(data, file);
	}

	private static MascotIdentifications parseEntries(CsvData data, File file) 
		throws IOException 
	{
		MascotIdentifications entries = new MascotIdentifications();
		for (CsvEntry e : data) {
			entries.add(parseEntry(e, data.getFormat(), file));
		}

		return entries;
	}
	
	private static MascotEntry parseEntry(CsvEntry entry, CsvFormat format, 
		File file) throws IOException 
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
			msCoverage, proteinMW, method, pIValue, accession, file);
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
