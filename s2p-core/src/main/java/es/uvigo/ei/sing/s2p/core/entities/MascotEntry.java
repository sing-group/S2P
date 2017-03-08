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
package es.uvigo.ei.sing.s2p.core.entities;

import java.io.File;
import java.util.Objects;

public class MascotEntry {
	public static final String LABEL_TITLE 			= "Protein name";
	public static final String LABEL_PLATE_POSITION = "Plate position";
	public static final String LABEL_SCORE 			= "Mascot score";
	public static final String LABEL_DIFFERENCE 	= "Difference";
	public static final String LABEL_MS_COVERAGE 	= "MS Coverage";
	public static final String LABEL_PROTEIN_MW 	= "Protein MW";
	public static final String LABEL_METHOD 		= "Method";
	public static final String LABEL_PI_VALUE 		= "pI-Value";
	public static final String LABEL_ACCESSION 		= "Accession";
	public static final String LABEL_SOURCE 		= "Source";

	private String title;
	private String platePosition;
	private int mascotScore;
	private int difference;
	private int msCoverage;
	private double proteinMW;
	private String method;
	private double pIValue;
	private String accession;
	private File source;

	public MascotEntry(String title, String platePosition, int mascotScore,
		int difference, int msCoverage, double proteinMW, String method,
		double pIValue, String accession, File source
	) {
		this.title 			= title;
		this.platePosition 	= platePosition;
		this.mascotScore 	= mascotScore;
		this.difference 	= difference;
		this.msCoverage 	= msCoverage;
		this.proteinMW 		= proteinMW;
		this.method 		= method;
		this.pIValue 		= pIValue;
		this.accession 		= accession;
		this.source = source;
	}
	
	public String getTitle() {
		return title;
	}
	
	public String getPlatePosition() {
		return platePosition;
	}
	
	public int getMascotScore() {
		return mascotScore;
	}
	
	public int getDifference() {
		return difference;
	}
	
	public int getMsCoverage() {
		return msCoverage;
	}
	
	public double getProteinMW() {
		return proteinMW;
	}
	
	public String getMethod() {
		return method;
	}

	public double getpIValue() {
		return pIValue;
	}

	public String getAccession() {
		return accession;
	}

	public File getSource() {
		return source;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb
			.append(title)
			.append(", ")
			.append(platePosition)
			.append(", ")
			.append(mascotScore)
			.append(", ")
			.append(difference)
			.append(", ")
			.append(msCoverage)
			.append(", ")
			.append(proteinMW)
			.append(", ")
			.append(method)
			.append(", ")
			.append(pIValue)
			.append(", ")
			.append(accession)
			.append(", ")
			.append(source.getName());
		return sb.toString();
	}
	
	@Override
	public boolean equals(Object aThat) {
		if(aThat == null) {
			return false;
		}
		if(!(aThat instanceof MascotEntry)) {
			return false;
		}
		MascotEntry that = (MascotEntry) aThat;
		
		return 		this.title.equals(that.title)
				&&	this.platePosition.equals(that.platePosition) 
				&& 	this.mascotScore == that.mascotScore 
				&& 	this.difference == that.difference 
				&& 	this.proteinMW == that.proteinMW 
				&& 	this.method.equals(that.method)
				&& 	this.pIValue == that.pIValue 
				&&	this.accession.equals(that.accession)
				&& 	this.source.equals(that.source);
	}
	
	@Override
	public int hashCode() {
		return 	Objects.hash(
					this.title, this.platePosition, this.mascotScore,
					this.difference, this.proteinMW, this.method, this.pIValue,
					this.accession, this.source
				);
	}
}
