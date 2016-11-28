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
package es.uvigo.ei.sing.s2p.gui.spots.heatmap;

import java.awt.Window;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JPanel;

import es.uvigo.ei.sing.hlfernandez.dialog.AbstractInputJDialog;
import es.uvigo.ei.sing.hlfernandez.input.InputParameter;
import es.uvigo.ei.sing.hlfernandez.input.InputParametersPanel;
import es.uvigo.ei.sing.s2p.gui.spots.heatmap.SpotRenderer.IdentificationsMode;

public class JHeatMapConfigurationDialog extends AbstractInputJDialog {
	private static final long serialVersionUID = 1L;

	private InputParameter[] parameters;
	private JCheckBox showSampleLabel;
	private JCheckBox showSpotName;
	private JComboBox<IdentificationsMode> identificationModeCmb;

	private boolean spotCustomization;
	
	public JHeatMapConfigurationDialog(Window parent, boolean spotCustomization) {
		super(parent);
		
		this.spotCustomization = spotCustomization;
		this.configure();
	}

	private void configure() {
		this.parameters[1].getInput().setEnabled(spotCustomization);
		this.parameters[2].getInput().setEnabled(spotCustomization);
	}

	@Override
	protected String getDialogTitle() {
		return "Customize heatmap";
	}

	@Override
	protected String getDescription() {
		return "This dialog allows you to select which information is used "
				+ "for heatmap rows.";
	}

	@Override
	protected JPanel getInputComponentsPane() {
		return new InputParametersPanel(getParameters());
	}

	private InputParameter[] getParameters() {
		parameters = new InputParameter[3];
		parameters[0] = getShowSampleLabelsParameter();
		parameters[1] = getShowSpotNameParameter();
		parameters[2] = getShowIdentificationModeParameter();
		return parameters;
	}

	private InputParameter getShowSpotNameParameter() {
		return new InputParameter(
			"Show spot name", 
			getShowSpotNameComponent(), 
			"Wether the spot name must be shown or not."
		);
	}

	private JComponent getShowSpotNameComponent() {
		showSpotName = new JCheckBox();
		showSpotName.setSelected(true);
		return showSpotName;
	}
	
	private InputParameter getShowIdentificationModeParameter() {
		return new InputParameter(
			"Show identifications", 
			getShowIdentificationsComponent(), 
			"Type of identifications to show."
		);
	}
	
	private JComponent getShowIdentificationsComponent() {
		identificationModeCmb = new JComboBox<IdentificationsMode>(
			IdentificationsMode.values()
		);
		return identificationModeCmb;
	}
	
	private InputParameter getShowSampleLabelsParameter() {
		return new InputParameter(
			"Show sample labels", 
			getShowSampleLabelsComponent(), 
			"Wether the sample labels must be shown or not."
		);
	}
	
	private JComponent getShowSampleLabelsComponent() {
		showSampleLabel = new JCheckBox();
		showSampleLabel.setSelected(false);
		return showSampleLabel;
	}
	
	public SpotRenderer getSelectedSpotRenderer() {
		return new SpotRenderer(getIdentificationsMode(), isShowSpotName());
	}

	private IdentificationsMode getIdentificationsMode() {
		return (IdentificationsMode) identificationModeCmb.getSelectedItem();
	}

	private boolean isShowSpotName() {
		return showSpotName.isSelected();
	}

	public boolean isShowSampleLabels() {
		return this.showSampleLabel.isSelected();
	}

	@Override
	public void setVisible(boolean b) {
		this.okButton.setEnabled(true);
		this.pack();
		super.setVisible(b);
	}
}
