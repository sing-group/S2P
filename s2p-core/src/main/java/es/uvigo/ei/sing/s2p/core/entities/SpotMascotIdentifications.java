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

import java.util.Collection;
import java.util.Map;
import java.util.Observable;
import java.util.Set;

public class SpotMascotIdentifications extends Observable {

	private Map<String, MascotIdentifications> spotToIdentifications;

	public SpotMascotIdentifications(SpotMascotIdentifications data) {
		this(data.spotToIdentifications);
	}

	public SpotMascotIdentifications(
		Map<String, MascotIdentifications> spotToIdentifications
	) {
		this.spotToIdentifications = spotToIdentifications;
	}

	public MascotIdentifications get(String spot) {
		return spotToIdentifications.getOrDefault(spot, new MascotIdentifications());
	}

	public Set<String> getSpots() {
		return this.spotToIdentifications.keySet();
	}

	public Collection<MascotIdentifications> getMascotIdentifications() {
		return this.spotToIdentifications.values();
	}

	public boolean containsSpot(String spot) {
		return this.spotToIdentifications.containsKey(spot);
	}

	public boolean removeIdentification(String spot,
		MascotEntry identification
	) {
		if (this.spotToIdentifications.containsKey(spot)) {
			MascotIdentifications identifications =
				this.spotToIdentifications.get(spot);
			if (identifications.remove(identification)) {
				this.setChanged();
				this.notifyObservers();
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	public void merge(SpotMascotIdentifications spotIdentifications) {
		spotIdentifications.spotToIdentifications.entrySet().forEach(entry -> {
			this.spotToIdentifications.putIfAbsent(entry.getKey(), new MascotIdentifications());
			this.spotToIdentifications.get(entry.getKey()).addAll(entry.getValue());
		});
		this.setChanged();
		this.notifyObservers();
	}

	@Override
	public boolean equals(Object aThat) {
		if(aThat == null) {
			return false;
		}
		if(!(aThat instanceof SpotMascotIdentifications)) {
			return false;
		}
		SpotMascotIdentifications that = (SpotMascotIdentifications) aThat;
		
		return this.spotToIdentifications.equals(that.spotToIdentifications);
	}
}
