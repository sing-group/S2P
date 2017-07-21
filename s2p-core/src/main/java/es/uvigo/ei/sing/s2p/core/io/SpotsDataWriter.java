package es.uvigo.ei.sing.s2p.core.io;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import es.uvigo.ei.sing.commons.csv.entities.CsvFormat;
import es.uvigo.ei.sing.commons.csv.io.CsvWriter;
import es.uvigo.ei.sing.s2p.core.entities.MascotEntry;
import es.uvigo.ei.sing.s2p.core.entities.Sample;
import es.uvigo.ei.sing.s2p.core.entities.SpotMascotIdentifications;
import es.uvigo.ei.sing.s2p.core.entities.Util;

public class SpotsDataWriter {

	private static final int SAMPLES_OFFSET = 4;
	private static final MascotEntry EMPTY_MASCOT_ENTRY =
		new MascotEntry("", "", 0, 0, 0, Double.NaN, "", Double.NaN, "", new File(""));

	public static void writeSamplesWithIdentifications(List<Sample> samples,
		SpotMascotIdentifications identifications, CsvFormat csvFormat,
		File file
	) throws IOException {
		Object[][] data = getData(samples, identifications);

		csvFormat.setDecimalFormatterMaximumFractionDigits(Integer.MAX_VALUE);

		CsvWriter csvWriter = CsvWriter.of(csvFormat);
		csvWriter.write(data, file);
	}

	private static Object[][] getData(List<Sample> samples,
		SpotMascotIdentifications identifications
	) {
		int nCol = samples.size() + SAMPLES_OFFSET;
		List<String> spots = new LinkedList<>(Util.getProteins(samples));
		List<Object[]> rows = new LinkedList<>();

		Object[] header = getHeader(samples, nCol);
		rows.add(header);

		for (String spot : spots) {
			if (identifications.containsSpot(spot)) {
				for (MascotEntry identification : identifications.get(spot)) {
					Object[] currentRow =
						getRowData(samples, nCol, spot,	identification);
					rows.add(currentRow);
				}
			} else {
				Object[] currentRow =
					getRowData(samples, nCol, spot, EMPTY_MASCOT_ENTRY);
				rows.add(currentRow);
			}
		}

		Object[][] toret = new Object[rows.size()][nCol];
		int rowIndex = 0;
		for (Object[] row : rows) {
			toret[rowIndex++] = row;
		}

		return toret;
	}

	private static Object[] getHeader(List<Sample> samples, int nCol) {
		Object[] header = new Object[nCol];
		header[0] = "Spot";
		header[1] = "Protein";
		header[2] = "Mascot score";
		header[3] = "Accession";

		for (int i = 0; i < samples.size(); i++) {
			header[i + SAMPLES_OFFSET] = samples.get(i).getName();
		}

		return header;
	}

	private static Object[] getRowData(List<Sample> samples, int nCol,
		String spot, MascotEntry identification
	) {
		Object[] currentRow = new Object[nCol];
		currentRow[0] = spot;
		currentRow[1] = identification.getTitle();
		currentRow[2] = identification.getMascotScore();
		currentRow[3] = identification.getAccession();

		for (int i = 0; i < samples.size(); i++) {
			currentRow[i + SAMPLES_OFFSET] = samples.get(i).getSpotValues()
				.getOrDefault(spot, Double.NaN);
		}

		return currentRow;
	}
}
