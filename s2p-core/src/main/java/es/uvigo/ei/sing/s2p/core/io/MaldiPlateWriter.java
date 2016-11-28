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
package es.uvigo.ei.sing.s2p.core.io;

import static es.uvigo.ei.sing.s2p.core.entities.MaldiPlateInformation.FIELD_DATE;
import static es.uvigo.ei.sing.s2p.core.entities.MaldiPlateInformation.FIELD_MALDI_TARGET;
import static es.uvigo.ei.sing.s2p.core.entities.MaldiPlateInformation.FIELD_MASCOT_EXPERIMENT;
import static es.uvigo.ei.sing.s2p.core.entities.MaldiPlateInformation.FIELD_SAMPLE_TYPE;
import static es.uvigo.ei.sing.s2p.core.entities.MaldiPlateInformation.FIELD_USER;
import static java.util.stream.IntStream.range;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import es.uvigo.ei.sing.s2p.core.entities.MaldiPlate;
import es.uvigo.ei.sing.s2p.core.entities.MaldiPlateInformation;

public class MaldiPlateWriter {

	private static final Font FONT = new Font(FontFamily.COURIER, 10);

	public static void toPdf(MaldiPlate plate, File f)
		throws FileNotFoundException, DocumentException 
	{
		Document document = new Document(PageSize.A3.rotate(), 10, 10, 10, 10);
		document.setMargins(50, 50, 50, 50);
		PdfWriter.getInstance(document, new FileOutputStream(f));
		document.open();
		document.add(getExperimentInfo(plate));
		document.add(getPdfTable(plate));
		document.close();
	}

	private static Element getPdfTable(MaldiPlate plate) {
		PdfPTable table = new PdfPTable(plate.getColNames().size() + 1);
		table.setWidthPercentage(100);
		addHeader(table, plate);
		addData(table, plate);
		
		return table;
	}

	private static void addHeader(PdfPTable table, MaldiPlate plate) {
        addHeaderCell(table, "");

        plate.getColNames().forEach(c -> {
        	addHeaderCell(table, c);
        });
        
        table.setHeaderRows(1);
	}

	private static void addHeaderCell(PdfPTable table, String c) {
		Font font = new Font(FONT);
        font.setColor(BaseColor.WHITE);
		PdfPCell cell = new PdfPCell(new Phrase(c, font));
        cell.setBackgroundColor(BaseColor.BLACK);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        table.addCell(cell);
	}
	
	private static void addData(PdfPTable table, MaldiPlate plate) {
		range(0, plate.getRowNames().size()).forEach(i -> addRow(plate, table, i));
	}

	private static void addRow(MaldiPlate plate, PdfPTable table, int row) {
		addHeaderCell(table, plate.getRowNames().get(row));
		
		range(0, plate.getColNames().size()).forEach(column -> {
			addCell(plate, table, row, column);
		});
	}

	private static void addCell(MaldiPlate plate, PdfPTable table, int row,
		int column
	) {
		String value = plate.getData()[row][column];
		
		table.addCell(
			new PdfPCell(new Phrase(value == null ? "" : value,	FONT))
		);
	}
	
	private static Element getExperimentInfo(MaldiPlate plate) {
		MaldiPlateInformation info = plate.getInfo();
		
		String maldiTarget = info.getMaldiTarget().isPresent() ? 
			info.getMaldiTarget().get().toString() : "";
		String sep = ": ";
		String fieldSep = "    ";
		
		StringBuilder infoSb = new StringBuilder();
		infoSb
			.append(FIELD_USER)
			.append(sep)
			.append(info.getUser())
			.append(fieldSep)
			.append(FIELD_DATE)
			.append(sep)
			.append(info.getDate())
			.append(fieldSep)
			.append(FIELD_SAMPLE_TYPE)
			.append(sep)
			.append(info.getSampleType())
			.append("\n")
			.append(FIELD_MALDI_TARGET)
			.append(sep)
			.append(maldiTarget)
			.append(fieldSep)
			.append(FIELD_MASCOT_EXPERIMENT)
			.append(sep)
			.append(info.getMascotExperiment());
		
		Paragraph toret = new Paragraph(infoSb.toString());
		toret.setSpacingAfter(10);
		
		return toret;
	}

	public static void toFile(MaldiPlate plate, File file) throws IOException {
		FileOutputStream fout = new FileOutputStream(file);
		ObjectOutputStream oos = new ObjectOutputStream(fout);
		oos.writeObject(plate);
		oos.close();
	}
}
