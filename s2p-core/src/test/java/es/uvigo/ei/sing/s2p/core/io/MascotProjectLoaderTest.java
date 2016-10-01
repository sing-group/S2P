package es.uvigo.ei.sing.s2p.core.io;

import static es.uvigo.ei.sing.s2p.core.resources.TestResources.MASCOT_PROJECT;
import static es.uvigo.ei.sing.s2p.core.resources.TestResources.MASCOT_PROJECT_FULL;
import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Test;

import es.uvigo.ei.sing.s2p.core.entities.MascotEntry;
import es.uvigo.ei.sing.s2p.core.entities.MascotIdentifications;

public class MascotProjectLoaderTest {
	
	private static final MascotEntry FIRST = new MascotEntry(
		"Uncharacterized protein KIAA1688", "B2", 52, 52, 8, 
		122236.00d, "50ppm_BladderCancer", 7.80d, "K1688_HUMAN"
	);

	@Test
	public void mascotProjectLoaderTest() throws IOException {
		MascotIdentifications entries = MascotProjectLoader.load(MASCOT_PROJECT);
		
		assertEquals(1, entries.size());
		assertEquals(entries.get(0), FIRST);
	}
	
	@Test
	public void mascotProjectLoaderTest2() throws IOException {
		MascotIdentifications entries =  
			MascotProjectLoader.load(MASCOT_PROJECT_FULL);
		
		assertEquals(1473, entries.size());
		assertEquals(entries.get(0), FIRST);
	}
	
	@Test
	public void mascotProjectLoaderTest3() throws IOException {
		MascotIdentifications entries =  
			MascotProjectLoader.load(MASCOT_PROJECT_FULL, 500, false);
		
		assertEquals(2, entries.size());
	}
	
	@Test
	public void mascotProjectLoaderTest4() throws IOException {
		MascotIdentifications entries =  
			MascotProjectLoader.load(MASCOT_PROJECT_FULL, 500, true);
		
		assertEquals(1, entries.size());
	}
}
