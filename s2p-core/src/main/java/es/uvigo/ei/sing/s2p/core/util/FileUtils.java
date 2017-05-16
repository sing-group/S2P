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
package es.uvigo.ei.sing.s2p.core.util;

import java.io.File;

public class FileUtils {
	/**
	 * Returns the name of {@code file} without the extension (e.g.: for a file
	 * with name {@code data.csv}, {@code data} is returned).
	 * 
	 * @param file a {@code File}.
	 * @return the name of the file without the extension.
	 */
	public static String removeExtension(File file) {
		String fname = file.getName();
		int pos = fname.lastIndexOf(".");
		if (pos > 0) {
			fname = fname.substring(0, pos);
		}
		return fname;
	}
	
	public static File getAvailableFileAtDirectory(File directory,
		String name, String extension
	) {
		File newFile = new File(directory, name + extension);
		int i = 1;
		while(newFile.exists()) {
			newFile = new File(directory, name + "_" + i + extension);
		}
		return newFile;
	}
}

