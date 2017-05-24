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
package es.uvigo.ei.sing.s2p.gui.mascot;

import static es.uvigo.ei.sing.s2p.gui.UISettings.BG_COLOR;
import static javax.swing.BorderFactory.createEmptyBorder;

import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import es.uvigo.ei.sing.s2p.core.entities.MascotIdentifications;

public class MascotIdentificationsViewer extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private MascotIdentifications entries;

	private MascotEntryTable entriesTable;

	public MascotIdentificationsViewer(MascotIdentifications entries) {
		this.entries = entries;

		this.initComponent();
	}

	private void initComponent() {
		this.setLayout(new BorderLayout());
		this.setBackground(BG_COLOR);
		this.add(new JScrollPane(getEntriesTable()), BorderLayout.CENTER);
	}

	private Component getEntriesTable() {
		if (this.entriesTable == null) {
			this.entriesTable = new MascotEntryTable(this.entries);
			this.entriesTable.setOpaque(false);
			this.entriesTable.setBorder(createEmptyBorder(10, 10, 10, 10));
		}
		return this.entriesTable;
	}
}
