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

import java.awt.GridLayout;
import java.awt.Window;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.event.ListDataEvent;

import org.sing_group.gc4s.dialog.AbstractInputJDialog;
import org.sing_group.gc4s.input.JInputList;
import org.sing_group.gc4s.list.DefaultListDataListener;

public class ConditionLabelInputDialog extends AbstractInputJDialog {
	private static final long serialVersionUID = 1L;
	private JInputList inputList;
	private List<String> conditionLabels;

	protected ConditionLabelInputDialog(Window parent, List<String> conditionLabels) {
		super(parent);
		
		this.conditionLabels = conditionLabels;
		this.initComponent();
	}
	
	private void initComponent() {
		this.inputList.addElements(
			this.conditionLabels.toArray(new String[this.conditionLabels.size()])
		);
		this.pack();
	}

	protected String getDialogTitle() {
		return "Set conditions' labels";
	}

	protected String getDescription() {
		return "Set conditions' labels.";
	}

	protected JPanel getInputComponentsPane() {
		JPanel toret = new JPanel();
		toret.setLayout(new GridLayout(0, 1));
		this.inputList = new JInputList(true, false, false);
		this.inputList.getListPanel().getBtnMoveDown().setVisible(false);
		this.inputList.getListPanel().getBtnMoveUp().setVisible(false);
		this.inputList.addListDataListener(new DefaultListDataListener() {
			
			@Override
			public void intervalAdded(ListDataEvent e) {
				inputListContentsChanged();
			}
			
			@Override
			public void intervalRemoved(ListDataEvent e) {
				inputListContentsChanged();
			}
		});
		
		toret.add(this.inputList);
		toret.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		return toret;
	}

	private void inputListContentsChanged() {
		this.okButton.setEnabled(inputList.getInputItems().size() > 0);
	}

	public List<String> getConditionLabels() {
		return inputList.getInputItems();
	}
}
