package es.uvigo.ei.sing.s2p.gui.mascot;

import static javax.swing.BorderFactory.createEmptyBorder;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Font;
import java.util.Map;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;

import org.jdesktop.swingx.renderer.DefaultTableRenderer;

import es.uvigo.ei.sing.hlfernandez.dialog.AbstractInputJDialog;
import es.uvigo.ei.sing.s2p.core.entities.MascotIdentifications;
import es.uvigo.ei.sing.s2p.gui.UISettings;
import es.uvigo.ei.sing.s2p.gui.table.CSVTable;

public class MascotIdentificationsSummaryDialog extends AbstractInputJDialog {
	private static final long serialVersionUID = 1L;
	
	private JPanel inputComponents;

	private CSVTable table;
	
	public MascotIdentificationsSummaryDialog(JFrame parent, Set<String> spots,
			Map<String, MascotIdentifications> spotIdentifications
		) {
		super(parent);
		
		this.table = new CSVTable(new MascotIdentificationsSummaryTableModel(spots, spotIdentifications));
		this.configureDialog();
	}
	

	private void configureDialog() {
		this.setResizable(true);
		this.getDescriptionPane().setVisible(false);
		this.cancelButton.setVisible(false);
		this.okButton.setEnabled(true);
		
		this.inputComponents.setBackground(UISettings.BG_COLOR);
		this.inputComponents.add(this.table, BorderLayout.CENTER);
		
		this.table.setDefaultRenderer(Object.class, new SummaryTableCellRenderer());
		
		this.table.packAll();
		this.pack();
	}

	@Override
	protected String getDialogTitle() {
		return "Summary";
	}

	@Override
	protected String getDescription() {
		return "";
	}

	@Override
	protected JPanel getInputComponentsPane() {
		this.inputComponents = new JPanel(new BorderLayout());
		this.inputComponents.setBorder(createEmptyBorder(10, 10, 10, 10));
		return this.inputComponents;
	}
	
	private class SummaryTableCellRenderer extends DefaultTableRenderer {
		private static final long serialVersionUID = 1L;

		public Component getTableCellRendererComponent(JTable table,
			Object value, boolean isSelected, boolean hasFocus, int row,
			int column
		) {
			
			final Component c = super.getTableCellRendererComponent(table,
				value, isSelected, hasFocus, row, column);

			c.setFont(c.getFont().deriveFont(Font.PLAIN, 14));
			
			return c;
		}
	}
}

