package es.uvigo.ei.sing.s2p.core.operations;

import static java.util.stream.Collectors.toList;

import es.uvigo.ei.sing.s2p.core.entities.MascotIdentifications;

public class MascotIdentificationsOperations {

	public static MascotIdentifications filter(
		MascotIdentifications mascotIdentifications, int mascotScore
	) {
		return new MascotIdentifications(
			mascotIdentifications.stream()
			.filter(e -> e.getMascotScore() > mascotScore)
			.collect(toList())
		);
	}
	
	public static MascotIdentifications removeDuplicates(
		MascotIdentifications mascotIdentifications
	) {
		return new MascotIdentifications(
			mascotIdentifications.stream().distinct().collect(toList())
		);
	}
}
