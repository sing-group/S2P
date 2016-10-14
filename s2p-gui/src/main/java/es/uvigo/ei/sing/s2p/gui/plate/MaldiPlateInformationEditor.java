package es.uvigo.ei.sing.s2p.gui.plate;

import static es.uvigo.ei.sing.s2p.core.entities.MaldiPlateInformation.FIELD_DATE;
import static es.uvigo.ei.sing.s2p.core.entities.MaldiPlateInformation.FIELD_MALDI_TARGET;
import static es.uvigo.ei.sing.s2p.core.entities.MaldiPlateInformation.FIELD_MASCOT_EXPERIMENT;
import static es.uvigo.ei.sing.s2p.core.entities.MaldiPlateInformation.FIELD_SAMPLE_TYPE;
import static es.uvigo.ei.sing.s2p.core.entities.MaldiPlateInformation.FIELD_USER;

import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ItemEvent;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.jdesktop.swingx.JXTextField;

import es.uvigo.ei.sing.hlfernandez.text.BindJXTextField;
import es.uvigo.ei.sing.s2p.core.entities.MaldiPlateInformation;
import es.uvigo.ei.sing.s2p.core.entities.MaldiPlateInformation.MaldiTarget;
import es.uvigo.ei.sing.s2p.gui.UISettings;

public class MaldiPlateInformationEditor extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private MaldiPlateInformation info;

	public MaldiPlateInformationEditor(MaldiPlateInformation info) {
		this.info = info;
		
		this.init();
	}

	private void init() {
		this.setLayout(new GridLayout(0, 1));
		this.setBackground(UISettings.BG_COLOR);
		
		JPanel firstRow = new JPanel();
		firstRow.setOpaque(false);
		firstRow.setLayout(new BoxLayout(firstRow, BoxLayout.X_AXIS));
		firstRow.add(new JLabel(FIELD_USER + ": "));
		firstRow.add(getUserTextField());
		firstRow.add(Box.createHorizontalStrut(50));
		firstRow.add(new JLabel(FIELD_DATE + ": "));
		firstRow.add(getDateTextField());
		firstRow.add(Box.createHorizontalStrut(50));
		firstRow.add(new JLabel(FIELD_MALDI_TARGET + ": "));
		firstRow.add(getMaldiTargetCombo());
		
		JPanel secondRow = new JPanel();
		secondRow.setOpaque(false);
		secondRow.setLayout(new BoxLayout(secondRow, BoxLayout.X_AXIS));
		secondRow.add(new JLabel(FIELD_SAMPLE_TYPE + ": "));
		secondRow.add(getSampleTypeTextField());
		secondRow.add(Box.createHorizontalStrut(50));
		secondRow.add(new JLabel(FIELD_MASCOT_EXPERIMENT + ": "));
		secondRow.add(getMascotField());
		
		this.add(firstRow);
		this.add(secondRow);
	}
	
	private Component getMaldiTargetCombo() {
		JComboBox<MaldiTarget> maldiTarget = 
			new JComboBox<MaldiTarget>(MaldiTarget.values());

		if (info.getMaldiTarget().isPresent()) {
			maldiTarget.setSelectedItem(info.getMaldiTarget().get());
		}

		maldiTarget.addItemListener(e -> {
			if(e.getStateChange() == ItemEvent.SELECTED) {
				this.info.setMaldiTarget(
					(MaldiTarget) maldiTarget.getSelectedItem());
			}
		});
		return maldiTarget;
	}

	public JXTextField getUserTextField() {
		return new BindJXTextField(FIELD_USER, info.getUser(), info::setUser);
	}
	
	public JXTextField getDateTextField() {
		return new BindJXTextField(FIELD_DATE, info.getDate(), info::setDate);
	}
	
	public JXTextField getSampleTypeTextField() {
		return new BindJXTextField(FIELD_SAMPLE_TYPE, info.getSampleType(), info::setSampleType);
	}
	
	public JXTextField getMascotField() {
		return new BindJXTextField(FIELD_MASCOT_EXPERIMENT, info.getMascotExperiment(), info::setMascotExperiment);
	}
}