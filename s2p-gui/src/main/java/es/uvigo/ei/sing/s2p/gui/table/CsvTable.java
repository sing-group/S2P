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

import static java.lang.String.format;
import static java.util.stream.Collectors.toList;
import static java.util.stream.IntStream.of;
import static java.util.stream.IntStream.range;

import java.awt.Component;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.stream.Collectors;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

import org.jdesktop.swingx.JXTable;

import es.uvigo.ei.sing.s2p.gui.table.CSVTransferHandler.Converter;

/**
 * An extension of {@code JXTable} that represents a CSV table.
 * 
 * @author Miguel Reboiro-Jato
 * @author Hugo López-Fernández
 *
 */
public class CsvTable extends JXTable {
	private static final long serialVersionUID = 1L;
	private CSVTransferHandler transferHandler;
	private Map<Integer, String> formats;

	public CsvTable() {
		super();
		this.configure();
	}

	public CsvTable(int numRows, int numColumns) {
		super(numRows, numColumns);
		this.configure();
	}

	public CsvTable(Object[][] rowData, Object[] columnNames) {
		super(rowData, columnNames);
		this.configure();
	}

	public CsvTable(TableModel dm, TableColumnModel cm, ListSelectionModel sm) {
		super(dm, cm, sm);
		this.configure();
	}

	public CsvTable(TableModel dm, TableColumnModel cm) {
		super(dm, cm);
		this.configure();
	}

	public CsvTable(TableModel dm) {
		super(dm);
		this.configure();
	}

	public CsvTable(Vector<?> rowData, Vector<?> columnNames) {
		super(rowData, columnNames);
		this.configure();
	}

	public void setFormat(int modelColumn, String format) {
		this.formats.put(modelColumn, format);
	}

	public String getFormat(int modelColumn) {
		return this.formats.get(modelColumn);
	}

	public void removeFormat(int modelColumn) {
		this.formats.remove(modelColumn);
	}

	public void setConverter(int modelColumn, Converter converter) {
		this.transferHandler.setConverter(modelColumn, converter);
	}

	public Converter getConverter(int modelColumn) {
		return this.transferHandler.getConverter(modelColumn);
	}
	
	public void removeConverter(int modelColumn) {
		this.transferHandler.removeConverter(modelColumn);
	}

	public void exportViewToFile(File file) throws FileNotFoundException {
		this.selectAll();
		BasicTransferable transferable = this.transferHandler.createTransferable(this);
		if (transferable == null) {
			throw new RuntimeException("There's not data to export.");
		} else {
			PrintWriter pw = new PrintWriter(file);
			pw.print(transferable.getPlainData());
			pw.close();
		}
		this.clearSelection();
	}
	
	public void exportSelectionToFile(File file) throws FileNotFoundException {
		BasicTransferable transferable = this.transferHandler.createTransferable(this);
		if (transferable == null) {
			throw new RuntimeException("There's not data to export.");
		} else {
			if (transferable.getPlainData().trim().equals("")) {
				throw new RuntimeException("No cell selected");
			} else {
				PrintWriter pw = new PrintWriter(file);
				pw.print(transferable.getPlainData());
				pw.close();
			}
		}
	}

	public void configure() {
		this.transferHandler = new CSVTransferHandler();
		this.formats = new HashMap<Integer, String>();
		for (int i = 0; i < this.getColumnCount(); i++) {
			this.formats.put(i, "%.4f");
		}

		this.setCellSelectionEnabled(true);
		this.setColumnControlVisible(true);
		this.setTransferHandler(this.transferHandler);
		this.setEditable(false);
		TableCellRenderer renderer = new DefaultTableCellRenderer() {
			private static final long serialVersionUID = 1L;

			public Component getTableCellRendererComponent(JTable table,
				Object value, boolean isSelected, boolean hasFocus, int row,
				int column
			) {
				Component component = super.getTableCellRendererComponent(table,
					value, isSelected, hasFocus, row, column);
				if ((value instanceof Float || value instanceof Double) && 
					component instanceof JLabel
				) {
					JLabel label = (JLabel) component;
					label.setHorizontalAlignment(SwingConstants.RIGHT);
					int modelColumn = table.convertColumnIndexToModel(column);
					label.setText(format(formats.get(modelColumn), value));
				}
				return component;
			}
		};

		this.setDefaultRenderer(Float.class, renderer);
		this.setDefaultRenderer(Double.class, renderer);
	}
	
	public List<Integer> getVisibleRows() {
		if (this.getRowSelectionAllowed()) {
			this.selectAll();
			int[] toret = this.getSelectedRows();
			this.clearSelection();
			
			return of(toret).boxed().map(this::convertRowIndexToModel).collect(toList());
		} else {
			return range(0, this.getRowCount()).boxed().collect(toList());
		}
	}

	public List<Integer> getVisibleColumns() {
		if (this.getColumnSelectionAllowed()) {
			this.selectAll();
			int[] toret = this.getSelectedColumns();
			this.clearSelection();
			
			return of(toret).boxed().map(this::convertColumnIndexToModel)
					.collect(toList());
		} else {
			return range(0, this.getColumnCount()).boxed().collect(toList());
		}
	}
	
	public Object[][] getData(List<Integer> rows, List<Integer> columns) {
		Object[][] toret = new Object[rows.size()][columns.size()];
		
		int dataRow = 0;
		for (int row : rows) {
			
			int dataColumn = 0;
			for (int column : columns) {
				toret[dataRow][dataColumn++] = this.getModel().getValueAt(row, column);
			}
			dataRow++;
		}
		
		return toret;
	}

	public List<String> getTableHeader(List<Integer> columns) {
		return 	columns.stream().map(this.getModel()::getColumnName)
				.collect(Collectors.toList());
	}
}
