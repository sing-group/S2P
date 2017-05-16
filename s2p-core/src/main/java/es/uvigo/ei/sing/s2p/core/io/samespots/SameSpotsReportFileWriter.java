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

import static es.uvigo.ei.sing.s2p.core.entities.MascotEntry.LABEL_DIFFERENCE;
import static es.uvigo.ei.sing.s2p.core.entities.MascotEntry.LABEL_METHOD;
import static es.uvigo.ei.sing.s2p.core.entities.MascotEntry.LABEL_MS_COVERAGE;
import static es.uvigo.ei.sing.s2p.core.entities.MascotEntry.LABEL_PLATE_POSITION;
import static es.uvigo.ei.sing.s2p.core.entities.MascotEntry.LABEL_SCORE;
import static es.uvigo.ei.sing.s2p.core.entities.MascotEntry.LABEL_SOURCE;
import static es.uvigo.ei.sing.s2p.core.operations.MascotIdentificationsOperations.removeDuplicatedProteinNames;
import static es.uvigo.ei.sing.s2p.core.util.FileUtils.getAvailableFileAtDirectory;
import static es.uvigo.ei.sing.s2p.core.util.FileUtils.removeExtension;
import static org.jsoup.Jsoup.parseBodyFragment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Scanner;
import java.util.StringJoiner;
import java.util.function.Function;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import es.uvigo.ei.sing.s2p.core.entities.MascotEntry;
import es.uvigo.ei.sing.s2p.core.entities.MascotIdentifications;
import es.uvigo.ei.sing.s2p.core.entities.SpotMascotIdentifications;

public class SameSpotsReportFileWriter {

	private static final String TABLE_CLOSE = "</table>";
	private static final String TABLE = "<table>";
	private static final String TR = "<tr>";
	private static final String TBODY_CLOSE = "</tbody>";
	private static final String TBODY = "<tbody>";
	private static final String TR_CLOSE = "</tr>";
	private static final String CLASS_INFO = "<div class=\"information\">";
	private static final String END_FILE = "</div></body></html>";
	private static final String NEW_LINE = "\n";
	private static final String TD_ALIGN_RIGHT = "<td align=\"right\">";
	private static final String TD_ALIGN_LEFT = "<td align=\"left\">";
	private static final String TD_ALIGN_RIGHT_NO_WRAP = "<td align=\"right\" nowrap=\"true\">";
	private static final String TD_CLOSE = "</td>";
	private static final String TD = "<td>";
	private static final CharSequence SPOT_TABLE = "class=\"spotTable\"";
	private static final CharSequence SPOTS_ROW_DELIMITER = " class=\"prodigyClassg1";
	private static final Function<File, Boolean> HTM_FILE = (File f) -> {
		return f.getName().endsWith(".htm");
	};

	public static void writeReportDirectory(
		SpotMascotIdentifications identifications, File reportDirectory,
		SameSpotsReportFileWriterConfiguration configuration
	) throws IOException {
		for (File f : reportDirectory.listFiles(HTM_FILE::apply)) {
			File outputFile = getOutputFile(reportDirectory, configuration, f);
			writeReportFile(identifications, f, configuration, outputFile);
		}
	}

	private static File getOutputFile(File reportDirectory,
			SameSpotsReportFileWriterConfiguration configuration, File f
		) {
		File outputFile;
		if (configuration.isOverwriteOriginalReport()) {
			outputFile = f;
		} else {
			String name = removeExtension(f) + configuration.getReportSuffix();
			outputFile =
				getAvailableFileAtDirectory(reportDirectory, name, ".htm");
		}
		return outputFile;
	}
	
	public static void writeReportFile(
		SpotMascotIdentifications identifications, File reportFile,
		File outputFile
	) throws IOException {
		writeReportFile(identifications, reportFile, 
			new SameSpotsReportFileWriterConfiguration(), outputFile);
	}
	
	public static void writeReportFile(
		SpotMascotIdentifications identifications, File reportFile,
		SameSpotsReportFileWriterConfiguration configuration,
		File outputFile
	) throws IOException {
		StringBuilder reportSb = new StringBuilder();
		parseReportFile(reportFile, identifications, reportSb, configuration);
		Files.write(outputFile.toPath(), reportSb.toString().getBytes());
	}

	private static void parseReportFile(File reportFile,
		SpotMascotIdentifications identifications, 
		StringBuilder reportSb, 
		SameSpotsReportFileWriterConfiguration configuration
	) throws FileNotFoundException {
		Scanner scanner = new Scanner(reportFile);
		boolean isSpotTable = false;
		boolean isIndividualSpotsZone = false;
		StringBuilder subReportSb = new StringBuilder();
		
		while(scanner.hasNextLine()) {
			String nextLine = scanner.nextLine();

			if(isSpotTable && nextLine.contains(SPOTS_ROW_DELIMITER)) {
				String beginning = nextLine.substring(0, nextLine.indexOf(TBODY) + 7);
				reportSb.append(beginning);
				
				String spots = nextLine.substring(
					nextLine.indexOf(TBODY) + 7, nextLine.indexOf(TBODY_CLOSE));
				Arrays.asList(spots.split(TR_CLOSE)).stream()
					.map(spot -> spot.replace(TR, ""))
					.forEach(spot -> parseSport(
						identifications, spot, reportSb, configuration)
					);
				
				String ending = nextLine.substring(nextLine.indexOf(TBODY_CLOSE));
				subReportSb.append(ending);

				isSpotTable = false;
				isIndividualSpotsZone = true;
			} else {
				if (isIndividualSpotsZone) {
					subReportSb.append(nextLine).append(NEW_LINE);
				} else {
					reportSb.append(nextLine).append(NEW_LINE);
				}

				if (nextLine.contains(SPOT_TABLE) && !isIndividualSpotsZone) {
					isSpotTable = true;
				}
			}
		}
		scanner.close();

		String subReportString = subReportSb.toString();
		String spotsTableEnd = subReportString.substring(0,
			subReportString.indexOf(CLASS_INFO));
		String individualSpotsHtml = subReportString.substring(
			subReportString.indexOf(CLASS_INFO), subReportString.indexOf(END_FILE));
		
		String processedIndividualSpotsHtml = parseIndividualSpotsReports(
			individualSpotsHtml, identifications, configuration);
		
		reportSb
			.append(spotsTableEnd)
			.append(processedIndividualSpotsHtml)
			.append(END_FILE)
			.append(NEW_LINE);
	}
	
	private static void parseSport(
		SpotMascotIdentifications identifications, String spot,
		StringBuilder reportSb, 
		SameSpotsReportFileWriterConfiguration configuration
	) {
		Document spotRow = parseBodyFragment(TABLE + spot + TABLE_CLOSE);
		Elements tds = spotRow.select("td");
	
		MascotIdentifications spotIdentifications = identifications
			.get(tds.get(0).html());

		if(!configuration.isRemoveDuplicatedProteinNames()) {
			spotIdentifications =
				removeDuplicatedProteinNames(spotIdentifications);
		}

		if (spotIdentifications.isEmpty()) {
			if (configuration.isIncludeSpotsWithoutIdentifications()) {
				reportSb.append(spotToRow(spot));
			}
		} else {
			spotIdentifications.forEach(sI -> {
				appendSpot(tds, reportSb, sI, configuration);
			});
		}
	}
	
	private static void appendSpot(Elements tds, StringBuilder reportSb,
		MascotEntry sI, SameSpotsReportFileWriterConfiguration configuration
	) {
		reportSb
			.append(TR)
			.append(TD_ALIGN_RIGHT)
			.append(tds.get(0).html())
			.append(TD_CLOSE)
			.append(TD_ALIGN_RIGHT_NO_WRAP)
			.append(tds.get(1).html())
			.append(TD_CLOSE)
			.append(TD_ALIGN_RIGHT_NO_WRAP)
			.append(tds.get(2).html())
			.append(TD_CLOSE)
			.append(TD)
			.append(tds.get(3).html())
			.append(TD_CLOSE)
			.append(TD_ALIGN_LEFT)
			.append(notes(sI, configuration))
			.append(TD_CLOSE)
			.append(TD_ALIGN_RIGHT)
			.append(tds.get(4).html())
			.append(TD_CLOSE)
			.append(TD_ALIGN_RIGHT)
			.append(tds.get(5).html())
			.append(TD_CLOSE)
			.append(TD_ALIGN_RIGHT)
			.append(configuration.isIncludeAccession() ? sI.getAccession() : "")
			.append(TD_CLOSE)
			.append(TD_ALIGN_RIGHT)
			.append(configuration.isIncludeTitle() ? sI.getTitle() : "")
			.append(TD_CLOSE)
			.append(TD_ALIGN_RIGHT)
			.append(configuration.isIncludePiValue() ? sI.getpIValue() : "")
			.append(TD_CLOSE)
			.append(TD_ALIGN_RIGHT)
			.append(configuration.isIncludeProteinMw() ? sI.getProteinMW() : "")
			.append(TD_CLOSE)
			.append(TD_ALIGN_RIGHT)
			.append(TD_CLOSE);
	
		for(int i = 12; i < tds.size(); i++) {
			reportSb
				.append(TD_ALIGN_RIGHT_NO_WRAP)
				.append(tds.get(i).html())
				.append(TD_CLOSE);
		}
		reportSb.append(TR_CLOSE).append(NEW_LINE);
	}

	private static String notes(MascotEntry sI,
		SameSpotsReportFileWriterConfiguration configuration
	) {
		StringJoiner joiner = new StringJoiner("; ");
		if (configuration.isIncludeMascotScore()) {
			joiner.add(LABEL_SCORE + " = " + sI.getMascotScore());
		}
		if (configuration.isIncludeDifference()) {
			joiner.add(LABEL_DIFFERENCE + " = " + sI.getDifference());
		}
		if (configuration.isIncludeMsCoverage()) {
			joiner.add(LABEL_MS_COVERAGE + " = " + sI.getMsCoverage());
		}
		if (configuration.isIncludePlatePosition()) {
			joiner.add(LABEL_PLATE_POSITION + " = " + sI.getPlatePosition());
		}
		if (configuration.isIncludeMethod()) {
			joiner.add(LABEL_METHOD + " = " + sI.getMethod());
		}
		if (configuration.isIncludeSource()) {
			joiner.add(LABEL_SOURCE + " = " + sI.getSource().getName());
		}
		return joiner.toString();
	}

	private static String spotToRow(String spot) {
		return TR + spot + TR_CLOSE + NEW_LINE;
	}

	private static String parseIndividualSpotsReports(
		String individualSpotsHtml,
		SpotMascotIdentifications identifications,
		SameSpotsReportFileWriterConfiguration configuration
	) {
		StringBuilder individualSpotsSB = new StringBuilder();
		Document individualSpotsDocument =
			parseBodyFragment(individualSpotsHtml);
		Elements divs = individualSpotsDocument.select("div.information");

		divs.forEach(div -> {
			String spot = div.select("h2").get(0)
				.html().replace("Identifier ", "");
			if (configuration.isIncludeSpotsWithoutIdentifications()
					|| identifications.containsSpot(spot)
			) {
				individualSpotsSB
					.append(div.toString().replaceAll(NEW_LINE, ""));
			}
		});
		return individualSpotsSB.toString();
	}
}