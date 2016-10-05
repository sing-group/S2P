package es.uvigo.ei.sing.s2p.aibench.views;

import static javax.swing.SwingUtilities.getWindowAncestor;

import es.uvigo.ei.sing.s2p.aibench.datatypes.SpotsDataDatatype;
import es.uvigo.ei.sing.s2p.aibench.views.spots.AIBenchLoadMascotIdentificationsDialog;
import es.uvigo.ei.sing.s2p.gui.mascot.LoadMascotIdentificationsDialog;
import es.uvigo.ei.sing.s2p.gui.spots.SpotsDataViewer;

public class SpotsDataDatatypeView extends SpotsDataViewer {
	private static final long serialVersionUID = 1L;

	public SpotsDataDatatypeView(SpotsDataDatatype data) {
		super(data);
	}
	
	@Override
	protected LoadMascotIdentificationsDialog getMascotIdentificationsDialog() {
		return new AIBenchLoadMascotIdentificationsDialog(getWindowAncestor(this));
	}
}
