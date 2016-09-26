package es.uvigo.ei.sing.s2p.core.operations;

import java.util.HashSet;
import java.util.Set;

public class SpotSetsComparator {

	private int set1;
	private int set2;
	private int intersection;
	
	public SpotSetsComparator(Set<String> set1, Set<String> set2) {
		Set<String> intersection = new HashSet<String>(set1);
		intersection.retainAll(set2);
		
		this.intersection = intersection.size();
		this.set1 = set1.size();
		this.set2 = set2.size();
	}
	
	public int getSet1() {
		return set1;
	}
	
	public int getSet1Unique() {
		return set1 - intersection;
	}
	
	public int getSet2() {
		return set2;
	}
	
	public int getSet2Unique() {
		return set2 - intersection;
	}
	
	public int getIntersection() {
		return intersection;
	}
}
