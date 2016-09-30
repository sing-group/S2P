package es.uvigo.ei.sing.s2p.core.entities;

import java.util.List;

public class SpotSummary {
	
	private int numSamples;
	private int totalSamples;
	private double mean;
	private double std;
	private List<Double> spotValues;

	public SpotSummary(int numSamples, int totalSamples, double mean,
		double std, List<Double> spotValues
	) {
		this.numSamples = numSamples;
		this.totalSamples = totalSamples;
		this.mean = mean;
		this.std = std;
		this.spotValues = spotValues;
	}
	
	public int getNumSamples() {
		return numSamples;
	}
	
	public int getTotalSamples() {
		return totalSamples;
	}
	
	public double getPop() {
		return 100d * (double) getNumSamples() / (double) getTotalSamples();
	}
	
	public double getMean() {
		return mean;
	}

	public double getStdDev() {
		return std;
	}

	public List<Double> getSpotValues() {
		return spotValues;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb
			.append("[SpotSummary] Total = ")
			.append(getTotalSamples())
			.append(" | Spots = ")
			.append(getNumSamples())
			.append(" | Mean = ")
			.append(getMean())
			.append(" | Std = ")
			.append(getStdDev())
			.append(" | POP = ")
			.append(getPop());

		return sb.toString();
	}
}
