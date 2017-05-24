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

import static es.uvigo.ei.sing.commons.csv.entities.CsvData.CsvDataBuilder.newCsvDataBuilder;
import static es.uvigo.ei.sing.s2p.core.entities.Util.getProteins;
import static java.util.stream.Collectors.toList;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import es.uvigo.ei.sing.commons.csv.entities.CsvData;
import es.uvigo.ei.sing.commons.csv.entities.CsvEntry;
import es.uvigo.ei.sing.commons.csv.entities.CsvFormat;
import es.uvigo.ei.sing.commons.csv.io.CsvWriter;
import es.uvigo.ei.sing.s2p.core.entities.Sample;

public class SameSpotsCsvWriter {

	public static void write(File tempFile, List<Sample> samples,
		CsvFormat csvFormat
	) throws IOException {
		write(tempFile, samples, csvFormat, new HashMap<>());
	}
	
	public static void write(File file, List<Sample> samples,
		CsvFormat csvFormat, Map<Sample,String> samplesConditions
	) throws IOException {
		
		CsvWriter csvWriter = CsvWriter.of(csvFormat);
		
		CsvData data = createData(samples, csvFormat, samplesConditions);
		
		csvWriter.write(data, file);
	}

	private static CsvData createData(List<Sample> samples,
		CsvFormat csvFormat, Map<Sample, String> samplesConditions
	) {
		List<CsvEntry> entries = new ArrayList<>();
		
		if(!samplesConditions.isEmpty()) {
			entries.add(sampleConditions(samples, samplesConditions));
		}
		
		entries.add(sampleNames(samples));
		
		getProteins(samples).stream().sorted().forEach(p -> {
			entries.add(proteinValues(p, samples, csvFormat));
		});

		CsvData data = createData(entries, csvFormat);
		return data;
	}

	private static CsvData createData(List<CsvEntry> entries, CsvFormat format) {
		return newCsvDataBuilder(format).withEntries(entries).build();
	}

	private static CsvEntry sampleNames(List<Sample> samples) {
		CsvEntry toret = csvEntry(samples.stream().map(Sample::getName));
		toret.add(0, "");
		return toret;
	}
	
	public static CsvEntry csvEntry(Stream<String> stream) {
		return new CsvEntry(stream.collect(toList()));
	}

	private static CsvEntry sampleConditions(List<Sample> samples,
		Map<Sample, String> sampleConditions
	) {
		CsvEntry toret = csvEntry(samples.stream().map(sampleConditions::get));
		toret.add(0, "");
		return toret;
	}
	
	private static CsvEntry proteinValues(String spot, List<Sample> samples,
		CsvFormat csvFormat
	) {
		List<String> proteinValues = new LinkedList<String>();
		proteinValues.add(spot);
		for(Sample s : samples) {
			Double value = s.getSpotValues().getOrDefault(spot, Double.NaN);
			if(Double.isNaN(value)) {
				proteinValues.add("");
			} else {
				proteinValues.add(csvFormat.getDecimalFormatter().format(value));
			}
		}
		return csvEntry(proteinValues.stream());
	}
}
