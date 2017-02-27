package es.uvigo.ei.sing.s2p.core.operations.quantification;

import static java.util.stream.Collectors.toList;

import es.uvigo.ei.sing.s2p.core.entities.quantification.DefaultProteinQuantification;
import es.uvigo.ei.sing.s2p.core.entities.quantification.QuantificationReplicate;
import es.uvigo.ei.sing.s2p.core.entities.quantification.QuantificationSample;

public class NormalizationUtils {

	public static final double getTotalProteinValue(
		QuantificationSample sample
	) {
		return 	sample.getReplicates().stream()
				.mapToDouble(NormalizationUtils::getTotalProteinValue).sum();
	}

	public static final double getTotalProteinValue(
		QuantificationReplicate replicate
	) {
		return 	replicate.getProteins().stream()
				.mapToDouble(pq -> pq.getValue()).sum();
	}

	public static final QuantificationReplicate normalizeReplicate(
			QuantificationReplicate replicate, double normalizationValue) {
		return 	new QuantificationReplicate(
			replicate.getProteins().stream().map(pq -> {
				return new DefaultProteinQuantification(
					pq.getProtein(), pq.getQuantificationMethod(), pq
					.getValue(), pq.getValue() / normalizationValue
				);
			}).collect(toList())
		);
	}
}
