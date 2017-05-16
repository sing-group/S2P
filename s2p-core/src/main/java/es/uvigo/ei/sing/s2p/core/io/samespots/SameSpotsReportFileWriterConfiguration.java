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

public class SameSpotsReportFileWriterConfiguration {

	public static final String DEFAULT_REPORT_SUFFIX = "_WithIdentifications";
	public static final boolean DEFAULT_REPORT_OVERWRITE = false;
	public static final boolean DEFAULT_INCLUDE_MASCOT_SCORE = true;
	public static final boolean DEFAULT_INCLUDE_ACCESSION = true;
	public static final boolean DEFAULT_INCLUDE_TITLE = true;
	public static final boolean DEFAULT_INCLUDE_PI_VALUE = true;
	public static final boolean DEFAULT_INCLUDE_PROTEIN_MW = true;
	public static final boolean DEFAULT_INCLUDE_DIFFERENCE = false;
	public static final boolean DEFAULT_INCLUDE_MS_COVERAGE = false;
	public static final boolean DEFAULT_INCLUDE_PLATE_POS = false;
	public static final boolean DEFAULT_INCLUDE_SOURCE = false;
	public static final boolean DEFAULT_INCLUDE_METHOD = false;
	public static final boolean DEFAULT_INCLUDE_SPOTS_WITHOUT_IDENTIFICATIONS = true;
	public static final boolean DEFAULT_REMOVE_DUPLICATED_PROTEIN_NAMES = false;
	
	private boolean includeMascotScore = DEFAULT_INCLUDE_MASCOT_SCORE;
	private boolean includeAccession = DEFAULT_INCLUDE_ACCESSION;
	private boolean includeTitle = DEFAULT_INCLUDE_TITLE;
	private boolean includePiValue = DEFAULT_INCLUDE_PI_VALUE;
	private boolean includeProteinMw = DEFAULT_INCLUDE_PROTEIN_MW;
	private boolean isIncludeDifference = DEFAULT_INCLUDE_DIFFERENCE;
	private boolean isIncludeMsCoverage = DEFAULT_INCLUDE_MS_COVERAGE;
	private boolean isIncludePlatePosition = DEFAULT_INCLUDE_PLATE_POS;
	private boolean isIncludeSource = DEFAULT_INCLUDE_SOURCE;
	private boolean isIncludeMethod = DEFAULT_INCLUDE_METHOD;
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
		boolean removeDuplicatedProteinNames,
		boolean isIncludeMethod, boolean isIncludeMsCoverage,
		boolean isIncludePlatePosition, boolean isIncludeSource,
		boolean isIncludeDifference
	) {
		this(includeMascotScore, includeAccession, includeTitle, includePiValue,
			includeProteinMw, includeSpotsWithoutIdentifications,
			removeDuplicatedProteinNames, isIncludeMethod,
			isIncludeMsCoverage, isIncludePlatePosition, isIncludeSource,
			isIncludeDifference, DEFAULT_REPORT_OVERWRITE,
			DEFAULT_REPORT_SUFFIX
		);
	}
	public SameSpotsReportFileWriterConfiguration(boolean includeMascotScore,
		boolean includeAccession, boolean includeTitle,
		boolean includePiValue, boolean includeProteinMw, 
		boolean includeSpotsWithoutIdentifications,
		boolean removeDuplicatedProteinNames,
		boolean isIncludeMethod, boolean isIncludeMsCoverage, 
		boolean isIncludePlatePosition, boolean isIncludeSource,
		boolean isIncludeDifference,
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
		this.isIncludeMethod = isIncludeMethod;
		this.isIncludeMsCoverage = isIncludeMsCoverage;
		this.isIncludePlatePosition = isIncludePlatePosition;
		this.isIncludeSource = isIncludeSource;
		this.isIncludeDifference = isIncludeDifference;
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

	public boolean isIncludeDifference() {
		return isIncludeDifference;
	}

	public boolean isIncludeMsCoverage() {
		return isIncludeMsCoverage;
	}

	public boolean isIncludeRemoveDuplicatedProteinNames() {
		return includeRemoveDuplicatedProteinNames;
	}

	public boolean isIncludePlatePosition() {
		return isIncludePlatePosition;
	}

	public boolean isIncludeSource() {
		return isIncludeSource;
	}

	public boolean isIncludeMethod() {
		return isIncludeMethod;
	}
}
