package es.uvigo.ei.sing.s2p.core.entities;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Condition {

	private String name;
	private List<Sample> samples;
	
	public Condition(String name, List<Sample> samples) {
		this.name = name;
		this.samples = samples;
	}
	
	public String getName() {
		return name;
	}
	
	public List<Sample> getSamples() {
		return samples;
	}

	public Set<String> getSpots() {
		return 	getSamples().stream()
				.map(Sample::getSpots)
				.flatMap(Collection::stream)
				.collect(Collectors.toSet());
	}
}
