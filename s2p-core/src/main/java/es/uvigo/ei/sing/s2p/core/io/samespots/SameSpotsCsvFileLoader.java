package es.uvigo.ei.sing.s2p.core.io.samespots;

import static es.uvigo.ei.sing.commons.csv.io.CsvReader.CsvReaderBuilder.newCsvReaderBuilder;
import static java.util.stream.Collectors.toList;
import static java.util.stream.IntStream.range;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
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
		DecimalFormat formater = data.getFormat().getDecimalFormatter();
		try {
			for(CsvEntry e : data) {
				String spot = e.get(INDEX_SPOT);
				double p 	= formater.parse(e.get(INDEX_P)).doubleValue();
				double fold = formater.parse(e.get(INDEX_FOLD)).doubleValue();
				double s1 =
					averageSampleValue(sampleColumns.getFirst(), formater, e);
				double s2 =
					averageSampleValue(sampleColumns.getSecond(), formater, e);
				
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
		DecimalFormat formater, CsvEntry e) throws ParseException 
	{
		List<String> sample1Fields = e.subList(
			sampleColumns.getStart().intValue(),
			sampleColumns.getEnd().intValue()
		);

		DescriptiveStatistics sample1Values = new DescriptiveStatistics();
		for (String s : sample1Fields) {
			sample1Values.addValue(formater.parse(s).doubleValue());
		}

		return sample1Values.getMean();
	}
}
