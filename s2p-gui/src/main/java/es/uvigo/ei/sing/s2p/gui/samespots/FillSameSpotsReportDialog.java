package es.uvigo.ei.sing.s2p.gui.samespots;

import java.awt.Window;
import java.beans.PropertyChangeEvent;
import java.io.File;

import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;

import es.uvigo.ei.sing.hlfernandez.filechooser.JFileChooserPanel;
import es.uvigo.ei.sing.hlfernandez.filechooser.JFileChooserPanel.SelectionMode;
import es.uvigo.ei.sing.hlfernandez.filechooser.JFileChooserPanelBuilder;
import es.uvigo.ei.sing.hlfernandez.input.InputParameter;
import es.uvigo.ei.sing.hlfernandez.input.InputParametersPanel;
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
