package es.uvigo.ei.sing.s2p.gui.charts;

import java.awt.Color;
import java.util.List;

public class DataSeries {

	private String label;
	private Color color;
	private List<Double> data;

	public DataSeries(String label, Color color, List<Double> data) {
		this.label = label;
		this.color = color;
		this.data = data;
	}

	public String getLabel() {
		return label;
	}

	public Color getColor() {
		return color;
	}

	public List<Double> getData() {
		return data;
	}
}
