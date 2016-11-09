package es.uvigo.ei.sing.s2p.gui.samespots;

import java.awt.Window;
import java.util.List;

import es.uvigo.ei.sing.hlfernandez.input.ItemSelectionDialog;
import es.uvigo.ei.sing.s2p.core.entities.Sample;

public class SampleMergeDialog extends ItemSelectionDialog<Sample> {
	private static final long serialVersionUID = 1L;

	protected SampleMergeDialog(Window parent, List<Sample> items) {
		super(parent, items, 2, (Sample s) -> s.getName(), false);
	}
	
	@Override
	public String getTitle() {
		return "Sample selection";
	}
	
	@Override
	protected String getDescription() {
		return "This dialog allows you to select two different samples and "
			+ "merge them into one.";
	}
}
