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

import static es.uvigo.ei.sing.commons.csv.io.CsvReader.CsvReaderBuilder.newCsvReaderBuilder;
import static java.util.stream.Collectors.toList;
import static java.util.stream.IntStream.range;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

import es.uvigo.ei.sing.commons.csv.entities.CsvData;
import es.uvigo.ei.sing.commons.csv.entities.CsvEntry;
import es.uvigo.ei.sing.commons.csv.entities.CsvFormat;
import es.uvigo.ei.sing.s2p.core.entities.Pair;
import es.uvigo.ei.sing.s2p.core.entities.Range;
import es.uvigo.ei.sing.s2p.core.entities.SameSpotsThrehold;
import es.uvigo.ei.sing.s2p.core.entities.Sample;


public class SameSpotsCsvFileLoader {

	public static final SameSpotsThrehold DEFAULT_CSV_THRESHOLD =
		new SameSpotsThrehold(1, 0);

	private static final int INDEX_SPOT 	= 0;
	private static final int INDEX_P 		= 1;
	private static final int INDEX_FOLD 	= 2;

	public static Pair<Sample, Sample> load(File file,
		SameSpotsThrehold threshold, CsvFormat csvFormat
	) throws IOException {
		CsvData data = 	newCsvReaderBuilder()
						.withFormat(csvFormat).withHeader(false)
						.build().read(file);

		return parseSamples(data, threshold);
	}

	private static Pair<Sample, Sample> parseSamples(CsvData data,
		SameSpotsThrehold threshold
	) throws IOException {
		List<Integer> normalizedValuesColumns =
			extractNormalizedColumns(data.remove(0));

		Pair<Sample, Sample> samples =
			extractSamples(data, threshold, normalizedValuesColumns);

		return samples;
	}

	private static List<Integer> extractNormalizedColumns(CsvEntry entry) {
		return 	range(
					entry.indexOf("Normalized Volume"),
					entry.indexOf("Volume")
				).boxed().collect(toList());
	}

	private static Pair<Sample, Sample> extractSamples(CsvData data,
		SameSpotsThrehold threshold, List<Integer> normalizedValuesColumns
	) throws IOException {
		CsvEntry sampleNamesEntry = data.remove(0);

		Pair<String, String> sampleNames =
			extractSampleNames(sampleNamesEntry, normalizedValuesColumns);

		Pair<Range, Range> sampleColumns =
			sampleColumns(sampleNamesEntry, sampleNames, normalizedValuesColumns);

		Pair<Map<String, Double>, Map<String, Double>>	sampleValues =
			extractSampleValues(data, threshold, sampleColumns, normalizedValuesColumns);

		return new Pair<Sample, Sample>(
			new Sample(sampleNames.getFirst(), sampleValues.getFirst()),
			new Sample(sampleNames.getSecond(), sampleValues.getSecond())
		);
	}

	private static Pair<Range, Range> sampleColumns(
		CsvEntry sampleNamesEntry, Pair<String, String> sampleNames,
		List<Integer> normalizedValuesColumns
	) {
		int firstSampleStart = sampleNamesEntry.indexOf(sampleNames.getFirst());
		int firstSampleEnd = sampleNamesEntry.indexOf(sampleNames.getSecond());

		int secondSampleStart = firstSampleEnd;
		int secondSampleEnd =
			normalizedValuesColumns.get(normalizedValuesColumns.size()-1);

		return new Pair<>(
			new Range(firstSampleStart, firstSampleEnd),
			new Range(secondSampleStart, secondSampleEnd)
		);
	}

	private static Pair<String, String> extractSampleNames(CsvEntry entry,
		List<Integer> samplesColumns
	) {
		List<String> sampleNames = 	samplesColumns.stream()
									.map(entry::get)
									.distinct()
									.filter(s -> s.length() > 0)
									.collect(toList());

		return new Pair<String, String>(sampleNames.get(0), sampleNames.get(1));
	}


	private static Pair<Map<String, Double>, Map<String, Double>> extractSampleValues(
		CsvData data, SameSpotsThrehold threshold,
		Pair<Range, Range> sampleColumns,
		List<Integer> normalizedValuesColumns
	) throws IOException {
		data.remove(0);

		Map<String, Double> sample1 = new HashMap<String, Double>();
		Map<String, Double> sample2 = new HashMap<String, Double>();
		CsvFormat format = data.getFormat();
		try {
			for(CsvEntry e : data) {
				String spot = e.get(INDEX_SPOT);
				double p 	= format.asDouble(e.get(INDEX_P));
				double fold = format.asDouble(e.get(INDEX_FOLD));
				double s1 =
					averageSampleValue(sampleColumns.getFirst(), format, e);
				double s2 =
					averageSampleValue(sampleColumns.getSecond(), format, e);

				if(p <= threshold.getP() && fold >= threshold.getFold()) {
					sample1.put(spot, s1);
					sample2.put(spot, s2);
				}
			}
		} catch(ParseException e) {
			throw new IOException(e);
		}

		return new Pair<>(sample1, sample2);
	}

	private static double averageSampleValue(Range sampleColumns,
		CsvFormat format, CsvEntry e) throws ParseException
	{
		List<String> sampleFields = e.subList(
			sampleColumns.getStart().intValue(),
			sampleColumns.getEnd().intValue()
		);

		DescriptiveStatistics sampleValues = new DescriptiveStatistics();
		for (String field : sampleFields) {
			sampleValues.addValue(format.asDouble(field));
		}

		return sampleValues.getMean();
	}
}
