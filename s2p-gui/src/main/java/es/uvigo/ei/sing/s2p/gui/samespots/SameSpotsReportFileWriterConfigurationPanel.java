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

import static es.uvigo.ei.sing.s2p.core.entities.MascotEntry.LABEL_ACCESSION;
import static es.uvigo.ei.sing.s2p.core.entities.MascotEntry.LABEL_DIFFERENCE;
import static es.uvigo.ei.sing.s2p.core.entities.MascotEntry.LABEL_METHOD;
import static es.uvigo.ei.sing.s2p.core.entities.MascotEntry.LABEL_MS_COVERAGE;
import static es.uvigo.ei.sing.s2p.core.entities.MascotEntry.LABEL_PI_VALUE;
import static es.uvigo.ei.sing.s2p.core.entities.MascotEntry.LABEL_PLATE_POSITION;
import static es.uvigo.ei.sing.s2p.core.entities.MascotEntry.LABEL_PROTEIN_MW;
import static es.uvigo.ei.sing.s2p.core.entities.MascotEntry.LABEL_SCORE;
import static es.uvigo.ei.sing.s2p.core.entities.MascotEntry.LABEL_SOURCE;
import static es.uvigo.ei.sing.s2p.core.entities.MascotEntry.LABEL_TITLE;
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

import org.sing_group.gc4s.event.DocumentAdapter;
import org.sing_group.gc4s.input.InputParameter;
import org.sing_group.gc4s.input.InputParametersPanel;

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

	private static final String includeDescription(String property) {
		return "Whether " + property
			+ " should be included in the report or not.";
	}

	private InputParameter[] getParameters() {
		this.inputParameters = new InputParameter[14];
		this.inputParameters[0] = new InputParameter(
			"", this.includeMascotScoreCb, 
			includeDescription(LABEL_SCORE)
		);
		this.inputParameters[1] = new InputParameter(
			"", this.includeMethodCb, 
			includeDescription(LABEL_METHOD)
		);
		this.inputParameters[2] = new InputParameter(
			"", this.includeMsCoverageCb, 
			includeDescription(LABEL_MS_COVERAGE)
		);
		this.inputParameters[3] = new InputParameter(
			"", this.includePlatePositionCb, 
			includeDescription(LABEL_PLATE_POSITION)
		);
		this.inputParameters[4] = new InputParameter(
			"", this.includeSourceCb, 
			includeDescription(LABEL_SOURCE)
		);
		this.inputParameters[5] = new InputParameter(
			"", this.includeDifference, 
			includeDescription(LABEL_DIFFERENCE)
		);
		this.inputParameters[6] = new InputParameter(
			"", this.includeTitleCb, 
			includeDescription(LABEL_TITLE)
		);
		this.inputParameters[7] = new InputParameter(
			"", this.includeAccession, 
			includeDescription(LABEL_ACCESSION)
		);
		this.inputParameters[8] = new InputParameter(
			"", this.includePiValueCb, 
			includeDescription(LABEL_PI_VALUE)
		);
		this.inputParameters[9] = new InputParameter(
			"", this.includeProteinMwCb, 
			includeDescription(LABEL_PROTEIN_MW)
		);
		this.inputParameters[10] = new InputParameter(
			"", this.includeSpotsWithoutIdentifications, 
			"Whether include spots without associated dentifications or not. "
				+ "If this option is selected, then spots without associated "
				+ "identifications will remain in the report."
		);
		this.inputParameters[11] = new InputParameter(
			"", this.includeDuplicatedProteinNames,
			"Whether include identifications with duplicated protein names or "
			+ "not. If you select this option, then a row will be added for "
			+ "each duplicated protein name of each spot identifier."
		);
		this.inputParameters[12] = new InputParameter(
			"", getFileModePanel(),
			"Whether overwrite original report files or make a copy of each one."
		);
		this.inputParameters[13] = new InputParameter(
			"", getSuffixPanel(),
			"The suffix for adding to the copies of the original report files."
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
}
