package es.uvigo.ei.sing.s2p.core.operations.quantification;

import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.stream.DoubleStream;

import es.uvigo.ei.sing.s2p.core.entities.quantification.DefaultProteinQuantification;
import es.uvigo.ei.sing.s2p.core.entities.quantification.ProteinQuantification;
import es.uvigo.ei.sing.s2p.core.entities.quantification.QuantificationReplicate;
import es.uvigo.ei.sing.s2p.core.entities.quantification.QuantificationSample;

public class NormalizationUtils {

	public static final double getTotalProteinValue(
		List<QuantificationSample> samples
	) {
		return 	samples.stream()
				.mapToDouble(NormalizationUtils::getTotalProteinValue).sum();
	}

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
	
	public static final QuantificationSample normalizeSample(
		QuantificationSample sample, double normalizationValue
	) {
		if(sample.getCondition().isPresent()) {
		return 	new QuantificationSample(
					sample.getName(),
					sample.getCondition().get(),
					sample.getReplicates().stream()
						.map(r -> normalizeReplicate(r, normalizationValue))
						.collect(toList()),
					sample.getProteinMass()
				);
		} else {
			return 	new QuantificationSample(
					sample.getName(),
					sample.getReplicates().stream()
						.map(r -> normalizeReplicate(r, normalizationValue))
						.collect(toList()),
					sample.getProteinMass()
				);
		}
	}

	public static final QuantificationReplicate normalizeReplicate(
		QuantificationReplicate replicate, double normalizationValue
	) {
		return 	new QuantificationReplicate(
			replicate.getProteins().stream().map(pq -> {
				return new DefaultProteinQuantification(
					pq.getProtein(), pq.getQuantificationMethod(), pq
					.getValue(), pq.getValue() / normalizationValue
				);
			}).collect(toList())
		);
	}

	public static DoubleStream getProteinValues(
			List<QuantificationSample> samples) {
		return samples.stream().flatMapToDouble(NormalizationUtils::getProteinValues);
	}

	public static DoubleStream getProteinValues(
			QuantificationSample sample) {
		return sample.getReplicates().stream().flatMapToDouble(NormalizationUtils::getProteinValues);
	}
	
	public static DoubleStream getProteinValues(
			QuantificationReplicate replicate) {
		return replicate.getProteins().stream().mapToDouble(ProteinQuantification::getValue);
	}
}
