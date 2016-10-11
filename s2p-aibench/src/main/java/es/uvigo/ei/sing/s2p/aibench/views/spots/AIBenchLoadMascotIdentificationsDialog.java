package es.uvigo.ei.sing.s2p.aibench.views.spots;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Window;
import java.awt.event.ItemEvent;
import java.beans.PropertyChangeEvent;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import es.uvigo.ei.aibench.core.Core;
import es.uvigo.ei.aibench.core.clipboard.ClipboardItem;
import es.uvigo.ei.sing.hlfernandez.input.InputParameter;
import es.uvigo.ei.sing.hlfernandez.input.InputParametersPanel;
import es.uvigo.ei.sing.hlfernandez.text.JIntegerTextField;
import es.uvigo.ei.sing.s2p.aibench.datatypes.MaldiPlateDatatype;
import es.uvigo.ei.sing.s2p.aibench.datatypes.MascotIdentificationsDatatype;
import es.uvigo.ei.sing.s2p.core.entities.MascotIdentifications;
import es.uvigo.ei.sing.s2p.gui.mascot.LoadMascotIdentificationsDialog;

public class AIBenchLoadMascotIdentificationsDialog extends
	LoadMascotIdentificationsDialog 
{
	private static final long serialVersionUID = 1L;
	
	private JPanel inputComponents;
	private JTabbedPane source;

	private JComboBox<Object> mascotCombo;
	private JComboBox<Object> plateCombo;
	private InputParameter[] sourceInputParameters;
	private JIntegerTextField mascotScoreThreshold;
	private JCheckBox mascotRemoveDuplicates;
	
	public AIBenchLoadMascotIdentificationsDialog(Window parent) {
		super(parent);
	}

	@Override
	protected JPanel getInputComponentsPane() {
		this.sourceInputParameters = super.getInputParameters();
		InputParametersPanel sourceInputPanel = 
			new InputParametersPanel(sourceInputParameters);
		initialiceSourceInputListeners();
		
		this.source = new JTabbedPane();
		this.source.addTab("From clipboard", getSourceClipboardPanel());
		this.source.addTab("From file", sourceInputPanel);
		
		this.inputComponents = new JPanel(new BorderLayout());
		this.inputComponents.add(this.source);
		return this.inputComponents;
	}

	private void initialiceSourceInputListeners() {
		((JIntegerTextField) this.sourceInputParameters[1].getInput())
			.addPropertyChangeListener("value",	this::mascotThresholdChanged);
		((JCheckBox) this.sourceInputParameters[2].getInput())
			.addItemListener(this::onMascotRemoveDuplicates);
	}

	private Component getSourceClipboardPanel() {
		return new InputParametersPanel(getInputParameters());
	}

	protected InputParameter[] getInputParameters() {
		List<InputParameter> parameters = new LinkedList<InputParameter>();
		parameters.add(getMascotFileInput());
		parameters.add(getMascotThresholdInputComponent());
		parameters.add(getMascotRemoveDuplicatesComponent());
		parameters.add(getMaldiPlateInputComponent());
		return parameters.toArray(new InputParameter[parameters.size()]);
	}

	private InputParameter getMascotThresholdInputComponent() {
		InputParameter inputThreshold = super.getMascotThresholdInput();
		mascotScoreThreshold = ((JIntegerTextField) inputThreshold.getInput()); 
		mascotScoreThreshold
			.addPropertyChangeListener("value", this::mascotThresholdChanged);
		return inputThreshold;
	}

	private void mascotThresholdChanged(PropertyChangeEvent e) {
		((JIntegerTextField) this.sourceInputParameters[1].getInput())
			.setValue(e.getNewValue());
		this.mascotScoreThreshold.setValue(e.getNewValue());
	}
	
	private InputParameter getMascotRemoveDuplicatesComponent() {
		InputParameter inputRemoveDuplicates = 
			super.getMascotRemoveDuplicatesInput();
		mascotRemoveDuplicates = (JCheckBox) inputRemoveDuplicates.getInput();
		mascotRemoveDuplicates.addItemListener(this::onMascotRemoveDuplicates);
		return inputRemoveDuplicates;
	}
	
	private void onMascotRemoveDuplicates(ItemEvent e) {
		JCheckBox source = (JCheckBox) e.getSource();
		boolean selected = source.isSelected();
		if(source == this.mascotRemoveDuplicates) {
			((JCheckBox) this.sourceInputParameters[2].getInput())
				.setSelected(selected);
		} else {
			this.mascotRemoveDuplicates.setSelected(selected);
		}
	}
	
	private InputParameter getMaldiPlateInputComponent() {
		this.plateCombo = new JComboBox<>(getLoadedMaldiPlateObjects());
		this.plateCombo.addItemListener(e -> {this.checkOkButton();});
		return new InputParameter(
			"Maldi plate",
			this.plateCombo,
			"A .CSV File containing the Maldi plate"
		);
	}
	
	private Object[] getLoadedMaldiPlateObjects() {
		return 	Core.getInstance().getClipboard()
				.getItemsByClass(MaldiPlateDatatype.class).toArray();
	}
	
	@Override
	protected void checkOkButton() {
		if(isShowingFileSelectionTab()) {
			super.checkOkButton();
		} else {
			boolean enabled = 	this.plateCombo.getSelectedIndex() 	!= -1 &&
								this.mascotCombo.getSelectedIndex() != -1;
			this.okButton.setEnabled(enabled);
		}
	}


	private boolean isShowingFileSelectionTab() {
		return this.source.getSelectedIndex() == 1;
	};

	private InputParameter getMascotFileInput() {
		this.mascotCombo = new JComboBox<>(getLoadedMascotIdentifications());
		this.mascotCombo.addItemListener(e -> {this.checkOkButton();});
		return new InputParameter(
			"Mascot identifications", 
			this.mascotCombo, 
			"A previously loaded Mascot identifications."
		);
	}
	
	private Object[] getLoadedMascotIdentifications() {
		return 	Core.getInstance().getClipboard()
				.getItemsByClass(MascotIdentificationsDatatype.class).toArray();
	}

	@Override
	protected MascotIdentifications getMascotEntries() {
		if(isShowingFileSelectionTab()) {
			return super.getMascotEntries();
		}
		
		MascotIdentifications toret = new MascotIdentifications();
		toret.addAll(
			getSelectedItem().stream()
			.filter(i -> i.getMascotScore() > this.mascotScoreThreshold.getValue())
			.collect(Collectors.toList())
		);
		return toret;
	}

	private MascotIdentificationsDatatype getSelectedItem() {
		return 	(MascotIdentificationsDatatype) 
				((ClipboardItem) 
				this.mascotCombo.getSelectedItem()).getUserData();
	}

	@Override
	protected Map<String, String> getMaldiPlate() {
		if(isShowingFileSelectionTab()) {
			return super.getMaldiPlate();
		}

		return getSelectedMaldiPlate().asMap();
	}

	private MaldiPlateDatatype getSelectedMaldiPlate() {
		return 	(MaldiPlateDatatype)
				((ClipboardItem)
				this.plateCombo.getSelectedItem()).getUserData();
	}

	@Override
	public void setVisible(boolean b) {
		this.checkOkButton();
		super.setVisible(b);
	}
}
