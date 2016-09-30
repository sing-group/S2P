package es.uvigo.ei.sing.s2p.core.entities;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class SpotsData {

	private List<String> spots;
	private List<String> sampleNames;
	private List<String> sampleLabels;
	private double[][] data;
	
	private List<Condition> conditions;
	
	public SpotsData(List<String> spos, List<String> sampleNames,
		List<String> sampleLabels, double[][] data
	) {
		this.spots = spos;
		this.sampleNames = sampleNames;
		this.sampleLabels = sampleLabels;
		this.data = data;
	}
	
	public List<String> getSpots() {
		return spots;
	}
	
	public List<String> getSampleLabels() {
		return sampleLabels;
	}
	
	public List<String> getSampleNames() {
		return sampleNames;
	}
	
	public double[][] getData() {
		return data;
	}
	
	public List<Condition> getConditions() {
		if(this.conditions == null) {
			Map<String, List<Sample>> labelSamples = new HashMap<String, List<Sample>>();
			for(int column = 0; column < getSampleNames().size(); column ++) {
				Map<String, Double> sampleSpotsValues = new HashMap<String, Double>();
				
				for(int row = 0; row < getSpots().size(); row++) {
					double value = getData()[row][column];
					if(!Double.isNaN(value)) {
						sampleSpotsValues.put(getSpots().get(row), value);
					}
				}
				
				labelSamples.putIfAbsent(getSampleLabels().get(column), new LinkedList<Sample>());
				labelSamples.get(getSampleLabels().get(column)).add(
					new Sample(getSampleNames().get(column), sampleSpotsValues)
				);
			}
		
			this.conditions = new LinkedList<Condition>();
			for(String condition : labelSamples.keySet()) {
				this.conditions.add(
					new Condition(condition, labelSamples.get(condition)));
			}
		}
		return this.conditions;
	}
}
