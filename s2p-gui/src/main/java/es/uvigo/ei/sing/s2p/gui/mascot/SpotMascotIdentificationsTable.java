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

import static javax.swing.JOptionPane.ERROR_MESSAGE;
import static javax.swing.JOptionPane.INFORMATION_MESSAGE;
import static javax.swing.JOptionPane.showMessageDialog;
import static org.sing_group.jsparklines_factory.JSparklinesBarChartTableCellRendererFactory.createMaxValueBarChartRenderer;

import java.awt.Component;
import java.awt.Window;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Set;
import java.util.stream.IntStream;

import javax.swing.Action;
import javax.swing.JLabel;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import org.jdesktop.swingx.renderer.DefaultTableRenderer;

import org.sing_group.gc4s.event.PopupMenuAdapter;
import org.sing_group.gc4s.table.ColumnSummaryTabeCellRenderer;
import org.sing_group.gc4s.utilities.ExtendedAbstractAction;
import es.uvigo.ei.sing.s2p.core.entities.MascotEntry;
import es.uvigo.ei.sing.s2p.core.entities.SpotMascotIdentifications;
import es.uvigo.ei.sing.s2p.core.io.samespots.SameSpotsReportFileWriter;
import es.uvigo.ei.sing.s2p.core.io.samespots.SameSpotsReportFileWriterConfiguration;
import es.uvigo.ei.sing.s2p.gui.samespots.FillSameSpotsReportDialog;
import es.uvigo.ei.sing.s2p.gui.table.ExtendedCsvTable;

public class SpotMascotIdentificationsTable extends ExtendedCsvTable {
	private static final long serialVersionUID = 1L;

	private ExtendedAbstractAction fillSameSpotsReportAction;
	private ExtendedAbstractAction removeSpotsAction;
	private SpotMascotIdentifications spotIdentifications;

	public SpotMascotIdentificationsTable(
		SpotMascotIdentifications spotIdentifications
	) {
		this(spotIdentifications.getSpots(), spotIdentifications);
	}

	public SpotMascotIdentificationsTable(Set<String> spots,
		SpotMascotIdentifications spotIdentifications
	) {
		super(createModel(spots, spotIdentifications));
		
		this.spotIdentifications = spotIdentifications;
		this.initComponent();
	}

	private static TableModel createModel(Set<String> spots,
		SpotMascotIdentifications identifications
	) {
		return new SpotMascotIdentificationsTableModel(spots, identifications);
	}

	private void initComponent() {
		this.setColumVisibilityActionsEnabled(false);
		this.setAutoCreateRowSorter(true);
		this.setColumnControlVisible(true);
		this.getRowSorter().toggleSortOrder(3);
		this.getRowSorter().toggleSortOrder(3);
		this.addExportToCsvAction();
		this.addFillReportaction();
		this.getTableHeader().setReorderingAllowed(false);
		this.getTableHeader().setDefaultRenderer(
			new ColumnSummaryTabeCellRenderer(
				this.getTableHeader().getDefaultRenderer()
			)
		);
		this.setDefaultRenderer(File.class, new FileCellRenderer());
		this.setComponentPopupMenu(getTablePopupMenu());
		this.updateSparklinesRenderers();
		this.getSpotsTableModel().addTableModelListener(
			new TableModelListener() {

				@Override
				public void tableChanged(TableModelEvent e) {
					updateSparklinesRenderers();
				}
			}
		);
		this.updateUI();
	}

	private void updateSparklinesRenderers() {
		for(int i : Arrays.asList(3, 4, 5, 6, 8)) {
			createMaxValueBarChartRenderer(this, i).showNumberAndChart(true, 40);
		}
	}

	private void addFillReportaction() {
		this.addAction(getFillSameSpotsReportButton());
	}

	private Action getFillSameSpotsReportButton() {
		if(this.fillSameSpotsReportAction == null) {
			this.fillSameSpotsReportAction =
				new ExtendedAbstractAction(
					"Fill SameSpots report", 
					this::fillSameSpotsReport
				);
		}
		return this.fillSameSpotsReportAction;
	}

	private void fillSameSpotsReport() {
		FillSameSpotsReportDialog dialog =
			new FillSameSpotsReportDialog(getDialogParent());
		dialog.setVisible(true);
		if (!dialog.isCanceled()) {
			fillSameSpotsReport(dialog.getSelectedFile(),
				dialog.getSelectedConfiguration());
		}
	}

	private Window getDialogParent() {
		return SwingUtilities.getWindowAncestor(this);
	}

	private void fillSameSpotsReport(File reportDirectory,
		SameSpotsReportFileWriterConfiguration configuration
	) {
		try {
			SameSpotsReportFileWriter.writeReportDirectory(
				this.spotIdentifications, reportDirectory, configuration);

			showMessageDialog(this,
				"Reports at " + reportDirectory
				+ " has been successfully processed",
				"Success", INFORMATION_MESSAGE);
		} catch (IOException e) {
			showMessageDialog(this, "An error ocurred writing report files",
				"Error", ERROR_MESSAGE);
		}
	}

	private JPopupMenu getTablePopupMenu() {
		JPopupMenu menu = new JPopupMenu();
		menu.add(getRemoveSelectedRowsAction());
		menu.addPopupMenuListener(new PopupMenuAdapter() {

			@Override
			public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
				removeSpotsAction.setEnabled(getSelectedRowCount() > 0);
			}
		});

		return menu;
	}

	private Action getRemoveSelectedRowsAction() {
		removeSpotsAction = new ExtendedAbstractAction(
			"Remove selected spots", this::removeSelectedSpots);
		removeSpotsAction.setEnabled(false);

		return removeSpotsAction;
	}

	private void removeSelectedSpots() {
		if (getSelectedRowCount() > 0) {
			this.removeSelectedSpotsFromSamples();
			getSpotsTableModel().fireTableDataChanged();
		}
	}

	private SpotMascotIdentificationsTableModel getSpotsTableModel() {
		return (SpotMascotIdentificationsTableModel) getModel();
	}

	private void removeSelectedSpotsFromSamples() {
		IntStream.of(getSelectedRows()).boxed()
			.map(this::convertRowIndexToModel)
			.sorted(Collections.reverseOrder(Integer::compareTo))
			.forEach(i -> {
				removeSpotFromSamples(i);
			});
	}

	private void removeSpotFromSamples(int row) {
		SpotMascotIdentificationsTableModel model = getSpotsTableModel();
		String spot = model.getSpotAtRow(row);
		MascotEntry entry = model.getMascotEntryAtRow(row);
		this.spotIdentifications.removeIdentification(spot, entry);
	}

	private class FileCellRenderer extends DefaultTableRenderer {
		private static final long serialVersionUID = 1L;

		public Component getTableCellRendererComponent(JTable table,
			Object value, boolean isSelected, boolean hasFocus, int row,
			int column
		) {
			final Component c = super.getTableCellRendererComponent(
				table, value, isSelected, hasFocus, row, column
			);

			if (c instanceof JLabel && value instanceof File) {
				JLabel label = (JLabel) c;
				label.setText(((File) value).getName());
				label.setToolTipText(((File) value).getAbsolutePath());
			}

			return c;
		}
	}
}
