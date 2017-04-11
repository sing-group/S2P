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
package es.uvigo.ei.sing.s2p.gui.table;

import static javax.swing.JOptionPane.showMessageDialog;
import static org.jdesktop.swingx.table.ColumnControlButton.COLUMN_CONTROL_MARKER;

import java.awt.Window;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.table.TableModel;

import org.jdesktop.swingx.JXTable;
import org.jdesktop.swingx.table.ColumnControlButton;
import org.jdesktop.swingx.table.TableColumnExt;

import es.uvigo.ei.sing.commons.csv.entities.CsvFormat;
import es.uvigo.ei.sing.commons.csv.io.CsvWriter;
import es.uvigo.ei.sing.s2p.gui.components.dialog.ExportCsvDialog;
import es.uvigo.ei.sing.s2p.gui.util.CsvUtils;

public class ExtendedCsvTable extends CSVTable {
	private static final long serialVersionUID = 1L;
	private CustomColumnControlButton columnControlButton;
	private boolean showVisibilityActions = true;

	public ExtendedCsvTable(TableModel dm) {
		super(dm);
		this.init();
	}
	
    private void init(){
    	this.columnControlButton = new CustomColumnControlButton(this);
    	setColumnControl(this.columnControlButton);
    }

    public void addExportToCsvAction() {
    	this.addAction(new AbstractAction("Export to CSV") {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				exportToCsv();
			}
		});
    }

	public void setColumVisibilityActionsEnabled(boolean enabled) {
		this.showVisibilityActions  = enabled;
		this.columnControlButton.update();
	}

	/**
	 * Adds {@code Action a} so that it will appear in the
	 * {@code ColumnControlButton} of the table.
	 * 
	 * @param a The action to add.
	 */
	public void addAction(Action a) {
		this.getActionMap().put(actionName(a), a);
		this.columnControlButton.update();
	}
    
	private String actionName(Action a) {
		return COLUMN_CONTROL_MARKER + a.getValue(Action.NAME);
	}

	private class CustomColumnControlButton extends ColumnControlButton {
		private static final long serialVersionUID = 1L;

		public CustomColumnControlButton(JXTable table) {
			super(table);
		}

		protected void createVisibilityActions() {
			if (showVisibilityActions == true) {
				super.createVisibilityActions();
			}
		}
	
		public void update() {
			super.populatePopup();
		}
	}
	
    protected void exportToCsv() {
		ExportCsvDialog dialog = new ExportCsvDialog(getDialogParent());
		dialog.setVisible(true);
		if (!dialog.isCanceled()) {
			try {
				this.exportToFile(
					dialog.getSelectedFile(), 
					CsvUtils.csvFormat(dialog.getSelectedCsvFormat())
				);
			} catch (IOException e) {
				showMessageDialog(this, "There was an error writing to "
					+ dialog.getSelectedFile(), "Input error",
					JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	private Window getDialogParent() {
		return SwingUtilities.getWindowAncestor(this);
	}

	public void exportToFile(File file, CsvFormat csvFormat) throws IOException {
		boolean header = 	this.getTableHeader() != null && 
							this.getTableHeader().isVisible();
		
		List<Integer> visibleColumns 	= getVisibleColumns();
		List<Integer> visibleRows 		= getVisibleRows();
		
		List<String> headerData = header ? 
			getTableHeader(visibleColumns) : 
			Collections.emptyList();
		
		Object[][] data = getData(visibleRows, visibleColumns);
		
		csvFormat.setDecimalFormatterMaximumFractionDigits(Integer.MAX_VALUE);

		CsvWriter csvWriter = CsvWriter.of(csvFormat);
		csvWriter.write(data, headerData, file);
	}

	public TableColumnExt getTableColumnExt(int modelIndex) {
		return (TableColumnExt) this.getColumns(true).get(modelIndex);
	}

	public void setColumnsVisibility(List<Integer> columnIndices,
			boolean visible) {
		columnIndices.stream()
		.map(this::getTableColumnExt)
		.forEach(column -> {
			column.setVisible(visible);
		});
	}
}
