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
package es.uvigo.ei.sing.s2p.gui.mascot;

import java.awt.BorderLayout;
import java.awt.Window;
import java.util.Set;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import es.uvigo.ei.sing.hlfernandez.dialog.AbstractInputJDialog;
import es.uvigo.ei.sing.s2p.core.entities.SpotMascotIdentifications;

public class SpotMascotIdentificationsDialog extends AbstractInputJDialog {
	private static final long serialVersionUID = 1L;
	
	private SpotMascotIdentificationsTable table;
	private JPanel inputComponents;
	
	public SpotMascotIdentificationsDialog(Window parent, Set<String> spots,
		SpotMascotIdentifications spotIdentifications
	) {
		super(parent);
		
		this.table = new SpotMascotIdentificationsTable(spots, spotIdentifications);
		this.configureDialog();
	}
	

	private void configureDialog() {
		this.setResizable(true);
		this.getDescriptionPane().setVisible(false);
		this.cancelButton.setVisible(false);
		this.okButton.setEnabled(true);
		this.setModal(false);
		
		this.inputComponents.add(new JScrollPane(this.table), BorderLayout.CENTER);
		
		this.pack();
	}

	@Override
	protected String getDialogTitle() {
		return "Mascot identifications";
	}

	@Override
	protected String getDescription() {
		return "";
	}

	@Override
	protected JPanel getInputComponentsPane() {
		this.inputComponents = new JPanel(new BorderLayout());
		return this.inputComponents;
	}
}
