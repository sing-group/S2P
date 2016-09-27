package es.uvigo.ei.sing.s2p.gui.spots;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;

import javax.swing.JPanel;

import es.uvigo.ei.sing.s2p.core.entities.Sample;
import es.uvigo.ei.sing.s2p.core.entities.SpotsData;
import es.uvigo.ei.sing.s2p.gui.components.ExtendedJTabbedPane;
import es.uvigo.ei.sing.s2p.gui.event.ProteinDataComparisonEvent;
import es.uvigo.ei.sing.s2p.gui.event.ProteinDataComparisonListener;
import es.uvigo.ei.sing.s2p.gui.samples.SampleComparisonView;
import es.uvigo.ei.sing.s2p.gui.spots.comparison.ConditionVsConditionComparisonView;
import es.uvigo.ei.sing.s2p.gui.spots.condition.ConditionComparisonTable;


public class SpotsDataViewer extends JPanel implements ProteinDataComparisonListener {
	private static final long serialVersionUID = 1L;
	
	private ExtendedJTabbedPane tabbedPane;
	protected SpotsData data;

	public SpotsDataViewer(SpotsData data) {
		this.data = data;
		this.initComponent();
	}

	private void initComponent() {
		this.setLayout(new BorderLayout());
		this.setBackground(Color.WHITE);

		this.tabbedPane = new ExtendedJTabbedPane();
		this.tabbedPane.setHideTabBarWhenSingleTab(true);
		this.add(this.tabbedPane, BorderLayout.CENTER);
		
		this.tabbedPane.addTab("Table view", getConditionsComparisonTable());
		
		ConditionVsConditionComparisonView proteinComparisonView = 
			new ConditionVsConditionComparisonView(this.data);
		proteinComparisonView.addTableListener(this);
		this.tabbedPane.addTab("Comparison view", proteinComparisonView);
	}
	
	protected Component getConditionsComparisonTable() {
		return new ConditionComparisonTable(this.data.getConditions());
	}

	@Override
	public void onSampleSelection(ProteinDataComparisonEvent event) {
		Sample[] samples = (Sample[]) event.getSource();
		this.tabbedPane.addTab(getSampleComparisonTabTitle(samples[0], samples[1]), new SampleComparisonView(samples[0], samples[1]), true);
		this.tabbedPane.setSelectedIndex(this.tabbedPane.getComponentCount()-2);
	}

	private String getSampleComparisonTabTitle(Sample sample1, Sample sample2) {
		return sample1.getName() + " vs. " + sample2.getName();
	}

	@Override
	public void onSampleSelectionCleared(ProteinDataComparisonEvent event) { }
}
