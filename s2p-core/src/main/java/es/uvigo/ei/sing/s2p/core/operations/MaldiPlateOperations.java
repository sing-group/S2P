package es.uvigo.ei.sing.s2p.core.operations;

import static es.uvigo.ei.sing.s2p.core.util.Checks.*;
import static java.lang.Math.ceil;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import es.uvigo.ei.sing.s2p.core.entities.MaldiPlate;

public class MaldiPlateOperations {

	private static final String REQ_REPLICATES = 
		"Number of replicates must be greater than 0";

	public static List<MaldiPlate> generateMaldiPlates(List<String> spotsList,
		int replicates, int rows, int cols, boolean calibrants, boolean random
	) {
		replicates = requireStrictPositive(replicates, REQ_REPLICATES);

		List<String> spots = getSpotsList(spotsList, replicates);
		int requiredPlates = computeRequiredPlates(
			spots.size(), rows, cols, calibrants);
		if (random) {
			Collections.shuffle(spots);
		}
		
		List<MaldiPlate> toret = new LinkedList<>();
		for(int i = 0; i < requiredPlates; i++) {
			MaldiPlate current = new MaldiPlate(rows, cols);
			if(calibrants) {
				current.addCalibrants();
			}

			current.setData(fillData(current.getData(), spots));
			toret.add(current);
		}
		return toret;
	}

	private static List<String> getSpotsList(List<String> spotsList,
		int replicates) {
		List<String> toret = new LinkedList<>();
		for(String s : spotsList) {
			for(int i = 0; i < replicates; i++) {
				toret.add(s);
			}
		}
		return toret;
	}

	private static String[][] fillData(String[][] data, List<String> spots) {
		for(int i = 0; i < data.length; i++) {
			for(int j = 0; j < data[i].length; j++) {
				String value = data[i][j];
				if ((value == null || value.equals("")) && spots.size() > 0) {
					data[i][j] = spots.remove(0);
				}
			}	
		}
		return data;
	}

	private static int computeRequiredPlates(int size, int rows, int cols,
		boolean calibrants
	) {
		MaldiPlate plate = new MaldiPlate(rows, cols);
		if (calibrants) {
			plate.addCalibrants();
		}
		int plateSize = countAvailablePositions(plate.getData());
		return (int) ceil((double) size / (double) plateSize);
	}

	private static int countAvailablePositions(String[][] data) {
		int count = 0;
		for(int i = 0; i < data.length; i++) {
			for(int j = 0; j < data[i].length; j++) {
				String value = data[i][j];
				if (value == null || value.equals("")) {
					count++;
				}
			}	
		}

		return count;
	}
}
