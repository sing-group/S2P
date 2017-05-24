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
package es.uvigo.ei.sing.s2p.gui.util;

import javax.swing.JFileChooser;

/**
 * 
 * A singleton class that provides a common file chooser to all the gui
 * components that request it. It is useful when integrating the application
 * into AIBench since the file chooser can be set using the
 * {@code setFileChooser} method.
 * 
 * @author hlfernandez
 *
 */
public class CommonFileChooser {
	private static final JFileChooser DEFAULT_FILECHOOSER = new JFileChooser(".");
	private static CommonFileChooser INSTANCE = null;
	private JFileChooser filechooser; 
    
	private CommonFileChooser() {
		filechooser = DEFAULT_FILECHOOSER;
	}
 
    private synchronized static void createInstance() {
        if (INSTANCE == null) { 
            INSTANCE = new CommonFileChooser();
        }
    }
 
    /**
     * Returns an instance of {@code CommonFileChooser}.
     * 
     * @return an instance of {@code CommonFileChooser}.
     */
	public static CommonFileChooser getInstance() {
		if (INSTANCE == null) {
			createInstance();
		}
		return INSTANCE;
	}

	/**
	 * Returns a {@code JFileChooser}.
	 * @return a {@code JFileChooser}.
	 */
	public JFileChooser getFilechooser() {
		return filechooser;
	}

	/**
	 * Establishes the {@code JFileChooser}.
	 * @param filechooser the {@code JFileChooser} to use.
	 */
	public void setFilechooser(JFileChooser filechooser) {
		this.filechooser = filechooser;
	}
}
