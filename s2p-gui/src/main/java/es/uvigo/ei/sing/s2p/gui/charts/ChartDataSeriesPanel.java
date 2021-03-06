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

import static es.uvigo.ei.sing.s2p.gui.UISettings.BG_COLOR;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.BoxAndWhiskerRenderer;
import org.jfree.data.statistics.DefaultBoxAndWhiskerCategoryDataset;

public class ChartDataSeriesPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
   	final DefaultBoxAndWhiskerCategoryDataset dataset 
   		= new DefaultBoxAndWhiskerCategoryDataset();

	private String title;
	private String xAxisTitle;
	private String yAxisTitle;
	private List<DataSeries> series;

	public ChartDataSeriesPanel(String title, String xAxisTitle,
		String yAxisTitle, List<DataSeries> series
	) {
		this.title = title;
		this.xAxisTitle = xAxisTitle;
		this.yAxisTitle = yAxisTitle;
		this.series = series;

		this.initComponent();
	}

	private void initComponent() {
		this.setLayout(new BorderLayout());
		this.add(
			createChartPanel(createDataset(), 
			this.title, xAxisTitle, yAxisTitle, true)
		);
	}
	
	private DefaultBoxAndWhiskerCategoryDataset createDataset() {
		final DefaultBoxAndWhiskerCategoryDataset dataset = 
			new DefaultBoxAndWhiskerCategoryDataset();
		
		for (int i = 0; i < this.series.size(); i++) {
			dataset.add(
				this.series.get(i).getData(), 
				this.series.get(i).getLabel(), 
				""
			);
		}

		return dataset;
	}

	public ChartPanel createChartPanel(
		DefaultBoxAndWhiskerCategoryDataset dataset, String chartTitle,
		String xAxisTitle, String yAxisTitle, boolean legend
	) {

		JFreeChart boxAndWhiskerChart = ChartFactory.createBoxAndWhiskerChart(
			chartTitle, xAxisTitle, yAxisTitle, dataset, legend);
		
		CategoryPlot pl = (CategoryPlot) boxAndWhiskerChart.getPlot();
		pl.setRangeGridlinePaint(Color.GRAY);
		pl.setBackgroundPaint(BG_COLOR);
		
		BoxAndWhiskerRenderer renderer = 
			(BoxAndWhiskerRenderer) pl.getRenderer();
		for(Entry<Integer, Color> entry : getSeriesColor().entrySet()){
			renderer.setSeriesPaint(entry.getKey(), entry.getValue());
		}

		renderer.setMaximumBarWidth(0.1);
		renderer.setFillBox(true);
		renderer.setMeanVisible(true);
		renderer.setMedianVisible(true);
		pl.setRangeGridlinesVisible(false);
		pl.setOutlineVisible(false);
		pl.getDomainAxis().setLabel(xAxisTitle);
		pl.getRangeAxis().setLabel(yAxisTitle);
		final ChartPanel chartPanel = new ChartPanel(boxAndWhiskerChart);

		chartPanel.setPreferredSize(new java.awt.Dimension(450, 270));
		return chartPanel;
	}

	private Map<Integer, Color> getSeriesColor() {
		Map<Integer, Color> seriesColor = new HashMap<Integer, Color>();
		for (int i = 0; i < this.series.size(); i++) {
			seriesColor.put(i, this.series.get(i).getColor());
		}
		return seriesColor;
	}
}
