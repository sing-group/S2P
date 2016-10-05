package es.uvigo.ei.sing.s2p.gui.table;

import java.util.Set;

public class SpotPresenceTester implements Tester {

	private Set<String> visibleProteins;

	public SpotPresenceTester(Set<String> visibleProteins) {
		this.visibleProteins = visibleProteins; 
	}
	
	@Override
	public boolean test(Object value) {
		return visibleProteins.contains(value);
	}
	
	public void setVisibleSpots(Set<String> visibleProteins) {
		this.visibleProteins = visibleProteins;
	}
}