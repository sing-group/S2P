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
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import es.uvigo.ei.sing.s2p.core.entities.SameSpotsThrehold;
import es.uvigo.ei.sing.s2p.core.entities.Sample;

public class SameSpotsFileLoader {

	private static final int INDEX_SPOT 	= 0;
	private static final int INDEX_P 		= 1;
	private static final int INDEX_FOLD 	= 2;
	private static final int INDEX_FIRST_SAMPLE = 12;

	public static final SameSpotsThrehold DEFAULT_THRESHOLD = new SameSpotsThrehold();

	public static List<Sample> load(File file,
		SameSpotsThrehold threshold
	) throws IOException {
		Document doc = Jsoup.parse(file, "UTF-8");
		Element table = doc.select("div.spotTable").get(0).select("table").get(0);

		return parseSamples(table, threshold);
	}

	private static List<Sample> parseSamples(Element table,
		SameSpotsThrehold threshold
	) {
		List<String> sampleNames =
			extractSampleNames(table.select("thead").first());

		List<Map<String, Double>> sampleValues = extractSampleValues(
			table.select("tbody").first(), threshold, sampleNames.size());


		return createSamples(sampleNames, sampleValues);
	}

	private static List<Sample> createSamples(List<String> sampleNames,
		List<Map<String, Double>> sampleValues
	) {
		List<Sample> samples = new LinkedList<Sample>();
		for (int i = 0; i < sampleNames.size(); i++) {
			samples.add(new Sample(sampleNames.get(i), sampleValues.get(i)));
		}
		return samples;
	}

	private static List<String> extractSampleNames(Element head) {
		Elements ths = head.select("tr").get(1).select("th");
		return ths.stream().map(Element::html).collect(Collectors.toList());
	}

	private static List<Map<String, Double>> extractSampleValues(Element first,
		SameSpotsThrehold threshold, int samplesCount
	) {
		List<Map<String, Double>> sampleValues = new LinkedList<>();
		for (int i = 0; i < samplesCount; i++) {
			sampleValues.add(new HashMap<>());
		}
		first.select("tr").forEach(row -> {
			Elements tds = row.select("td");

			String spot = tds.get(INDEX_SPOT).html();
			double p 	= asDouble(tds.get(INDEX_P));
			double fold = asDouble(tds.get(INDEX_FOLD));

			if(p <= threshold.getP() && fold >= threshold.getFold()) {
				for(int i = 0; i < samplesCount; i++) {
					double sampleValue = asDouble((tds.get(i + INDEX_FIRST_SAMPLE)));
					sampleValues.get(i).put(spot, sampleValue);
				}
			}
		});

		return sampleValues;
	}

	private static double asDouble(Element s) {
		return Double.parseDouble(s.html().replaceAll(",", "."));
	}
}
