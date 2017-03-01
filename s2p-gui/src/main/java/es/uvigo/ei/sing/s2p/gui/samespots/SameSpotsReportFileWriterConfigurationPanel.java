package es.uvigo.ei.sing.s2p.gui.samespots;

import static es.uvigo.ei.sing.s2p.core.io.samespots.SameSpotsReportFileWriterConfiguration.DEFAULT_REPORT_SUFFIX;
import static es.uvigo.ei.sing.s2p.core.io.samespots.SameSpotsReportFileWriterConfiguration.DEFAULT_REPORT_OVERWRITE;

import java.awt.GridLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;

import es.uvigo.ei.sing.hlfernandez.demo.DemoUtils;
import es.uvigo.ei.sing.hlfernandez.event.DocumentAdapter;
import es.uvigo.ei.sing.hlfernandez.input.InputParameter;
import es.uvigo.ei.sing.hlfernandez.input.InputParametersPanel;
import es.uvigo.ei.sing.s2p.core.io.samespots.SameSpotsReportFileWriterConfiguration;

public class SameSpotsReportFileWriterConfigurationPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	public static final String CONFIGURATION = "property.report.configuration";

	private InputParameter[] inputParameters;
	private JCheckBox includeMascotScoreCb = new JCheckBox("Mascot score", true);
	private JCheckBox includeTitleCb = new JCheckBox("Protein name", true);
	private JCheckBox includeAccession = new JCheckBox("Protein accession", true);
	private JCheckBox includePiValueCb = new JCheckBox("pI value", true);
	private JCheckBox includeProteinMwCb = new JCheckBox("Protein Mw", true);
	private JCheckBox includeSpotsWithoutIdentifications = new JCheckBox("Include spots without associated identifications", true);
	private JCheckBox includeDuplicatedProteinNames = new JCheckBox("Include identifications with duplicated protein names", true);
	private JRadioButton copyReportRbtn = new JRadioButton("Copy", !DEFAULT_REPORT_OVERWRITE);
	private JRadioButton overWriteRbtn = new JRadioButton("Overwrite", DEFAULT_REPORT_OVERWRITE);
	private JTextField fileSuffixTextField = new JTextField(DEFAULT_REPORT_SUFFIX);
	
	public SameSpotsReportFileWriterConfigurationPanel() {
		this.init();
	}

	private void init() {
		this.add(new InputParametersPanel(getParameters()));
	}
	
	private void stateChanged(ItemEvent e){
		this.stateChanged();
	}

	private void stateChanged() {
		PropertyChangeEvent event = new PropertyChangeEvent(this, CONFIGURATION, null, null);
		for(PropertyChangeListener pL : this.getPropertyChangeListeners(CONFIGURATION)) {
			pL.propertyChange(event);
		}
	}

	private InputParameter[] getParameters() {
		this.inputParameters = new InputParameter[9];
		this.inputParameters[0] = new InputParameter(
			"", this.includeMascotScoreCb, 
			"Whether include Mascot score or not."
		);
		this.inputParameters[1] = new InputParameter(
			"", this.includeTitleCb, 
			"Whether include Mascot score or not."
		);
		this.inputParameters[2] = new InputParameter(
			"", this.includeAccession, 
			"Whether include Mascot score or not."
		);
		this.inputParameters[3] = new InputParameter(
			"", this.includePiValueCb, 
			"Whether include Mascot score or not."
		);
		this.inputParameters[4] = new InputParameter(
			"", this.includeProteinMwCb, 
			"Whether include Mascot score or not."
		);
		this.inputParameters[5] = new InputParameter(
			"", this.includeSpotsWithoutIdentifications, 
			"Whether include spots without associated dentifications or not."
		);
		this.inputParameters[6] = new InputParameter(
			"", this.includeDuplicatedProteinNames,
			"Whether include identifications with duplicated protein names or not."
		);
		this.inputParameters[7] = new InputParameter(
			"", getFileModePanel(),
			"Wether overwrite original report files or make a copy of each one."
		);
		this.inputParameters[8] = new InputParameter(
			"", getSuffixPanel(),
			"Files suffix."
		);
		this.addListeners();
		return this.inputParameters;
	}

	private JComponent getFileModePanel() {
		JPanel fileModePanel = new JPanel();
		fileModePanel.setLayout(new GridLayout(1, 2));
		overWriteRbtn.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				overWriteStateChanged(e);
			}
		});

		fileModePanel.add(copyReportRbtn);
		fileModePanel.add(overWriteRbtn);
		
		ButtonGroup fileModeGroup = new ButtonGroup();
		fileModeGroup.add(copyReportRbtn);
		fileModeGroup.add(overWriteRbtn);
		return fileModePanel;
	}
	
	private JComponent getSuffixPanel() {
		JPanel suffixPanel = new JPanel();
		suffixPanel.setLayout(new BoxLayout(suffixPanel, BoxLayout.X_AXIS));
		suffixPanel.add(new JLabel("Files suffix"));
		suffixPanel.add(Box.createHorizontalStrut(10));
		suffixPanel.add(this.fileSuffixTextField);
		return suffixPanel;
	}

	private void overWriteStateChanged(ItemEvent e) {
		this.fileSuffixTextField.setEnabled(!this.overWriteRbtn.isSelected());
		this.stateChanged();
	}

	private void addListeners() {
		this.includeMascotScoreCb.addItemListener(this::stateChanged);
		this.includeTitleCb.addItemListener(this::stateChanged);
		this.includeAccession.addItemListener(this::stateChanged);
		this.includePiValueCb.addItemListener(this::stateChanged);
		this.includeProteinMwCb.addItemListener(this::stateChanged);
		this.includeSpotsWithoutIdentifications.addItemListener(this::stateChanged);
		this.includeDuplicatedProteinNames.addItemListener(this::stateChanged);
		this.fileSuffixTextField.getDocument().addDocumentListener(new DocumentAdapter() {
			
			@Override
			public void removeUpdate(DocumentEvent e) {
				stateChanged();
			}
			
			@Override
			public void insertUpdate(DocumentEvent e) {
				stateChanged();
			}
		});
	}

	public boolean isValidConfiguration() {
		return 	(this.includeMascotScoreCb.isSelected() 	||
				this.includeTitleCb.isSelected() 		||
				this.includeAccession.isSelected() 		||
				this.includePiValueCb.isSelected() 		||
				this.includeProteinMwCb.isSelected())	&&
				this.isValidFileMode();
	}

	private boolean isValidFileMode() {
		return this.overWriteRbtn.isSelected() || !this.fileSuffixTextField.getText().isEmpty();
	}

	public SameSpotsReportFileWriterConfiguration getSelectedConfiguration() {
		return new SameSpotsReportFileWriterConfiguration(
			this.includeMascotScoreCb.isSelected(), 
			this.includeAccession.isSelected(), 
			this.includeTitleCb.isSelected(), 
			this.includePiValueCb.isSelected(), 
			this.includeProteinMwCb.isSelected(),
			this.includeSpotsWithoutIdentifications.isSelected(),
			this.includeDuplicatedProteinNames.isSelected(),
			this.overWriteRbtn.isSelected(),
			this.fileSuffixTextField.getText()
		);
	}
	
	public static void main(String[] args) {
		DemoUtils.showComponent(new SameSpotsReportFileWriterConfigurationPanel());
	}
}
