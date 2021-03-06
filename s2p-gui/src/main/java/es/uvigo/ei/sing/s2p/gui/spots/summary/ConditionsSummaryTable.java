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
package es.uvigo.ei.sing.s2p.gui.spots.summary;

import static es.uvigo.ei.sing.s2p.gui.UISettings.FONT_SIZE;
import static es.uvigo.ei.sing.s2p.gui.UISettings.FONT_SIZE_HEADER;
import static es.uvigo.ei.sing.s2p.gui.spots.SpotUtils.spotTooltip;
import static es.uvigo.ei.sing.s2p.gui.spots.SpotUtils.spotValue;
import static es.uvigo.ei.sing.s2p.gui.spots.summary.ConditionsSummaryTableModel.SUMMARY_FIELDS;
import static java.util.stream.Collectors.toList;
import static java.util.stream.IntStream.range;
import static javax.swing.BorderFactory.createEmptyBorder;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Point;
import java.awt.Window;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.swing.Action;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

import org.jdesktop.swingx.renderer.DefaultTableRenderer;

import es.uvigo.ei.sing.s2p.core.entities.Condition;
import es.uvigo.ei.sing.s2p.core.entities.SpotMascotIdentifications;
import es.uvigo.ei.sing.s2p.core.entities.SpotSummary;
import es.uvigo.ei.sing.s2p.core.entities.SpotsData;
import es.uvigo.ei.sing.s2p.gui.UISettings;
import es.uvigo.ei.sing.s2p.gui.charts.spots.ChartSpotSummary;
import es.uvigo.ei.sing.s2p.gui.charts.spots.SpotsSummaryChartDialog;
import es.uvigo.ei.sing.s2p.gui.table.ExtendedCsvTable;
import es.uvigo.ei.sing.s2p.gui.table.TestRowFilter;
import es.uvigo.ei.sing.s2p.gui.table.spots.ConditionControlAction;
import es.uvigo.ei.sing.s2p.gui.table.spots.SpotPresenceTester;
import es.uvigo.ei.sing.s2p.gui.table.spots.SpotsCsvTable;
import es.uvigo.ei.sing.s2p.gui.util.ColorUtils;

public class ConditionsSummaryTable extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private ExtendedCsvTable table;
	private ConditionsSummaryTableModel tableModel;

	private SpotsData data;
	private Map<Condition, Color> conditionsColors;
	
	private SpotPresenceTester spotPresenceTester;
	private boolean showProteinIdentifications = false;
	private Optional<SpotMascotIdentifications> mascotIdentifications =
		Optional.empty();

	public ConditionsSummaryTable(SpotsData data) {
		this.data = data;

		this.initComponent();
	}

	private void initComponent() {
		this.initColors();

		this.setLayout(new BorderLayout());
		this.setBackground(UISettings.BG_COLOR);
		this.setBorder(createEmptyBorder(5, 5, 5, 5));

		this.add(new JScrollPane(getTable()), BorderLayout.CENTER);
	}

	private void initColors() {
		this.conditionsColors = new HashMap<Condition, Color>();
		for(Condition c : getConditions()) {
			Color conditionColor = 
				ColorUtils.getSoftColor(getConditions().indexOf(c));
			this.conditionsColors.put(c, conditionColor);
		}
	}

	private List<Condition> getConditions() {
		return data.getConditions();
	}

	private Component getTable() {
		if (this.table == null) {
			tableModel = new ConditionsSummaryTableModel(data);
			this.table = new SpotsCsvTable(tableModel, this::getSpotValue);
			ConditionsSummaryTableCellRenderer renderer = 
				new ConditionsSummaryTableCellRenderer();
			this.table.setDefaultRenderer(String.class, renderer);
			this.table.setDefaultRenderer(Double.class, renderer);
			this.table.getTableHeader().setDefaultRenderer(
				new ConditionsSummaryTableHeaderCellRenderer(
					this.table.getTableHeader().getDefaultRenderer())
			);
			this.table.getTableHeader().setReorderingAllowed(false);
			this.table.addExportToCsvAction();
			this.createHideConditionsActions();

			this.table.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					JTable table = (JTable) e.getSource();
					Point p = e.getPoint();
					int row = table.rowAtPoint(p);
					if (e.getClickCount() == 2 && row != -1) {
						viewSpotAtRow(table.convertRowIndexToModel(row));
					}
				}
			});

			spotPresenceTester = new SpotPresenceTester(getSpots());
			table.setRowFilter(new TestRowFilter<>(spotPresenceTester, 0));
		}
		return this.table;
	}

	private void createHideConditionsActions() {
		this.getConditions().forEach(c -> {
			this.table.addAction(getHideConditionAction(c));
		});
	}

	private Action getHideConditionAction(Condition c) {
		return new ConditionControlAction(c, this::setConditionVisibility);
	}

	private void setConditionVisibility(Condition c, boolean visible) {
		int conditionIndex = getConditions().indexOf(c) * SUMMARY_FIELDS + 1;
		List<Integer> columnIndices =
			range(conditionIndex, conditionIndex+SUMMARY_FIELDS)
			.boxed().collect(toList());

		this.table.setColumnsVisibility(columnIndices, visible);
	}

	private String getSpotValue(String spot) {
		return spotValue(spot,
				showProteinIdentifications, mascotIdentifications);
	}

	private Set<String> getSpots() {
		return new HashSet<>(data.getSpots());
	}

	private void viewSpotAtRow(int row) {
		String spot = this.tableModel.getSpotAt(row);
		List<ChartSpotSummary> summaries = new LinkedList<ChartSpotSummary>();
		this.data.getConditions().forEach(c -> {
			summaries.add(
				new ChartSpotSummary(
					this.tableModel.getSpotSummary(spot, c), 
					c.getName(), 
					conditionsColors.get(c))
			);
		});
		SpotsSummaryChartDialog dialog = 
			new SpotsSummaryChartDialog(getDialogParent(), spot, summaries);
		dialog.setVisible(true);
	}

	private Window getDialogParent() {
		return SwingUtilities.getWindowAncestor(this);
	}

	private class ConditionsSummaryTableCellRenderer 
		extends DefaultTableRenderer 
	{
		private static final long serialVersionUID = 1L;

		public Component getTableCellRendererComponent(JTable table,
			Object value, boolean isSelected, boolean hasFocus, int row,
			int column
		) {
			final int columnModel = table.convertColumnIndexToModel(column);
			
			final Component c = super.getTableCellRendererComponent(
				table, value, isSelected, hasFocus, row, column
			);
			
			Optional<Condition> condition = tableModel.getConditionAt(columnModel);
			if(condition.isPresent()) {
				c.setBackground(conditionsColors.get(condition.get()));
			}

			c.setFont(c.getFont().deriveFont(Font.PLAIN, FONT_SIZE));

			if(columnModel == 0) {
				String spot = value.toString();
				JLabel spotLabel = (JLabel) c;
				spotLabel.setText(spotValue(spot, showProteinIdentifications, mascotIdentifications));
				spotLabel.setToolTipText(spotTooltip(spot, mascotIdentifications));

				if (mascotIdentifications.isPresent()) {
					if (!mascotIdentifications.get().get(spot).isEmpty()) {
						if (!showProteinIdentifications) {
							spotLabel.setFont(c.getFont().deriveFont(Font.BOLD));
						}
					} else {
						spotLabel.setForeground(Color.RED);
					}
				}
			}
			
			return c;
		}
	}
	
	private class ConditionsSummaryTableHeaderCellRenderer extends
		DefaultTableCellRenderer 
	{
		private static final long serialVersionUID = 1L;
		private TableCellRenderer defaultRenderer;

		public ConditionsSummaryTableHeaderCellRenderer(
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
			
			final int columnModel = table.convertColumnIndexToModel(column);
			Optional<Condition> condition = tableModel.getConditionAt(columnModel);

			if (c instanceof JLabel) {
				c.setFont(c.getFont().deriveFont(Font.BOLD, FONT_SIZE_HEADER));
				if(condition.isPresent()) {
					((JLabel) c).setToolTipText(condition.get().getName());
				} else {
					((JLabel) c).setToolTipText("");
				}
			}
			
			return c;
		}
	}

	public void setVisibleSpots(Set<String> visibleSpots) {
		spotPresenceTester = new SpotPresenceTester(visibleSpots);
		table.setRowFilter(new TestRowFilter<>(spotPresenceTester, 0));
	}

	public void setMascotIdentifications(
		SpotMascotIdentifications mascotIdentifications
	) {
		this.mascotIdentifications = Optional.ofNullable(mascotIdentifications);
		fireTableStructureChanged();
	}

	public void fireTableStructureChanged() {
		this.tableModel.fireTableStructureChanged();
	}

	public void setShowProteinIdentifications(boolean show) {
		this.showProteinIdentifications = show;
		this.updateUI();
	}

	public SpotSummary getSpotSummary(String spot, Condition c) {
		return this.tableModel.getSpotSummary(spot, c);
	}

	public void removeMascotIdentifications() {
		this.mascotIdentifications = Optional.empty();
	}
}
