package es.uvigo.ei.sing.s2p.gui.charts.spots;

import java.awt.Color;

import es.uvigo.ei.sing.s2p.core.entities.SpotSummary;

public class ChartSpotSummary {

	private SpotSummary summary;
	private String condition;
	private Color color;

	public ChartSpotSummary(SpotSummary summary, String condition, Color color) {
		this.summary = summary;
		this.condition = condition;
		this.color = color;
	}
	
	public SpotSummary getSummary() {
		return summary;
	}
	
	public Color getColor() {
		return color;
	}
	
	public String getCondition() {
		return condition;
	}
}
