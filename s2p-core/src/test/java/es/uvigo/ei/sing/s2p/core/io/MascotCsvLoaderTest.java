package es.uvigo.ei.sing.s2p.core.io;

import static es.uvigo.ei.sing.s2p.core.io.MascotCsvLoader.load;
import static java.util.Arrays.asList;
import static es.uvigo.ei.sing.s2p.core.resources.TestResources.CSV_FORMAT;
import static es.uvigo.ei.sing.s2p.core.resources.TestResources.MASCOT_CSV;
import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Test;

import es.uvigo.ei.sing.s2p.core.entities.MascotEntry;
import es.uvigo.ei.sing.s2p.core.entities.MascotIdentifications;

public class MascotCsvLoaderTest {
	
	private static final MascotEntry FIRST = new MascotEntry(
		"Gelsolin", "A1", 247, 54, 45, 
		86043.00d, "50ppm_BladderCancer", 5.90d, "GELS_HUMAN"
	);

	private static final MascotEntry SECOND = new MascotEntry(
		"Serum albumin", "A1", 193, 54, 49, 
		71317.00d, "50ppm_BladderCancer", 5.88d, "ALBU_HUMAN"
	);
	
	private static final MascotIdentifications ENTRIES = 
		new MascotIdentifications(asList(FIRST, SECOND));

	@Test
	public void mascotProjectLoaderTest() throws IOException {
		MascotIdentifications entries = load(MASCOT_CSV, CSV_FORMAT);
		assertEquals(ENTRIES, entries);
	}

	@Test
	public void mascotProjectLoaderTest2() throws IOException {
		MascotIdentifications entries = load(MASCOT_CSV, CSV_FORMAT, 200, true);
		assertEquals(new MascotIdentifications(asList(FIRST)), entries);
	}
}
