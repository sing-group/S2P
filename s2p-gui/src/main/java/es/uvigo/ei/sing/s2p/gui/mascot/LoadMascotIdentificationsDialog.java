package es.uvigo.ei.sing.s2p.gui.mascot;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;

import es.uvigo.ei.sing.hlfernandez.dialog.AbstractInputJDialog;
import es.uvigo.ei.sing.hlfernandez.filechooser.JFileChooserPanel;
import es.uvigo.ei.sing.hlfernandez.input.InputParameter;
import es.uvigo.ei.sing.hlfernandez.input.InputParametersPanel;
import es.uvigo.ei.sing.s2p.core.entities.MascotIdentifications;
import es.uvigo.ei.sing.s2p.core.io.MaldiPlateLoader;
import es.uvigo.ei.sing.s2p.core.io.MascotProjectLoader;
import es.uvigo.ei.sing.s2p.core.operations.SpotMascotEntryPositionJoiner;

public class LoadMascotIdentificationsDialog extends AbstractInputJDialog {
	private static final long serialVersionUID = 1L;
	
	private JPanel inputComponentsPane;
	private JFileChooserPanel mascotFile;
	private JFileChooserPanel maldiPlateFile;
	private MascotIdentifications mascotEntries;
	private Map<String, String> maldi;

	public LoadMascotIdentificationsDialog(JFrame parent) {
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

	private InputParameter[] getInputParameters() {
		List<InputParameter> parameters = new LinkedList<InputParameter>();
		parameters.add(getMascotFileInput());
		parameters.add(getMaldiPlateFileInput());
		return parameters.toArray(new InputParameter[parameters.size()]);
	}

	private InputParameter getMascotFileInput() {
		this.mascotFile = new JFileChooserPanel(JFileChooserPanel.Mode.OPEN);
		this.mascotFile.addFileChooserListener(this::onMascotFileSelection);
		return new InputParameter(
			"Mascot identifications", 
			this.mascotFile, 
			"A .HTM File containing the Mascot identifications"
		);
	}
	
	private InputParameter getMaldiPlateFileInput() {
		this.maldiPlateFile = new JFileChooserPanel(JFileChooserPanel.Mode.OPEN);
		this.maldiPlateFile.addFileChooserListener(this::onMaldiFileSelection);
		return new InputParameter(
			"Maldi plate", 
			this.maldiPlateFile, 
			"A .CSV File containing the Maldi plate"
		);
	}
	
	private void onMascotFileSelection(ChangeEvent e) {
		File selectedFile = this.mascotFile.getSelectedFile();
		try {
			this.mascotEntries = MascotProjectLoader.load(selectedFile);
		} catch (IOException e1) {
			this.mascotEntries = null;
			showMessage("Can't load Mascot identifications from " + selectedFile);
		}
		this.checkOkButton();
	}
	
	private void showMessage(String message) {
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
	
	private void checkOkButton() {
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
		return SpotMascotEntryPositionJoiner.join(maldi, mascotEntries);
	}
}
