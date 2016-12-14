package es.uvigo.ei.sing.s2p.gui.table.spots;

import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.util.function.BiConsumer;

import org.jdesktop.swingx.action.AbstractActionExt;

import es.uvigo.ei.sing.s2p.core.entities.Condition;

public class ConditionControlAction extends AbstractActionExt {
	private static final long serialVersionUID = 1L;
	private Condition condition;
	private BiConsumer<Condition, Boolean> action;

	public ConditionControlAction(Condition c,
		BiConsumer<Condition, Boolean> action
	) {
		super("Condition: " + c.getName());
		this.condition = c;
		this.action = action;
		this.setSelected(true);
		setStateAction();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
	}

	@Override
	public void itemStateChanged(final ItemEvent e) {
		action.accept(condition, e.getStateChange() == ItemEvent.SELECTED);
	}
}