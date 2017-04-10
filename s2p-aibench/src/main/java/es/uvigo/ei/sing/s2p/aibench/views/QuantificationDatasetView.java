package es.uvigo.ei.sing.s2p.aibench.views;

import es.uvigo.ei.sing.s2p.aibench.datatypes.QuantificationDatasetDatatype;
import es.uvigo.ei.sing.s2p.gui.quantification.QuantificationDatasetViewer;

public class QuantificationDatasetView extends QuantificationDatasetViewer {
	private static final long serialVersionUID = 1L;

	public QuantificationDatasetView(QuantificationDatasetDatatype dataset
	) {
		super(dataset, dataset.getProteinComparison());
	}
}
