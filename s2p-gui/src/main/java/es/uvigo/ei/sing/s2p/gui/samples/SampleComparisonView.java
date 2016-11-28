/*
 * #%L
 * S2P GUI
 * %%
 * Copyright (C) 2016 José Luis Capelo Martínez, José Eduardo Araújo, Florentino Fdez-Riverola, Miguel
 * 			Reboiro-Jato, Hugo López-Fernández, and Daniel Glez-Peña
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */
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
