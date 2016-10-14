package es.uvigo.ei.sing.s2p.core.io.samespots;

import static es.uvigo.ei.sing.s2p.core.io.samespots.SameSpotsCsvFileLoader.DEFAULT_CSV_THRESHOLD;
import static es.uvigo.ei.sing.s2p.core.resources.TestResources.SAMESPOTS_CSV_DIRECTORY;
import static es.uvigo.ei.sing.s2p.core.resources.TestResources.SAMESPOTS_CSV_FILE;
import static es.uvigo.ei.sing.s2p.core.resources.TestResources.SAMESPOTS_CSV_FORMAT;
import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import org.junit.Test;

import es.uvigo.ei.sing.s2p.core.entities.Pair;
import es.uvigo.ei.sing.s2p.core.entities.Sample;

public class SameSpotsCsvLoaderTest {
	
	@Test
	public void sameSpotsCsvFileLoaderTest() throws IOException, ParseException {
		Pair<Sample, Sample> samples = SameSpotsCsvFileLoader.load(
			SAMESPOTS_CSV_FILE,
			DEFAULT_CSV_THRESHOLD,
			SAMESPOTS_CSV_FORMAT
		);
		
		assertEquals(54, samples.getFirst().getSpots().size());
		assertEquals(54, samples.getSecond().getSpots().size());
	}


	@Test
	public void sameSpotsDirectoryLoaderTest() throws Exception {
		
		List<Sample> samples = SameSpotsCsvDirectoryLoader.loadDirectory(
			SAMESPOTS_CSV_DIRECTORY, DEFAULT_CSV_THRESHOLD, SAMESPOTS_CSV_FORMAT
		);
		
		assertEquals(15, samples.size());
	}
}
