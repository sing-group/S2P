package es.uvigo.ei.sing.s2p.core.entities;

import static java.util.stream.Collectors.toSet;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Util {
	public static Set<String> getProteins(List<Sample> samples) {
		return 	samples.stream()
				.map(Sample::getSpots)
				.flatMap(Collection::stream)
				.collect(Collectors.toSet());
	}

	public static Set<String> getConditionProteins(Condition condition,
		Map<String, MascotIdentifications> identifications
	) {
		return 	condition.getSpots().stream()
				.map(spot -> {
					return identifications.getOrDefault(spot, new MascotIdentifications());
				})
				.flatMap(Collection::stream)
				.collect(toSet()).stream()
				.map(MascotEntry::getTitle)
				.collect(toSet());
	}

	public static Set<String> getSampleProteins(Sample sample,
		Map<String, MascotIdentifications> identifications
	) {
		return 	sample.getSpots().stream()
				.map(spot -> {
					return identifications.getOrDefault(spot, new MascotIdentifications());
				})
				.flatMap(Collection::stream)
				.collect(toSet()).stream()
				.map(MascotEntry::getTitle)
				.collect(toSet());
	}
}
