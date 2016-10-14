package es.uvigo.ei.sing.s2p.core.entities;

public class Range {

	private Number start;
	private Number end;

	public Range(Number start, Number end) {
		this.start = start;
		this.end = end;
	}
	
	public Number getStart() {
		return start;
	}
	
	public Number getEnd() {
		return end;
	}
}
