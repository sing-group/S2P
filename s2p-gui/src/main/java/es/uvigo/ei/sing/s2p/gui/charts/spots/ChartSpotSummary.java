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
