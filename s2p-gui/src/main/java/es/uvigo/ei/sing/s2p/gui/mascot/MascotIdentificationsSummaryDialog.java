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

import static javax.swing.BorderFactory.createEmptyBorder;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Font;
import java.awt.Window;
import java.util.Set;

import javax.swing.JPanel;
import javax.swing.JTable;

import org.jdesktop.swingx.renderer.DefaultTableRenderer;

import es.uvigo.ei.sing.hlfernandez.dialog.AbstractInputJDialog;
import es.uvigo.ei.sing.s2p.core.entities.SpotMascotIdentifications;
import es.uvigo.ei.sing.s2p.gui.UISettings;
import es.uvigo.ei.sing.s2p.gui.table.CSVTable;

public class MascotIdentificationsSummaryDialog extends AbstractInputJDialog {
	private static final long serialVersionUID = 1L;
	
	private JPanel inputComponents;

	private CSVTable table;
	
	public MascotIdentificationsSummaryDialog(Window parent, Set<String> spots,
		SpotMascotIdentifications spotIdentifications
	) {
		super(parent);
		
		this.table = new CSVTable(new MascotIdentificationsSummaryTableModel(spots, spotIdentifications));
		this.configureDialog();
	}
	

	private void configureDialog() {
		this.setResizable(true);
		this.getDescriptionPane().setVisible(false);
		this.cancelButton.setVisible(false);
		this.okButton.setEnabled(true);
		
		this.inputComponents.setBackground(UISettings.BG_COLOR);
		this.inputComponents.add(this.table, BorderLayout.CENTER);
		
		this.table.setDefaultRenderer(Object.class, new SummaryTableCellRenderer());
		
		this.table.packAll();
		this.pack();
	}

	@Override
	protected String getDialogTitle() {
		return "Summary";
	}

	@Override
	protected String getDescription() {
		return "";
	}

	@Override
	protected JPanel getInputComponentsPane() {
		this.inputComponents = new JPanel(new BorderLayout());
		this.inputComponents.setBorder(createEmptyBorder(10, 10, 10, 10));
		return this.inputComponents;
	}
	
	private class SummaryTableCellRenderer extends DefaultTableRenderer {
		private static final long serialVersionUID = 1L;

		public Component getTableCellRendererComponent(JTable table,
			Object value, boolean isSelected, boolean hasFocus, int row,
			int column
		) {
			
			final Component c = super.getTableCellRendererComponent(table,
				value, isSelected, hasFocus, row, column);

			c.setFont(c.getFont().deriveFont(Font.PLAIN, 14));
			
			return c;
		}
	}
}

