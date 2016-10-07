package es.uvigo.ei.sing.s2p.core.operations;

import java.util.HashSet;
import java.util.Set;

public class StringSetComparison {

	private Set<String> set1;
	private Set<String> set2;
	private Set<String> intersection;
	
	private int set1Size;
	private int set2Size;
	private int intersectionSize;
	
	public StringSetComparison(Set<String> set1, Set<String> set2) {
		this.set1 = set1;
		this.set2 = set2;
		
		this.initData();
	}
	
	private void initData() {
		intersection = new HashSet<String>(this.set1);
		intersection.retainAll(this.set2);

		this.intersectionSize = this.intersection.size();
		this.set1Size = set1.size();
		this.set2Size = set2.size();
	}
	
	public Set<String> getSet1() {
		return set1;
	}
	
	public Set<String> getSet1Unique() {
		return unique(set1);
	}
	
	private Set<String> unique(Set<String> set) {
		Set<String> unique = new HashSet<String>(set);
		unique.removeAll(intersection);
		
		return unique;
	}

	public Set<String> getSet2() {
		return set2;
	}
	
	public Set<String> getSet2Unique() {
		return unique(set2);
	}
	
	public Set<String> getIntersection() {
		return intersection;
	}

	public int getSet1Size() {
		return set1Size;
	}
	
	public int getSet1UniqueSize() {
		return set1Size - intersectionSize;
	}
	
	public int getSet2Size() {
		return set2Size;
	}
	
	public int getSet2UniqueSize() {
		return set2Size - intersectionSize;
	}
	
	public int getIntersectionSize() {
		return intersectionSize;
	}
}
