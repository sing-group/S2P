package es.uvigo.ei.sing.s2p.core.io.samespots;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import es.uvigo.ei.sing.s2p.core.entities.SameSpotsThrehold;
import es.uvigo.ei.sing.s2p.core.entities.Sample;

public class SameSpotsSampleRepeatedMeasuresFileLoader {
	public static List<Sample> load(File file, SameSpotsThrehold threshold,
		String spotsSuffix
	) throws IOException {
		return addSuffix(SameSpotsFileLoader.load(file, threshold), spotsSuffix);
	}

	private static List<Sample> addSuffix(List<Sample> load,
		String spotsSuffix
	) {
		return load.stream().map(s -> SameSpotsSampleRepeatedMeasuresFileLoader
			.addSuffix(s, spotsSuffix)).collect(Collectors.toList());
	}

	private static Sample addSuffix(Sample s, String spotsSuffix) {
		Map<String, Double> newSpotValues = new HashMap<>();
		Map<String, Double> oldSpotValues = s.getSpotValues();
		oldSpotValues.keySet().forEach(spot -> {
			newSpotValues.put(spot + spotsSuffix, oldSpotValues.get(spot));
		});
		s.setSpotValues(newSpotValues);
		return s;
	}
}
