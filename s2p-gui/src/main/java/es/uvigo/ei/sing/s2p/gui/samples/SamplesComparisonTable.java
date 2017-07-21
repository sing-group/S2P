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
package es.uvigo.ei.sing.s2p.gui.samples;

import static es.uvigo.ei.sing.s2p.gui.UISettings.FONT_SIZE;
import static es.uvigo.ei.sing.s2p.gui.UISettings.FONT_SIZE_HEADER;
import static es.uvigo.ei.sing.s2p.gui.spots.SpotUtils.spotTooltip;
import static es.uvigo.ei.sing.s2p.gui.spots.SpotUtils.spotValue;
import static es.uvigo.ei.sing.s2p.gui.spots.heatmap.HeatMapModelBuilder.createHeatMapModelBuilder;
import static java.awt.Font.BOLD;
import static java.util.Collections.emptyMap;
import static java.util.Optional.ofNullable;
import static javax.swing.JOptionPane.showMessageDialog;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.IntStream;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

import org.jdesktop.swingx.renderer.DefaultTableRenderer;
import org.sing_group.gc4s.event.PopupMenuAdapter;
import org.sing_group.gc4s.utilities.ExtendedAbstractAction;
import org.sing_group.gc4s.visualization.JHeatMapModel;

import es.uvigo.ei.sing.s2p.core.entities.Sample;
import es.uvigo.ei.sing.s2p.core.entities.SpotMascotIdentifications;
import es.uvigo.ei.sing.s2p.core.io.SpotsDataWriter;
import es.uvigo.ei.sing.s2p.gui.components.dialog.ExportCsvDialog;
import es.uvigo.ei.sing.s2p.gui.spots.heatmap.SpotRenderer;
import es.uvigo.ei.sing.s2p.gui.table.ExtendedCsvTable;
import es.uvigo.ei.sing.s2p.gui.table.TestRowFilter;
import es.uvigo.ei.sing.s2p.gui.table.spots.SpotPresenceTester;
import es.uvigo.ei.sing.s2p.gui.table.spots.SpotsCsvTable;
import es.uvigo.ei.sing.s2p.gui.util.CsvUtils;

public class SamplesComparisonTable extends JPanel {
	private static final long serialVersionUID = 1L;

	private ExtendedCsvTable table;
	private SamplesComparisonTableModel tableModel;

	private List<Sample> samples;
	private Map<Sample, Color> sampleColors;
	private Map<Sample, String> sampleLabels;
	private SpotPresenceTester spotPresenceTester;

	private boolean allowSpotEdition = false;
	private boolean showProteinIdentifications = false;
	private Optional<SpotMascotIdentifications> mascotIdentifications =
		Optional.empty();

	private ExtendedAbstractAction removeSpotsAction;
	private AbstractAction exportToCsvWithIdentificationsAction;

	public SamplesComparisonTable(List<Sample> samples) {
		this(samples, false);
	}

	public SamplesComparisonTable(List<Sample> samples,
		boolean allowSpotEdition
	) {
		this(samples, new HashMap<>(), new HashMap<>(), allowSpotEdition);
	}

	public SamplesComparisonTable(List<Sample> samples,
		Map<Sample, Color> sampleColors, Map<Sample, String> sampleLabels
	) {
		this(samples, sampleColors, sampleLabels, false);
	}

	public SamplesComparisonTable(List<Sample> samples,
		Map<Sample, Color> sampleColors, Map<Sample, String> sampleLabels,
		boolean allowSpotEdition
	) {
		this.samples = samples;
		this.sampleColors = sampleColors;
		this.sampleLabels = sampleLabels;
		this.allowSpotEdition = allowSpotEdition;

		this.initComponent();
	}

	private void initComponent() {
		this.initTable();

		this.setLayout(new BorderLayout());
		this.add(new JScrollPane(table), BorderLayout.CENTER);
	}

	private void initTable() {
		this.tableModel = new SamplesComparisonTableModel(
			this.samples, this.allowSpotEdition);
		this.tableModel.addTableModelListener(new TableModelListener() {

			@Override
			public void tableChanged(TableModelEvent e) {
				updateSpotPresenceTester();
			}
		});
		this.table = new SpotsCsvTable(this.tableModel, this::getSpotValue);
		this.table.setEditable(true);
		final SamplesComparisonTableCellRenderer renderer =
			new SamplesComparisonTableCellRenderer();
		this.table.setDefaultRenderer(Double.class, renderer);
		this.table.setDefaultRenderer(Object.class, renderer);
		this.table.getTableHeader().setDefaultRenderer(
			new SamplesComparisonTableHeaderCellRenderer(
				this.table.getTableHeader().getDefaultRenderer()));
		this.table.setColumnControlVisible(true);
		this.table.getTableHeader().setReorderingAllowed(false);
		this.table.addExportToCsvAction();
		this.table.addAction(getExportToCsvWithIdentificationsAction());

		updateSpotPresenceTester();
	}

	private void updateSpotPresenceTester() {
		spotPresenceTester = new SpotPresenceTester(getProteins(this.samples));
		table.setRowFilter(new TestRowFilter<>(spotPresenceTester, 0));
	}

	private String getSpotValue(String spot) {
		return spotValue(spot,
				showProteinIdentifications, mascotIdentifications);
	}

	private static Set<String> getProteins(List<Sample> samples) {
		Set<String> all = new HashSet<String>();
		samples.forEach(s -> {
			all.addAll(s.getSpots());
		});
		return all;
	}

	private Action getExportToCsvWithIdentificationsAction() {
		this.exportToCsvWithIdentificationsAction = new AbstractAction(
			"Export to CSV with identifications") {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				exportToCsvWithIdentifications();
			}
		};
		this.exportToCsvWithIdentificationsAction.setEnabled(false);

		return this.exportToCsvWithIdentificationsAction;
	}

	protected void exportToCsvWithIdentifications() {
		ExportCsvDialog dialog = new ExportCsvDialog(getDialogParent());
		dialog.setVisible(true);
		if (!dialog.isCanceled()) {
			try {
				SpotsDataWriter.writeSamplesWithIdentifications(
					samples,
					this.mascotIdentifications.get(),
					CsvUtils.csvFormat(dialog.getSelectedCsvFormat()),
					dialog.getSelectedFile()
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

	private class SamplesComparisonTableCellRenderer extends DefaultTableRenderer {
		private static final long serialVersionUID = 1L;

		public Component getTableCellRendererComponent(JTable table,
			Object value, boolean isSelected, boolean hasFocus, int row,
			int column
		) {
			final int columnModel = table.convertColumnIndexToModel(column);

			final Component c = super.getTableCellRendererComponent(table,
				value, isSelected, hasFocus, row, column);

			c.setFont(c.getFont().deriveFont(Font.PLAIN, FONT_SIZE));


			if(columnModel > 0) {
				if(c instanceof JLabel) {
					JLabel label = (JLabel) c;
					String cellValue = Double.isNaN((double) value) ?
							"" : String.format("%6.2e", value);
					if(!Double.isNaN((double) value)) {
						label.setToolTipText(cellValue);
					}
					label.setText(cellValue);
					Color background = sampleColors.get(
						samples.get(columnModel - 1));
					if (background != null) {
						c.setBackground(background);
					}
				}
			} else {
				String spot = value.toString();
				JLabel spotLabel = (JLabel) c;
				spotLabel.setText(spotValue(
					spot, showProteinIdentifications, mascotIdentifications
				));
				spotLabel.setToolTipText(spotTooltip(spot, mascotIdentifications));

				if (mascotIdentifications.isPresent()) {
					if (!mascotIdentifications.get().get(spot).isEmpty()) {
						if (!showProteinIdentifications) {
							spotLabel.setFont(c.getFont().deriveFont(BOLD));
						}
					} else {
						spotLabel.setForeground(Color.RED);
					}
				}
			}

			return c;
		}
	}

	private class SamplesComparisonTableHeaderCellRenderer
	extends DefaultTableCellRenderer {
		private static final long serialVersionUID = 1L;
		private TableCellRenderer defaultRenderer;

		public SamplesComparisonTableHeaderCellRenderer(
			TableCellRenderer defaultRenderer) {
			this.defaultRenderer = defaultRenderer;
		}

		@Override
		public Component getTableCellRendererComponent(
			JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column
		) {
			final Component c = defaultRenderer.getTableCellRendererComponent(
				table, value, isSelected, hasFocus, row, column);

			if (c instanceof JLabel) {
				c.setFont(c.getFont().deriveFont(Font.BOLD, FONT_SIZE_HEADER));
			}

			final int columnModel = table.convertColumnIndexToModel(column);

			if (columnModel > 0) {
				if(c instanceof JLabel) {
					((JLabel) c).setToolTipText(
						sampleLabels.get(samples.get(columnModel - 1))
					);
				}
			}

			return c;
		}
	}

	public void setVisibleSpots(Set<String> visibleSpots) {
		this.spotPresenceTester.setVisibleSpots(visibleSpots);
		table.setRowFilter(new TestRowFilter<>(spotPresenceTester, 0));
	}

	public void setShowProteinIdentifications(boolean show) {
		this.showProteinIdentifications = show;
		this.updateUI();
	}

	public void setMascotIdentifications(
		SpotMascotIdentifications mascotIdentifications
	) {
		this.mascotIdentifications = ofNullable(mascotIdentifications);
		this.exportToCsvWithIdentificationsAction.setEnabled(true);
	}

	public void fireTableStructureChanged() {
		this.tableModel.fireTableStructureChanged();
	}

	public JHeatMapModel getHeatMapModel(SpotRenderer spotRenderer, boolean showSampleLabels) {
		return 	createHeatMapModelBuilder(this.table, spotRenderer)
			.withMascotIdentifications(this.mascotIdentifications)
			.withSampleLabels(showSampleLabels ? this.sampleLabels : emptyMap())
		.build();
	}

	public void addTableAction(Action a) {
		this.table.addAction(a);
	}

	public void setColumnsVisibility(List<Integer> columnIndices,
		boolean visible
	) {
		this.table.setColumnsVisibility(columnIndices, visible);
	}

	public void removeMascotIdentifications() {
		this.mascotIdentifications = Optional.empty();
	}

	public void setShowComponentPopupMenu(boolean show) {
		if(show) {
			this.table.setComponentPopupMenu(getSpotsTablePopupMenu());
		} else {
			this.table.setComponentPopupMenu(null);
		}
	}

	private JPopupMenu getSpotsTablePopupMenu() {
		JPopupMenu menu = new JPopupMenu();
		menu.add(getRemoveSelectedRowsAction());
		menu.addPopupMenuListener(new PopupMenuAdapter() {

			@Override
			public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
				removeSpotsAction.setEnabled(table.getSelectedRowCount() > 0);
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
		if (table.getSelectedRowCount() > 0) {
			this.removeSelectedSpotsFromSamples();
			this.tableModel.fireTableDataChanged();
		}
	}

	private void removeSelectedSpotsFromSamples() {
		IntStream.of(table.getSelectedRows()).boxed()
			.map(table::convertRowIndexToModel)
			.sorted(Collections.reverseOrder(Integer::compareTo))
			.forEach(i -> {
				removeSpotFromSamples(tableModel.getValueAt(i, 0).toString());
			});
	}

	private void removeSpotFromSamples(String spot) {
		this.samples.forEach(s -> {
			s.removeSpot(spot);
		});
	}
}