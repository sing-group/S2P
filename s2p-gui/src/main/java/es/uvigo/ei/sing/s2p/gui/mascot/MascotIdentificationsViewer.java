package es.uvigo.ei.sing.s2p.gui.mascot;

import static es.uvigo.ei.sing.s2p.gui.UISettings.BG_COLOR;
import static javax.swing.BorderFactory.createEmptyBorder;

import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import es.uvigo.ei.sing.s2p.core.entities.MascotIdentifications;

public class MascotIdentificationsViewer extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private MascotIdentifications entries;

	private MascotEntryTable entriesTable;

	public MascotIdentificationsViewer(MascotIdentifications entries) {
		this.entries = entries;

		this.initComponent();
	}

	private void initComponent() {
		this.setLayout(new BorderLayout());
		this.setBackground(BG_COLOR);
		this.add(new JScrollPane(getEntriesTable()), BorderLayout.CENTER);
	}

	private Component getEntriesTable() {
		if (this.entriesTable == null) {
			this.entriesTable = new MascotEntryTable(this.entries);
			this.entriesTable.setOpaque(false);
			this.entriesTable.setBorder(createEmptyBorder(10, 10, 10, 10));
		}
		return this.entriesTable;
	}
}
