package es.uvigo.ei.sing.s2p.gui.plate;

import static javax.swing.JOptionPane.showMessageDialog;

import java.awt.BorderLayout;
import java.awt.Component;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import org.jdesktop.swingx.JXTable;

import es.uvigo.ei.sing.commons.csv.entities.CsvFormat;
import es.uvigo.ei.sing.commons.csv.io.CsvWriter;
import es.uvigo.ei.sing.hlfernandez.dialog.ExportCsvDialog;
import es.uvigo.ei.sing.hlfernandez.table.RowHeaderExtendedJXTable;
import es.uvigo.ei.sing.hlfernandez.utilities.ExtendedAbstractAction;
import es.uvigo.ei.sing.s2p.core.entities.MaldiPlate;
import es.uvigo.ei.sing.s2p.gui.util.CsvUtils;

public class MaldiPlateTable extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private MaldiPlate plate;
	protected RowHeaderExtendedJXTable table;

	public MaldiPlateTable(MaldiPlate plate) {
		this.plate = plate;
		
		this.init();
	}

	private void init() {
		this.setLayout(new BorderLayout());
		this.add(new JScrollPane(getMaldiPlateTable()));
	}

	private Component getMaldiPlateTable() {
		table = new RowHeaderExtendedJXTable(
			plate.getData(), 
			plate.getColNames().toArray(),
			plate.getRowNames().toArray()
		);
		table.setColumnControlVisible(true);
		table.addAction(
			new ExtendedAbstractAction("Export to CSV", this::exportToCsv)
		);
		configureTable(table);
		
		return table;
	}
	
	protected static void configureTable(JXTable table) {
		table.setColumnSelectionAllowed(false);
		table.setRowSelectionAllowed(false);
		table.setCellSelectionEnabled(true);
		table.setSelectionMode(
			ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		table.setEditable(true);
		table.setHorizontalScrollEnabled(true);
		table.putClientProperty("terminateEditOnFocusLost", true);
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
			 				JOptionPane.ERROR_MESSAGE
			 			);
			 	}
		 }
	}
	    
	public void exportToFile(File file, CsvFormat csvFormat) throws IOException {
		List<String> headerData = table.getColumnNames();
		
		Object[][] data = table.getData();
		
		CsvWriter csvWriter = CsvWriter.of(csvFormat);
		csvWriter.write(data, headerData, file);
	}
}
