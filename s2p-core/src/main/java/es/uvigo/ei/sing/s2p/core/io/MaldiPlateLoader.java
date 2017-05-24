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
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import es.uvigo.ei.sing.commons.csv.entities.CsvFormat;
import es.uvigo.ei.sing.commons.csv.io.CsvReader;
import es.uvigo.ei.sing.s2p.core.entities.MaldiPlate;

public class MaldiPlateLoader {

	public static MaldiPlate importCsv(File file, CsvFormat format) throws IOException {
		return new MaldiPlate(getCsvReader(format).read(file));
	}

	private static CsvReader getCsvReader(CsvFormat format) {
		return newCsvReaderBuilder().withFormat(format).build();
	}

	public static MaldiPlate readFile(File file) throws IOException,
		ClassNotFoundException
	{
		FileInputStream fis = new FileInputStream(file);
		try(ObjectInputStream ois = new ObjectInputStream(fis)) {;
			return (MaldiPlate) ois.readObject();
		}
	}
}
