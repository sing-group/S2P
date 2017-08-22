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
