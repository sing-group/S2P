package es.uvigo.ei.sing.s2p.gui.table;

import static javax.swing.JOptionPane.showMessageDialog;
import static org.jdesktop.swingx.table.ColumnControlButton.COLUMN_CONTROL_MARKER;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JOptionPane;
import javax.swing.table.TableModel;

import org.jdesktop.swingx.JXTable;
import org.jdesktop.swingx.table.ColumnControlButton;

import es.uvigo.ei.sing.commons.csv.entities.CsvFormat;
import es.uvigo.ei.sing.commons.csv.io.CsvWriter;
import es.uvigo.ei.sing.hlfernandez.dialog.ExportCsvDialog;
import es.uvigo.ei.sing.s2p.gui.util.CsvUtils;

public class ExtendedCsvTable extends CSVTable {
	private static final long serialVersionUID = 1L;
	private CustomColumnControlButton columnControlButton;

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
	
		public void update() {
			super.populatePopup();
		}
	}
	
    protected void exportToCsv() {
		ExportCsvDialog dialog = new ExportCsvDialog(null);
		dialog.setVisible(true);
		if(!dialog.isCanceled()) {
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
    
	public void exportToFile(File file, CsvFormat csvFormat) throws IOException {
		boolean header = 	this.getTableHeader() != null && 
							this.getTableHeader().isVisible();
		
		List<Integer> visibleColumns 	= getVisibleColumns();
		List<Integer> visibleRows 		= getVisibleRows();
		
		List<String> headerData = header ? 
			getTableHeader(visibleColumns) : 
			Collections.emptyList();
		
		Object[][] data = getData(visibleRows, visibleColumns);
		
		CsvWriter csvWriter = CsvWriter.of(csvFormat);
		csvWriter.write(data, headerData, file);
	}
}
