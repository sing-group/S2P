/*
 * #%L
 * S2P Core
 * %%
 * Copyright (C) 2016 José Luis Capelo Martínez, José Eduardo Araújo, Florentino Fdez-Riverola, Miguel
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
package es.uvigo.ei.sing.s2p.core.entities;

import static java.util.stream.Collectors.toSet;

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

	public static Set<String> getConditionProteins(Condition condition,
		SpotMascotIdentifications identifications
	) {
		return 	condition.getSpots().stream()
				.map(spot -> {
					return identifications.get(spot);
				})
				.flatMap(Collection::stream)
				.collect(toSet()).stream()
				.map(MascotEntry::getTitle)
				.collect(toSet());
	}

	public static Set<String> getSampleProteins(Sample sample,
		SpotMascotIdentifications identifications
	) {
		return 	sample.getSpots().stream()
				.map(spot -> {
					return identifications.get(spot);
				})
				.flatMap(Collection::stream)
				.collect(toSet()).stream()
				.map(MascotEntry::getTitle)
				.collect(toSet());
	}
}
