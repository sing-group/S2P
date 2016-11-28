/*
 * #%L
 * S2P Core
 * %%
 * Copyright (C) 2016 José Luis Capelo Martínez, José Eduardo Araújo, Florentino Fdez-Riverola, Miguel
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

	public static enum Positions {
		LETTERS("A, B, C, ..."),
		NUMBERS("1, 2, 3, ...");

		private String description;

		Positions(String description) {
			this.description = description;
		}

		@Override
		public String toString() {
			return description;
		}

		public List<String> generatePositions(int length) {
			if(this.equals(LETTERS)) {
				return 	range(0, length).boxed()
							.map(MaldiPlate::str).collect(toList());
			} else {
				return 	rangeClosed(1, length).boxed()
							.map(String::valueOf).collect(toList());
			}
		}
	};

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

	public MaldiPlate(int rows, int cols, Positions rowsPositions,
		Positions columnsPositions
	) {
		this.initData(
			requireStrictPositive(rows, "Number of rows must be greater than 0"), 
			requireStrictPositive(cols, "Number of columns must be greater than 0"),
			rowsPositions, columnsPositions
		);
	}

	private void initData(int rows, int cols, Positions rowsPositions,
		Positions columnsPositions
	) {
		this.rowNames = rowsPositions.generatePositions(rows);
		this.colNames = columnsPositions.generatePositions(cols);

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
