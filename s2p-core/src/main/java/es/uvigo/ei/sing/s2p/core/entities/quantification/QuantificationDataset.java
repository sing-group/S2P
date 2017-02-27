package es.uvigo.ei.sing.s2p.core.entities.quantification;

import static java.util.stream.Collectors.toList;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class QuantificationDataset extends LinkedList<QuantificationSample> {
	private static final long serialVersionUID = 1L;

	public QuantificationDataset(List<QuantificationSample> samples) {
		super(samples);
	}

	public List<QuantificationCondition> getConditions() {
		Map<String, List<QuantificationSample>> conditionSamples =
			getConditionSamples();
		return extractConditions(conditionSamples);
	}

	private Map<String, List<QuantificationSample>> getConditionSamples() {
		Map<String, List<QuantificationSample>> conditionSamples =
				new HashMap<>();
		stream()
		.filter(s -> s.getCondition().isPresent())
		.forEach(s -> {
			conditionSamples.putIfAbsent(s.getCondition().get(), new LinkedList<>());
			conditionSamples.get(s.getCondition().get()).add(s);
		});
		return conditionSamples;
	}

	private List<QuantificationCondition> extractConditions(
		Map<String, List<QuantificationSample>> conditionSamples
	) {
		List<QuantificationCondition> conditions = new LinkedList<>();
		conditionSamples.forEach((e, k) -> {
			conditions.add(new QuantificationCondition(e, k));
		});
		return conditions;
	}

	public List<String> getProteins() {
		return getProteins(stream());
	}
	
	public static final List<String> getProteins(
		Stream<QuantificationSample> samples
	) {
		return samples
				.map(QuantificationSample::getReplicates)
				.flatMap(Collection::stream)
				.map(QuantificationReplicate::getProteins)
				.flatMap(Collection::stream)
				.map(ProteinQuantification::getProtein)
				.collect(toList());
	}
}
