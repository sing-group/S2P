package es.uvigo.ei.sing.s2p.core.io.samespots;

import static es.uvigo.ei.sing.s2p.core.entities.Util.getProteins;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import es.uvigo.ei.sing.s2p.core.entities.Sample;
import es.uvigo.ei.sing.s2p.core.io.csv.CsvFormat;

public class SameSpotsCsvWriter {

	public static void write(File tempFile, List<Sample> samples,
		CsvFormat csvFormat
	) throws IOException {
		write(tempFile, samples, csvFormat, new HashMap<>());
	}
	
	public static void write(File tempFile, List<Sample> samples,
		CsvFormat csvFormat, Map<Sample,String> samplesConditions
	) throws IOException {
		Collector<CharSequence, ?, String> columnJoining = 
			Collectors.joining(csvFormat.getColumnSeparator());
		
		StringBuilder sb = new StringBuilder();
		
		if(!samplesConditions.isEmpty()) {
			sb
				.append(csvFormat.getColumnSeparator())
				.append(sampleConditions(samples, samplesConditions, columnJoining))
				.append(csvFormat.getLineBreak());
		}
		
		sb
			.append(csvFormat.getColumnSeparator())
			.append(sampleNames(samples, columnJoining))
			.append(csvFormat.getLineBreak());
		
		getProteins(samples).stream().sorted().forEach(p -> {
			sb
				.append(proteinValues(p, samples, columnJoining, csvFormat))
				.append(csvFormat.getLineBreak());
		});
		
		try (Writer writer = new BufferedWriter(new OutputStreamWriter(
			new FileOutputStream(tempFile), "UTF-8"))
		) {
			writer.write(sb.toString());
		}
	}

	private static String sampleNames(List<Sample> samples,
		Collector<CharSequence, ?, String> columnJoining
	) {
		return samples.stream().map(Sample::getName).collect(columnJoining);
	}

	private static String sampleConditions(List<Sample> samples,
		Map<Sample, String> sampleConditions,
		Collector<CharSequence, ?, String> columnJoining
	) {
		return samples.stream().map(sampleConditions::get)
				.collect(columnJoining);
	}
	
	private static String proteinValues(String spot, List<Sample> samples,
		Collector<CharSequence, ?, String> columnJoining,
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
		return proteinValues.stream().collect(columnJoining);
	}
}
