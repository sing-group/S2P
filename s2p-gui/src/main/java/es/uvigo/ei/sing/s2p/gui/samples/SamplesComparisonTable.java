package es.uvigo.ei.sing.s2p.gui.samples;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.IntUnaryOperator;
import java.util.stream.Collectors;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

import org.jdesktop.swingx.JXTable;
import org.jdesktop.swingx.renderer.DefaultTableRenderer;

import es.uvigo.ei.sing.hlfernandez.visualization.JHeatMapModel;
import es.uvigo.ei.sing.s2p.core.entities.MascotIdentifications;
import es.uvigo.ei.sing.s2p.core.entities.Sample;
import es.uvigo.ei.sing.s2p.gui.table.CSVTable;
import es.uvigo.ei.sing.s2p.gui.table.TestRowFilter;
import es.uvigo.ei.sing.s2p.gui.table.Tester;

public class SamplesComparisonTable extends JPanel {
	private static final long serialVersionUID = 1L;

	private static final int FONT_SIZE = 14;
	
	private boolean showProteinIdentifications = false;
	
	private List<Sample> samples;
	private JXTable table;
	private SamplesComparisonTableModel tableModel;
	private ProteinPresenceTester proteinPresenceTester;
	private Map<Sample, Color> sampleColors;
	private Map<Sample, String> sampleLabels;
	private Optional<Map<String, MascotIdentifications>> mascotIdentifications = 
		Optional.empty();

	public SamplesComparisonTable(List<Sample> samples) {
		this(samples, new HashMap<>(), new HashMap<>());
	}
	
	public SamplesComparisonTable(List<Sample> samples,
		Map<Sample, Color> sampleColors, Map<Sample, String> sampleLabels
	) {
		this.samples = samples;
		this.sampleColors = sampleColors;
		this.sampleLabels = sampleLabels;

		this.initComponent();
	}

	private void initComponent() {
		this.initTable();
		
		this.setLayout(new BorderLayout());
		this.add(new JScrollPane(table), BorderLayout.CENTER);
	}

	private void initTable() {
		this.tableModel = new SamplesComparisonTableModel(this.samples);
		this.table = new CSVTable(this.tableModel);
		final SamplesComparisonTableCellRenderer renderer = 
			new SamplesComparisonTableCellRenderer();
		this.table.setDefaultRenderer(Double.class, renderer);
		this.table.setDefaultRenderer(Object.class, renderer);
		this.table.getTableHeader().setDefaultRenderer(
			new SamplesComparisonTableHeaderCellRenderer(
				this.table.getTableHeader().getDefaultRenderer()));
		this.table.setColumnControlVisible(true);	
		proteinPresenceTester = new ProteinPresenceTester(getProteins(this.samples));
		table.setRowFilter(new TestRowFilter<>(proteinPresenceTester, 0));
	}
	
	private static Set<String> getProteins(List<Sample> samples) {
		Set<String> all = new HashSet<String>();
		samples.forEach(s -> {
			all.addAll(s.getSpots());
		});
		return all;
	}

	private class ProteinPresenceTester implements Tester {

		private Set<String> visibleProteins;

		public ProteinPresenceTester(Set<String> visibleProteins) {
			this.visibleProteins = visibleProteins; 
		}
		
		@Override
		public boolean test(Object value) {
			return visibleProteins.contains(value);
		}
		
		public void setVisibleProteins(Set<String> visibleProteins) {
			this.visibleProteins = visibleProteins;
		}
	}
	
	private class SamplesComparisonTableCellRenderer extends DefaultTableRenderer {
		private static final long serialVersionUID = 1L;

		public Component getTableCellRendererComponent(JTable table,
				Object value, boolean isSelected, boolean hasFocus, int row,
				int column) {
			final int columnModel = table.convertColumnIndexToModel(column);
			
			final Component c = super.getTableCellRendererComponent(
					table, value, isSelected, hasFocus, row, column
				);
			
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
				spotLabel.setText(spotValue(spot));
				spotLabel.setToolTipText(spotTooltip(spot));
				if (mascotIdentifications.isPresent()) {
					if (mascotIdentifications.get().get(spot) != null) {
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

		private String spotValue(String spot) {
			StringBuilder spotValue = new StringBuilder(spot);
			if(showProteinIdentifications) {
				if(mascotIdentifications.isPresent()) {
					MascotIdentifications spotIdentifications = 
						mascotIdentifications.get().get(spot);
					if(spotIdentifications != null) {
						spotValue
							.append(" (")
							.append(spotIdentifications.get(0).getTitle())
							.append(")");
					}
				}
			}
			return spotValue.toString();
		}

		private String spotTooltip(String value) {
			if(mascotIdentifications.isPresent()) {
				MascotIdentifications spotIdentifications = 
					mascotIdentifications.get().get(value);
				if(spotIdentifications != null) {
					StringBuilder tooltip = new StringBuilder();
					tooltip
						.append(spotIdentifications.get(0).getTitle())
						.append(" (MS=")
						.append(spotIdentifications.get(0).getMascotScore())
						.append(")");
					if(spotIdentifications.size() > 1) {
						tooltip.append(" [...]");
					}
					return tooltip.toString();
				}
			}
			return value;
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
			
			c.setFont(c.getFont().deriveFont(Font.PLAIN, FONT_SIZE));
			
			if (c instanceof JLabel) {
				c.setFont(c.getFont().deriveFont(Font.BOLD, FONT_SIZE));
			}
			
			final int columnModel = table.convertColumnIndexToModel(column);
			
			if (columnModel > 0) {
				if(c instanceof JLabel) {
					((JLabel) c).setToolTipText(sampleLabels.get(samples.get(columnModel - 1)));
				}
			}
			
			return c;
		}
	}

	public void setVisibleProteins(Set<String> visibleProteins) {
		this.proteinPresenceTester.setVisibleProteins(visibleProteins);
		table.setRowFilter(new TestRowFilter<>(proteinPresenceTester, 0));
	}
	
	public void setShowProteinIdentifications(boolean show) {
		this.showProteinIdentifications = show;
		this.updateUI();
	}
	
	public void setMascotIdentifications(
		Map<String, MascotIdentifications> mascotIdentifications
	) {
		this.mascotIdentifications = Optional.ofNullable(mascotIdentifications);
		fireTableStructureChanged();
	}

	public void fireTableStructureChanged() {
		this.tableModel.fireTableStructureChanged();
	}
	
	public JHeatMapModel getVisibleData() {
		this.table.selectAll();
		int[] visibleColumns = this.table.getSelectedColumns();
		int[] visibleRows = this.table.getSelectedRows();
		this.table.clearSelection();

		List<Integer> visibileModelColumns = getVisibleModelIndexes(
			visibleColumns, this.table::convertColumnIndexToModel);
		List<Integer> visibileModelRows = getVisibleModelIndexes(
			visibleRows, this.table::convertRowIndexToModel);
		visibileModelColumns.remove(new Integer(0));
		
		double[][] toret = new double[visibileModelRows.size()][visibileModelColumns.size()];
		int currentRow = 0;
		for(int row : visibileModelRows) {
			int currentColumn = 0;
			for(int col : visibileModelColumns) {
				toret[currentRow][currentColumn++] = (double) this.tableModel.getValueAt(row, col);
			}
			currentRow++;
		}
		
		List<String> rowNames = new LinkedList<String>();
		visibileModelRows.forEach(r -> rowNames.add(this.tableModel.getValueAt(r, 0).toString()));
		List<String> colNames = visibileModelColumns.stream().map(this.tableModel::getColumnName).collect(Collectors.toList());
		
		if(showProteinIdentifications) {
			List<String> identifications = new LinkedList<String>();
			rowNames.forEach(spot -> {
				MascotIdentifications spotIdentifications = this.mascotIdentifications.get().get(spot);
				if(spotIdentifications  != null) {
					identifications.add(spotIdentifications.getFirst().getTitle());
				} else {
					identifications.add(spot);
				}
			});
			rowNames.clear();
			rowNames.addAll(identifications);
		}
		
		return new JHeatMapModel(toret, rowNames.toArray(new String[rowNames.size()]), colNames.toArray(new String[colNames.size()]));
	}

	private List<Integer> getVisibleModelIndexes(int[] visibleColumns, IntUnaryOperator mapper) {
		return Arrays.stream(visibleColumns).map(mapper).boxed().collect(Collectors.toList());
	}
}
