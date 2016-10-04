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
