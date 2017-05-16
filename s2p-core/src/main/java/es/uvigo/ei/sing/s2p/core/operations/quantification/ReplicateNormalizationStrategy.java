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
package es.uvigo.ei.sing.s2p.core.operations.quantification;

import static es.uvigo.ei.sing.s2p.core.operations.quantification.NormalizationUtils.normalizeReplicate;
import static java.util.stream.Collectors.toList;

import java.util.List;

import es.uvigo.ei.sing.s2p.core.entities.quantification.QuantificationReplicate;
import es.uvigo.ei.sing.s2p.core.entities.quantification.QuantificationSample;

public class ReplicateNormalizationStrategy implements NormalizationStrategy {

	@Override
	public List<QuantificationSample> normalize(
		List<QuantificationSample> samples, NormalizationFactor factor
	) {
		return samples.stream()
				.map(s -> normalizeSample(s, factor))
				.collect(toList());
	}

	private static final QuantificationSample normalizeSample(
		QuantificationSample sample, NormalizationFactor factor
	) {
		if(sample.getCondition().isPresent()) {
			return 	new QuantificationSample(
						sample.getName(),
						sample.getCondition().get(),
						sample.getReplicates().stream()
							.map(r -> normalize(r, factor))
							.collect(toList()),
						sample.getProteinMass()
					);
		} else {
			return 	new QuantificationSample(
					sample.getName(),
					sample.getReplicates().stream()
						.map(r -> normalize(r, factor))
						.collect(toList()),
					sample.getProteinMass()
				);
		}
	}
	
	private static final QuantificationReplicate normalize(
	QuantificationReplicate replicate, NormalizationFactor factor
	) {
		double totalProteinValue = factor.getNormalizationFactor(
			NormalizationUtils.getProteinValues(replicate));

		return normalizeReplicate(replicate, totalProteinValue);
	}
}
