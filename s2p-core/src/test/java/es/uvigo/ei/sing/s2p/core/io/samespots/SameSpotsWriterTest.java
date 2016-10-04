package es.uvigo.ei.sing.s2p.core.io.samespots;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import es.uvigo.ei.sing.commons.csv.entities.CsvFormat;
import es.uvigo.ei.sing.s2p.core.entities.Sample;

public class SameSpotsWriterTest {
	
	private static final CsvFormat CSV_FORMAT = 
		new CsvFormat(",", '.', false, "\n");

	private static final List<Sample> SAMPLES = Arrays.asList(new Sample[]{
		new Sample("Sample 1", new HashMap<>()),
		new Sample("Sample 2", new HashMap<>()),
		new Sample("Sample 3", new HashMap<>())
	});
	
	static {
		SAMPLES.get(0).getSpotValues().put("140", 1.0);
		SAMPLES.get(0).getSpotValues().put("355", 1.0);
	}
	
	static {
		SAMPLES.get(1).getSpotValues().put("140", 2.0);
		SAMPLES.get(1).getSpotValues().put("400", 2.0);
	}
	
	static {
		SAMPLES.get(2).getSpotValues().put("255", 3.0);
		SAMPLES.get(2).getSpotValues().put("400", 3.0);
	}
	
	private static final Map<Sample, String> SAMPLES_CONDITIONS = 
		new HashMap<>();
	
	static {
		SAMPLES_CONDITIONS.put(SAMPLES.get(0), "Condition A");
		SAMPLES_CONDITIONS.put(SAMPLES.get(1), "Condition B");
		SAMPLES_CONDITIONS.put(SAMPLES.get(2), "Condition C");
	}
	
	@Test
	public void sameSpotsWriterTest() throws IOException {
		File tempFile = File.createTempFile("s2p-writertest", "s2p");
		tempFile.deleteOnExit();
		
		SameSpotsCsvWriter.write(tempFile, SAMPLES, CSV_FORMAT);
		
		List<String> file = Files.readAllLines(tempFile.toPath());
		assertEquals(Arrays.asList(new String[]{
				",Sample 1,Sample 2,Sample 3",
				"140,1.0000,2.0000,",
				"255,,,3.0000",
				"355,1.0000,,",
				"400,,2.0000,3.0000"
		}), file);
	}
	
	@Test
	public void sameSpotsWriterWithConditionsTest() throws IOException {
		File tempFile = File.createTempFile("s2p-writertest", "s2p");
		tempFile.deleteOnExit();
		
		SameSpotsCsvWriter.write(tempFile, SAMPLES, CSV_FORMAT, SAMPLES_CONDITIONS);
		
		List<String> file = Files.readAllLines(tempFile.toPath());
		assertEquals(Arrays.asList(new String[]{
				",Condition A,Condition B,Condition C",
				",Sample 1,Sample 2,Sample 3",
				"140,1.0000,2.0000,",
				"255,,,3.0000",
				"355,1.0000,,",
				"400,,2.0000,3.0000"
		}), file);
	}
}
