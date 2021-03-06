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

import static es.uvigo.ei.sing.s2p.core.resources.TestResources.SAMESPOTS_MULTIPLE_SAMPLES_FILE;
import static es.uvigo.ei.sing.s2p.core.resources.TestResources.SAMESPOTS_DIRECTORY;
import static es.uvigo.ei.sing.s2p.core.resources.TestResources.SAMESPOTS_FILE;
import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.junit.Test;

import es.uvigo.ei.sing.s2p.core.entities.SameSpotsThrehold;
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

	private static final Sample MULTIPLE_REPORT_FIRST_SAMPLE =
		new Sample("1st Month", new HashMap<>());
	
	static {
		MULTIPLE_REPORT_FIRST_SAMPLE.getSpotValues().put("89", 9631000.0);
		MULTIPLE_REPORT_FIRST_SAMPLE.getSpotValues().put("170", 2455000.0);
		MULTIPLE_REPORT_FIRST_SAMPLE.getSpotValues().put("194", 167300.0);
		MULTIPLE_REPORT_FIRST_SAMPLE.getSpotValues().put("393", 1598000.0);
		MULTIPLE_REPORT_FIRST_SAMPLE.getSpotValues().put("372", 2.24E7);
		MULTIPLE_REPORT_FIRST_SAMPLE.getSpotValues().put("691", 1.192E7);
		MULTIPLE_REPORT_FIRST_SAMPLE.getSpotValues().put("175", 2722000.0);
		MULTIPLE_REPORT_FIRST_SAMPLE.getSpotValues().put("176", 5125000.0);
		MULTIPLE_REPORT_FIRST_SAMPLE.getSpotValues().put("177", 3742000.0);
		MULTIPLE_REPORT_FIRST_SAMPLE.getSpotValues().put("232", 7839000.0);
		MULTIPLE_REPORT_FIRST_SAMPLE.getSpotValues().put("299", 1.076E8);
		MULTIPLE_REPORT_FIRST_SAMPLE.getSpotValues().put("178", 2006000.0);
		MULTIPLE_REPORT_FIRST_SAMPLE.getSpotValues().put("355", 3.061E7);
		MULTIPLE_REPORT_FIRST_SAMPLE.getSpotValues().put("234", 4576000.0);
		MULTIPLE_REPORT_FIRST_SAMPLE.getSpotValues().put("157", 1.415E7);
		MULTIPLE_REPORT_FIRST_SAMPLE.getSpotValues().put("356", 4.34E7);
		MULTIPLE_REPORT_FIRST_SAMPLE.getSpotValues().put("456", 7130000.0);
		MULTIPLE_REPORT_FIRST_SAMPLE.getSpotValues().put("137", 1.4E7);
		MULTIPLE_REPORT_FIRST_SAMPLE.getSpotValues().put("96", 1.296E7);
		MULTIPLE_REPORT_FIRST_SAMPLE.getSpotValues().put("57", 3462000.0);
		MULTIPLE_REPORT_FIRST_SAMPLE.getSpotValues().put("361", 2.492E7);
		MULTIPLE_REPORT_FIRST_SAMPLE.getSpotValues().put("384", 2016000.0);
		MULTIPLE_REPORT_FIRST_SAMPLE.getSpotValues().put("101", 3686000.0);
		MULTIPLE_REPORT_FIRST_SAMPLE.getSpotValues().put("366", 4.659E7);
		MULTIPLE_REPORT_FIRST_SAMPLE.getSpotValues().put("224", 4497000.0);
		MULTIPLE_REPORT_FIRST_SAMPLE.getSpotValues().put("389", 3397000.0);
		MULTIPLE_REPORT_FIRST_SAMPLE.getSpotValues().put("467", 1.443E7);
		MULTIPLE_REPORT_FIRST_SAMPLE.getSpotValues().put("820", 9.194E7);
		MULTIPLE_REPORT_FIRST_SAMPLE.getSpotValues().put("469", 1.183E7);
		MULTIPLE_REPORT_FIRST_SAMPLE.getSpotValues().put("404", 3213000.0);
		MULTIPLE_REPORT_FIRST_SAMPLE.getSpotValues().put("307", 7576000.0);
		MULTIPLE_REPORT_FIRST_SAMPLE.getSpotValues().put("805", 3472000.0);

	}
	
	@Test
	public void sameSpotsFileLoaderTest() throws IOException {
		List<Sample> samples = SameSpotsFileLoader.load(
			SAMESPOTS_FILE,
			SameSpotsFileLoader.DEFAULT_THRESHOLD
		);

		assertEquals(FIRST_SAMPLE, 	samples.get(0));
		assertEquals(SECOND_SAMPLE, samples.get(1));
	}
	
	@Test
	public void sameSpotsFileLoaderTest2() throws IOException {
		List<Sample> samples = SameSpotsFileLoader.load(
			SAMESPOTS_FILE,
			new SameSpotsThrehold(0.01, 2.5)
		);

		assertEquals(2, samples.get(0).getSpotValues().size());
		assertEquals(2, samples.get(1).getSpotValues().size());
	}

	@Test
	public void sameSpotsFileLoaderMultipleSamplesTest() throws IOException {
		List<Sample> samples = SameSpotsFileLoader.load(
			SAMESPOTS_MULTIPLE_SAMPLES_FILE,
			SameSpotsFileLoader.DEFAULT_THRESHOLD
		);

		assertEquals(4, samples.size());
		assertEquals(MULTIPLE_REPORT_FIRST_SAMPLE, samples.get(0));
	}

	@Test
	public void sameSpotsDirectoryLoaderTest() throws IOException {
		List<Sample> samples = SameSpotsDirectoryLoader.loadDirectory(
			SAMESPOTS_DIRECTORY, SameSpotsFileLoader.DEFAULT_THRESHOLD);
		assertEquals(15, samples.size());
	}
}
