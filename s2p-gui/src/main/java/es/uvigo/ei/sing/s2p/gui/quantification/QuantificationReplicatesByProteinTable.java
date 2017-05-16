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
package es.uvigo.ei.sing.s2p.gui.quantification;

import static java.lang.Double.isNaN;
import static java.lang.String.format;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;

import org.jdesktop.swingx.renderer.DefaultTableRenderer;

import es.uvigo.ei.sing.s2p.core.entities.quantification.QuantificationDataset;
import es.uvigo.ei.sing.s2p.gui.table.ExtendedCsvTable;

public class QuantificationReplicatesByProteinTable extends ExtendedCsvTable {
	private static final long serialVersionUID = 1L;

	public QuantificationReplicatesByProteinTable(QuantificationDataset dataset) {
		super(new QuantificationReplicatesByProteinTableModel(dataset));

		this.initComponent();
	}

	private void initComponent() {
		this.addExportToCsvAction();
		QuantificationReplicatesTableCellRenderer renderer = 
			new QuantificationReplicatesTableCellRenderer();
		this.setDefaultRenderer(Double.class, renderer);
		this.setHorizontalScrollEnabled(true);
		this.packAll();
	}
	
	private class QuantificationReplicatesTableCellRenderer
	extends DefaultTableRenderer 
{
	private static final long serialVersionUID = 1L;

	public Component getTableCellRendererComponent(JTable table,
		Object value, boolean isSelected, boolean hasFocus, int row,
		int column
	) {
		final Component c = super.getTableCellRendererComponent(
			table, value, isSelected, hasFocus, row, column
		);

		JLabel label = (JLabel) c;

			if (!(value instanceof String)) {
				Double doubleValue = Double.parseDouble(value.toString());
				String cellValue = isNaN((double) doubleValue)
					? "" : format("%6.2e", doubleValue);
				if (!cellValue.isEmpty()) {
					label.setToolTipText(doubleValue.toString());
				}
				label.setText(cellValue);
			}
		
		return c;
	}
}
}
