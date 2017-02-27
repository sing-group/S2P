package es.uvigo.ei.sing.s2p.core.entities.quantification;

import java.util.Map;

public class QuantificationConditionsComparison {

	private QuantificationCondition condition1;
	private QuantificationCondition condition2;
	private Map<String, Double> proteinPvalues;
	private Map<String, Double> proteinQvalues;

	public QuantificationConditionsComparison(
		QuantificationCondition condition1,
		QuantificationCondition condition2,
		Map<String, Double> proteinPvalues,
		Map<String, Double> proteinQvalues
	) {
		this.condition1 = condition1;
		this.condition2 = condition2;
		this.proteinPvalues = proteinPvalues;
		this.proteinQvalues = proteinQvalues;
	}

	public QuantificationCondition getCondition1() {
		return condition1;
	}

	public QuantificationCondition getCondition2() {
		return condition2;
	}

	public Map<String, Double> getProteinPvalues() {
		return proteinPvalues;
	}

	public Map<String, Double> getProteinQvalues() {
		return proteinQvalues;
	}
	
	@Override
	public String toString() {
		return condition1 + " vs. " + condition2;
	}
}
