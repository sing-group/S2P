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
