package es.uvigo.ei.sing.s2p.core.io;

import static org.junit.Assert.*;
import static es.uvigo.ei.sing.s2p.core.resources.TestResources.MASCOT_PROJECT;
import static es.uvigo.ei.sing.s2p.core.resources.TestResources.MASCOT_PROJECT_FULL;

import java.io.IOException;
import java.util.List;

import org.junit.Test;

import es.uvigo.ei.sing.s2p.core.entities.MascotEntry;
import es.uvigo.ei.sing.s2p.core.io.MascotProjectLoader;

public class MascotProjectLoaderTest {
	
	private static final MascotEntry FIRST = new MascotEntry(
		"Uncharacterized protein KIAA1688 OS=Homo sapiens GN=KIAA1688 PE=1 SV=2", 
		"B2", 52, 52, 8, "K1688_HUMAN");

	@Test
	public void mascotProjectLoaderTest() throws IOException {
		List<MascotEntry> entries = MascotProjectLoader.load(MASCOT_PROJECT);
		
		assertEquals(1, entries.size());
		assertEquals(entries.get(0), FIRST);
	}
	
	@Test
	public void mascotProjectLoaderTest2() throws IOException {
		List<MascotEntry> entries =  
			MascotProjectLoader.load(MASCOT_PROJECT_FULL);
		
		assertEquals(1473, entries.size());
		assertEquals(entries.get(0), FIRST);
	}
}
