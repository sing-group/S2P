package es.uvigo.ei.sing.s2p.core.io;

import static es.uvigo.ei.sing.s2p.core.io.SpotMascotIdentificationsCsvLoader.load;
import static java.util.Arrays.asList;
import static es.uvigo.ei.sing.s2p.core.resources.TestResources.CSV_FORMAT;
import static es.uvigo.ei.sing.s2p.core.resources.TestResources.MASCOT_SPOT_CSV;
import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import es.uvigo.ei.sing.s2p.core.entities.MascotEntry;
import es.uvigo.ei.sing.s2p.core.entities.MascotIdentifications;
import es.uvigo.ei.sing.s2p.core.entities.SpotMascotIdentifications;

public class SpotMascotIdentificationsCsvLoaderTest {

	private static final File SOURCE_FILE = new File("MascotProject.BTR.HTM");

	private static final MascotEntry S1_FIRST = new MascotEntry(
		"Gelsolin", "A1", 247, 54, 45,
		86043.00d, "50ppm_BladderCancer", 5.90d, "GELS_HUMAN",
		SOURCE_FILE
	);

	private static final MascotEntry S1_SECOND = new MascotEntry(
		"Serum albumin", "A1", 193, 54, 49,
		71317.00d, "50ppm_BladderCancer", 5.88d, "ALBU_HUMAN",
		SOURCE_FILE
	);

	private static final MascotEntry S2_FIRST = new MascotEntry(
		"Gelsolin", "A2", 246, 54, 45,
		86043.00d, "50ppm_BladderCancer", 5.90d, "GELS_HUMAN",
		SOURCE_FILE
	);

	private static final MascotEntry S2_SECOND = new MascotEntry(
		"Serum albumin", "A2", 192, 54, 49, 
		71317.00d, "50ppm_BladderCancer", 5.88d, "ALBU_HUMAN",
		SOURCE_FILE
	);

	private static final SpotMascotIdentifications IDENTIFICATIONS;

	static {
		Map<String, MascotIdentifications> map = new HashMap<>();
		map.put("1", new MascotIdentifications(asList(S1_FIRST, S1_SECOND)));
		map.put("2", new MascotIdentifications(asList(S2_FIRST, S2_SECOND)));
		IDENTIFICATIONS = new SpotMascotIdentifications(map);
	}

	@Test
	public void spotMascotIdentificationsLoaderTest() throws IOException {
		SpotMascotIdentifications entries = load(MASCOT_SPOT_CSV, CSV_FORMAT);
		assertEquals(IDENTIFICATIONS, entries);
	}
}
