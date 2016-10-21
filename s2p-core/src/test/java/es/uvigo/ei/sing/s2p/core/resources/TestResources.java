package es.uvigo.ei.sing.s2p.core.resources;

import static es.uvigo.ei.sing.s2p.core.resources.ResourceLoader.loadResource;

import java.io.File;

import es.uvigo.ei.sing.commons.csv.entities.CsvFormat;

public class TestResources {

	public static final CsvFormat CSV_FORMAT = 
		new CsvFormat(",", '.', false, "\n");
	
	public static final CsvFormat SAMESPOTS_CSV_FORMAT =
		new CsvFormat(";", ',', true, "\n");
	
	public static final File TEST_SPOT_DATA = 
		loadResource("/test/spotsdata.csv");

	public static final File MALDI_PLATE_FILE = 
		loadResource("/test/maldiplate.csv");
	
	public static final CsvFormat MALDI_PLATE_FORMAT =
		new CsvFormat(",", '.', false, "\n");

	public static final File MASCOT_PROJECT = 
		loadResource("/test/MascotProject.BTR.HTM");

	public static final File MASCOT_CSV = 
		loadResource("/test/MascotIdentifications.csv");

	public static final File MASCOT_PROJECT_FULL =
		loadResource("/real/2016.07.21_BladderCancerProject.BTR.HTM");
	
	public static final File SAMESPOTS_FILE =
		loadResource("/real/SameSpots.htm");
	
	public static final File SAMESPOTS_DIRECTORY = 
		loadResource("/real/Samespots/Old");

	public static final File SAMESPOTS_CSV_FILE =
		loadResource("/real/SameSpots.csv");

	public static final File SAMESPOTS_CSV_DIRECTORY =
		loadResource("/real/Samespots/Old (Individual)");
}
