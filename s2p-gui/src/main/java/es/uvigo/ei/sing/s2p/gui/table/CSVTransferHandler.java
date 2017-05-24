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
package es.uvigo.ei.sing.s2p.gui.table;


import java.util.Hashtable;
import java.util.Map;

import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.TransferHandler;
import javax.swing.plaf.UIResource;

/**
 * 
 * @author Miguel Reboiro-Jato
 *
 */
public class CSVTransferHandler extends TransferHandler implements UIResource {
	private static final long serialVersionUID = 1L;
	private final Map<Integer, Converter> converters;
	
	public CSVTransferHandler() {
		super();
		this.converters = new Hashtable<Integer, Converter>();
	}
	
	public void setConverter(int modelColumn, Converter format) {
		this.converters.put(modelColumn, format);
	}
	
	public Converter getConverter(int modelColumn) {
		return this.converters.get(modelColumn);
	}
	
	public void removeConverter(int modelColumn) {
		this.converters.remove(modelColumn);
	}

	/**
	 * Create a Transferable to use as the source for a data transfer.
	 * 
	 * @param c
	 *            The component holding the data to be transfered. This
	 *            argument is provided to enable sharing of TransferHandlers
	 *            by multiple components.
	 * @return The representation of the data to be transfered.
	 * 
	 */
	@Override
	protected BasicTransferable createTransferable(JComponent c) {
		if (c instanceof JTable) {
			JTable table = (JTable) c;
			int[] rows;
			int[] cols;

			if (!table.getRowSelectionAllowed()	&& !table.getColumnSelectionAllowed()) {
				return null;
			}

			if (!table.getRowSelectionAllowed()) {
				int rowCount = table.getRowCount();

				rows = new int[rowCount];
				for (int counter = 0; counter < rowCount; counter++) {
					rows[counter] = counter;
				}
			} else {
				rows = table.getSelectedRows();
			}

			if (!table.getColumnSelectionAllowed()) {
				int colCount = table.getColumnCount();

				cols = new int[colCount];
				for (int counter = 0; counter < colCount; counter++) {
					cols[counter] = counter;
				}
			} else {
				cols = table.getSelectedColumns();
			}

			if (rows == null || cols == null || rows.length == 0 || cols.length == 0) {
				return null;
			}

			StringBuilder plainSB = new StringBuilder();
			StringBuilder htmlSB = new StringBuilder();

			htmlSB.append("<html>\n<body>\n<table>\n");

			htmlSB.append("<tr>\n");
			for (int col : cols) {
				final String columnName = table.getColumnName(col);
				
				plainSB.append(columnName).append(',');
				htmlSB.append("  <th>" + columnName + "</th>\n");
			}
			htmlSB.append("</tr>\n");
			plainSB.replace(plainSB.length()-1, plainSB.length(), "\n");
			
			for (int row = 0; row < rows.length; row++) {
				htmlSB.append("<tr>\n");
				for (int col = 0; col < cols.length; col++) {
					Object obj = table.getValueAt(rows[row], cols[col]);
					int modelIndex = table.convertColumnIndexToModel(cols[col]);
					if (this.converters.containsKey(modelIndex)) {
						obj = this.converters.get(modelIndex).convert(obj);
					}
					
					String val = ((obj == null) ? "" : obj.toString());
					plainSB.append(val + ",");
					htmlSB.append("  <td>" + val + "</td>\n");
				}
				// we want a newline at the end of each line and not a tab
				plainSB.deleteCharAt(plainSB.length() - 1).append("\n");
				htmlSB.append("</tr>\n");
			}

			// remove the last newline
			plainSB.deleteCharAt(plainSB.length() - 1);
			htmlSB.append("</table>\n</body>\n</html>");

			return new BasicTransferable(plainSB.toString(), htmlSB.toString());
		}

		return null;
	}

	public int getSourceActions(JComponent c) {
		return COPY;
	}
	
	public interface Converter {
		public Object convert(Object value);
	}
}
