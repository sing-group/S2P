package es.uvigo.ei.sing.s2p.gui.samespots;

import java.awt.GridLayout;
import java.awt.Window;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.event.ListDataEvent;

import es.uvigo.ei.sing.hlfernandez.dialog.AbstractInputJDialog;
import es.uvigo.ei.sing.hlfernandez.input.JInputList;
import es.uvigo.ei.sing.hlfernandez.list.DefaultListDataListener;

public class ConditionLabelInputDialog extends AbstractInputJDialog {
	private static final long serialVersionUID = 1L;
	private JInputList inputList;
	private List<String> conditionLabels;

	protected ConditionLabelInputDialog(Window parent, List<String> conditionLabels) {
		super(parent);
		
		this.conditionLabels = conditionLabels;
		this.initComponent();
	}
	
	private void initComponent() {
		this.inputList.addElements(
			this.conditionLabels.toArray(new String[this.conditionLabels.size()])
		);
		this.pack();
	}

	protected String getDialogTitle() {
		return "Set conditions' labels";
	}

	protected String getDescription() {
		return "Set conditions' labels.";
	}

	protected JPanel getInputComponentsPane() {
		JPanel toret = new JPanel();
		toret.setLayout(new GridLayout(0, 1));
		this.inputList = new JInputList(true, false, false);
		this.inputList.getListPanel().getBtnMoveDown().setVisible(false);
		this.inputList.getListPanel().getBtnMoveUp().setVisible(false);
		this.inputList.addListDataListener(new DefaultListDataListener() {
			
			@Override
			public void intervalAdded(ListDataEvent e) {
				inputListContentsChanged();
			}
			
			@Override
			public void intervalRemoved(ListDataEvent e) {
				inputListContentsChanged();
			}
		});
		
		toret.add(this.inputList);
		toret.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		return toret;
	}

	private void inputListContentsChanged() {
		this.okButton.setEnabled(inputList.getInputItems().size() > 0);
	}

	public List<String> getConditionLabels() {
		return inputList.getInputItems();
	}
}
