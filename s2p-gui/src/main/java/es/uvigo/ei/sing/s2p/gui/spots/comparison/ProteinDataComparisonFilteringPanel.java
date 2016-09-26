package es.uvigo.ei.sing.s2p.gui.spots.comparison;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import es.uvigo.ei.sing.s2p.core.entities.Condition;

public class ProteinDataComparisonFilteringPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
//	private Condition condition1;
	private Condition condition2;

	private ConditionProteinsFilteringList conditionFilteringPanel;

	public ProteinDataComparisonFilteringPanel(Condition condition1,
		Condition condition2
	) {
//		this.condition1 = condition1;
		this.condition2 = condition2;
		this.initComponent();
	}

	private void initComponent() {
		this.setLayout(new BorderLayout());
		this.add(getConditionFilteringPanel(), BorderLayout.CENTER);
	}

	private JPanel getConditionFilteringPanel() {
		if (this.conditionFilteringPanel == null) {
			this.conditionFilteringPanel = new ConditionProteinsFilteringList(condition2);
			
		}
		return this.conditionFilteringPanel;
	}
	
	public void setComparison(Condition condition1, Condition condition2) {
//		this.condition1 = condition1;
		this.condition2 = condition2;
		this.conditionFilteringPanel.setCondition(condition2);
	}
}
