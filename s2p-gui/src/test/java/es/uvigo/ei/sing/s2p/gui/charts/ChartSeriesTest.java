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
package es.uvigo.ei.sing.s2p.gui.charts;

import static org.sing_group.gc4s.demo.DemoUtils.showComponent;
import static es.uvigo.ei.sing.s2p.gui.TestUtils.setNimbusLookAndFeel;
import static java.util.Arrays.asList;

import java.awt.Color;
import java.awt.Dimension;
import java.io.IOException;
import java.util.List;

public class ChartSeriesTest {

	public static void main(String[] args) throws IOException {

		List<DataSeries> series = asList(
			new DataSeries("Condition A", Color.BLUE, asList(1d, 2d, 3d)),
			new DataSeries("Condition B", Color.RED, asList(1.1d, 1.2d, 1.3d))
		);
		ChartDataSeriesPanel chart = 
			new ChartDataSeriesPanel("Chart title", "x axis", "y axis", series);

		setNimbusLookAndFeel();
		showComponent(chart, new Dimension(500, 500));
	}
}
