package es.uvigo.ei.sing.s2p.core.operations.quantification.comparison;

import org.apache.commons.math3.exception.NumberIsTooSmallException;

import es.uvigo.ei.sing.s2p.core.entities.quantification.ProteinSummary;

public interface ProteinSummaryTest {
	public double test(ProteinSummary ps1, ProteinSummary ps2)
		throws NumberIsTooSmallException;
}
