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
package es.uvigo.ei.sing.s2p.gui.mascot;

import java.util.Collections;
import java.util.stream.IntStream;

import javax.swing.Action;
import javax.swing.JPopupMenu;
import javax.swing.event.PopupMenuEvent;

import org.jdesktop.swingx.decorator.HighlighterFactory;

import es.uvigo.ei.sing.hlfernandez.event.PopupMenuAdapter;
import es.uvigo.ei.sing.hlfernandez.utilities.ExtendedAbstractAction;
import es.uvigo.ei.sing.s2p.core.entities.MascotIdentifications;
import es.uvigo.ei.sing.s2p.gui.table.ExtendedCsvTable;

public class MascotEntryTable extends ExtendedCsvTable {
	private static final long serialVersionUID = 1L;

	private MascotIdentifications entries;

	private ExtendedAbstractAction removeRowsAction;

	public MascotEntryTable(MascotIdentifications entries) {
		super(new MascotEntryTableModel(entries));
		
		this.entries = entries;
		this.initComponent();
	}

	private void initComponent() {
		this.setAutoCreateRowSorter(true);
		this.setColumVisibilityActionsEnabled(false);
		this.setColumnControlVisible(true);
		this.setCellSelectionEnabled(false);
		this.setColumnSelectionAllowed(false);
		this.setRowSelectionAllowed(true);
		this.addHighlighter(HighlighterFactory.createAlternateStriping());
		this.setComponentPopupMenu(getPopupMenu());
		this.addExportToCsvAction();
		this.getTableHeader().setReorderingAllowed(false);
	}

	private JPopupMenu getPopupMenu() {
		JPopupMenu menu = new JPopupMenu();
		menu.add(getRemoveSelectedRowsAction());
		menu.addPopupMenuListener(new PopupMenuAdapter() {
			
			@Override
			public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
				removeRowsAction.setEnabled(getSelectedRowCount() > 0);
			}
		});

		return menu;
	}
	
	private Action getRemoveSelectedRowsAction() {
		removeRowsAction = new ExtendedAbstractAction(
			"Remove selected rows", this::removeSelectedRows);
		removeRowsAction.setEnabled(false);

		return removeRowsAction;
	}

	private void removeSelectedRows() {
		if (this.getSelectedRowCount() > 0) {
			this.removeSelectedMascotEntries();
			((MascotEntryTableModel) this.getModel()).fireTableDataChanged();
		}
	}

	private void removeSelectedMascotEntries() {
		IntStream.of(this.getSelectedRows()).boxed()
			.map(this::convertRowIndexToModel)
			.sorted(Collections.reverseOrder(Integer::compareTo))
			.forEach(i -> {
				entries.remove((int) i);
			});
	}
}
