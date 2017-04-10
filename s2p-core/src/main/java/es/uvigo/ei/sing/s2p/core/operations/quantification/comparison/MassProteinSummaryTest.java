package es.uvigo.ei.sing.s2p.core.operations.quantification.comparison;

import static es.uvigo.ei.sing.s2p.core.util.ArrayUtils.doubleArray;

import org.apache.commons.math3.exception.NumberIsTooSmallException;
import org.apache.commons.math3.stat.inference.TestUtils;

import es.uvigo.ei.sing.s2p.core.entities.quantification.ProteinSummary;

public class MassProteinSummaryTest implements ProteinSummaryTest {

	@Override
	public double test(ProteinSummary ps1, ProteinSummary ps2)
		throws NumberIsTooSmallException {

		return TestUtils.tTest(
			doubleArray(ps1.getProteinMassValues()),
			doubleArray(ps2.getProteinMassValues())
		);
	}
}
