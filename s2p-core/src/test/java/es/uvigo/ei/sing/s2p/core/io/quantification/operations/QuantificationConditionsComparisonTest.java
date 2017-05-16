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
package es.uvigo.ei.sing.s2p.core.io.quantification.operations;

import static es.uvigo.ei.sing.s2p.core.io.quantification.operations.QuantificationConditionsResources.DATASET;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import es.uvigo.ei.sing.s2p.core.entities.quantification.QuantificationConditionsComparison;
import es.uvigo.ei.sing.s2p.core.entities.quantification.QuantificationConditionsComparisons;
import es.uvigo.ei.sing.s2p.core.operations.quantification.comparison.QuantificationsConditionsTest;
import es.uvigo.ei.sing.s2p.core.operations.quantification.comparison.ComparisonMode;

public class QuantificationConditionsComparisonTest {

	@Test
	public void quantificationConditionsComparisonNormalizationTest() {
		QuantificationConditionsComparisons result =
			QuantificationsConditionsTest.compare(
				DATASET, ComparisonMode.NORMALIZED_QUANTIFICATION.getTest()
			);
		assertEquals(1, result.size());

		QuantificationConditionsComparison comparison = result.get(0);
		assertEquals(0.5883, comparison.getProteinPvalues().get("P1"), 0.001d);
		assertEquals(0.5883, comparison.getProteinQvalues().get("P1"), 0.001d);
		assertEquals(0.2928, comparison.getProteinPvalues().get("P4"), 0.001d);
		assertEquals(0.5857, comparison.getProteinQvalues().get("P4"), 0.001d);
 	}

	@Test
	public void quantificationConditionsComparisonMassValueTest() {
		QuantificationConditionsComparisons result =
			QuantificationsConditionsTest.compare(
				DATASET, ComparisonMode.PROTEIN_MASS.getTest()
			);
		assertEquals(1, result.size());

		QuantificationConditionsComparison comparison = result.get(0);
		assertEquals(0.5883, comparison.getProteinPvalues().get("P1"), 0.001d);
		assertEquals(0.5883, comparison.getProteinQvalues().get("P1"), 0.001d);
		assertEquals(0.2928, comparison.getProteinPvalues().get("P4"), 0.001d);
		assertEquals(0.5857, comparison.getProteinQvalues().get("P4"), 0.001d);
	}
}
