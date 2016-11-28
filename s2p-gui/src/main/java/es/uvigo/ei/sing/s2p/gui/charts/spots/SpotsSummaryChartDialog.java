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
package es.uvigo.ei.sing.s2p.gui.charts.spots;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Window;
import java.util.List;

import javax.swing.JPanel;

import es.uvigo.ei.sing.hlfernandez.dialog.AbstractInputJDialog;

public class SpotsSummaryChartDialog extends AbstractInputJDialog {
	private static final long serialVersionUID = 1L;
	
	private JPanel inputComponents;

	private String spot;
	private List<ChartSpotSummary> summaries;

	public SpotsSummaryChartDialog(Window parent, String spot,
		List<ChartSpotSummary> summaries
	) {
		super(parent);

		this.spot = spot;
		this.summaries = summaries;
		this.configureDialog();
	}

	private void configureDialog() {
		this.setResizable(true);
		this.getDescriptionPane().setVisible(false);
		this.cancelButton.setVisible(false);
		this.okButton.setEnabled(true);
		this.setModal(true);
		
		this.inputComponents.add(getChart(), BorderLayout.CENTER);
		
		this.pack();
	}

	private Component getChart() {
		return new SpotsSummaryChart(spot, summaries);
	}

	@Override
	protected String getDialogTitle() {
		return "Expression values comparison";
	}

	@Override
	protected String getDescription() {
		return "";
	}

	@Override
	protected JPanel getInputComponentsPane() {
		this.inputComponents = new JPanel(new BorderLayout());
		return this.inputComponents;
	}
}
