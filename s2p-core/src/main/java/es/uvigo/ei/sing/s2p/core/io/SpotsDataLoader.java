package es.uvigo.ei.sing.s2p.core.io;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import es.uvigo.ei.sing.s2p.core.entities.SpotsData;
import es.uvigo.ei.sing.s2p.core.io.csv.CsvFormat;

public class SpotsDataLoader {

	public static SpotsData load(File file, CsvFormat csvFormat) throws IOException {
		List<String> lines = Files.readAllLines(file.toPath());
		String sampleLabels = lines.get(0);
		String sampleNamesStr = lines.get(1);
		List<String> data = lines.subList(2, lines.size());
		
		List<String> sampleNames = parseSampleNames(sampleNamesStr, csvFormat);
		
		return new SpotsData(
			parseSpots(data, csvFormat),
			sampleNames,
			parseSampleLabels(sampleLabels, csvFormat),
			parseData(data, sampleNames.size(), csvFormat)
		);
	}

	private static List<String> parseSampleLabels(String sampleLabels,
		CsvFormat csvFormat
	) {
		List<String> sampleLabelsList = Arrays.asList(sampleLabels.split(csvFormat.getColumnSeparator()));
		
		return sampleLabelsList.subList(1, sampleLabelsList.size());
	}

	private static List<String> parseSampleNames(String sampleNames,
		CsvFormat csvFormat
	) {
		String col = csvFormat.getColumnSeparator();
		
		List<String> sampleNamesList = Arrays.asList(sampleNames.split(col));
		
		return sampleNamesList.subList(1, sampleNamesList.size());
	}

	
	private static List<String> parseSpots(List<String> data,
		CsvFormat csvFormat
	) {
		String col = csvFormat.getColumnSeparator();
		
		List<String> spots = new LinkedList<String>();
		data.forEach(l -> spots.add(Arrays.asList(l.split(col)).get(0)));
		
		return spots;
	}
	
	private static double[][] parseData(List<String> data, int columns,
		CsvFormat csvFormat
	) {
		String col = csvFormat.getColumnSeparator();
		DecimalFormat decimalFormatter = csvFormat.getDecimalFormatter();
		
		double[][] matrixData = new double[data.size()][columns];
		data.forEach(l -> {
			List<String> rowValues = Arrays.asList(l.concat(" ").split(col));
			int rowIndex = data.indexOf(l);
			for(int i = 1; i < rowValues.size(); i++) {
				String currentValue = rowValues.get(i).trim();
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
