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
package es.uvigo.ei.sing.s2p.gui.samespots;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Window;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.sing_group.gc4s.dialog.AbstractInputJDialog;
import es.uvigo.ei.sing.s2p.core.entities.Sample;

public class SampleEditorDialog extends AbstractInputJDialog {
	private static final long serialVersionUID = 1L;
	
	private List<Sample> samples;
	private List<String> conditions;
	private Map<Sample, String> sampleConditions;
	private JPanel inputComponentsPane;

	protected SampleEditorDialog(Window parent, List<Sample> samples,
		List<String> conditions, Map<Sample, String> sampleConditions
	) {
		super(parent);
		
		this.samples = samples;
		this.conditions = conditions;
		this.sampleConditions = sampleConditions;
		this.initInputComponentsPane();
	}

	private void initInputComponentsPane() {
		this.inputComponentsPane.setLayout(new GridLayout(samples.size(), 1));
		samples.forEach(s -> {
			SamplePanel samplePanel = new SamplePanel(s);
			this.inputComponentsPane.add(samplePanel);
		});
		this.okButton.setEnabled(true);
		this.centerDialogOnScreen();
	}

	protected String getDialogTitle() {
		return "Assign conditions to samples";
	}

	protected String getDescription() {
		return "Assign conditions to samples and edit samples' names.";
	}

	protected JPanel getInputComponentsPane() {
		inputComponentsPane = new JPanel();
		return inputComponentsPane;
	}
	
	class SamplePanel extends JPanel {
		private static final long serialVersionUID = 1L;
		private Sample sample;
		private JComboBox<String> conditionCombo;
		private JTextField sampleName;

		public SamplePanel(Sample sample) {
			this.sample = sample;
			this.initComponent();
		}

		private void initComponent() {
			this.setLayout(new BorderLayout());
			this.conditionCombo = new JComboBox<String>(
				new DefaultComboBoxModel<String>(
					conditions.toArray(new String[conditions.size()]))
				);
			
			String sampleCondition = sampleConditions.get(this.sample);
			if(sampleCondition != null) {
				this.conditionCombo.setSelectedItem(sampleCondition);
			}
			
			this.sampleName = new JTextField(sample.getName(), 50);
			this.sampleName.getDocument().addDocumentListener(new DocumentListener() {
				@Override
				public void changedUpdate(DocumentEvent arg0) {
				}

				@Override
				public void insertUpdate(DocumentEvent arg0) {
					sampleNameChanged();
				}

				@Override
				public void removeUpdate(DocumentEvent arg0) {
					sampleNameChanged();
				}
			});
			
			this.add(this.sampleName, BorderLayout.WEST);
			this.add(this.conditionCombo, BorderLayout.EAST);
		}
		
		public String getSampleName() {
			return sampleName.getText();
		}
		
		public Sample getSample() {
			return sample;
		}
		
		public String getSampleCondition() {
			return this.conditionCombo.getSelectedItem().toString();
		}
	}
	
	private void sampleNameChanged() {
		this.checkOkButton();
	}

	private void checkOkButton() {
		this.okButton.setEnabled(allSamplesHasName());
	}

	private boolean allSamplesHasName() {
		return 	!getSamplePanels()
				.map(SamplePanel::getSampleName)
				.filter(s -> s.equals(""))
				.findAny().isPresent();
	}

	private Stream<SamplePanel> getSamplePanels() {
		return 	Stream.of(this.inputComponentsPane.getComponents())
				.filter(c -> c instanceof SamplePanel)
				.map(c -> ((SamplePanel) c));
	}
	
	public Map<Sample, String> getSampleNames() {
		Map<Sample, String> toret = new HashMap<Sample, String>();
		getSamplePanels().forEach(p -> {
			toret.put(p.getSample(), p.getSampleName());
		});
		return toret;
	}
	
	public Map<Sample, String> getSampleConditions() {
		Map<Sample, String> toret = new HashMap<Sample, String>();
		getSamplePanels().forEach(p -> {
			toret.put(p.getSample(), p.getSampleCondition());
		});
		return toret;
	}
}
