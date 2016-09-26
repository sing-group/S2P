package es.uvigo.ei.sing.s2p.core.io.samespots;

import static es.uvigo.ei.sing.s2p.core.resources.TestResources.SAMESPOTS_DIRECTORY;
import static es.uvigo.ei.sing.s2p.core.resources.TestResources.SAMESPOTS_FILE;
import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.junit.Test;

import es.uvigo.ei.sing.s2p.core.entities.Pair;
import es.uvigo.ei.sing.s2p.core.entities.Sample;

public class SameSpotsLoaderTest {
	
	private static final Sample FIRST_SAMPLE = 
		new Sample("Health Pool", new HashMap<>());
	
	static {
		FIRST_SAMPLE.getSpotValues().put("24", 1052000.0);
		FIRST_SAMPLE.getSpotValues().put("391", 3.727E7);
		FIRST_SAMPLE.getSpotValues().put("271", 4348000.0);
		FIRST_SAMPLE.getSpotValues().put("152", 6268000.0);
		FIRST_SAMPLE.getSpotValues().put("196", 1.033E7);
		FIRST_SAMPLE.getSpotValues().put("275", 198800.0);
		FIRST_SAMPLE.getSpotValues().put("198", 2.023E7);
		FIRST_SAMPLE.getSpotValues().put("374", 1.16E8);
		FIRST_SAMPLE.getSpotValues().put("276", 2136000.0);
		FIRST_SAMPLE.getSpotValues().put("332", 964200.0);
		FIRST_SAMPLE.getSpotValues().put("333", 855600.0);
		FIRST_SAMPLE.getSpotValues().put("114", 678700.0);
		FIRST_SAMPLE.getSpotValues().put("137", 1.563E7);
		FIRST_SAMPLE.getSpotValues().put("337", 1.117E7);
		FIRST_SAMPLE.getSpotValues().put("117", 502300.0);
		FIRST_SAMPLE.getSpotValues().put("359", 3.62E7);
		FIRST_SAMPLE.getSpotValues().put("360", 8282000.0);
		FIRST_SAMPLE.getSpotValues().put("141", 2.214E7);
		FIRST_SAMPLE.getSpotValues().put("263", 1.881E7);
		FIRST_SAMPLE.getSpotValues().put("362", 939700.0);
		FIRST_SAMPLE.getSpotValues().put("285", 210100.0);
		FIRST_SAMPLE.getSpotValues().put("265", 9413000.0);
		FIRST_SAMPLE.getSpotValues().put("266", 2095000.0);
		FIRST_SAMPLE.getSpotValues().put("267", 2362000.0);
		FIRST_SAMPLE.getSpotValues().put("345", 6751000.0);
		FIRST_SAMPLE.getSpotValues().put("229", 9490000.0);
		FIRST_SAMPLE.getSpotValues().put("209", 1.165E7);
	}
	
	private static final Sample SECOND_SAMPLE = 
		new Sample("Bladder Cancer Patients (VF3)", new HashMap<>());
	
	static {
		SECOND_SAMPLE.getSpotValues().put("24", 1624000.0);
		SECOND_SAMPLE.getSpotValues().put("391", 2.573E7);
		SECOND_SAMPLE.getSpotValues().put("271", 9678000.0);
		SECOND_SAMPLE.getSpotValues().put("152", 4012000.0);
		SECOND_SAMPLE.getSpotValues().put("196", 2.726E7);
		SECOND_SAMPLE.getSpotValues().put("275", 423300.0);
		SECOND_SAMPLE.getSpotValues().put("198", 4.228E7);
		SECOND_SAMPLE.getSpotValues().put("374", 1.053E8);
		SECOND_SAMPLE.getSpotValues().put("276", 7460000.0);
		SECOND_SAMPLE.getSpotValues().put("332", 5532000.0);
		SECOND_SAMPLE.getSpotValues().put("333", 4775000.0);
		SECOND_SAMPLE.getSpotValues().put("114", 1669000.0);
		SECOND_SAMPLE.getSpotValues().put("137", 8649000.0);
		SECOND_SAMPLE.getSpotValues().put("337", 7078000.0);
		SECOND_SAMPLE.getSpotValues().put("117", 1921000.0);
		SECOND_SAMPLE.getSpotValues().put("359", 3.073E7);
		SECOND_SAMPLE.getSpotValues().put("360", 2.047E7);
		SECOND_SAMPLE.getSpotValues().put("141", 1.806E7);
		SECOND_SAMPLE.getSpotValues().put("263", 3.959E7);
		SECOND_SAMPLE.getSpotValues().put("362", 532900.0);
		SECOND_SAMPLE.getSpotValues().put("285", 746800.0);
		SECOND_SAMPLE.getSpotValues().put("265", 2.294E7);
		SECOND_SAMPLE.getSpotValues().put("266", 6928000.0);
		SECOND_SAMPLE.getSpotValues().put("267", 3753000.0);
		SECOND_SAMPLE.getSpotValues().put("345", 1.5E7);
		SECOND_SAMPLE.getSpotValues().put("229", 5589000.0);
		SECOND_SAMPLE.getSpotValues().put("209", 1.82E7);
	}

	@Test
	public void sameSpotsFileLoaderTest() throws IOException {
		Pair<Sample, Sample> samples = SameSpotsFileLoader.load(
			SAMESPOTS_FILE,
			SameSpotsFileLoader.DEFAULT_THRESHOLD
		);
		
		assertEquals(FIRST_SAMPLE, 	samples.getFirst());
		assertEquals(SECOND_SAMPLE, samples.getSecond());
	}

	@Test
	public void sameSpotsDirectoryLoaderTest() throws IOException {
		List<Sample> samples = SameSpotsDirectoryLoader.load(
			SAMESPOTS_DIRECTORY, SameSpotsFileLoader.DEFAULT_THRESHOLD);
		assertEquals(15, samples.size());
	}
}
