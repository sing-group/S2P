/*
 * #%L
 * S2P Core
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
package es.uvigo.ei.sing.s2p.core.entities;

import java.util.Map;
import java.util.Set;

public class Sample {

	private String name;
	private Map<String, Double> spotValues;

	public Sample(String name, Map<String, Double> spotValues) {
		this.name = name;
		this.spotValues = spotValues;
	}

	public String getName() {
		return name;
	}

	public Map<String, Double> getSpotValues() {
		return spotValues;
	}

	public Set<String> getSpots() {
		return getSpotValues().keySet();
	}

	public void setName(String name) {
		this.name = name;
	}

	public void removeSpot(String spot) {
		this.spotValues.remove(spot);
	}

	public void setSpotValues(Map<String, Double> newSpotValues) {
		this.spotValues.clear();
		this.spotValues.putAll(newSpotValues);
	}

	public void renameSpot(String oldSpotName, String newSpotName) {
		if (this.spotValues.containsKey(oldSpotName)) {
			this.spotValues.put(
				newSpotName, this.spotValues.remove(oldSpotName));
		}
	}

	@Override
	public boolean equals(Object aThat) {
		if (aThat == null) {
			return false;
		}
		if (!(aThat instanceof Sample)) {
			return false;
		}

		Sample that = (Sample) aThat;
		return 	this.name.equals(that.name) &&
				this.spotValues.equals(that.spotValues);
	}

}
