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
