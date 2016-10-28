package es.uvigo.ei.sing.s2p.gui.charts;

import static es.uvigo.ei.sing.s2p.gui.TestUtils.setNimbusLookAndFeel;
import static es.uvigo.ei.sing.s2p.gui.TestUtils.showComponent;

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
