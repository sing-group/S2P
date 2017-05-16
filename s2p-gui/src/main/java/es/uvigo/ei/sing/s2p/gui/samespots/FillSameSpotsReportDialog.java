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
package es.uvigo.ei.sing.s2p.gui.samespots;

import java.awt.Window;
import java.beans.PropertyChangeEvent;
import java.io.File;

import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;

import org.sing_group.gc4s.filechooser.JFileChooserPanel;
import org.sing_group.gc4s.filechooser.JFileChooserPanel.SelectionMode;
import org.sing_group.gc4s.filechooser.JFileChooserPanelBuilder;
import org.sing_group.gc4s.input.InputParameter;
import org.sing_group.gc4s.input.InputParametersPanel;
import es.uvigo.ei.sing.s2p.core.io.samespots.SameSpotsReportFileWriterConfiguration;
import es.uvigo.ei.sing.s2p.gui.components.dialog.AbstractFileInputJDialog;

public class FillSameSpotsReportDialog extends AbstractFileInputJDialog {
	private static final long serialVersionUID = 1L;

	private InputParametersPanel inputParametersPanel;
	private InputParameter[] parameters;

	private SameSpotsReportFileWriterConfigurationPanel configurationPanel;
	private JFileChooserPanel fileChooserPanel;

	public FillSameSpotsReportDialog(Window parent) {
		super(parent);
	}

	@Override
	protected String getDialogTitle() {
		return "Fill SameSpots report";
	}

	@Override
	protected String getDescription() {
		return "This operation allows you to fill a SameSpots report using a "
			+ "set of loaded Mascot identifications.";
	}

	@Override
	protected JPanel getInputComponentsPane() {
		this.inputParametersPanel = new InputParametersPanel(getParameters());
		return this.inputParametersPanel;
	}
	
	private InputParameter[] getParameters() {
		this.parameters = new InputParameter[2];
		parameters[0] = createDirectorySelectionParameter();
		parameters[1] = createConfigurationParameter();
		return parameters;
	}

	private InputParameter createDirectorySelectionParameter() {
		this.fileChooserPanel = JFileChooserPanelBuilder
			.createOpenJFileChooserPanel()
				.withFileChooserSelectionMode(SelectionMode.DIRECTORIES)
				.withFileChooser(getFileChooser())
				.withLabel("")
			.build();
		this.fileChooserPanel.addFileChooserListener(this::onFileChoosed);

		return new InputParameter(
			"SameSports report directory", 
			fileChooserPanel, 
			"The directory containing the SameSpots HTM reports."
		);
	}

	private void onFileChoosed(ChangeEvent event) {
		this.checkOkButton();
	}

	private InputParameter createConfigurationParameter() {
		this.configurationPanel =
			new SameSpotsReportFileWriterConfigurationPanel();
		this.configurationPanel.addPropertyChangeListener(
			SameSpotsReportFileWriterConfigurationPanel.CONFIGURATION,
			this::reportConfigurationChanged
		);

		return new InputParameter(
			"Configuration", 
			configurationPanel, 
			"Different options to configure the way that the report is processed."
		);
	}

	private void reportConfigurationChanged(PropertyChangeEvent e) {
		this.checkOkButton();
	}

	private void checkOkButton() {
		this.okButton.setEnabled(isValidUserInput());
	}

	public boolean isValidUserInput() {
		return 	this.configurationPanel.isValidConfiguration()	&&
				isSelectedFileValid();
	}

	private boolean isSelectedFileValid() {
		return 	this.fileChooserPanel.getSelectedFile() != null	&&
				!this.fileChooserPanel.getSelectedFile().equals("");
	}

	public SameSpotsReportFileWriterConfiguration getSelectedConfiguration() {
		return this.configurationPanel.getSelectedConfiguration();
	}

	public File getSelectedFile() {
		return this.fileChooserPanel.getSelectedFile();
	}
	
	@Override
	public void setVisible(boolean b) {
		this.pack();
		super.setVisible(b);
	}
}
