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
