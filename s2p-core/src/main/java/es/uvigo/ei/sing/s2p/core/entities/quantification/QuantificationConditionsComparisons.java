package es.uvigo.ei.sing.s2p.core.entities.quantification;

import java.util.Collection;
import java.util.LinkedList;

public class QuantificationConditionsComparisons 
	extends LinkedList<QuantificationConditionsComparison> {
	private static final long serialVersionUID = 1L;

	public QuantificationConditionsComparisons(
		Collection<QuantificationConditionsComparison> comparisons
	) {
		super(comparisons);
	}
}
