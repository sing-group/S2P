package es.uvigo.ei.sing.s2p.core.entities;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

public class SpotMascotIdentifications {

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
