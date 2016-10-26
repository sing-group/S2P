package es.uvigo.ei.sing.s2p.core.operations;

import static es.uvigo.ei.sing.s2p.core.entities.MaldiPlate.Positions.*;
import static es.uvigo.ei.sing.s2p.core.TestUtils.assertMatrixEquals;
import static es.uvigo.ei.sing.s2p.core.operations.MaldiPlateOperations.generateMaldiPlates;
import static java.util.Collections.emptyList;
import static org.junit.Assert.assertEquals;

import java.util.LinkedList;
import java.util.List;

import org.junit.Test;

import es.uvigo.ei.sing.s2p.core.entities.MaldiPlate;

public class MaldiPlateOperationsTest {

	private static final List<String> SPOTS_9 = spotsList(9);
	
	private static final String[][] SPOTS_9_MATRIX = new String[][] {
		{"1", "2", "3"}, {"4", "5", "6"}, {"7", "8", "9"}
	};
	
	private static final String[][] SPOTS_18_MATRIX = new String[][] {
		{"10", "11", "12"}, {"13", "14", "15"}, {"16", "17", "18"}
	};

	private static final String[][] SPOTS_9_MATRIX_CAL_1 = new String[][] {
		{"1", "2", "3"}, {"4", "Cal 1", "5"}, {"6", "7", "8"}
	};
	
	private static final String[][] SPOTS_9_MATRIX_CAL_2 = new String[][] {
		{"9", null, null}, {null, "Cal 1", null}, {null, null, null}
	};
	
	private static final List<String> SPOTS_18 = spotsList(18);
	
	@Test
	public void generateMaldiPlatesEmptySpotsList() {
		List<MaldiPlate> plates = generateMaldiPlates(
			emptyList(), 1, 3, 3, LETTERS, NUMBERS, false, false);
		assertEquals(0, plates.size());
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void generateMaldiPlatesWithoutRowsTest() {
		generateMaldiPlates(SPOTS_9, 1, 0, 3, LETTERS, NUMBERS, false, false);
	}

	@Test (expected = IllegalArgumentException.class)
	public void generateMaldiPlatesWithoutColumnsTest() {
		generateMaldiPlates(SPOTS_9, 1, 3, 0, LETTERS, NUMBERS, false, false);
	}
	
	@Test
	public void generateMaldiPlatesTest1() {
		List<MaldiPlate> plates = 
			generateMaldiPlates(SPOTS_9, 1, 3, 3, LETTERS, NUMBERS, false, false);
		
		assertEquals(1, plates.size());
		assertMatrixEquals(SPOTS_9_MATRIX , plates.get(0).getData());
	}
	
	@Test
	public void generateMaldiPlatesTest2() {
		List<MaldiPlate> plates = 
			generateMaldiPlates(SPOTS_18, 1, 3, 3, LETTERS, NUMBERS, false, false);
		
		assertEquals(2, plates.size());
		assertMatrixEquals(SPOTS_9_MATRIX , plates.get(0).getData());
		assertMatrixEquals(SPOTS_18_MATRIX , plates.get(1).getData());
	}
	
	@Test
	public void generateMaldiPlatesWithCalibrantsTest1() {
		List<MaldiPlate> plates = 
			generateMaldiPlates(SPOTS_9, 1, 3, 3, LETTERS, NUMBERS, true, false);
		
		assertEquals(2, plates.size());
		assertMatrixEquals(SPOTS_9_MATRIX_CAL_1 , plates.get(0).getData());
		assertMatrixEquals(SPOTS_9_MATRIX_CAL_2 , plates.get(1).getData());
	}


	private static List<String> spotsList(int size) {
		List<String> spots = new LinkedList<>();
		for (int i = 1; i <= size; i++) {
			spots.add(String.valueOf(i));
		}
		return spots;
	}
}
