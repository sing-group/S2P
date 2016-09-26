package es.uvigo.ei.sing.s2p.gui.samples;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import es.uvigo.ei.sing.s2p.core.entities.Sample;
import es.uvigo.ei.sing.s2p.core.operations.SpotSetsComparator;
import es.uvigo.ei.sing.s2p.gui.components.VennDiagram;

public class SamplesComparisonInformationPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private Sample sample1;
	private Sample sample2;
	private JLabel informationLabel;
	private SpotSetsComparator comparison;
	private VennDiagram vennDiagram;

	public SamplesComparisonInformationPanel(Sample sample1, Sample sample2) {
		this.sample1 = sample1;
		this.sample2 = sample2;
		this.comparison = new SpotSetsComparator(sample1.getSpots(), sample2.getSpots());
		this.initComponent();
	}

	private void initComponent() {
		this.setLayout(new BorderLayout());
		this.setBackground(Color.WHITE);
		this.add(getInformationLabel(), BorderLayout.NORTH);
		this.add(getVennDiagram(), BorderLayout.CENTER);
	}
	
	private JLabel getInformationLabel() {
		if (this.informationLabel == null) {
			this.informationLabel = new JLabel(getLabelText(), SwingConstants.CENTER);
			this.informationLabel.setFont(this.informationLabel.getFont().deriveFont(Font.PLAIN));
		}
		return this.informationLabel;
	}

	private String getLabelText() {
		StringBuilder sb = new StringBuilder();
		sb
			.append("Common proteins: ")
			.append(comparison.getIntersection())
			.append(" | ")
			.append(this.sample1.getName())
			.append(" unique proteins: ")
			.append(comparison.getSet1Unique())
			.append(" | ")
			.append(this.sample2.getName())
			.append(" unique proteins: ")
			.append(comparison.getSet2Unique());
		return sb.toString();
	}
	
	private VennDiagram getVennDiagram() {
		if (this.vennDiagram == null) {
			
			int max = Math.max(comparison.getSet1(), comparison.getSet2());
			this.vennDiagram = new VennDiagram(
				this.sample1.getName(), this.sample2.getName(), 
				(double) comparison.getSet1() / (double) max, 
				(double) comparison.getSet2() / (double) max, 
				(double) comparison.getIntersection() / (double) max);
		}
		return this.vennDiagram;
	}
	
}
