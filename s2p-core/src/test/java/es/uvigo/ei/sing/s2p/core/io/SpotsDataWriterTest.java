package es.uvigo.ei.sing.s2p.core.io;

import static es.uvigo.ei.sing.s2p.core.matcher.HasEqualFileContentMatcher.hasEqualFileContent;
import static es.uvigo.ei.sing.s2p.core.resources.TestResources.TEST_SPOT_DATA_WITH_IDENTIFICATIONS;
import static java.nio.file.Files.createTempFile;
import static java.util.Arrays.asList;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import es.uvigo.ei.sing.commons.csv.entities.CsvFormat;
import es.uvigo.ei.sing.s2p.core.entities.MascotEntry;
import es.uvigo.ei.sing.s2p.core.entities.MascotIdentifications;
import es.uvigo.ei.sing.s2p.core.entities.Sample;
import es.uvigo.ei.sing.s2p.core.entities.SpotMascotIdentifications;

public class SpotsDataWriterTest {
	private static final CsvFormat CSV_FORMAT = new CsvFormat(",", '.', false, "\n");

	private static final Map<String, MascotIdentifications> IDENTIFICATIONS_MAP
		= new HashMap<>();

	static {
		IDENTIFICATIONS_MAP.put("276", new MascotIdentifications(asList(
			new MascotEntry("Serum albumin I", "A1", 193, 54, 49,	71317.00d,
			"50ppm_BladderCancer", 5.88d, "ALBU_HUMAN_I",	new File("file.csv"))
		)));
		IDENTIFICATIONS_MAP.put("266", new MascotIdentifications(asList(
			new MascotEntry("Serum albumin II", "A1", 193, 54, 49,	71317.00d,
			"50ppm_BladderCancer", 5.88d, "ALBU_HUMAN_II",	new File("file.csv"))
		)));
	}

	private static final Map<String, Double> SAMPLE_A_SPOTS = new HashMap<>();
	private static final Map<String, Double> SAMPLE_B_SPOTS = new HashMap<>();

	private static final List<Sample> SAMPLES = asList(
		new Sample("A", SAMPLE_A_SPOTS), new Sample("B", SAMPLE_B_SPOTS));

	static {
		SAMPLE_A_SPOTS.put("276", 1d);
		SAMPLE_B_SPOTS.put("276", 2d);
		SAMPLE_B_SPOTS.put("266", 3d);
	}

	@Test
	public void testWriteSpotsWithIdentifications() throws IOException {
		File tmpSpotsFile =
			createTempFile("spots-identifications-test-", ".htm").toFile();

		SpotsDataWriter.writeSamplesWithIdentifications(
			SAMPLES,
			new SpotMascotIdentifications(IDENTIFICATIONS_MAP),
			CSV_FORMAT, tmpSpotsFile
		);

		assertThat(
			tmpSpotsFile,
			hasEqualFileContent(TEST_SPOT_DATA_WITH_IDENTIFICATIONS)
		);
	}
}
