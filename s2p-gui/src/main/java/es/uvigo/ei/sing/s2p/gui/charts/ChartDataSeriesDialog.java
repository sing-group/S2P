/*
 * #%L
 * S2P GUI
 * %%
 * Copyright (C) 2016 - 2017 José Luis Capelo Martínez, José Eduardo Araújo, Florentino Fdez-Riverola, Miguel
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
package es.uvigo.ei.sing.s2p.gui.charts;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Window;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.List;

import javax.swing.JPanel;

import org.sing_group.gc4s.dialog.AbstractInputJDialog;

public class ChartDataSeriesDialog extends AbstractInputJDialog {
	private static final long serialVersionUID = 1L;
	
	private JPanel inputComponents;
	private static Dimension LAST_COMPONENT_SIZE = null;

	private String title;
	private String xAxisTitle;
	private String yAxisTitle;
	private List<DataSeries> series;

	public ChartDataSeriesDialog(Window parent, String chartTitle,
		String xAxisTitle, String yAxisTitle, List<DataSeries> series
	) {
		super(parent);

		this.title = chartTitle;
		this.xAxisTitle = xAxisTitle;
		this.yAxisTitle = yAxisTitle;
		this.series = series;

		this.configureDialog();
	}

	private void configureDialog() {
		this.setResizable(true);
		if (LAST_COMPONENT_SIZE != null) {
			this.setPreferredSize(LAST_COMPONENT_SIZE);
		}
		this.getDescriptionPane().setVisible(false);
		this.cancelButton.setVisible(false);
		this.okButton.setEnabled(true);
		this.setModal(true);
		this.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				super.componentResized(e);
				ChartDataSeriesDialog.this.componentResized();
			}
		});
		this.inputComponents.add(getChart(), BorderLayout.CENTER);
		
		this.pack();
	}

	private void componentResized() {
		LAST_COMPONENT_SIZE = this.getSize();
	}
	
	private Component getChart() {
		return new ChartDataSeriesPanel(title, xAxisTitle, yAxisTitle, series);
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