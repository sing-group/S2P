package es.uvigo.ei.sing.s2p.core.entities;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

public class MaldiPlateTest {

	@Test (expected = IllegalArgumentException.class)
	public void createPlateWithoutRowsTest() {
		new MaldiPlate(0, 1);
	}

	@Test (expected = IllegalArgumentException.class)
	public void createPlateWithoutColumnsTest() {
		new MaldiPlate(1, 0);
	}
	
	@Test
	public void createEmptyPlateTest() {
		List<String> expectedRowNames = asList("A", "B");
		List<String> expectedColNames = asList("1", "2");
		
		MaldiPlate plate = new MaldiPlate(2, 2);
		
		assertTrue(plate.asMap().isEmpty());
		assertEquals(expectedRowNames, plate.getRowNames());
		assertEquals(expectedColNames, plate.getColNames());
	}
	
	@Test
	public void createEmptyPlateTest2() {
		List<String> rowNames = asList("A", "B", "C");
		List<String> colNames = asList("1", "2", "3");
		String[][] data = new String[][] {
			{"", "", ""}, {"", "", ""}, {"", "", ""}
		};
		MaldiPlateInformation info = new MaldiPlateInformation();
		
		MaldiPlate plate = new MaldiPlate(rowNames, colNames, data, info);
		assertTrue(plate.asMap().isEmpty());
		assertEquals(rowNames, plate.getRowNames());
		assertEquals(colNames, plate.getColNames());
	}
	
	@Test
	public void createEmptyPlateAndAddCalibrants() {
		MaldiPlate plate = new MaldiPlate(3, 3);
		assertTrue(plate.asMap().isEmpty());
		
		plate.addCalibrants();
		
		assertEquals("Cal 1", plate.asMap().get("B2"));
	}
}
