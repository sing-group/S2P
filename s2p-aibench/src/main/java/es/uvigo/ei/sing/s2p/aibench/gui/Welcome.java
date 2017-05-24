/*
 * #%L
 * S2P
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
package es.uvigo.ei.sing.s2p.aibench.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.util.ResourceBundle;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.itextpdf.text.Font;

import es.uvigo.ei.sing.s2p.gui.UISettings;

public class Welcome extends JPanel {
	private static final long serialVersionUID = 1L;
	private final static ImageIcon IMAGE_S2P =
		new ImageIcon(Welcome.class.getResource("/icons/s2p-icon.png"));

	private static final String S2P_DESCRIPTION = "<html><i>S2P</i> is an open source application for fast and easy processing of 2D-gel and MALDI-based mass spectrometry protein data.</html>";
	private static final String S2P_FEATURES = "<html><i>S2P</i> allows you to:   <ul><li>Import data from <i>Progenesis SameSpots</i> and save it as comma-separated values (CSV) files.</li>"
		+ "<li>Import, process and save Mascot identifications reports.</li>"
		+ "<li>Create MALDI plate designs and export them as PDF or CSV files.</li>"
		+ "<li>Link Mascot identifications to spots data.</li>"
		+ "<li>Visualize and explore spots data in different ways.</li></ul></html>";

	public Welcome() {
		this.initComponent();
	}

	private void initComponent() {
		this.setBackground(UISettings.BG_COLOR);
		this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		this.setLayout(new BorderLayout());
		this.add(getS2pPanelInfo(), BorderLayout.CENTER);
		this.setAlignmentX(CENTER_ALIGNMENT);
	}

	private Component getS2pPanelInfo() {
		String version = ResourceBundle.getBundle("s2p").getString("version");
		JPanel s2pPanelInfo = new JPanel();
		s2pPanelInfo.setLayout(new BoxLayout(s2pPanelInfo, BoxLayout.Y_AXIS));
		s2pPanelInfo.setOpaque(false);
		s2pPanelInfo.setAlignmentX(CENTER_ALIGNMENT);

		s2pPanelInfo.add(Box.createVerticalGlue());
		
		JLabel label = createLabel("<html>Welcome to <i>S2P</i> " + version + "!</html>");
		label.setFont(label.getFont().deriveFont(Font.BOLD, 15f));
		s2pPanelInfo.add(label);
		JLabel icon = createLabel("<html></html>");
		icon.setIcon(IMAGE_S2P);
		s2pPanelInfo.add(icon);
		s2pPanelInfo.add(createLabel(S2P_DESCRIPTION));
		s2pPanelInfo.add(createLabel(S2P_FEATURES));
		
		s2pPanelInfo.add(Box.createVerticalGlue());
		
		return s2pPanelInfo;
	}

	private JLabel createLabel(String versionString) {
		JLabel label = new JLabel(versionString);
		label.setBorder(BorderFactory.createEmptyBorder(15, 0, 5, 0));
		label.setFont(label.getFont().deriveFont(Font.NORMAL, 13f));
		label.setHorizontalAlignment(SwingConstants.CENTER);
		return label;
	}
}
