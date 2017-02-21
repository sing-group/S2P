package es.uvigo.ei.sing.s2p.aibench.util;

public class PortConfiguration {

	public static final String EXTRAS_CSV_FILES = "selectionMode=files, " +
		"filters=.*\\.csv|:Comma-separated values (CSV) files";
	
	public static final String EXTRAS_MPL_FILES = "selectionMode=files, " +
		"filters=.*\\.mpl|:Maldi plate (mpl) files";

	public static final String EXTRAS_HTML_FILES = "selectionMode=files, " +
		"filters=.*\\.(HTM|htm)|:Hypertext Markup Language file (HTM) files";
}
