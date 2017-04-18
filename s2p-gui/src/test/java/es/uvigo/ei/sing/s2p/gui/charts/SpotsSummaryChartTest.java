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
package es.uvigo.ei.sing.s2p.gui.charts;

import static es.uvigo.ei.sing.s2p.gui.TestUtils.setNimbusLookAndFeel;
import static es.uvigo.ei.sing.hlfernandez.demo.DemoUtils.showComponent;

import java.awt.Color;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.swing.JFrame;

import es.uvigo.ei.sing.s2p.core.entities.SpotSummary;
import es.uvigo.ei.sing.s2p.gui.charts.spots.ChartSpotSummary;
import es.uvigo.ei.sing.s2p.gui.charts.spots.SpotsSummaryChart;

public class SpotsSummaryChartTest {

	public static void main(String[] args) throws IOException {
		
		List<ChartSpotSummary> summaries = Arrays.asList(
			new ChartSpotSummary[]{
				new ChartSpotSummary(
					new SpotSummary(
						5, 5, 3d, 1.58d, 
						Arrays.asList(new Double[]{1d, 2d, 3d, 4d, 5d})
					),
					"Condition A", Color.CYAN
				),
				new ChartSpotSummary(
					new SpotSummary(
						5, 5, 5.8d, 0.83d, 
						Arrays.asList(new Double[]{5d, 6d, 7d, 6d, 5d})
					),
					"Condition B", Color.RED
				),
			});

		SpotsSummaryChart chart = new SpotsSummaryChart("test", summaries);

		setNimbusLookAndFeel();
		showComponent(chart, JFrame.MAXIMIZED_BOTH);
	}
}
