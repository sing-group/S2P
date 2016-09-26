package es.uvigo.ei.sing.s2p.gui.spots.comparison;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.util.Arrays;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableColumnModelEvent;
import javax.swing.event.TableColumnModelListener;

import org.jdesktop.swingx.JXTable;
import org.jdesktop.swingx.renderer.DefaultTableRenderer;

import es.uvigo.ei.sing.s2p.core.entities.Condition;
import es.uvigo.ei.sing.s2p.core.entities.Sample;
import es.uvigo.ei.sing.s2p.gui.event.ProteinDataComparisonEvent;
import es.uvigo.ei.sing.s2p.gui.event.ProteinDataComparisonListener;
import es.uvigo.ei.sing.s2p.gui.table.CSVTable;

public class ProteinDataComparisonTable extends JPanel {
	private static final long serialVersionUID = 1L;
	private static final int FONT_SIZE = 14;
	
	private List<Condition> conditions;
	private JXTable table;
	private ProteinDataComparisonTableModel tableModel;
	private JPanel southPane;

	private ProteinDataComparisonInformationPanel comparisonInformationPanel;
	private ProteinDataComparisonFilteringPanel comparisonFilteringPanel;

	public ProteinDataComparisonTable(List<Condition> conditions) {
		this.conditions = conditions;
		this.initComponent();
	}

	private void initComponent() {
		this.initTable();
		
		this.setLayout(new BorderLayout());
		this.add(new JScrollPane(table), BorderLayout.CENTER);
		this.add(getSouthPanel(), BorderLayout.SOUTH);
	}

	private void initTable() {
		this.tableModel = new ProteinDataComparisonTableModel(this.conditions.get(0), this.conditions.get(1));
		this.table = new CSVTable(this.tableModel);
		this.table.setTableHeader(null);
		this.table.setColumnControlVisible(false);
		final ComparisonTableCellRenderer renderer = new ComparisonTableCellRenderer();
		this.table.setDefaultRenderer(Object.class, renderer);
		
		this.table.getSelectionModel().addListSelectionListener(new ProteinDataComparisonTableSelectionListener());
		this.table.getColumnModel().addColumnModelListener(new ProteinDataComparisonTableColumnModelListener());
	}
	
	private class ProteinDataComparisonTableSelectionListener
	implements ListSelectionListener {
		@Override
		public void valueChanged(ListSelectionEvent e) {
			if (!e.getValueIsAdjusting()) {
				if (isSingleValueSelected()) {
					fireSamplesSelectionEvent(
						table.getSelectedRow(), table.getSelectedColumn());
				} else {
					fireSampleSelectionClearedEvent();
				}
			}
		}
	}
	
	private class ProteinDataComparisonTableColumnModelListener 
	implements TableColumnModelListener {
		@Override
		public void columnSelectionChanged(ListSelectionEvent e) {
			if (!e.getValueIsAdjusting()) {
				if (isSingleValueSelected()) {
					fireSamplesSelectionEvent(
						table.getSelectedRow(), table.getSelectedColumn());
				} else {
					fireSampleSelectionClearedEvent();
				}
			}
		}
		
		@Override
		public void columnRemoved(TableColumnModelEvent e) {}
		
		@Override
		public void columnMoved(TableColumnModelEvent e) {}
		
		@Override
		public void columnMarginChanged(ChangeEvent e) {}
		
		@Override
		public void columnAdded(TableColumnModelEvent e) {}
	}
	
	private synchronized void fireSamplesSelectionEvent(
			int selectedRow, int selectedColumn
		) {
			final int colIndex = table.convertColumnIndexToModel(selectedColumn);
			final int rowIndex = table.convertRowIndexToModel(selectedRow);
			if(rowIndex > 0 && colIndex > 0) {
				Sample[] selectedSamples = this.tableModel.getSamplesAt(rowIndex, colIndex);
				fireSampleSelectionEvent(new ProteinDataComparisonEvent(
						selectedSamples,
						ProteinDataComparisonEvent.Type.SAMPLE_SELECTION));
			} else {
				fireSampleSelectionClearedEvent();
			}
	}
	
	private void fireSampleSelectionEvent(
			ProteinDataComparisonEvent proteinDataComparisonTableEvent
	) {
		Arrays.asList(getTableListeners()).stream()
			.forEach(l -> l.onSampleSelection(proteinDataComparisonTableEvent));
	}
	
	private void fireSampleSelectionClearedEvent() {
		fireSampleSelectionClearedEvent(new ProteinDataComparisonEvent(this, ProteinDataComparisonEvent.Type.SAMPLE_SELECTION_CLEARED));
	}
	
	private void fireSampleSelectionClearedEvent(
		ProteinDataComparisonEvent proteinDataComparisonTableEvent
	) {
		Arrays.asList(getTableListeners()).stream()
		.forEach(l -> l.onSampleSelectionCleared(proteinDataComparisonTableEvent));
	}

	private boolean isSingleValueSelected() {
		return table.getSelectedColumnCount() == 1
			&& table.getSelectedRowCount() == 1;
	}

	private Component getSouthPanel() {
		if(this.southPane == null) {
			this.southPane = new JPanel(new BorderLayout());
			this.southPane.add(getProteinDataInformationPanel(), BorderLayout.WEST);
			this.southPane.add(getProteinDataFilteringPanel(), BorderLayout.CENTER);
		}
		return this.southPane;
	}

	private Component getProteinDataFilteringPanel() {
		this.comparisonFilteringPanel = new ProteinDataComparisonFilteringPanel(this.conditions.get(0), this.conditions.get(1));
		return this.comparisonFilteringPanel;
	}

	private Component getProteinDataInformationPanel() {
		this.comparisonInformationPanel = new ProteinDataComparisonInformationPanel(this.conditions.get(0), this.conditions.get(1));
		return this.comparisonInformationPanel;
	}

	public void setComparison(int condition1, int condition2) {
		this.tableModel.setComparison(this.conditions.get(condition1), this.conditions.get(condition2));
		this.comparisonInformationPanel.setComparison(this.conditions.get(condition1), this.conditions.get(condition2));
		this.comparisonFilteringPanel.setComparison(this.conditions.get(condition1), this.conditions.get(condition2));
	}
	
	private class ComparisonTableCellRenderer extends DefaultTableRenderer {
		private static final long serialVersionUID = 1L;

		public Component getTableCellRendererComponent(JTable table,
				Object value, boolean isSelected, boolean hasFocus, int row,
				int column) {
			final int columnModel = table.convertColumnIndexToModel(column);
			final int rowModel = table.convertRowIndexToModel(row);

			final Component c = super.getTableCellRendererComponent(
					table, value, isSelected, hasFocus, row, column
				);
			
			
			Font font = c.getFont().deriveFont(Font.PLAIN, FONT_SIZE);
			Color background = c.getBackground();
			if(rowModel == 0 && columnModel == 0) {
				background = Color.WHITE;
			} else if (rowModel == 0 || columnModel == 0) {
				background = Color.LIGHT_GRAY;
				font = font.deriveFont(Font.BOLD);
			}
			
			if(value instanceof ProteinDataComparisonTableCell) {
				ProteinDataComparisonTableCell cell = (ProteinDataComparisonTableCell) value;
				if (c instanceof JLabel) {
					((JLabel) c).setText(cell.getCellValue());
					((JLabel) c).setToolTipText(cell.getCellTooltip());
				}
			}
			
			c.setFont(font);
			c.setBackground(background);
			return c;
		}
	}
	
	/**
	 * Adds the specified sample listener to receive sample events from this
	 * component. If listener {@code l} is  {@code null}, no exception is
	 * thrown and no action is performed.
	 * 
	 * @param l the sample listener.
	 */
	public void addTableListener(ProteinDataComparisonListener l) {
		this.listenerList.add(ProteinDataComparisonListener.class, l);
	}

	/**
	 * Removes the specified sample listener so that it no longer receives
	 * sample events from this component. This method performs no function, nor
	 * does it throw an exception, if the listener specified by the argument 
	 * was not previously added to this component. If listener {@code l} is 
	 * {@code null}, no exception is thrown and no action is performed.
	 * 
	 * @param l the sample listener.
	 */
	public void removeTableListener(ProteinDataComparisonListener l) {
		this.listenerList.remove(ProteinDataComparisonListener.class, l);
	}

	/**	
	 * Returns an array of all the sample listeners registered on this
	 * component.
	 * 
	 * @return all of this component's @{code SampleListener} or an empty
	 * array if no sample listeners are currently registered.
	 */
	public ProteinDataComparisonListener[] getTableListeners() {
		return this.listenerList.getListeners(ProteinDataComparisonListener.class);
	}
}
