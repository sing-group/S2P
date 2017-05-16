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

import static es.uvigo.ei.sing.s2p.core.operations.MascotIdentificationsOperations.removeDuplicatedProteinNames;
import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;

import java.io.File;

import org.junit.Test;

import es.uvigo.ei.sing.s2p.core.entities.MascotEntry;
import es.uvigo.ei.sing.s2p.core.entities.MascotIdentifications;

public class MascotIdentificationsOperationsTest {

	private static final File EXAMPLE_FILE_1 = new File("1.csv");
	private static final File EXAMPLE_FILE_2 = new File("2.csv");
	
	private static final MascotEntry ME1 = new MascotEntry(
		"Protein A", "B2", 100, 1, 1, 1d, "Method", 1d, "PA_ACCESSION_DB1",
		EXAMPLE_FILE_1
	);

	private static final MascotEntry ME2 = new MascotEntry(
		"Protein A", "B3", 1, 1, 1, 1d, "Method", 1d, "PA_ACCESSION_DB2",
		EXAMPLE_FILE_2
	);
	
	private static final MascotEntry ME3 = new MascotEntry(
		"Protein B", "B4", 100, 1, 1, 1d, "Method", 1d, "PA_ACCESSION_DB1",
		EXAMPLE_FILE_1
	);
	
	private static final MascotEntry ME4 = new MascotEntry(
		"Protein B", "B5", 1, 1, 1, 1d, "Method", 1d, "PA_ACCESSION_DB2",
		EXAMPLE_FILE_2
	);

	private static final MascotIdentifications IDENTIFICATIONS =
		new MascotIdentifications(asList(ME1, ME2, ME3, ME4));

	@Test
	public void removeDuplicatedProteinNamesTest() {
		MascotIdentifications result =
			removeDuplicatedProteinNames(IDENTIFICATIONS);
		assertEquals(new MascotIdentifications(asList(ME3, ME1)), result);
	}
}
