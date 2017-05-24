/*
 * #%L
 * S2P
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
package es.uvigo.ei.sing.s2p.aibench.views.spots;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Window;
import java.awt.event.ActionEvent;
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
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import es.uvigo.ei.aibench.core.Core;
import es.uvigo.ei.aibench.core.clipboard.ClipboardItem;
import org.sing_group.gc4s.input.InputParameter;
import org.sing_group.gc4s.input.InputParametersPanel;
import org.sing_group.gc4s.text.JIntegerTextField;
import org.sing_group.gc4s.ui.CenteredJPanel;
import es.uvigo.ei.sing.s2p.aibench.datatypes.MaldiPlateDatatype;
import es.uvigo.ei.sing.s2p.aibench.datatypes.MascotIdentificationsDatatype;
import es.uvigo.ei.sing.s2p.aibench.datatypes.SpotMascotIdentificationsDatatype;
import es.uvigo.ei.sing.s2p.core.entities.MascotIdentifications;
import es.uvigo.ei.sing.s2p.core.entities.SpotMascotIdentifications;
import es.uvigo.ei.sing.s2p.gui.mascot.LoadMascotIdentificationsDialog;

public class AIBenchLoadMascotIdentificationsDialog extends
	LoadMascotIdentificationsDialog 
{
	private static final long serialVersionUID = 1L;
	
	private JPanel inputComponents;
	private JTabbedPane source;

	private JComboBox<Object> spotMascotCombo;
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
		this.source.addTab("From clipboard (I)", getSourceSpotIdentificationsFromClipboardPanel());
		this.source.addTab("From clipboard (II)", getSourceClipboardPanel());
		this.source.addTab("From file", sourceInputPanel);
		this.source.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				checkOkButton();
			}
		});

		this.inputComponents = new JPanel(new BorderLayout());
		this.inputComponents.add(this.source);
		return this.inputComponents;
	}

	private void initialiceSourceInputListeners() {
		super.getMascotScoreThresholdTextField()
			.addPropertyChangeListener("value",	this::mascotThresholdChanged);
		super.getMascotRemoveDuplicatesCheckbox()
			.addItemListener(this::onMascotRemoveDuplicates);
	}

	private Component getSourceSpotIdentificationsFromClipboardPanel() {
		JPanel toret = new CenteredJPanel(
			new InputParametersPanel(
				getInputParametersForSpotIdentificationsFromClipboard()
			)
		);
		return toret;
	}

	protected InputParameter[] getInputParametersForSpotIdentificationsFromClipboard() {
		List<InputParameter> parameters = new LinkedList<InputParameter>();
		parameters.add(getSpotMascotIdentificationsInput());
		return parameters.toArray(new InputParameter[parameters.size()]);
	}

	private InputParameter getSpotMascotIdentificationsInput() {
		this.spotMascotCombo = new JComboBox<>(getLoadedSpotMascotIdentifications());
		setMinimumComboboxSize(this.spotMascotCombo);
		this.spotMascotCombo.addItemListener(e -> {this.checkOkButton();});
		return new InputParameter(
			"Spot Mascot identifications", 
			this.spotMascotCombo, 
			"A previously loaded spot Mascot identifications."
		);
	}

	private static void setMinimumComboboxSize(JComboBox<Object> combo) {
		combo.setMinimumSize(new Dimension(100, 20));
	}

	private Object[] getLoadedSpotMascotIdentifications() {
		return 	Core.getInstance().getClipboard()
				.getItemsByClass(SpotMascotIdentificationsDatatype.class).toArray();
	}

	private Component getSourceClipboardPanel() {
		JPanel toret = new CenteredJPanel(
			new InputParametersPanel(getInputParameters())
		);
		return toret;
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
		super.getMascotScoreThresholdTextField().setValue(e.getNewValue());
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
			super.getMascotRemoveDuplicatesCheckbox().setSelected(selected);
		} else {
			this.mascotRemoveDuplicates.setSelected(selected);
		}
	}

	private InputParameter getMaldiPlateInputComponent() {
		this.plateCombo = new JComboBox<>(getLoadedMaldiPlateObjects());
		setMinimumComboboxSize(this.plateCombo);
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
			boolean enabled = false;
			if(isShowingSpotDataFromClipboardTab()) {
				enabled = this.spotMascotCombo.getSelectedIndex() != -1;
			} else {
				enabled = 	this.plateCombo.getSelectedIndex() 	!= -1 &&
							this.mascotCombo.getSelectedIndex() != -1;
			}
			this.okButton.setEnabled(enabled);
		}
	}

	private boolean isShowingSpotDataFromClipboardTab() {
		return this.source.getSelectedIndex() == 0;
	}

	private boolean isShowingFileSelectionTab() {
		return this.source.getSelectedIndex() == 2;
	}

	private InputParameter getMascotFileInput() {
		this.mascotCombo = new JComboBox<>(getLoadedMascotIdentifications());
		setMinimumComboboxSize(this.mascotCombo);
		this.mascotCombo.addItemListener(e -> {this.checkOkButton();});
		return new InputParameter(
			"Mascot identifications", 
			this.mascotCombo, 
			"A previously loaded Mascot identifications."
		);
	}

	private Object[] getLoadedMascotIdentifications() {
		return Core.getInstance().getClipboard()
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
	
	@Override
	protected void onOkButtonEvent(ActionEvent event) { 
		if(isShowingFileSelectionTab()) {
			super.onOkButtonEvent(event);
		} else {
			canceled = false;
			dispose();
		}
	}

	@Override
	public SpotMascotIdentifications getMascotIdentifications() {
		if (isShowingSpotDataFromClipboardTab()) {
			return getSelectedSpotMascotIdentifications();
		} else {
			return super.getMascotIdentifications();
		}
	}

	public SpotMascotIdentifications getSelectedSpotMascotIdentifications() {
		return 	(SpotMascotIdentifications) 
					((ClipboardItem) this.spotMascotCombo.getSelectedItem())
				.getUserData();
	}
}
