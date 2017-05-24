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
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import es.uvigo.ei.sing.s2p.core.entities.Pair;
import es.uvigo.ei.sing.s2p.core.entities.SameSpotsThrehold;
import es.uvigo.ei.sing.s2p.core.entities.Sample;

public abstract class AbstractSameSpotsDirectoryLoader {

	public List<Sample> load(File directory, SameSpotsThrehold threshold)
		throws IOException 
	{
		List<Sample> samples = new LinkedList<Sample>();

		for(File f : listFiles(directory)){
			Pair<Sample, Sample> pair = loadFile(f, threshold);
			addSample(pair.getFirst(), samples);
			addSample(pair.getSecond(), samples);
		}
		
		Collections.sort(samples, (o1, o2) -> {
			return o1.getName().compareTo(o2.getName());
		});
		
		return samples;
	}
	
	protected abstract Pair<Sample, Sample> loadFile(File file, SameSpotsThrehold threshold) throws IOException;
	protected abstract List<File> listFiles(File directory);

	private static void addSample(Sample sample, List<Sample> samples) {
		Optional<Sample> sample1Present = findByName(samples, sample.getName());
		if(sample1Present.isPresent()) {
			sample1Present.get().getSpotValues().putAll(sample.getSpotValues());
		} else {
			samples.add(sample);
		}
	}

	public static Optional<Sample> findByName(List<Sample> samples, String name) {
		return samples.stream().filter(s -> s.getName().equals(name)).findFirst();
	}
}
