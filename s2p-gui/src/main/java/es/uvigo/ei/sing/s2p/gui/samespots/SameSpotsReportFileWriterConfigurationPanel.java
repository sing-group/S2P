package es.uvigo.ei.sing.s2p.gui.samespots;

import static es.uvigo.ei.sing.s2p.core.entities.MascotEntry.*;
import static es.uvigo.ei.sing.s2p.core.io.samespots.SameSpotsReportFileWriterConfiguration.DEFAULT_INCLUDE_ACCESSION;
import static es.uvigo.ei.sing.s2p.core.io.samespots.SameSpotsReportFileWriterConfiguration.DEFAULT_INCLUDE_DIFFERENCE;
import static es.uvigo.ei.sing.s2p.core.io.samespots.SameSpotsReportFileWriterConfiguration.DEFAULT_INCLUDE_MASCOT_SCORE;
import static es.uvigo.ei.sing.s2p.core.io.samespots.SameSpotsReportFileWriterConfiguration.DEFAULT_INCLUDE_METHOD;
import static es.uvigo.ei.sing.s2p.core.io.samespots.SameSpotsReportFileWriterConfiguration.DEFAULT_INCLUDE_MS_COVERAGE;
import static es.uvigo.ei.sing.s2p.core.io.samespots.SameSpotsReportFileWriterConfiguration.DEFAULT_INCLUDE_PI_VALUE;
import static es.uvigo.ei.sing.s2p.core.io.samespots.SameSpotsReportFileWriterConfiguration.DEFAULT_INCLUDE_PLATE_POS;
import static es.uvigo.ei.sing.s2p.core.io.samespots.SameSpotsReportFileWriterConfiguration.DEFAULT_INCLUDE_PROTEIN_MW;
import static es.uvigo.ei.sing.s2p.core.io.samespots.SameSpotsReportFileWriterConfiguration.DEFAULT_INCLUDE_SOURCE;
import static es.uvigo.ei.sing.s2p.core.io.samespots.SameSpotsReportFileWriterConfiguration.DEFAULT_INCLUDE_SPOTS_WITHOUT_IDENTIFICATIONS;
import static es.uvigo.ei.sing.s2p.core.io.samespots.SameSpotsReportFileWriterConfiguration.DEFAULT_INCLUDE_TITLE;
import static es.uvigo.ei.sing.s2p.core.io.samespots.SameSpotsReportFileWriterConfiguration.DEFAULT_REMOVE_DUPLICATED_PROTEIN_NAMES;
import static es.uvigo.ei.sing.s2p.core.io.samespots.SameSpotsReportFileWriterConfiguration.DEFAULT_REPORT_OVERWRITE;
import static es.uvigo.ei.sing.s2p.core.io.samespots.SameSpotsReportFileWriterConfiguration.DEFAULT_REPORT_SUFFIX;

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
	private JCheckBox includeMascotScoreCb = 
		new JCheckBox(LABEL_SCORE, DEFAULT_INCLUDE_MASCOT_SCORE);
	private JCheckBox includeTitleCb = 
		new JCheckBox(LABEL_TITLE, DEFAULT_INCLUDE_TITLE);
	private JCheckBox includeAccession = 
		new JCheckBox(LABEL_ACCESSION, DEFAULT_INCLUDE_ACCESSION);
	private JCheckBox includePiValueCb = 
		new JCheckBox(LABEL_PI_VALUE, DEFAULT_INCLUDE_PI_VALUE);
	private JCheckBox includeProteinMwCb = 
		new JCheckBox(LABEL_PROTEIN_MW, DEFAULT_INCLUDE_PROTEIN_MW);
	private JCheckBox includeSpotsWithoutIdentifications = 
		new JCheckBox("Include spots without associated identifications", 
			DEFAULT_INCLUDE_SPOTS_WITHOUT_IDENTIFICATIONS);
	private JCheckBox includeDuplicatedProteinNames = 
		new JCheckBox("Include identifications with duplicated protein names", 
			DEFAULT_REMOVE_DUPLICATED_PROTEIN_NAMES);
	private JCheckBox includeMethodCb = 
		new JCheckBox(LABEL_METHOD, DEFAULT_INCLUDE_METHOD);
	private JCheckBox includeMsCoverageCb = 
		new JCheckBox(LABEL_MS_COVERAGE, DEFAULT_INCLUDE_MS_COVERAGE);
	private JCheckBox includePlatePositionCb = 
		new JCheckBox(LABEL_PLATE_POSITION, DEFAULT_INCLUDE_PLATE_POS);
	private JCheckBox includeSourceCb = 
		new JCheckBox(LABEL_SOURCE, DEFAULT_INCLUDE_SOURCE);
	private JCheckBox includeDifference = 
		new JCheckBox(LABEL_DIFFERENCE, DEFAULT_INCLUDE_DIFFERENCE);

	private JRadioButton copyReportRbtn = 
		new JRadioButton("Copy", !DEFAULT_REPORT_OVERWRITE);
	private JRadioButton overWriteRbtn = 
		new JRadioButton("Overwrite", DEFAULT_REPORT_OVERWRITE);
	private JTextField fileSuffixTextField = 
		new JTextField(DEFAULT_REPORT_SUFFIX);
	
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
		this.inputParameters = new InputParameter[14];
		this.inputParameters[0] = new InputParameter(
			"", this.includeMascotScoreCb, 
			"Whether include Mascot score in the report or not."
		);
		this.inputParameters[1] = new InputParameter(
			"", this.includeMethodCb, 
			"Whether include Method in the report or not."
		);
		this.inputParameters[2] = new InputParameter(
			"", this.includeMsCoverageCb, 
			"Whether include MS coverage  in the report or not."
		);
		this.inputParameters[3] = new InputParameter(
			"", this.includePlatePositionCb, 
			"Whether include plate position in the report or not."
		);
		this.inputParameters[4] = new InputParameter(
			"", this.includeSourceCb, 
			"Whether include Source in the report or not."
		);
		this.inputParameters[5] = new InputParameter(
			"", this.includeDifference, 
			"Whether include difference in the report or not."
		);
		this.inputParameters[6] = new InputParameter(
			"", this.includeTitleCb, 
			"Whether include Protein name in the reportor not."
		);
		this.inputParameters[7] = new InputParameter(
			"", this.includeAccession, 
			"Whether include Accession in the report or not."
		);
		this.inputParameters[8] = new InputParameter(
			"", this.includePiValueCb, 
			"Whether include pI-value in the report or not."
		);
		this.inputParameters[9] = new InputParameter(
			"", this.includeProteinMwCb, 
			"Whether include protein MW in the report or not."
		);
		this.inputParameters[10] = new InputParameter(
			"", this.includeSpotsWithoutIdentifications, 
			"Whether include spots without associated dentifications or not."
		);
		this.inputParameters[11] = new InputParameter(
			"", this.includeDuplicatedProteinNames,
			"Whether include identifications with duplicated protein names or not."
		);
		this.inputParameters[12] = new InputParameter(
			"", getFileModePanel(),
			"Whether overwrite original report files or make a copy of each one."
		);
		this.inputParameters[13] = new InputParameter(
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
		this.includeMethodCb.addItemListener(this::stateChanged);
		this.includeMsCoverageCb.addItemListener(this::stateChanged);
		this.includePlatePositionCb.addItemListener(this::stateChanged);
		this.includeSourceCb.addItemListener(this::stateChanged);
		this.includeDifference.addItemListener(this::stateChanged);
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
				this.includeProteinMwCb.isSelected()	||
				this.includeMethodCb.isSelected() 	||
				this.includeMsCoverageCb.isSelected() 	||
				this.includePlatePositionCb.isSelected() 	||
				this.includeSourceCb.isSelected() 	||
				this.includeDifference.isSelected()
				) &&	this.isValidFileMode();
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
			this.includeMethodCb.isSelected(),
			this.includeMsCoverageCb.isSelected(),
			this.includePlatePositionCb.isSelected(),
			this.includeSourceCb.isSelected(),
			this.includeDifference.isSelected(),
			this.overWriteRbtn.isSelected(),
			this.fileSuffixTextField.getText()
		);
	}
	
	public static void main(String[] args) {
		DemoUtils.showComponent(new SameSpotsReportFileWriterConfigurationPanel());
	}
}
