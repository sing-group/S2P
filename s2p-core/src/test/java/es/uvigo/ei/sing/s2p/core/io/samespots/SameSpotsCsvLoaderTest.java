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
