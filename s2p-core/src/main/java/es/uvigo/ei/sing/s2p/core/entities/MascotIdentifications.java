package es.uvigo.ei.sing.s2p.core.entities;

import java.util.LinkedList;
import java.util.List;

public class MascotIdentifications extends LinkedList<MascotEntry> {
	private static final long serialVersionUID = 1L;
	
	public MascotIdentifications() {
		super();
	}

	public MascotIdentifications(List<MascotEntry> entries) {
		super(entries);
	}
}
