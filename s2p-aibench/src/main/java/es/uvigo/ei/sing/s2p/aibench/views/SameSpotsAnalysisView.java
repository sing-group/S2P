/*
 * #%L
 * S2P
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
package es.uvigo.ei.sing.s2p.aibench.views;

import java.io.File;
import java.io.IOException;

import javax.swing.JOptionPane;

import es.uvigo.ei.aibench.core.Core;
import es.uvigo.ei.sing.commons.csv.entities.CsvFormat;
import es.uvigo.ei.sing.s2p.aibench.datatypes.SameSpotsAnalysisDatatype;
import es.uvigo.ei.sing.s2p.aibench.datatypes.SpotsDataDatatype;
import es.uvigo.ei.sing.s2p.aibench.operations.LoadSpotsData;
import es.uvigo.ei.sing.s2p.gui.samespots.SameSpotsDatasetViewer;

public class SameSpotsAnalysisView extends SameSpotsDatasetViewer {
	private static final long serialVersionUID = 1L;

	public SameSpotsAnalysisView(SameSpotsAnalysisDatatype datatype) {
		super(datatype.getSamples());
	}
	
	@Override
	protected void exportToCsv(File file, CsvFormat csvFormat) throws IOException {
		try {
			super.exportToCsv(file, csvFormat);
			loadSpotsData(file, csvFormat);
		} catch (IOException e) {
			throw e;
		}
	}

	private void loadSpotsData(File file, CsvFormat csvFormat) {
		LoadSpotsData loadSpotsData = new LoadSpotsData();
		loadSpotsData.setCsvFile(file);
		loadSpotsData.setCsvFormat(csvFormat);
		
		SpotsDataDatatype spotsData;
		try {
			spotsData = loadSpotsData.loadData();
			Core.getInstance().getClipboard().putItem(spotsData, spotsData.getName());
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null,
				"There was an error while loading spots data from"
				+ file, "Error",
				JOptionPane.ERROR_MESSAGE
			);
		}
	}
}
