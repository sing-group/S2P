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

import static es.uvigo.ei.sing.s2p.core.resources.TestResources.CSV_FORMAT;
import static es.uvigo.ei.sing.s2p.core.resources.TestResources.TEST_SPOT_DATA;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import es.uvigo.ei.sing.s2p.core.entities.SpotsData;

public class SpotsDataLoaderTest {
	
	private List<String> expectedSpots = Arrays.asList(
		new String[]{"P1", "P2", "P3"});
	
	private List<String> expectedSampleNames = Arrays.asList(
		new String[]{"A1", "A2", "B1", "B2"});
	
	private List<String> expectedSampleLabels = Arrays.asList(
		new String[]{"A", "A", "B", "B"});
	
	private double[][] expectedData = new double[][] {
		new double[]{101.0, 	200.0, 		100.0, 			200.0},	
		new double[]{102.0, 	200.0, 		Double.NaN, 	200.0},
		new double[]{103.0, 	200.0, 		100.0, 			Double.NaN},
	};
	
	@Test
	public void spotsDataLoaderTest() throws IOException {
		SpotsData data = SpotsDataLoader.load(TEST_SPOT_DATA, CSV_FORMAT);
		
		assertEquals(expectedSpots, data.getSpots());
		assertEquals(expectedSampleNames, data.getSampleNames());
		assertEquals(expectedSampleLabels, data.getSampleLabels());
		assertArrayEquals(expectedData, data.getData());
	}
}
