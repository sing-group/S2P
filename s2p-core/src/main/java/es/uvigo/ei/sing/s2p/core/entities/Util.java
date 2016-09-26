package es.uvigo.ei.sing.s2p.core.entities;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Util {
	public static Set<String> getProteins(List<Sample> samples) {
		return 	samples.stream()
				.map(Sample::getSpots)
				.flatMap(Collection::stream)
				.collect(Collectors.toSet());
	}
}
