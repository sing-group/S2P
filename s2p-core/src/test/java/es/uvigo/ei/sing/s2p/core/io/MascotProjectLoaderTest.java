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

import static es.uvigo.ei.sing.s2p.core.io.MascotProjectLoader.load;
import static es.uvigo.ei.sing.s2p.core.resources.TestResources.MASCOT_PROJECT_EMPTY_ROWS_BEGINNING;
import static es.uvigo.ei.sing.s2p.core.resources.TestResources.MASCOT_PROJECT;
import static es.uvigo.ei.sing.s2p.core.resources.TestResources.MASCOT_PROJECT_FULL;
import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Test;

import es.uvigo.ei.sing.s2p.core.entities.MascotEntry;
import es.uvigo.ei.sing.s2p.core.entities.MascotIdentifications;

public class MascotProjectLoaderTest {

	private static final MascotEntry FIRST_WITH_EMPTY_ROWS = new MascotEntry(
		"Uncharacterized protein KIAA1688", "B2", 52, 52, 8,
		122236.00d, "50ppm_BladderCancer", 7.80d, "K1688_HUMAN",
		MASCOT_PROJECT_EMPTY_ROWS_BEGINNING
	);

	private static final MascotEntry FIRST = new MascotEntry(
		"Uncharacterized protein KIAA1688", "B2", 52, 52, 8, 
		122236.00d, "50ppm_BladderCancer", 7.80d, "K1688_HUMAN",
		MASCOT_PROJECT
	);

	private static final MascotEntry FIRST_FULL = new MascotEntry(
		"Uncharacterized protein KIAA1688", "B2", 52, 52, 8, 
		122236.00d, "50ppm_BladderCancer", 7.80d, "K1688_HUMAN",
		MASCOT_PROJECT_FULL
	);

	@Test
	public void mascotProjectLoaderTestEmptyRowsBeginning() throws IOException {
		MascotIdentifications entries = load(MASCOT_PROJECT_EMPTY_ROWS_BEGINNING);

		assertEquals(1, entries.size());
		assertEquals(entries.get(0), FIRST_WITH_EMPTY_ROWS);
	}

	@Test
	public void mascotProjectLoaderTest() throws IOException {
		MascotIdentifications entries = load(MASCOT_PROJECT);
		
		assertEquals(2, entries.size());
		assertEquals(entries.get(0), FIRST);
	}
	
	@Test
	public void mascotProjectLoaderTest2() throws IOException {
		MascotIdentifications entries = load(MASCOT_PROJECT_FULL);
		
		assertEquals(1473, entries.size());
		assertEquals(entries.get(0), FIRST_FULL);
	}
	
	@Test
	public void mascotProjectLoaderTest3() throws IOException {
		MascotIdentifications entries = load(MASCOT_PROJECT_FULL, 541, false);
		
		assertEquals(2, entries.size());
	}
	
	@Test
	public void mascotProjectLoaderTest4() throws IOException {
		MascotIdentifications entries = load(MASCOT_PROJECT_FULL, 541, true);
		assertEquals(1, entries.size());
	}
}
