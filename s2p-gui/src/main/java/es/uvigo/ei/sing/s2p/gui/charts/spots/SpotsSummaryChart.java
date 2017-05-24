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
package es.uvigo.ei.sing.s2p.gui.charts.spots;

import java.util.LinkedList;
import java.util.List;

import org.jfree.data.statistics.DefaultBoxAndWhiskerCategoryDataset;

import es.uvigo.ei.sing.s2p.gui.charts.ChartDataSeriesPanel;
import es.uvigo.ei.sing.s2p.gui.charts.DataSeries;

public class SpotsSummaryChart extends ChartDataSeriesPanel {
	private static final long serialVersionUID = 1L;
	
   	final DefaultBoxAndWhiskerCategoryDataset dataset 
   		= new DefaultBoxAndWhiskerCategoryDataset();

	public SpotsSummaryChart(String spot, List<ChartSpotSummary> summaries) {
		this("Spot: " + spot, "", "Expression value", toSeries(summaries));
	}

	public SpotsSummaryChart(String title, String xAxisTitle, String yAxisTitle,
		List<DataSeries> series
	) {
		super(title, xAxisTitle, yAxisTitle, series);
	}

	private static List<DataSeries> toSeries(List<ChartSpotSummary> summaries) {
		List<DataSeries> series = new LinkedList<>();
		summaries.forEach(s -> {
			series.add(
				new DataSeries(
					s.getCondition(),
					s.getColor(),
					s.getSummary().getSpotValues()
				)
			);
		});
		return series;
	}
}
