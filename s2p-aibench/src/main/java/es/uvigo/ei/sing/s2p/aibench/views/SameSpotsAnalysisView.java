package es.uvigo.ei.sing.s2p.aibench.views;

import es.uvigo.ei.sing.s2p.aibench.datatypes.SameSpotsAnalysisDatatype;
import es.uvigo.ei.sing.s2p.gui.samespots.SameSpotsDatasetViewer;

public class SameSpotsAnalysisView extends SameSpotsDatasetViewer {
	private static final long serialVersionUID = 1L;

	public SameSpotsAnalysisView(SameSpotsAnalysisDatatype datatype) {
		super(datatype.getSamples());
	}
}
