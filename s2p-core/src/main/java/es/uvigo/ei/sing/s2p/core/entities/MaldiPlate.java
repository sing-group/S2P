package es.uvigo.ei.sing.s2p.core.entities;

import static es.uvigo.ei.sing.s2p.core.util.Checks.requireStrictPositive;
import static java.util.stream.Collectors.toList;
import static java.util.stream.IntStream.range;
import static java.util.stream.IntStream.rangeClosed;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.itextpdf.text.DocumentException;

import es.uvigo.ei.sing.commons.csv.entities.CsvData;
import es.uvigo.ei.sing.s2p.core.io.MaldiPlateWriter;

public class MaldiPlate implements Serializable {
	private static final long serialVersionUID = 1L;

	private String[][] data;
	private List<String> colNames;
	private List<String> rowNames;
	private MaldiPlateInformation info = new MaldiPlateInformation();

	public MaldiPlate(CsvData csvData) {
		this.colNames = csvData.remove(0);
		this.colNames.remove(0);

		this.rowNames = new LinkedList<>();
		csvData.forEach(e -> {
			this.rowNames.add(e.remove(0));
		});

		this.data = new String[this.rowNames.size()][this.colNames.size()];
		for (int i = 0; i < this.rowNames.size(); i++) {
			this.data[i] = csvData.get(i).toArray();
		}
	}

	public MaldiPlate(MaldiPlate plate) {
		this(plate.getRowNames(), plate.getColNames(), plate.getData(),
			plate.getInfo());
	}

	public MaldiPlate(List<String> rowNames, List<String> colNames,
		String[][] data, MaldiPlateInformation info
	) {
		this.rowNames = rowNames;
		this.colNames = colNames;
		this.data = data;
		this.info = info;
	}

	public MaldiPlate(int rows, int cols) {
		this.initData(
			requireStrictPositive(rows, "Number of rows must be greater than 0"), 
			requireStrictPositive(cols, "Number of columns must be greater than 0")
		);
	}

	private void initData(int rows, int cols) {
		this.rowNames = range(0, rows).boxed()
						.map(MaldiPlate::str).collect(toList());
		this.colNames = rangeClosed(1, cols).boxed()
						.map(String::valueOf).collect(toList());
		this.data = new String[rows][cols];
	}

	private static String str(int i) {
		return i < 0 ? "" : str((i / 26) - 1) + (char) (65 + i % 26);
	}

	public void addCalibrants() {
		int cal = 1;
		for (int i = 1; i < this.rowNames.size(); i = i + 3) {
			for (int j = 1; j < this.colNames.size(); j = j + 3) {
				this.data[i][j] = "Cal " + (cal++);
			}
		}
	}
	
	public String[][] getData() {
		return data;
	}
	
	public void setData(String[][] data) {
		this.data = data;
	}

	public List<String> getRowNames() {
		return rowNames;
	}
	
	public List<String> getColNames() {
		return colNames;
	}

	public MaldiPlateInformation getInfo() {
		return info;
	}

	public void setInfo(MaldiPlateInformation info) {
		this.info = info;
	}

	public Map<String, String> asMap() {
		Map<String, String> toret = new HashMap<String, String>();

		for (int i = 0; i < this.rowNames.size(); i++) {
			for (int j = 0; j < this.colNames.size(); j++) {
				String cellValue = this.data[i][j];
				if (cellValue != null && !cellValue.equals("")) {
					toret.put(cell(i, j), cellValue);
				}
			}
		}

		return toret;
	}

	private String cell(int row, int col) {
		return this.rowNames.get(row) + this.colNames.get(col);
	}

	public void toPdf(File file) throws FileNotFoundException, DocumentException {
		MaldiPlateWriter.toPdf(this, file);
	}

	public void toFile(File file) throws IOException {
		MaldiPlateWriter.toFile(this, file);
	}
}
