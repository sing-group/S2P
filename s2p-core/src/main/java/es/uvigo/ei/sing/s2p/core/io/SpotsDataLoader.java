package es.uvigo.ei.sing.s2p.core.io;

import static es.uvigo.ei.sing.commons.csv.io.CsvReader.CsvReaderBuilder.newCsvReaderBuilder;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.LinkedList;
import java.util.List;

import es.uvigo.ei.sing.commons.csv.entities.CsvData;
import es.uvigo.ei.sing.commons.csv.entities.CsvEntry;
import es.uvigo.ei.sing.commons.csv.entities.CsvFormat;
import es.uvigo.ei.sing.s2p.core.entities.SpotsData;

public class SpotsDataLoader {

	public static SpotsData load(File file, CsvFormat csvFormat) throws IOException {
		CsvData data = 	newCsvReaderBuilder().withFormat(csvFormat).build()
						.read(file);

		List<String> sampleLabels = parseSampleLabels(data.remove(0));
		List<String> sampleNames = parseSampleNames(data.remove(0));

		return new SpotsData(
			parseSpots(data),
			sampleNames,
			sampleLabels,
			parseData(data, sampleNames.size(), csvFormat)
		);
	}

	private static List<String> parseSampleLabels(CsvEntry sampleLabels) {
		return sampleLabels.subList(1, sampleLabels.size());
	}

	private static List<String> parseSampleNames(CsvEntry sampleNames) {
		return sampleNames.subList(1, sampleNames.size());
	}
	
	private static List<String> parseSpots(List<CsvEntry> data) {
		List<String> spots = new LinkedList<String>();
		data.forEach(row -> spots.add(row.get(0)));

		return spots;
	}

	private static double[][] parseData(List<CsvEntry> data, int columns,
		CsvFormat csvFormat
	) {
		DecimalFormat decimalFormatter = csvFormat.getDecimalFormatter();

		double[][] matrixData = new double[data.size()][columns];
		data.forEach(row -> {
			int rowIndex = data.indexOf(row);
			for(int i = 1; i < row.size(); i++) {
				String currentValue = row.get(i).trim();
				if(currentValue.equals("")) {
					matrixData[rowIndex][i-1] = Double.NaN;
				} else {
					try {
						matrixData[rowIndex][i-1] = 
							decimalFormatter.parse(currentValue).doubleValue();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		});
		
		return matrixData;
	}
}
