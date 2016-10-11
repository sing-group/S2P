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
