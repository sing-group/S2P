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
package es.uvigo.ei.sing.s2p.core.operations;

import static es.uvigo.ei.sing.s2p.core.operations.SpotMascotEntryPositionJoiner.UNKNOWN_SPOT;
import static es.uvigo.ei.sing.s2p.core.resources.TestResources.MALDI_PLATE_FORMAT;
import static es.uvigo.ei.sing.s2p.core.resources.TestResources.MALDI_PLATE_FILE;
import static es.uvigo.ei.sing.s2p.core.resources.TestResources.MASCOT_PROJECT;
import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.Map;

import org.junit.Test;

import es.uvigo.ei.sing.s2p.core.entities.MascotIdentifications;
import es.uvigo.ei.sing.s2p.core.entities.SpotMascotIdentifications;
import es.uvigo.ei.sing.s2p.core.io.MaldiPlateLoader;
import es.uvigo.ei.sing.s2p.core.io.MascotProjectLoader;

public class SpotMascotEntryPositionJoinerTest {

	@Test
	public void spotMascotEntryPositionJoinerTest() throws IOException {
		MascotIdentifications entries = MascotProjectLoader.load(MASCOT_PROJECT);
		Map<String, String> posToSpot = MaldiPlateLoader
			.importCsv(MALDI_PLATE_FILE, MALDI_PLATE_FORMAT).asMap();
		
		SpotMascotIdentifications join = 
			SpotMascotEntryPositionJoiner.join(posToSpot, entries);

		assertEquals(2, join.getSpots().size());
		assertTrue(join.getSpots().contains("45"));
		assertTrue(join.getSpots().contains(UNKNOWN_SPOT));
		assertEquals(1, join.get("45").size());
		assertEquals(1, join.get(UNKNOWN_SPOT).size());
	}
}
