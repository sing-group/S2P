package es.uvigo.ei.sing.s2p.core.entities;

public class MascotEntry {

	private String title; 
	private String platePosition; 
	private int mascotScore; 
	private int difference; 
	private int msCoverage; 
	private String accession;
	
	public MascotEntry(String title, String platePosition, int mascotScore,
		int difference, int msCoverage, String accession
	) {
		this.title = title;
		this.platePosition = platePosition;
		this.mascotScore = mascotScore;
		this.difference = difference;
		this.msCoverage = msCoverage;
		this.accession = accession;
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
				&&	this.accession.equals(that.accession) ;
	}
}
