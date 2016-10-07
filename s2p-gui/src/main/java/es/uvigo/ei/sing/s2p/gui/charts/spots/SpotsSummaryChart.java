package es.uvigo.ei.sing.s2p.gui.charts.spots;

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

public class SpotsSummaryChart extends JPanel {
	private static final long serialVersionUID = 1L;
	
   	final DefaultBoxAndWhiskerCategoryDataset dataset 
   		= new DefaultBoxAndWhiskerCategoryDataset();

	private List<ChartSpotSummary> summaries;

	public SpotsSummaryChart(List<ChartSpotSummary> summaries) {
		this.summaries = summaries;

		this.initComponent();
	}

	private void initComponent() {
		this.setLayout(new BorderLayout());
		this.add(
			createChartPanel(createDataset(), 
			"Spot comparison", "Condition", "Expression value", true, 10)
		);
	}
	
	private DefaultBoxAndWhiskerCategoryDataset createDataset() {
		final DefaultBoxAndWhiskerCategoryDataset dataset = 
			new DefaultBoxAndWhiskerCategoryDataset();
		
		for (int i = 0; i < this.summaries.size(); i++) {
			dataset.add(
				this.summaries.get(i).getSummary().getSpotValues(), 
				this.summaries.get(i).getCondition(), 
				""
			);
		}

		return dataset;
	}

	public ChartPanel createChartPanel(
		DefaultBoxAndWhiskerCategoryDataset dataset, String chartTitle,
		String xAxisTitle, String yAxisTitle, boolean legend,
		double maximumBarWidth
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

		renderer.setMaximumBarWidth(maximumBarWidth);
		renderer.setFillBox(true);

		final ChartPanel chartPanel = new ChartPanel(boxAndWhiskerChart);

		chartPanel.setPreferredSize(new java.awt.Dimension(450, 270));
		return chartPanel;
	}

	private Map<Integer, Color> getSeriesColor() {
		Map<Integer, Color> seriesColor = new HashMap<Integer, Color>();
		for (int i = 0; i < this.summaries.size(); i++) {
			seriesColor.put(i, this.summaries.get(i).getColor());
		}
		return seriesColor;
	}
}
