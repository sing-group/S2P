package es.uvigo.ei.sing.s2p.gui.mascot;

import static es.uvigo.ei.sing.s2p.core.resources.TestResources.MALDI_PLATE_FILE;
import static es.uvigo.ei.sing.s2p.core.resources.TestResources.MALDI_PLATE_FORMAT;
import static es.uvigo.ei.sing.s2p.core.resources.TestResources.MASCOT_PROJECT;
import static es.uvigo.ei.sing.s2p.gui.TestUtils.setNimbusLookAndFeel;
import static es.uvigo.ei.sing.s2p.gui.TestUtils.showComponent;

import java.io.IOException;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JScrollPane;

import es.uvigo.ei.sing.s2p.core.entities.MascotIdentifications;
import es.uvigo.ei.sing.s2p.core.entities.SpotMascotIdentifications;
import es.uvigo.ei.sing.s2p.core.io.MaldiPlateLoader;
import es.uvigo.ei.sing.s2p.core.io.MascotProjectLoader;
import es.uvigo.ei.sing.s2p.core.operations.SpotMascotEntryPositionJoiner;

public class SpotMascotIdentificationsViewerTest {
	
	public static void main(String[] args) throws IOException {
		setNimbusLookAndFeel();

		MascotIdentifications entries = MascotProjectLoader.load(MASCOT_PROJECT);
		Map<String, String> posToSpot = MaldiPlateLoader
			.importCsv(MALDI_PLATE_FILE, MALDI_PLATE_FORMAT).asMap();
		
		SpotMascotIdentifications join = 
			SpotMascotEntryPositionJoiner.join(posToSpot, entries);
	
		SpotMascotIdentificationsTable table =
			new SpotMascotIdentificationsTable(join.getSpots(), join);
		
		showComponent(new JScrollPane(table), JFrame.MAXIMIZED_BOTH);
	}
}
