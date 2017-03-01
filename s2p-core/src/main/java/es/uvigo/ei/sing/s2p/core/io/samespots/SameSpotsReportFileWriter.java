package es.uvigo.ei.sing.s2p.core.io.samespots;

import static es.uvigo.ei.sing.s2p.core.operations.MascotIdentificationsOperations.removeDuplicatedProteinNames;
import static es.uvigo.ei.sing.s2p.core.util.FileUtils.getAvailableFileAtDirectory;
import static es.uvigo.ei.sing.s2p.core.util.FileUtils.removeExtension;
import static org.jsoup.Jsoup.parseBodyFragment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Function;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import es.uvigo.ei.sing.s2p.core.entities.MascotEntry;
import es.uvigo.ei.sing.s2p.core.entities.MascotIdentifications;

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
		Map<String, MascotIdentifications> identifications, File reportDirectory,
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
		Map<String, MascotIdentifications> identifications, File reportFile,
		File outputFile
	) throws IOException {
		writeReportFile(identifications, reportFile, 
			new SameSpotsReportFileWriterConfiguration(), outputFile);
	}
	
	public static void writeReportFile(
		Map<String, MascotIdentifications> identifications, File reportFile,
		SameSpotsReportFileWriterConfiguration configuration,
		File outputFile
	) throws IOException {
		StringBuilder reportSb = new StringBuilder();
		parseReportFile(reportFile, identifications, reportSb, configuration);
		Files.write(outputFile.toPath(), reportSb.toString().getBytes());
	}

	private static void parseReportFile(File reportFile,
		Map<String, MascotIdentifications> identifications, 
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
		Map<String, MascotIdentifications> identifications, String spot,
		StringBuilder reportSb, 
		SameSpotsReportFileWriterConfiguration configuration
	) {
		Document spotRow = parseBodyFragment(TABLE + spot + TABLE_CLOSE);
		Elements tds = spotRow.select("td");
	
		MascotIdentifications spotIdentifications = identifications
			.getOrDefault(tds.get(0).html(), new MascotIdentifications());

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
			.append(configuration.isIncludeMascotScore() ? 
					"Mascot score = "+ sI.getMascotScore() 
					: "")
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

	private static String spotToRow(String spot) {
		return TR + spot + TR_CLOSE + NEW_LINE;
	}

	private static String parseIndividualSpotsReports(
		String individualSpotsHtml,
		Map<String, MascotIdentifications> identifications,
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
					|| identifications.containsKey(spot)
			) {
				individualSpotsSB
					.append(div.toString().replaceAll(NEW_LINE, ""));
			}
		});
		return individualSpotsSB.toString();
	}
}