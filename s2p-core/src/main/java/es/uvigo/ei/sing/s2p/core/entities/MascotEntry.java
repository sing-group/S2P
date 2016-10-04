package es.uvigo.ei.sing.s2p.core.entities;

import java.util.Objects;

public class MascotEntry {

	private String title;
	private String platePosition;
	private int mascotScore;
	private int difference;
	private int msCoverage;
	private double proteinMW;
	private String method;
	private double pIValue;
	private String accession;

	public MascotEntry(String title, String platePosition, int mascotScore,
		int difference, int msCoverage, double proteinMW, String method,
		double pIValue, String accession
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
			.append(accession);
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
				&&	this.accession.equals(that.accession);
	}
	
	@Override
	public int hashCode() {
		return 	Objects.hash(
					this.title, this.platePosition, this.mascotScore,
					this.difference, this.proteinMW, this.method, this.pIValue,
					this.accession
				);
	}
}
