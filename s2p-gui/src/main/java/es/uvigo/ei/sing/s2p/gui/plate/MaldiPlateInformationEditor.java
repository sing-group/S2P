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
package es.uvigo.ei.sing.s2p.gui.plate;

import static es.uvigo.ei.sing.s2p.core.entities.MaldiPlateInformation.FIELD_DATE;
import static es.uvigo.ei.sing.s2p.core.entities.MaldiPlateInformation.FIELD_MALDI_TARGET;
import static es.uvigo.ei.sing.s2p.core.entities.MaldiPlateInformation.FIELD_MASCOT_EXPERIMENT;
import static es.uvigo.ei.sing.s2p.core.entities.MaldiPlateInformation.FIELD_SAMPLE_TYPE;
import static es.uvigo.ei.sing.s2p.core.entities.MaldiPlateInformation.FIELD_USER;

import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ItemEvent;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.jdesktop.swingx.JXTextField;
import org.sing_group.gc4s.input.text.BindJXTextField;

import es.uvigo.ei.sing.s2p.core.entities.MaldiPlateInformation;
import es.uvigo.ei.sing.s2p.core.entities.MaldiPlateInformation.MaldiTarget;
import es.uvigo.ei.sing.s2p.gui.UISettings;

public class MaldiPlateInformationEditor extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private MaldiPlateInformation info;

	public MaldiPlateInformationEditor(MaldiPlateInformation info) {
		this.info = info;
		
		this.init();
	}

	private void init() {
		this.setLayout(new GridLayout(0, 1));
		this.setBackground(UISettings.BG_COLOR);
		
		JPanel firstRow = new JPanel();
		firstRow.setOpaque(false);
		firstRow.setLayout(new BoxLayout(firstRow, BoxLayout.X_AXIS));
		firstRow.add(new JLabel(FIELD_USER + ": "));
		firstRow.add(getUserTextField());
		firstRow.add(Box.createHorizontalStrut(50));
		firstRow.add(new JLabel(FIELD_DATE + ": "));
		firstRow.add(getDateTextField());
		firstRow.add(Box.createHorizontalStrut(50));
		firstRow.add(new JLabel(FIELD_MALDI_TARGET + ": "));
		firstRow.add(getMaldiTargetCombo());
		
		JPanel secondRow = new JPanel();
		secondRow.setOpaque(false);
		secondRow.setLayout(new BoxLayout(secondRow, BoxLayout.X_AXIS));
		secondRow.add(new JLabel(FIELD_SAMPLE_TYPE + ": "));
		secondRow.add(getSampleTypeTextField());
		secondRow.add(Box.createHorizontalStrut(50));
		secondRow.add(new JLabel(FIELD_MASCOT_EXPERIMENT + ": "));
		secondRow.add(getMascotField());
		
		this.add(firstRow);
		this.add(secondRow);
	}
	
	private Component getMaldiTargetCombo() {
		JComboBox<MaldiTarget> maldiTarget = 
			new JComboBox<MaldiTarget>(MaldiTarget.values());

		if (info.getMaldiTarget().isPresent()) {
			maldiTarget.setSelectedItem(info.getMaldiTarget().get());
		}

		maldiTarget.addItemListener(e -> {
			if(e.getStateChange() == ItemEvent.SELECTED) {
				this.info.setMaldiTarget(
					(MaldiTarget) maldiTarget.getSelectedItem());
			}
		});
		return maldiTarget;
	}

	public JXTextField getUserTextField() {
		return new BindJXTextField(FIELD_USER, info.getUser(), info::setUser);
	}
	
	public JXTextField getDateTextField() {
		return new BindJXTextField(FIELD_DATE, info.getDate(), info::setDate);
	}
	
	public JXTextField getSampleTypeTextField() {
		return new BindJXTextField(FIELD_SAMPLE_TYPE, info.getSampleType(), info::setSampleType);
	}
	
	public JXTextField getMascotField() {
		return new BindJXTextField(FIELD_MASCOT_EXPERIMENT, info.getMascotExperiment(), info::setMascotExperiment);
	}
}
