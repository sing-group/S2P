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
package es.uvigo.ei.sing.s2p.core.io.quantification;

import static es.uvigo.ei.sing.s2p.core.entities.quantification.MascotQuantificationMethod.EMPAI;
import static es.uvigo.ei.sing.s2p.core.resources.TestResources.QUANTIFICATION_EMPAI_FILE;
import static java.util.Arrays.asList;
import static junit.framework.Assert.assertEquals;

import java.io.FileNotFoundException;

import org.junit.Test;

import es.uvigo.ei.sing.s2p.core.entities.quantification.DefaultProteinQuantification;
import es.uvigo.ei.sing.s2p.core.entities.quantification.QuantificationReplicate;

public class QuantificationCsvFileLoaderTest {

	private static final QuantificationReplicate REPLICATE =
		new QuantificationReplicate(asList(
			new DefaultProteinQuantification("Serum albumin", EMPAI, 11.46),
			new DefaultProteinQuantification("Alpha-2-macroglobulin", EMPAI, 1.78),
			new DefaultProteinQuantification("Alpha-1-antitrypsin", EMPAI, 2.41)
		)
	);

	@Test
	public void quantificationCsvFileLoaderTest() throws FileNotFoundException {
		QuantificationReplicate replicate = 
			QuantificationCsvFileLoader.load(QUANTIFICATION_EMPAI_FILE, EMPAI);
		assertEquals(REPLICATE, replicate);
	}
}
