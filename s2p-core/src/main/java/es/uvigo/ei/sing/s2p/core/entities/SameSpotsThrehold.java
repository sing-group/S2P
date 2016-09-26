package es.uvigo.ei.sing.s2p.core.entities;

public class SameSpotsThrehold {

	public static final double DEFAULT_P 	= 0.05;
	public static final double DEFAULT_FOLD = 0;
	
	private double p;
	private double fold;

	public SameSpotsThrehold() {
		this.p = DEFAULT_P;
		this.fold = DEFAULT_FOLD;
	}
	
	public SameSpotsThrehold(double p, double fold) {
		this.p = p;
		this.fold = fold;
	}
	
	public double getP() {
		return p;
	}
	
	public double getFold() {
		return fold;
	}
}
