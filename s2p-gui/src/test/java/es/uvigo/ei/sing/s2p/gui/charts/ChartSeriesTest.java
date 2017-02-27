package es.uvigo.ei.sing.s2p.gui.charts;

import static es.uvigo.ei.sing.s2p.gui.TestUtils.setNimbusLookAndFeel;
import static es.uvigo.ei.sing.s2p.gui.TestUtils.showComponent;
import static java.util.Arrays.asList;

import java.awt.Color;
import java.io.IOException;
import java.util.List;

import javax.swing.JFrame;

public class ChartSeriesTest {

	public static void main(String[] args) throws IOException {

		List<DataSeries> series = asList(
			new DataSeries("Condition A", Color.BLUE, asList(1d, 2d, 3d)),
			new DataSeries("Condition B", Color.RED, asList(1.1d, 1.2d, 1.3d))
		);
		ChartDataSeriesPanel chart = 
			new ChartDataSeriesPanel("Chart title", "x axis", "y axis", series);

		setNimbusLookAndFeel();
		showComponent(chart, JFrame.MAXIMIZED_BOTH);
	}
}