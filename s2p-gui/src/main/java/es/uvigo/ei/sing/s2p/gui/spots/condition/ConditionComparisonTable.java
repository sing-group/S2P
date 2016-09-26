package es.uvigo.ei.sing.s2p.gui.spots.condition;

import static es.uvigo.ei.sing.hlfernandez.ui.UIUtils.setOpaqueRecursive;
import static es.uvigo.ei.sing.s2p.gui.UISettings.BG_COLOR;
import static javax.swing.BorderFactory.createEmptyBorder;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import es.uvigo.ei.sing.hlfernandez.input.RangeInputPanel;
import es.uvigo.ei.sing.s2p.core.entities.Condition;
import es.uvigo.ei.sing.s2p.core.entities.Sample;
import es.uvigo.ei.sing.s2p.core.entities.SpotsCount;
import es.uvigo.ei.sing.s2p.gui.UISettings;
import es.uvigo.ei.sing.s2p.gui.samples.SamplesComparisonTable;
import es.uvigo.ei.sing.s2p.gui.util.ColorUtils;

public class ConditionComparisonTable extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private static final int FONT_SIZE = 13;
	
	private List<Condition> conditions;
	private Map<Condition, SpotsCount> conditionsCount;
	private Map<Condition, Range> conditionsThreshold;
	private Map<Condition, Color> conditionsColors;
	private Map<Sample, Color> samplesColors;
	private Map<Sample, String> samplesLabels;
	private JPanel northPanel;
	private Set<String> allProteins;
	private SamplesComparisonTable samplesTable;
	
	public ConditionComparisonTable(List<Condition> conditions) {
		this.conditions = conditions;
		this.allProteins = getProteins(this.conditions);
		
		this.conditionsCount = new HashMap<Condition, SpotsCount>();
		this.conditionsThreshold = new HashMap<Condition, Range>();
		this.conditionsColors = new HashMap<Condition, Color>();
		this.samplesColors = new HashMap<Sample, Color>();
		this.samplesLabels = new HashMap<Sample, String>();
		this.conditions.forEach(c -> {
			this.conditionsCount.put(c, new SpotsCount(c, allProteins));
			this.conditionsThreshold.put(c, new Range(0, c.getSamples().size()));
			Color conditionColor = ColorUtils.getSoftColor(this.conditions.indexOf(c));
			this.conditionsColors.put(c, conditionColor);
			
			c.getSamples().forEach(s -> {
				samplesColors.put(s, conditionColor);
				samplesLabels.put(s, c.getName());
			});
		});
		this.initComponent();
	}
	
	private void initComponent() {
		this.setLayout(new BorderLayout());
		
		this.add(getNorthPanel(), BorderLayout.NORTH);
		
		samplesTable = new SamplesComparisonTable(
			getSamples(),
			getSampleColors(),
			getSampleLabels());
		
		samplesTable.setBorder(createEmptyBorder(1, 5, 1, 5));
		samplesTable.setBackground(BG_COLOR);
		this.add(samplesTable, BorderLayout.CENTER);
	}

	private List<Sample> getSamples() {
		return 	getConditions().stream()
				.map(Condition::getSamples)
				.flatMap(Collection::stream)
				.collect(Collectors.toList());
	}
	
	private Map<Sample, Color> getSampleColors() {
		return samplesColors;
	}
	
	public Map<Sample, String> getSampleLabels() {
		return samplesLabels;
	}

	private List<Condition> getConditions() {
		return conditions;
	}

	private JPanel getNorthPanel() {
		if(this.northPanel == null) {
			this.northPanel = new JPanel();
			this.northPanel.setBorder(createEmptyBorder(2, 10, 2, 10));
			this.northPanel.setLayout(new GridLayout(0, 2));
			this.northPanel.setBackground(Color.WHITE);
			
			getConditions().forEach(c -> {
				if (c.getSamples().size() > 1) {
					this.northPanel.add(createConditionFilteringPanel(c));
				}
			});
		}
		return this.northPanel;
	}

	private Component createConditionFilteringPanel(Condition condition) {
		JPanel panel = new JPanel(new BorderLayout());
		panel.setBorder(BorderFactory.createTitledBorder(condition.getName()));
		panel.setBackground(Color.WHITE);
		
		RangeInputPanel rangeInput = new RangeInputPanel(0, 
			condition.getSamples().size(), "Minimum number of samples",
			"Maximum number of samples"
		);
		setOpaqueRecursive(rangeInput, false);
		rangeInput.setBorder(createEmptyBorder(2, 0, 2, 0));
		
		rangeInput.setBackground(UISettings.BG_COLOR);
		
		int numSamples = condition.getSamples().size();
		JLabel label = new JLabel(getLabelText(0, numSamples, numSamples));
		label.setFont(label.getFont().deriveFont(Font.BOLD, FONT_SIZE));
		
		rangeInput.addChangeListener(e -> {
			conditionFilterChanged(condition, rangeInput.getMinValue(),
				rangeInput.getMaxValue(), label);
		});
		
		panel.add(label, BorderLayout.NORTH);
		panel.add(rangeInput, BorderLayout.CENTER);
		return panel;
	}
	
	private static String getLabelText(int min, int max, int samples) {
		return 	"Show spots present in at least " + min +  
				" and at maximum " + max + " (of " + samples + ") samples:";
	}
	
	private static Set<String> getProteins(List<Condition> conditions) {
		Set<String> all = new HashSet<String>();
		conditions.forEach(c -> {
			all.addAll(c.getSpots());
		});
		return all;
	}

	private void conditionFilterChanged(Condition condition, int min, int max, 
		JLabel label
	) {
		label.setText(getLabelText(min,  max, condition.getSamples().size()));
		this.conditionsThreshold.put(condition, new Range(min, max));
		Set<String> visibleProteins = new HashSet<String>(allProteins);

		conditions.forEach(c -> {
			Set<String> proteins = conditionsCount.get(c).getSpots(
				conditionsThreshold.get(c).getMin(), 
				conditionsThreshold.get(c).getMax()
			);
			visibleProteins.retainAll(proteins);
		});
		this.samplesTable.setVisibleProteins(visibleProteins);
	}
	
	static class Range {
		
		private int max;
		private int min;

		public Range(int min, int max) {
			this.min = min;
			this.max = max;
		}
		
		public int getMax() {
			return max;
		}
		
		public int getMin() {
			return min;
		}
	}
}
