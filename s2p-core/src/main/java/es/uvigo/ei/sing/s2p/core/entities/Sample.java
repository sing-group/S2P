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
