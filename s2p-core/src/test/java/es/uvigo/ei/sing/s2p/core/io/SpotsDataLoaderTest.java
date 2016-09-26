package es.uvigo.ei.sing.s2p.core.io;

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
		SpotsData data = SpotsDataLoader.load(TEST_SPOT_DATA);
		
		assertEquals(expectedSpots, data.getSpots());
		assertEquals(expectedSampleNames, data.getSampleNames());
		assertEquals(expectedSampleLabels, data.getSampleLabels());
		assertArrayEquals(expectedData, data.getData());
	}
}
