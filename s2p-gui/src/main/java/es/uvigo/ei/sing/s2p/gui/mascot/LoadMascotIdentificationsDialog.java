package es.uvigo.ei.sing.s2p.gui.mascot;

import static es.uvigo.ei.sing.s2p.core.operations.SpotMascotEntryPositionJoiner.join;
import static javax.swing.JOptionPane.ERROR_MESSAGE;
import static javax.swing.JOptionPane.showMessageDialog;

import java.awt.Window;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;

import es.uvigo.ei.sing.commons.csv.entities.CsvFormat;
import es.uvigo.ei.sing.hlfernandez.filechooser.JFileChooserPanel;
import es.uvigo.ei.sing.hlfernandez.input.InputParameter;
import es.uvigo.ei.sing.hlfernandez.input.InputParametersPanel;
import es.uvigo.ei.sing.hlfernandez.text.JIntegerTextField;
import es.uvigo.ei.sing.s2p.core.entities.MascotIdentifications;
import es.uvigo.ei.sing.s2p.core.io.MaldiPlateLoader;
import es.uvigo.ei.sing.s2p.core.io.MascotCsvLoader;
import es.uvigo.ei.sing.s2p.gui.components.dialog.AbstractFileInputJDialog;
import es.uvigo.ei.sing.s2p.gui.util.CommonFileChooser;
import es.uvigo.ei.sing.s2p.gui.util.CsvPanel;

public class LoadMascotIdentificationsDialog extends AbstractFileInputJDialog {
	private static final long serialVersionUID = 1L;
	
	private JPanel inputComponentsPane;
	private JFileChooserPanel mascotFile;
	private JIntegerTextField mascotScoreThreshold;
	private CsvPanel mascotFileFormat;
	private JCheckBox mascotRemoveDuplicates; 
	private JFileChooserPanel maldiPlateFile;
	
	protected Map<String, String> maldi;

	private MascotIdentifications mascotIdentifications;

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
		
		parameters.add(getMascotFileFormatInput());
		
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
			"A .CSV file containing the Mascot identifications."
		);
	}
	
	private InputParameter getMascotFileFormatInput() {
		this.mascotFileFormat = new CsvPanel();
		this.mascotFileFormat.addCsvListener(this::onCsvFormatChanged);
		return new InputParameter(
			"CSV format", 
			this.mascotFileFormat, 
			"The format of the .CSV file."
		);
	}
	
	protected static InputParameter getMascotRemoveDuplicatesInput() {
		JCheckBox mascotRemoveDuplicates = new JCheckBox();
		return new InputParameter(
			"Remove duplicates",
			mascotRemoveDuplicates,
			"Check this option to remove duplicated entries."
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
			"A .mpl file containing the Maldi plate."
		);
	}
	
	private void onMascotFileSelection(ChangeEvent e) {
		this.checkOkButton();
	}
	private void onCsvFormatChanged(ChangeEvent e) {
		this.pack();
		this.checkOkButton();
	}
	
	private MascotIdentifications loadMascotIdentifications() throws IOException {
		File selectedFile = this.mascotFile.getSelectedFile();
		int mascotThreshold = this.mascotScoreThreshold.getValue();
		CsvFormat csvFormat = this.mascotFileFormat.getConvertedCsvFormat();
		boolean removeDuplicates = this.mascotRemoveDuplicates.isSelected();
		return MascotCsvLoader.load(selectedFile, csvFormat,
			mascotThreshold, removeDuplicates);
	}

	private void onMaldiFileSelection(ChangeEvent e) {
		File selectedFile = this.maldiPlateFile.getSelectedFile();
		try {
			this.maldi = MaldiPlateLoader.readFile(selectedFile).asMap();
		} catch (IOException | ClassNotFoundException e1) {
			this.maldi = null;
			showError("Can't load the maldi plate from " + selectedFile 
				+ " (" + e1.toString() + ")");
		}
		this.checkOkButton();
	}
	
	protected void checkOkButton() {
		boolean enabled = 
			this.mascotFileFormat.isValidFormat()		&&
			this.mascotFile.getSelectedFile() != null 	&&
			this.mascotFile.getSelectedFile().isFile() 	&&
			this.maldi != null;
		
		this.okButton.setEnabled(enabled);
	}

	@Override
	public void setVisible(boolean b) {
		this.pack();
		super.setVisible(b);
	}

	public Map<String, MascotIdentifications> getMascotIdentifications() {
		return join(getMaldiPlate(), getMascotEntries());
	}

	protected Map<String, String> getMaldiPlate() {
		return this.maldi;
	}

	protected MascotIdentifications getMascotEntries()  {
		return this.mascotIdentifications;
	}
	
	protected JIntegerTextField getMascotScoreThresholdTextField() {
		return mascotScoreThreshold;
	}
	
	protected JCheckBox getMascotRemoveDuplicatesCheckbox() {
		return mascotRemoveDuplicates;
	}
	
	@Override
	protected void onOkButtonEvent(ActionEvent event) {
		try {
			this.mascotIdentifications = loadMascotIdentifications();
			super.onOkButtonEvent(event);
		} catch (IOException e) {
			showError("Can't load mascot identifications.");
		}
	}

	private void showError(String message) {
		showMessageDialog(this, message, "Error", ERROR_MESSAGE);
	}
}
