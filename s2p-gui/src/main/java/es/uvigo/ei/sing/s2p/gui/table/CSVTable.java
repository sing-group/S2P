package es.uvigo.ei.sing.s2p.gui.table;

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
 * 
 * @author Miguel Reboiro-Jato
 * @author Hugo López-Fernández
 *
 */
public class CSVTable extends JXTable {
	private static final long serialVersionUID = 1L;
	private CSVTransferHandler transferHandler;
	private Map<Integer, String> formats;
	private Map<Integer, TableCellRenderer> columnRenderers;

	public CSVTable() {
		super();
		this.configure();
	}

	public CSVTable(int numRows, int numColumns) {
		super(numRows, numColumns);
		this.configure();
	}

	public CSVTable(Object[][] rowData, Object[] columnNames) {
		super(rowData, columnNames);
		this.configure();
	}

	public CSVTable(TableModel dm, TableColumnModel cm, ListSelectionModel sm) {
		super(dm, cm, sm);
		this.configure();
	}

	public CSVTable(TableModel dm, TableColumnModel cm) {
		super(dm, cm);
		this.configure();
	}

	public CSVTable(TableModel dm) {
		super(dm);
		this.configure();
	}

	public CSVTable(Vector<?> rowData, Vector<?> columnNames) {
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
	
	public TableCellRenderer setColumnCellRenderer(int index, TableCellRenderer renderer) {
		return this.columnRenderers.put(index, renderer);
	}
	
	public TableCellRenderer getColumnCellRenderer(int index) {
		return this.columnRenderers.get(index);
	}
	
	@Override
	public TableCellRenderer getCellRenderer(int row, int column) {
		try {
			if (this.columnRenderers.containsKey(column)) {
				return this.columnRenderers.get(column);
			} else if (this.getValueAt(row, column) instanceof Float) {
				return this.getDefaultRenderer(Float.class);
			} else if (this.getValueAt(row, column) instanceof Double) {
				return this.getDefaultRenderer(Double.class);
			} else {
				return super.getCellRenderer(row, column);
			}
		} catch (Exception e) {
			return super.getCellRenderer(row, column);
		}
	}
	
	public void configure() {
		this.transferHandler = new CSVTransferHandler();
		this.formats = new HashMap<Integer, String>();
		this.columnRenderers = new HashMap<Integer, TableCellRenderer>();
		for (int i=0; i<this.getColumnCount(); i++) {
			this.formats.put(i, "%.4f");
		}
		
		this.setCellSelectionEnabled(true);
		this.setColumnControlVisible(true);
		this.setTransferHandler(this.transferHandler);
		this.setEditable(false);
		TableCellRenderer renderer = new DefaultTableCellRenderer() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public Component getTableCellRendererComponent(JTable table,
					Object value, boolean isSelected, boolean hasFocus,
					int row, int column) {
				Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
				if ((value instanceof Float || value instanceof Double) && component instanceof JLabel) {
					JLabel label = (JLabel) component;
					label.setHorizontalAlignment(SwingConstants.RIGHT);
					int modelColumn = table.convertColumnIndexToModel(column);
					label.setText(String.format(CSVTable.this.formats.get(modelColumn), value));///*String.format("%.4f", */BigDecimal.valueOf((Float) value).round(new MathContext(4, RoundingMode.HALF_UP)).toPlainString()/*.floatValue())*/);
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
