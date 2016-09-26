package es.uvigo.ei.sing.s2p.gui.spots.comparison;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.util.HashSet;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import es.uvigo.ei.sing.s2p.core.entities.Condition;
import es.uvigo.ei.sing.s2p.gui.components.VennDiagram;

public class ProteinDataComparisonInformationPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private Condition condition1;
	private Condition condition2;

	private JLabel informationLabel;
	private VennDiagram vennDiagram;

	public ProteinDataComparisonInformationPanel(Condition condition1,
		Condition condition2
	) {
		this.condition1 = condition1;
		this.condition2 = condition2;
		this.initComponent();
		this.updateComponents();
	}

	private void initComponent() {
		this.setBorder(BorderFactory.createTitledBorder("Condition comparison"));
		this.setBackground(Color.WHITE);
		
		this.setLayout(new BorderLayout());
		this.add(getInformationLabel(), BorderLayout.NORTH);
		this.add(getVennDiagram(), BorderLayout.SOUTH);
	}

	private JLabel getInformationLabel() {
		if (this.informationLabel == null) {
			this.informationLabel = new JLabel("", SwingConstants.LEFT);
			this.informationLabel.setFont(this.informationLabel.getFont().deriveFont(Font.PLAIN));
		}
		return this.informationLabel;
	}
	
	private VennDiagram getVennDiagram() {
		if (this.vennDiagram == null) {
			this.vennDiagram = new VennDiagram(this.condition1.getName(), this.condition2.getName(), 0d, 0d, 0d);
		}
		return this.vennDiagram;
	}
	
	private void updateComponents() {
		Set<String> condition1Proteins =  getConditionProteins(this.condition1);
		Set<String> condition2Proteins =  getConditionProteins(this.condition2);
		Set<String> intersection = new HashSet<String>(condition1Proteins);
		intersection.retainAll(condition2Proteins);
		
		StringBuilder sb = new StringBuilder();
		sb
			.append("<html>Proteins present in any sample of: <ul><li>Both conditions: ")
			.append(intersection.size())
			.append(" </li><li> ")
			.append(this.condition1.getName())
			.append(" (only): ")
			.append(condition1Proteins.size() - intersection.size())
			.append("  </li><li>  ")
			.append(this.condition2.getName())
			.append(" (only): ")
			.append(condition2Proteins.size() - intersection.size())
			.append("</li></ul></html>");
		
		getInformationLabel().setText(sb.toString());
		getInformationLabel().updateUI();
		
		int max = Math.max(condition1Proteins.size(), condition2Proteins.size());
		getVennDiagram().setAreas(this.condition1.getName(), this.condition2.getName(), (double) condition1Proteins.size() / (double) max, (double) condition2Proteins.size() / (double) max, (double) intersection.size() / (double) max);
	}

	private static Set<String> getConditionProteins(Condition condition) {
		return 	condition.getSpots();
	}

	public void setComparison(Condition condition1, Condition condition2) {
		this.condition1 = condition1;
		this.condition2 = condition2;
		this.updateComponents();
	}
}
