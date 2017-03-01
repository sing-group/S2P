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
package es.uvigo.ei.sing.s2p.core.operations;

import static java.lang.Integer.valueOf;
import static java.util.stream.Collectors.toList;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import es.uvigo.ei.sing.s2p.core.entities.MascotEntry;
import es.uvigo.ei.sing.s2p.core.entities.MascotIdentifications;

public class MascotIdentificationsOperations {

	public static MascotIdentifications filter(
		MascotIdentifications mascotIdentifications, int mascotScore
	) {
		return new MascotIdentifications(
			mascotIdentifications.stream()
			.filter(e -> e.getMascotScore() >= mascotScore)
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

	public static MascotIdentifications removeDuplicatedProteinNames(
		MascotIdentifications identifications
	) {
		Map<String, MascotIdentifications> proteinNamesToIdentifications = 
			getProteinNamesMap(identifications);

		proteinNamesToIdentifications.values()
			.forEach(MascotIdentificationsOperations::sortByMascotScore);

		return new MascotIdentifications(
			proteinNamesToIdentifications.values().stream()
			.map(m -> m.get(0)).collect(toList())
		);
	}

	private static Map<String, MascotIdentifications> getProteinNamesMap(
		MascotIdentifications identifications
	) {
		Map<String, MascotIdentifications> proteinNamesToIdentifications =
			new HashMap<>();

		for(MascotEntry identification : identifications) {
			proteinNamesToIdentifications.putIfAbsent(
				identification.getTitle(), new MascotIdentifications());
			proteinNamesToIdentifications.get(identification.getTitle())
				.add(identification);
		}

		return proteinNamesToIdentifications;
	}

	private static void sortByMascotScore(
			MascotIdentifications identifications
	) {
		Collections.sort(identifications, new Comparator<MascotEntry>() {

			@Override
			public int compare(MascotEntry o1, MascotEntry o2) {
				return valueOf(o2.getMascotScore())
						.compareTo(valueOf(o1.getMascotScore()));
			}
		});
	}
}
