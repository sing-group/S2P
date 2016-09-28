package es.uvigo.ei.sing.s2p.aibench.views.spots;

import java.util.List;

import es.uvigo.ei.sing.s2p.core.entities.Condition;
import es.uvigo.ei.sing.s2p.gui.mascot.LoadMascotIdentificationsDialog;
import es.uvigo.ei.sing.s2p.gui.spots.condition.ConditionComparisonTable;

public class AIBenchConditionComparisonTable extends ConditionComparisonTable {
	private static final long serialVersionUID = 1L;
	
	public AIBenchConditionComparisonTable(List<Condition> conditions) {
		super(conditions);
	}
	
	@Override
	protected LoadMascotIdentificationsDialog getMascotIdentificationsDialog() {
		return new AIBenchLoadMascotIdentificationsDialog(null);
	}
}
