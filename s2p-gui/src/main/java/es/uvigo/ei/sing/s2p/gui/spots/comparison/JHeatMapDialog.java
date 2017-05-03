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
package es.uvigo.ei.sing.s2p.gui.spots.comparison;

import static java.awt.BorderLayout.CENTER;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Window;
import java.text.DecimalFormat;

import javax.swing.JPanel;
import javax.swing.JRootPane;

import org.sing_group.gc4s.dialog.AbstractInputJDialog;
import org.sing_group.gc4s.visualization.JHeatMap;
import org.sing_group.gc4s.visualization.JHeatMapModel;
import org.sing_group.gc4s.visualization.JHeatMapPanel;

public class JHeatMapDialog extends AbstractInputJDialog {
	private static final long serialVersionUID = 1L;
	
	private JPanel inputComponents;
	private JHeatMapModel model;
	
	public JHeatMapDialog(Window parent, JHeatMapModel model) {
		super(parent);
		
		this.model = model;
		this.configureDialog();
	}

	private void configureDialog() {
		this.setResizable(true);
		this.getDescriptionPane().setVisible(false);
		this.cancelButton.setVisible(false);
		this.okButton.setEnabled(true);
		this.setModal(true);
		this.getRootPane().setWindowDecorationStyle(JRootPane.FRAME);
		
		this.inputComponents.add(getJHeatMapPanel(), CENTER);
		
		this.maximize();
	}

	private Component getJHeatMapPanel() {
		return new JHeatMapPanel(getJHeatMap());
	}

	protected JHeatMap getJHeatMap() {
		JHeatMap heatmap = new JHeatMap(model);
		heatmap.setDecimalFormat(new DecimalFormat("0.00E0"));
		return heatmap;
	}

	@Override
	protected String getDialogTitle() {
		return "HeatMap view";
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
