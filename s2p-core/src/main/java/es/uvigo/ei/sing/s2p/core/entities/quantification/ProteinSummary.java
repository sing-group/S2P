package es.uvigo.ei.sing.s2p.core.entities.quantification;

import static es.uvigo.ei.sing.s2p.core.util.ArrayUtils.doubleArray;
import java.util.List;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

public class ProteinSummary {

	private int numReplicates;
	private int totalReplicates;
	private List<Double> 			proteinValues;
	private DescriptiveStatistics 	proteinValuesStatistics;
	private List<Double> 			normalizedProteinValues;
	private DescriptiveStatistics 	normalizedProteinValuesStatistics;

	public ProteinSummary(int totalReplicates, List<Double> proteinValues,
		List<Double> proteinNormalizedValues
	) {
		this.totalReplicates = totalReplicates;
		this.proteinValuesStatistics = 
				new DescriptiveStatistics(doubleArray(proteinValues));
		this.normalizedProteinValuesStatistics = 
				new DescriptiveStatistics(doubleArray(proteinNormalizedValues));
		this.numReplicates = (int) this.proteinValuesStatistics.getN();
		this.proteinValues = proteinValues;
		this.normalizedProteinValues = proteinNormalizedValues;
	}

	public int getNumReplicates() {
		return numReplicates;
	}

	public int getTotalReplicates() {
		return totalReplicates;
	}

	public double getProteinValueMean() {
		return this.proteinValuesStatistics.getMean();
	}

	public double getProteinValueStd() {
		return this.proteinValuesStatistics.getStandardDeviation();
	}

	public double getProteinValueRsd() {
		return getProteinValueStd() / getProteinValueMean();
	}	

	public double getNormalizedProteinValueMean() {
		return this.normalizedProteinValuesStatistics.getMean();
	}
	
	public double getNormalizedProteinValueStd() {
		return this.normalizedProteinValuesStatistics.getStandardDeviation();
	}

	public double getNormalizedProteinValueRsd() {
		return getNormalizedProteinValueStd() / getNormalizedProteinValueMean();
	}

	public double getPop() {
		return 100d * (double) getNumReplicates() / (double) getTotalReplicates();
	}
	
	public List<Double> getProteinValues() {
		return this.proteinValues;
	}
	
	public List<Double> getNormalizedProteinValues() {
		return this.normalizedProteinValues;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb
			.append("[SpotSummary] Total = ")
			.append(getTotalReplicates())
			.append(" | Spots = ")
			.append(getNumReplicates())
			.append(" | POP = ")
			.append(getPop());

		return sb.toString();
	}
}
