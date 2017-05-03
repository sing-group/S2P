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
package es.uvigo.ei.sing.s2p.gui.plate;

import static org.sing_group.gc4s.utilities.MatrixUtils.asStringMatrix;
import static org.sing_group.gc4s.utilities.MatrixUtils.removeColumn;
import static es.uvigo.ei.sing.s2p.gui.UISettings.FONT_SIZE;
import static java.awt.BorderLayout.CENTER;
import static java.awt.BorderLayout.NORTH;
import static javax.swing.BorderFactory.createEmptyBorder;
import static javax.swing.JOptionPane.showMessageDialog;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Window;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.event.TableModelEvent;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import com.itextpdf.text.DocumentException;

import es.uvigo.ei.sing.commons.csv.entities.CsvFormat;
import es.uvigo.ei.sing.commons.csv.io.CsvWriter;
import org.sing_group.gc4s.table.RowHeaderExtendedJXTable;
import org.sing_group.gc4s.utilities.ExtendedAbstractAction;
import es.uvigo.ei.sing.s2p.core.entities.MaldiPlate;
import es.uvigo.ei.sing.s2p.gui.components.dialog.ExportCsvDialog;
import es.uvigo.ei.sing.s2p.gui.util.CommonFileChooser;
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
		this.add(getInfoEditorPanel(), NORTH);
		this.add(new JScrollPane(getMaldiPlateTable()), CENTER);
	}

	private Component getInfoEditorPanel() {
		MaldiPlateInformationEditor editor =
			new MaldiPlateInformationEditor(this.plate.getInfo());
		editor.setBorder(createEmptyBorder(5, 5, 5, 5));
		return editor;
	}

	private Component getMaldiPlateTable() {
		table = new RowHeaderExtendedJXTable(
			plate.getData(), 
			plate.getColNames().toArray(),
			plate.getRowNames().toArray()
		);

		this.configureTable();
		
		return table;
	}
	
	protected void configureTable() {
		this.table.setBorder(createEmptyBorder(5, 5, 5, 5));
		this.table.setColumVisibilityActionsEnabled(false);
		this.table.setColumnControlVisible(true);
		this.table.setColumnSelectionAllowed(false);
		this.table.setRowSelectionAllowed(false);
		this.table.setCellSelectionEnabled(true);
		this.table.setSelectionMode(
			ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		this.table.setEditable(true);
		this.table.setHorizontalScrollEnabled(true);
		this.table.putClientProperty("terminateEditOnFocusLost", true);
		this.table.getModel().addTableModelListener(this::tableChanged);
		this.table.setFont(this.table.getFont().deriveFont(FONT_SIZE));
		this.table.getTableHeader().setReorderingAllowed(false);
		this.table
			.setRowSorter(new TableRowSorter<TableModel>(table.getModel()) {
			    @Override
			    public boolean isSortable(int column) {
			        return false;
			    }
			});

		this.table.addAction(
			new ExtendedAbstractAction("Export to CSV", this::exportToCsv)
		);
		this.table.addAction(
			new ExtendedAbstractAction("Export as PDF", this::exportToPdf)
		);
		this.table.addAction(
			new ExtendedAbstractAction("Save plate", this::savePlate)
		);
		this.table.packAll();
	}

	private void exportToPdf() {
		JFileChooser fileChooser = CommonFileChooser.getInstance().getFilechooser();
		int result = fileChooser.showSaveDialog(this);
		if (result == JFileChooser.APPROVE_OPTION) {
			File selectedFile = fileChooser.getSelectedFile();
			if(!selectedFile.getName().endsWith("pdf")) {
				selectedFile = new File(selectedFile.getAbsolutePath() + ".pdf");
			}

			try {
				this.plate.toPdf(selectedFile);
			} catch (FileNotFoundException | DocumentException e) {
				showMessageDialog(this, "There was an error writing to "
					+ selectedFile, "Input error",
					JOptionPane.ERROR_MESSAGE
				);
			}
		}
	}

	private void savePlate() {
		JFileChooser fileChooser = CommonFileChooser.getInstance().getFilechooser();
		int result = fileChooser.showSaveDialog(this);
		if (result == JFileChooser.APPROVE_OPTION) {
			File selectedFile = fileChooser.getSelectedFile();
			if(!selectedFile.getName().endsWith(".mpl")) {
				selectedFile = new File(selectedFile.getAbsolutePath() + ".mpl");
			}

			try {
				this.plate.toFile(selectedFile);
			} catch (IOException e) {
				showMessageDialog(this, "There was an error writing to "
					+ selectedFile, "Input error",
					JOptionPane.ERROR_MESSAGE
				);
			}
		}
	}
	
	private void tableChanged(TableModelEvent event) {
		if(event.getType() == TableModelEvent.UPDATE) {
			this.plate.setData(getPlateData());
		}
	}

	private String[][] getPlateData() {
		return asStringMatrix(removeColumn(0, table.getData()));
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
			 		JOptionPane.ERROR_MESSAGE
	 			);
		 	}
		 }
	}
	    
	private Window getDialogParent() {
		return SwingUtilities.getWindowAncestor(this);
	}

	public void exportToFile(File file, CsvFormat csvFormat) throws IOException {
		List<String> headerData = table.getColumnNames();
		
		Object[][] data = table.getData();
		
		CsvWriter csvWriter = CsvWriter.of(csvFormat);
		csvWriter.write(data, headerData, file);
	}
}
