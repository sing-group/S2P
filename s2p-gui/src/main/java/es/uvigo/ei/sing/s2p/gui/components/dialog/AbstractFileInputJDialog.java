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

import org.sing_group.gc4s.dialog.AbstractInputJDialog;
import es.uvigo.ei.sing.s2p.gui.util.CommonFileChooser;

public abstract class AbstractFileInputJDialog extends AbstractInputJDialog {
	private static final long serialVersionUID = 1L;

	protected AbstractFileInputJDialog(Window parent) {
		super(parent);
	}
	
	/**
	 * Returns the dialog filechooser.
	 * 
	 * @return the dialog filechooser.
	 */
	protected JFileChooser getFileChooser() {
		return CommonFileChooser.getInstance().getFilechooser();
	}
}
