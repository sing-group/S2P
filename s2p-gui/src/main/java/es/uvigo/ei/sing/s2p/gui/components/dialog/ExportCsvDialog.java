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
package es.uvigo.ei.sing.s2p.gui.components.dialog;

import java.awt.Window;

import javax.swing.JFileChooser;

import es.uvigo.ei.sing.s2p.gui.util.CommonFileChooser;

/**
 * An extension of GC4S's {@link ExportCsvDialog} in order to return the common
 * file chooser configured in S2P.
 * 
 * @author Hugo López-Fernández
 *
 */
public class ExportCsvDialog
	extends org.sing_group.gc4s.dialog.ExportCsvDialog {
	private static final long serialVersionUID = 1L;

	public ExportCsvDialog(Window parent) {
		super(parent);
	}

	@Override
	protected JFileChooser getFileChooser() {
		return CommonFileChooser.getInstance().getFilechooser();
	}
}
