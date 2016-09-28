package es.uvigo.ei.sing.s2p.aibench.views;

import java.awt.Component;

import es.uvigo.ei.sing.s2p.aibench.datatypes.SpotsDataDatatype;
import es.uvigo.ei.sing.s2p.aibench.views.spots.AIBenchConditionComparisonTable;
import es.uvigo.ei.sing.s2p.gui.spots.SpotsDataViewer;

public class SpotsDataDatatypeView extends SpotsDataViewer {
	private static final long serialVersionUID = 1L;

	public SpotsDataDatatypeView(SpotsDataDatatype data) {
		super(data);
	}
	
	@Override
	protected Component getConditionsComparisonTable() {
		return new AIBenchConditionComparisonTable(this.data.getConditions());
	}
}
