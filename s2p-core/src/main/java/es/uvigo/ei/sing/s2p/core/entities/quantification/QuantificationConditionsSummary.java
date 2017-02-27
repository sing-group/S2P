package es.uvigo.ei.sing.s2p.core.entities.quantification;

import java.util.HashMap;
import java.util.Set;

public class QuantificationConditionsSummary 
	extends HashMap<QuantificationCondition, QuantificationConditionSummary>
{
	private static final long serialVersionUID = 1L;

	public Set<QuantificationCondition> getConditions() {
		return this.keySet();
	}
}