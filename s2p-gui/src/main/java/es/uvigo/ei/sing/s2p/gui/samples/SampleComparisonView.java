package es.uvigo.ei.sing.s2p.gui.samples;

import java.awt.BorderLayout;
import java.awt.Component;
import java.util.Arrays;

import javax.swing.JPanel;

import es.uvigo.ei.sing.s2p.core.entities.Sample;

public class SampleComparisonView extends JPanel {
	private static final long serialVersionUID = 1L;

	private Sample sample1;
	private Sample sample2;

	private SamplesComparisonTable samplesTable;

	private JPanel southComponent;
	
	public SampleComparisonView(Sample sample1, Sample sample2) {
		this.sample1 = sample1;
		this.sample2 = sample2;
		this.initComponent();
	}
	
	private void initComponent() {
		this.setLayout(new BorderLayout());
		this.add(getSamplesTable(), BorderLayout.CENTER);
		this.add(getSouthComponent(), BorderLayout.SOUTH);
	}

	private Component getSouthComponent() {
		if(this.southComponent == null) {
			this.southComponent = new JPanel(new BorderLayout());
			this.southComponent.add(new SamplesComparisonInformationPanel(sample1, sample2), BorderLayout.CENTER);
		}
		return this.southComponent;
	}

	private SamplesComparisonTable getSamplesTable() {
		if (this.samplesTable == null) {
			this.samplesTable = new SamplesComparisonTable(
				Arrays.asList(new Sample[] { sample1, sample2 }));
		}
		return this.samplesTable;
	}
}
