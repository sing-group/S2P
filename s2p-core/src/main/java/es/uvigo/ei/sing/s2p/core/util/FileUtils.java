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

