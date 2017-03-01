package es.uvigo.ei.sing.s2p.core.io.samespots;

public class SameSpotsReportFileWriterConfiguration {

	public static final String DEFAULT_REPORT_SUFFIX = "_WithIdentifications";
	public static final boolean DEFAULT_REPORT_OVERWRITE = false;
	public static final boolean DEFAULT_INCLUDE_MASCOT_SCORE = true;
	public static final boolean DEFAULT_INCLUDE_ACCESSION = true;
	public static final boolean DEFAULT_INCLUDE_TITLE = true;
	public static final boolean DEFAULT_INCLUDE_PI_VALUE = true;
	public static final boolean DEFAULT_INCLUDE_PROTEIN_MW = true;
	public static final boolean DEFAULT_INCLUDE_SPOTS_WITHOUT_IDENTIFICATIONS = true;
	public static final boolean DEFAULT_REMOVE_DUPLICATED_PROTEIN_NAMES = false;
	
	private boolean includeMascotScore = DEFAULT_INCLUDE_MASCOT_SCORE;
	private boolean includeAccession = DEFAULT_INCLUDE_ACCESSION;
	private boolean includeTitle = DEFAULT_INCLUDE_TITLE;
	private boolean includePiValue = DEFAULT_INCLUDE_PI_VALUE;
	private boolean includeProteinMw = DEFAULT_INCLUDE_PROTEIN_MW;
	private boolean includeSpotsWithoutIdentifications = DEFAULT_INCLUDE_SPOTS_WITHOUT_IDENTIFICATIONS;
	private boolean includeRemoveDuplicatedProteinNames = DEFAULT_REMOVE_DUPLICATED_PROTEIN_NAMES;
	private boolean overwriteOriginalReport = DEFAULT_REPORT_OVERWRITE;
	private String reportSuffix = DEFAULT_REPORT_SUFFIX;

	public SameSpotsReportFileWriterConfiguration() {

	}

	public SameSpotsReportFileWriterConfiguration(boolean includeMascotScore,
		boolean includeAccession, boolean includeTitle,
		boolean includePiValue, boolean includeProteinMw, 
		boolean includeSpotsWithoutIdentifications,
		boolean removeDuplicatedProteinNames
	) {
		this(includeMascotScore, includeAccession, includeTitle, includePiValue,
			includeProteinMw, includeSpotsWithoutIdentifications,
			removeDuplicatedProteinNames, DEFAULT_REPORT_OVERWRITE,
			DEFAULT_REPORT_SUFFIX
		);
	}
	
	public SameSpotsReportFileWriterConfiguration(boolean includeMascotScore,
		boolean includeAccession, boolean includeTitle,
		boolean includePiValue, boolean includeProteinMw, 
		boolean includeSpotsWithoutIdentifications,
		boolean removeDuplicatedProteinNames,
		boolean overwriteOriginalReport, String reportSuffix
	) {
		this.includeMascotScore = includeMascotScore;
		this.includeAccession = includeAccession;
		this.includeTitle = includeTitle;
		this.includePiValue = includePiValue;
		this.includeProteinMw = includeProteinMw;
		this.includeSpotsWithoutIdentifications = includeSpotsWithoutIdentifications;
		this.includeRemoveDuplicatedProteinNames = removeDuplicatedProteinNames;
		this.overwriteOriginalReport = overwriteOriginalReport;
		this.reportSuffix = reportSuffix;
	}

	public boolean isIncludeMascotScore() {
		return includeMascotScore;
	}

	public boolean isIncludeAccession() {
		return includeAccession;
	}

	public boolean isIncludePiValue() {
		return includePiValue;
	}

	public boolean isIncludeProteinMw() {
		return includeProteinMw;
	}

	public boolean isIncludeTitle() {
		return includeTitle;
	}

	public boolean isIncludeSpotsWithoutIdentifications() {
		return includeSpotsWithoutIdentifications;
	}

	public boolean isRemoveDuplicatedProteinNames() {
		return includeRemoveDuplicatedProteinNames;
	}

	public boolean isOverwriteOriginalReport() {
		return overwriteOriginalReport;
	}

	public String getReportSuffix() {
		return reportSuffix;
	}
}