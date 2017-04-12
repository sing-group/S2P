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
package es.uvigo.ei.sing.s2p.gui;

import java.awt.Dimension;

import javax.swing.JComponent;
import javax.swing.JFrame;

import es.uvigo.ei.sing.hlfernandez.demo.DemoUtils;

public class TestUtils {
	
	/**
	 * Shows a JFrame containing the specified <code>component</code>.
	 * 
	 * @param component JComponent to show
	 * @param size the frame size.
	 */
	public static final void showComponent(JComponent component, Dimension size) {
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(component);
		frame.pack();
		frame.setVisible(true);
		frame.setMinimumSize(size);
	}
	
	/**
	 * Shows a JFrame containing the specified <code>component</code>.
	 * 
	 * @param component
	 *            JComponent to show
	 */
	public static final void showComponent(JComponent component, int state) {
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(component);
		frame.pack();
		frame.setVisible(true);
		frame.setExtendedState(state);
	}
	
	public static final void setNimbusLookAndFeel() {
		DemoUtils.setNimbusKeepAlternateRowColor();
		DemoUtils.setNimbusLookAndFeel();
	}
}
