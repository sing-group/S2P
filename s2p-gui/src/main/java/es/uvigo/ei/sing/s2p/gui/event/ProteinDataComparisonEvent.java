package es.uvigo.ei.sing.s2p.gui.event;

import java.util.EventObject;

public class ProteinDataComparisonEvent extends EventObject {
	private static final long serialVersionUID = 1L;
	
	public static enum Type {
		/**
		 * Samples are selected.
		 */
		SAMPLE_SELECTION,
		/**
		 * Samples are unselected.
		 */
		SAMPLE_SELECTION_CLEARED,
	}
	
	private Type type;
	
	public ProteinDataComparisonEvent(Object source, Type type) {
		super(source);
		this.type = type;
	}

	public Type getType() {
		return type;
	}
}
