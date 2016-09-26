package es.uvigo.ei.sing.s2p.gui.event;

import java.util.EventListener;

public interface ProteinDataComparisonListener extends EventListener {
	public void onSampleSelection(ProteinDataComparisonEvent event);
	public void onSampleSelectionCleared(ProteinDataComparisonEvent event);
}
