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
