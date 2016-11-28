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
package es.uvigo.ei.sing.s2p.gui.samespots;

import java.awt.Window;
import java.util.List;

import es.uvigo.ei.sing.hlfernandez.input.ItemSelectionDialog;
import es.uvigo.ei.sing.s2p.core.entities.Sample;

public class SampleMergeDialog extends ItemSelectionDialog<Sample> {
	private static final long serialVersionUID = 1L;

	protected SampleMergeDialog(Window parent, List<Sample> items) {
		super(parent, items, 2, (Sample s) -> s.getName(), false);
	}
	
	@Override
	public String getTitle() {
		return "Sample selection";
	}
	
	@Override
	protected String getDescription() {
		return "This dialog allows you to select two different samples and "
			+ "merge them into one.";
	}
}
