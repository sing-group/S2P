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
package es.uvigo.ei.sing.s2p.gui.components.dialog;

import static java.util.stream.Collectors.toList;

import java.awt.Window;
import java.util.List;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JPanel;

import es.uvigo.ei.sing.hlfernandez.combobox.ComboBoxItem;
import es.uvigo.ei.sing.hlfernandez.input.InputParameter;
import es.uvigo.ei.sing.hlfernandez.input.InputParametersPanel;
import es.uvigo.ei.sing.hlfernandez.text.DoubleTextField;
import es.uvigo.ei.sing.s2p.core.entities.Condition;
import es.uvigo.ei.sing.s2p.core.entities.Pair;
import es.uvigo.ei.sing.s2p.core.operations.SpotSummaryOperations.DifferentialSpotFunction;

public class ConditionSelectionDialog extends AbstractFileInputJDialog {
	private static final long serialVersionUID = 1L;
	
	private InputParametersPanel inputParametersPanel;
	private InputParameter[] parameters;
	private List<Condition> conditions;
	private JComboBox<DifferentialSpotFunction> differentialSpotFunctionCmb;
	private DoubleTextField pValue;
	private JCheckBox correctPvalue;
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
		this.parameters = new InputParameter[5];
		parameters[0] = createCondition1Parameter();
		parameters[1] = createCondition2Parameter();
		parameters[2] = createFunctionParameter();
		parameters[3] = createPvalueParameter();
		parameters[4] = createCorrectPvalueParameter();
		return parameters;
	}

	private InputParameter createCondition1Parameter() {
		condition1Cmb = new JComboBox<ComboBoxItem<Condition>>();
		return new InputParameter("Condition 1", condition1Cmb,
			"First condition to compare.");
	}
	
	private InputParameter createCondition2Parameter() {
		condition2Cmb = new JComboBox<ComboBoxItem<Condition>>();
		return new InputParameter("Condition 2", condition2Cmb,
			"Second condition to compare.");
	}

	private InputParameter createFunctionParameter() {
		differentialSpotFunctionCmb = new JComboBox<DifferentialSpotFunction>(
			DifferentialSpotFunction.values());
		return new InputParameter("Function", differentialSpotFunctionCmb,
			"Function to find differentially expressed spots.");
	}
	
	private InputParameter createPvalueParameter() {
		pValue = new DoubleTextField(0.05d);
		return new InputParameter("p-value", pValue,
			"Maximum p-value allowed.");
	}

	private InputParameter createCorrectPvalueParameter() {
		correctPvalue = new JCheckBox();
		correctPvalue.setSelected(true);
		return new InputParameter("Correct p-value", correctPvalue,
			"Wether p-values must be corrected to take care of"
			+ "multiple comparisons. Corrections are done using"
			+ "Benjamini-Hochberg FDR correction.");
	}

	@Override
	public void setVisible(boolean b) {
		this.okButton.setEnabled(true);
		this.pack();
		super.setVisible(b);
	}
	
	public double getSelectedPvalue() {
		return pValue.getValue();
	}
	
	public boolean isCorrectPvalue() {
		return correctPvalue.isSelected();
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

	public DifferentialSpotFunction getDiferentialSpotFunction() {
		return (DifferentialSpotFunction) 
			this.differentialSpotFunctionCmb.getSelectedItem();
	}
}