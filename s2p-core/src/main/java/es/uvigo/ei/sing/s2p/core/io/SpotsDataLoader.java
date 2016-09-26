package es.uvigo.ei.sing.s2p.core.io;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import es.uvigo.ei.sing.s2p.core.entities.SpotsData;

public class SpotsDataLoader {

	public static SpotsData load(File file) throws IOException {
		List<String> lines = Files.readAllLines(file.toPath());
		String sampleLabels = lines.get(0);
		String sampleNames = lines.get(1);
		List<String> data = lines.subList(2, lines.size());
		
		return new SpotsData(
				parseSpots(data),
				parseSampleNames(sampleNames),
				parseSampleLabels(sampleLabels),
				parseData(data, parseSampleNames(sampleNames).size())
		);
	}

	private static List<String> parseSampleLabels(String sampleLabels) {
		List<String> sampleLabelsList = Arrays.asList(sampleLabels.split(","));
		
		return sampleLabelsList.subList(1, sampleLabelsList.size());
	}

	private static List<String> parseSampleNames(String sampleNames) {
		List<String> sampleNamesList = Arrays.asList(sampleNames.split(","));
		
		return sampleNamesList.subList(1, sampleNamesList.size());
	}

	
	private static List<String> parseSpots(List<String> data) {
		List<String> spots = new LinkedList<String>();
		data.forEach(l -> spots.add(Arrays.asList(l.split(",")).get(0)));
		
		return spots;
	}
	
	private static double[][] parseData(List<String> data, int columns) {
		double[][] matrixData = new double[data.size()][columns];
		data.forEach(l -> {
			List<String> rowValues = Arrays.asList(l.concat(" ").split(","));
			int rowIndex = data.indexOf(l);
			for(int i = 1; i < rowValues.size(); i++) {
				String currentValue = rowValues.get(i).trim();;
				if(currentValue.equals("")) {
					matrixData[rowIndex][i-1] = Double.NaN;
				} else {
					matrixData[rowIndex][i-1] = Double.parseDouble(currentValue);
				}
			}
		});
		
		return matrixData;
	}
}
