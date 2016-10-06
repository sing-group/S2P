package es.uvigo.ei.sing.s2p.gui.spots.comparison;

import static es.uvigo.ei.sing.s2p.gui.UISettings.FONT_SIZE;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.util.LinkedList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.border.EtchedBorder;

import es.uvigo.ei.sing.s2p.core.entities.Condition;
import es.uvigo.ei.sing.s2p.core.entities.SpotsCount;

public class ConditionProteinsFilteringList extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private Condition condition;
	private JLabel label;
	private ProteinsListModel proteinsListModel;
	private JList<String> proteinsList;
	private JSlider presenceSlider;
	
	public ConditionProteinsFilteringList(Condition condition) {
		this.condition = condition;
		this.initComponent();
	}

	private void initComponent() {
		this.setLayout(new BorderLayout());
		this.setBorder(BorderFactory.createTitledBorder(condition.getName()));
		this.setBackground(Color.WHITE);
		
		label = new JLabel();
		label.setFont(label.getFont().deriveFont(Font.BOLD, FONT_SIZE));
		label.setBackground(Color.WHITE);
		
		presenceSlider = new JSlider(JSlider.HORIZONTAL, 0, condition.getSamples().size(), condition.getSamples().size()-1);
		presenceSlider.setOpaque(false);
		proteinsListModel = new ProteinsListModel(condition, presenceSlider.getValue());
		proteinsList = new JList<>(proteinsListModel);
		presenceSlider.addChangeListener(e -> {
			setPop(presenceSlider.getValue());
		});
		presenceSlider.setPaintTicks(true);
		presenceSlider.setPaintLabels(true);
		presenceSlider.setMajorTickSpacing(1);
		presenceSlider.setMinorTickSpacing(1);
		
		updateLabelText();
		
		JLabel sliderLabel = new JLabel("Number of samples: ");
		JPanel sliderPanel = new JPanel(new BorderLayout());
		sliderPanel.setBackground(Color.WHITE);
		sliderPanel.add(label, BorderLayout.SOUTH);
		sliderPanel.add(sliderLabel, BorderLayout.WEST);
		sliderPanel.add(presenceSlider, BorderLayout.CENTER);

		this.add(sliderPanel, BorderLayout.NORTH);
		this.add(new JScrollPane(proteinsList), BorderLayout.CENTER);
	}

	private void setPop(int value) {
		proteinsListModel.setPop(value);
		proteinsList.updateUI();
		updateLabelText();
	}

	private void updateLabelText() {
		this.label.setText("The following " + proteinsListModel.getSize() + " spots are present in at least " + presenceSlider.getValue() +  " (of " + condition.getSamples().size() + ") " + condition.getName() + " samples:" );
	}
	
	public void setCondition(Condition condition) {
		this.condition = condition;
		this.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), condition.getName()));
		this.presenceSlider.setMaximum(condition.getSamples().size());
		this.presenceSlider.setValue(condition.getSamples().size()-1);
		this.proteinsListModel.setCondition(this.condition);
		updateLabelText();
		proteinsList.updateUI();
	}
	
	private class ProteinsListModel extends DefaultListModel<String> {
		private static final long serialVersionUID = 1L;
		private Condition condition;
		private SpotsCount proteinsCount;
		private List<String> proteins;
		private int presenceThreshold;

		public ProteinsListModel(Condition condition, int presenceThreshold) {
			this.condition = condition;
			this.presenceThreshold = presenceThreshold;
			this.proteinsCount = new SpotsCount(this.condition);
			loadProteinsList();
		}

		private void loadProteinsList() {
			this.proteins = new LinkedList<String>(
				this.proteinsCount.getSpots(this.presenceThreshold));
		}

		public void setCondition(Condition condition) {
			this.condition = condition;
			this.proteinsCount = new SpotsCount(this.condition);
			loadProteinsList();
		}

		public void setPop(int pop) {
			this.presenceThreshold = pop;
			loadProteinsList();
		}

		@Override
		public int getSize() {
			return this.proteins.size();
		}
		
		public String getElementAt(int index) {
			return this.proteins.get(index);
		};
	}
}
