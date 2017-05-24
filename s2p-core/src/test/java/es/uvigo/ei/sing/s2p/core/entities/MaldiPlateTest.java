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
package es.uvigo.ei.sing.s2p.core.entities;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import es.uvigo.ei.sing.s2p.core.entities.MaldiPlate.Positions;

public class MaldiPlateTest {

	@Test (expected = IllegalArgumentException.class)
	public void createPlateWithoutRowsTest() {
		new MaldiPlate(0, 1, Positions.LETTERS, Positions.NUMBERS);
	}

	@Test (expected = IllegalArgumentException.class)
	public void createPlateWithoutColumnsTest() {
		new MaldiPlate(1, 0, Positions.LETTERS, Positions.NUMBERS);
	}
	
	@Test
	public void createEmptyPlateTest() {
		List<String> expectedRowNames = asList("A", "B");
		List<String> expectedColNames = asList("1", "2");
		
		MaldiPlate plate = new MaldiPlate(2, 2, Positions.LETTERS, Positions.NUMBERS);
		
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
		MaldiPlate plate = new MaldiPlate(3, 3, Positions.LETTERS, Positions.NUMBERS);
		assertTrue(plate.asMap().isEmpty());
		
		plate.addCalibrants();
		
		assertEquals("Cal 1", plate.asMap().get("B2"));
	}
}
