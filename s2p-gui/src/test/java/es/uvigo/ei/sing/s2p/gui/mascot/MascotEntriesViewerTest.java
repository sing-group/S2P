package es.uvigo.ei.sing.s2p.gui.mascot;

import static es.uvigo.ei.sing.s2p.core.resources.TestResources.MASCOT_PROJECT_FULL;
import static es.uvigo.ei.sing.s2p.gui.TestUtils.setNimbusLookAndFeel;
import static es.uvigo.ei.sing.s2p.gui.TestUtils.showComponent;

import java.io.IOException;

import javax.swing.JFrame;

import es.uvigo.ei.sing.s2p.core.entities.MascotIdentifications;
import es.uvigo.ei.sing.s2p.core.io.MascotProjectLoader;

public class MascotEntriesViewerTest {
	
	public static void main(String[] args) throws IOException {
		MascotIdentifications entries =  
			MascotProjectLoader.load(MASCOT_PROJECT_FULL, 56, true);
		
		setNimbusLookAndFeel();
		showComponent(new MascotIdentificationsViewer(entries), JFrame.MAXIMIZED_BOTH);
	}
}
