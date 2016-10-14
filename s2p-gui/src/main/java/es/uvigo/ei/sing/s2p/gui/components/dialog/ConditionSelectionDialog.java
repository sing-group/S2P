package es.uvigo.ei.sing.s2p.gui.components.dialog;

import static java.util.stream.Collectors.toList;

import java.awt.Window;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JPanel;

import es.uvigo.ei.sing.hlfernandez.combobox.ComboBoxItem;
import es.uvigo.ei.sing.hlfernandez.input.InputParameter;
import es.uvigo.ei.sing.hlfernandez.input.InputParametersPanel;
import es.uvigo.ei.sing.s2p.core.entities.Condition;
import es.uvigo.ei.sing.s2p.core.entities.Pair;

public class ConditionSelectionDialog extends AbstractFileInputJDialog {
	private static final long serialVersionUID = 1L;
	
	private InputParametersPanel inputParametersPanel;
	private InputParameter[] parameters;
	private List<Condition> conditions;
	private JComboBox<ComboBoxItem<Condition>> condition1Cmb;
	private JComboBox<ComboBoxItem<Condition>> condition2Cmb;

	public ConditionSelectionDialog(Window parent, List<Condition> conditions) {
		super(parent);
		
		this.conditions = conditions;
		this.configure();
	}

	private void configure() {
		getItemsList().forEach(condition1Cmb::addItem);
		getItemsList().forEach(condition2Cmb::addItem);
		
		if (this.conditions.size() > 0) {
			this.condition1Cmb.setSelectedIndex(0);
			this.condition2Cmb.setSelectedIndex(1);
		}
	}

	private List<ComboBoxItem<Condition>> getItemsList() {
		return 	this.conditions.stream()
				.map(c -> new ComboBoxItem<Condition>(c, c.getName()))
				.collect(toList());
	}

	@Override
	protected String getDialogTitle() {
		return "Select conditions";
	}

	@Override
	protected String getDescription() {
		return "This dialog allows you to select two conditions.";
	}

	@Override
	protected JPanel getInputComponentsPane() {
		this.inputParametersPanel = new InputParametersPanel(getParameters());
		return this.inputParametersPanel;
	}

	private InputParameter[] getParameters() {
		this.parameters = new InputParameter[2];
		parameters[0] = createCondition1Parameter();
		parameters[1] = createCondition2Parameter();
		return parameters;
	}

	private InputParameter createCondition1Parameter() {
		condition1Cmb = new JComboBox<ComboBoxItem<Condition>>();
		return new InputParameter("Condition 1", condition1Cmb,
			"Condition to compare");
	}
	
	private InputParameter createCondition2Parameter() {
		condition2Cmb = new JComboBox<ComboBoxItem<Condition>>();
		return new InputParameter("Condition 2", condition2Cmb,
				"Condition to compare");
	}

	@Override
	public void setVisible(boolean b) {
		this.okButton.setEnabled(true);
		this.pack();
		super.setVisible(b);
	}
	
	public Pair<Condition, Condition> getSelectedConditions() {
		return new Pair<>(getCondition1(), getCondition2());
	}
	
	private Condition getCondition1() {
		ComboBoxItem<?> item = (ComboBoxItem<?>) condition1Cmb.getSelectedItem();
		return (Condition) item.getItem();
	}
	
	private Condition getCondition2() {
		ComboBoxItem<?> item = (ComboBoxItem<?>) condition2Cmb.getSelectedItem();
		return (Condition) item.getItem();
	}
}