/*
 * #%L
 * S2P Core
 * %%
 * Copyright (C) 2016 - 2017 José Luis Capelo Martínez, José Eduardo Araújo, Florentino Fdez-Riverola, Miguel
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
