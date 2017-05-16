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
package es.uvigo.ei.sing.s2p.core.io.samespots;

import static es.uvigo.ei.sing.s2p.core.matcher.HasEqualFileContentMatcher.hasEqualFileContent;
import static es.uvigo.ei.sing.s2p.core.resources.TestResources.SAMESPOTS_TINY_FILE;
import static es.uvigo.ei.sing.s2p.core.resources.TestResources.SAMESPOTS_TINY_FILE_WITH_IDENTIFICATIONS;
import static es.uvigo.ei.sing.s2p.core.resources.TestResources.SAMESPOTS_TINY_FILE_WITH_IDENTIFICATIONS_EXCLUDE_SPOTS;
import static java.nio.file.Files.createTempFile;
import static java.util.Arrays.asList;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import es.uvigo.ei.sing.s2p.core.entities.MascotEntry;
import es.uvigo.ei.sing.s2p.core.entities.MascotIdentifications;
import es.uvigo.ei.sing.s2p.core.entities.SpotMascotIdentifications;

public class SameSpotsReportFileWriterTest {

	private static final File TEST_MASCOT_CSV_FILE = new File("test.csv");
	private static final Map<String, MascotIdentifications> IDENTIFICATIONS_A_MAP =
		new HashMap<>();

	static {
		IDENTIFICATIONS_A_MAP.put("276", new MascotIdentifications(asList(
			new MascotEntry("Serum albumin", "A1", 193, 54, 49,	71317.00d, 
			"50ppm_BladderCancer", 5.88d, "ALBU_HUMAN",	TEST_MASCOT_CSV_FILE)
		)));
		IDENTIFICATIONS_A_MAP.put("266", new MascotIdentifications(asList(
			new MascotEntry("Serum albumin", "A1", 193, 54, 49,	71317.00d, 
			"50ppm_BladderCancer", 5.88d, "ALBU_HUMAN",	TEST_MASCOT_CSV_FILE)
		)));
	}

	private static final Map<String, MascotIdentifications> IDENTIFICATIONS_B_MAP =
			new HashMap<>();

	static {
		IDENTIFICATIONS_B_MAP.put("276", new MascotIdentifications(asList(
			new MascotEntry("Serum albumin", "A1", 193, 54, 49,	71317.00d, 
			"50ppm_BladderCancer", 5.88d, "ALBU_HUMAN",	TEST_MASCOT_CSV_FILE)
		)));
	}

	@Test
	public void testWriteAllData() throws IOException {
		File tmpReportFile =
			createTempFile("samespots-report-a-", ".htm").toFile();

		SameSpotsReportFileWriter.writeReportFile(
			new SpotMascotIdentifications(IDENTIFICATIONS_A_MAP),
			SAMESPOTS_TINY_FILE,
			new SameSpotsReportFileWriterConfiguration(),
			tmpReportFile);

		assertThat(
			tmpReportFile,
			hasEqualFileContent(SAMESPOTS_TINY_FILE_WITH_IDENTIFICATIONS)
		);
	}

	@Test
	public void testExcludeSportReportsWithoutIdentifications() 
		throws IOException {
		File tmpReportFile = 
				createTempFile("samespots-report-b-", ".htm").toFile();

		SameSpotsReportFileWriter.writeReportFile(
			new SpotMascotIdentifications(IDENTIFICATIONS_B_MAP),
			SAMESPOTS_TINY_FILE,
			new SameSpotsReportFileWriterConfiguration(
				true, true, true, true, true,
				false, /** Exclude spots without identifications **/ 
				false, false, false, false, false ,false
			), 
			tmpReportFile);

		assertThat(
			tmpReportFile, 
			hasEqualFileContent(SAMESPOTS_TINY_FILE_WITH_IDENTIFICATIONS_EXCLUDE_SPOTS)
		);
	}
}
