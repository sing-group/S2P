/*
 * #%L
 * S2P Core
 * %%
 * Copyright (C) 2016 - 2017 José Luis Capelo Martínez, José Eduardo Araújo, Florentino Fdez-Riverola, Miguel
 * 			Reboiro-Jato, Hugo López-Fernández, and Daniel Glez-Peña
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */
package es.uvigo.ei.sing.s2p.core.entities.quantification;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
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

	public Set<String> getProteins() {
		return getProteins(stream());
	}

	public static final Set<String> getProteins(
		Stream<QuantificationSample> samples
	) {
		return samples
				.map(QuantificationSample::getReplicates)
				.flatMap(Collection::stream)
				.map(QuantificationReplicate::getProteins)
				.flatMap(Collection::stream)
				.map(ProteinQuantification::getProtein)
				.collect(Collectors.toSet());
	}
}
