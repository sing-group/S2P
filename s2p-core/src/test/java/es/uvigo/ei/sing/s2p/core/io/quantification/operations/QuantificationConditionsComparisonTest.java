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
