package es.uvigo.ei.sing.s2p.gui.samespots;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import es.uvigo.ei.sing.hlfernandez.dialog.AbstractInputJDialog;
import es.uvigo.ei.sing.s2p.core.entities.Sample;

public class SampleEditorDialog extends AbstractInputJDialog {
	private static final long serialVersionUID = 1L;
	private List<Sample> samples;
	private List<String> conditions;
	private Map<Sample, String> sampleConditions;
	private JPanel inputComponentsPane;

	protected SampleEditorDialog(JFrame parent, List<Sample> samples,
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
