/*
 * #%L
 * S2P Core
 * %%
 * Copyright (C) 2016 José Luis Capelo Martínez, José Eduardo Araújo, Florentino Fdez-Riverola, Miguel
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

import static es.uvigo.ei.sing.s2p.core.resources.TestResources.MALDI_PLATE_FILE;
import static es.uvigo.ei.sing.s2p.core.resources.TestResources.MALDI_PLATE_FORMAT;
import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.Map;

import org.junit.Test;

public class MaldiPlateLoaderTest {
	
	@Test
	public void maldiPlateLoaderTest() throws IOException {
		Map<String, String> mpl = MaldiPlateLoader
			.importCsv(MALDI_PLATE_FILE, MALDI_PLATE_FORMAT)
			.asMap();
		assertEquals(4, mpl.size());
		assertEquals(mpl.get("A1"), "140");
		assertEquals(mpl.get("A2"), "200");
		assertEquals(mpl.get("B2"), "45");
		assertEquals(mpl.get("C1"), "50");
	}
}
