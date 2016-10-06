package es.uvigo.ei.sing.s2p.gui.mascot;

import java.awt.Window;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;

import es.uvigo.ei.sing.hlfernandez.filechooser.JFileChooserPanel;
import es.uvigo.ei.sing.hlfernandez.input.InputParameter;
import es.uvigo.ei.sing.hlfernandez.input.InputParametersPanel;
import es.uvigo.ei.sing.hlfernandez.text.JIntegerTextField;
import es.uvigo.ei.sing.s2p.core.entities.MascotIdentifications;
import es.uvigo.ei.sing.s2p.core.io.MaldiPlateLoader;
import es.uvigo.ei.sing.s2p.core.io.MascotProjectLoader;
import es.uvigo.ei.sing.s2p.core.operations.SpotMascotEntryPositionJoiner;
import es.uvigo.ei.sing.s2p.gui.components.dialog.AbstractFileInputJDialog;
import es.uvigo.ei.sing.s2p.gui.util.CommonFileChooser;

public class LoadMascotIdentificationsDialog extends AbstractFileInputJDialog {
	private static final long serialVersionUID = 1L;
	
	private JPanel inputComponentsPane;
	private JFileChooserPanel mascotFile;
	private JIntegerTextField mascotScoreThreshold;
	private JCheckBox mascotRemoveDuplicates; 
	private JFileChooserPanel maldiPlateFile;
	
	private MascotIdentifications mascotEntries;
	protected Map<String, String> maldi;

	public LoadMascotIdentificationsDialog(Window parent) {
		super(parent);
	}

	@Override
	protected String getDialogTitle() {
		return "Load Mascot identifications";
	}

	@Override
	protected String getDescription() {
		return "This dialog allows you to load a set of Mascot identifications"
				+ " along with the MALDI plate.";
	}

	@Override
	protected JPanel getInputComponentsPane() {
		this.inputComponentsPane = new InputParametersPanel(getInputParameters());
		return this.inputComponentsPane;
	}

	protected InputParameter[] getInputParameters() {
		List<InputParameter> parameters = new LinkedList<InputParameter>();
		parameters.add(getMascotFileInput());
		
		InputParameter mascotThresholdInput = getMascotThresholdInput();
		this.mascotScoreThreshold = (JIntegerTextField) mascotThresholdInput.getInput();
		parameters.add(mascotThresholdInput);
		
		InputParameter mascotRemoveDuplicates = getMascotRemoveDuplicatesInput();
		this.mascotRemoveDuplicates = (JCheckBox) mascotRemoveDuplicates.getInput();
		parameters.add(mascotRemoveDuplicates);
		
		InputParameter maldiPlateFileInput = getMaldiPlateFileInput();
		this.maldiPlateFile = (JFileChooserPanel) maldiPlateFileInput.getInput();
		maldiPlateFile.addFileChooserListener(this::onMaldiFileSelection);
		parameters.add(maldiPlateFileInput);
		
		return parameters.toArray(new InputParameter[parameters.size()]);
	}

	private InputParameter getMascotFileInput() {
		this.mascotFile = new JFileChooserPanel(
			JFileChooserPanel.Mode.OPEN, 
			CommonFileChooser.getInstance().getFilechooser()
		);
		this.mascotFile.getComponentLabelFile().setVisible(false);
		this.mascotFile.addFileChooserListener(this::onMascotFileSelection);
		return new InputParameter(
			"Mascot identifications", 
			this.mascotFile, 
			"A .HTM File containing the Mascot identifications"
		);
	}
	
	protected static InputParameter getMascotRemoveDuplicatesInput() {
		JCheckBox mascotRemoveDuplicates = new JCheckBox();
		return new InputParameter(
			"Remove duplicates",
			mascotRemoveDuplicates,
			"Check this option to remove duplicated entries"
		);
	}
	
	protected static InputParameter getMascotThresholdInput() {
		JIntegerTextField mascotScoreThreshold = new JIntegerTextField(0);
		return new InputParameter(
			"Minimum Mascot Score", 
			mascotScoreThreshold, 
			"The minimum Mascot Score."
		);
	}
	
	protected static InputParameter getMaldiPlateFileInput() {
		JFileChooserPanel maldiPlateFile = new JFileChooserPanel(
			JFileChooserPanel.Mode.OPEN, 
			CommonFileChooser.getInstance().getFilechooser()
		);
		maldiPlateFile.getComponentLabelFile().setVisible(false);
		return new InputParameter(
			"Maldi plate", 
			maldiPlateFile, 
			"A .CSV File containing the Maldi plate"
		);
	}
	
	private void onMascotFileSelection(ChangeEvent e) {
		this.loadMascotIdentifications();
		this.checkOkButton();
	}
	
	private void loadMascotIdentifications() {
		File selectedFile = this.mascotFile.getSelectedFile();
		int mascotThreshold = this.mascotScoreThreshold.getValue();
		boolean removeDuplicates = this.mascotRemoveDuplicates.isSelected();
		try {
			this.mascotEntries = MascotProjectLoader.load(
				selectedFile, mascotThreshold, removeDuplicates
			);
		} catch (Exception ex) {
			this.mascotEntries = null;
			showMessage("Can't load Mascot identifications from " + selectedFile);
		}
	}

	protected void showMessage(String message) {
		JOptionPane.showMessageDialog(this, message, "Input error",
			JOptionPane.ERROR_MESSAGE);
	}

	private void onMaldiFileSelection(ChangeEvent e) {
		File selectedFile = this.maldiPlateFile.getSelectedFile();
		try {
			this.maldi = MaldiPlateLoader.load(selectedFile);
		} catch (IOException e1) {
			this.maldi = null;
			showMessage("Can't load the maldi plate from " + selectedFile);
		}
		this.checkOkButton();
	}
	
	protected void checkOkButton() {
		boolean enabled = 
			this.mascotEntries != null && this.maldi != null;
		
		this.okButton.setEnabled(enabled);
	}

	@Override
	public void setVisible(boolean b) {
		this.pack();
		super.setVisible(b);
	}

	public Map<String, MascotIdentifications> getMascotIdentifications() {
		return SpotMascotEntryPositionJoiner.join(maldi, getMascotEntries());
	}

	protected MascotIdentifications getMascotEntries() {
		this.loadMascotIdentifications();
		return this.mascotEntries;
	}
}
