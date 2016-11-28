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
package es.uvigo.ei.sing.s2p.gui.components;

import static com.googlecode.charts4j.Color.BLUE;
import static com.googlecode.charts4j.Color.RED;
import static com.googlecode.charts4j.Color.WHITE;
import static es.uvigo.ei.sing.s2p.gui.UISettings.BG_COLOR;

import java.awt.Component;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.googlecode.charts4j.Fills;
import com.googlecode.charts4j.GCharts;

public class VennDiagram extends JPanel {
	private static final long serialVersionUID = 1L;

	private String label1;
	private String label2;
	private double area1;
	private double area2;
	private double intersection;

	public VennDiagram(String label1, String label2, double area1, double area2,
		double areaintersection
	) {
		this.label1 = label1;
		this.label2 = label2;
		this.area1 = area1;
		this.area2 = area2;
		this.intersection = areaintersection;
		this.initComponent();
	}

	private void initComponent() {
		this.setBackground(BG_COLOR);
		this.add(getVennDiagram());
	}

	private Component getVennDiagram() {
		return getVennDiagram(label1, label2, area1, area2, intersection);
	}

	private static Component getVennDiagram(String label1, String label2,
		double area1, double area2, double areaintersection
	) {
		final com.googlecode.charts4j.VennDiagram chart = 
			GCharts.newVennDiagram(area1, area2, 0, areaintersection, 0, 0, 0);
		chart.setSize(400, 300);
		chart.setCircleLegends(label1, label2, "");
		chart.setCircleColors(BLUE, RED, WHITE);
		chart.setBackgroundFill(Fills.newSolidFill(WHITE));
      
		try {
			return new JLabel(
				new ImageIcon(ImageIO.read(new URL(chart.toURLString())))
			);
		} catch (Exception e) {
			return new JLabel("");
		}
	}

	public void setAreas(String label1, String label2, double area1,
		double area2, double intersection
	) {
		this.label1 = label1;
		this.label2 = label2;
		this.area1 = area1;
		this.area2 = area2;
		this.intersection = intersection;
		this.removeAll();
		this.add(getVennDiagram());
	}
}
