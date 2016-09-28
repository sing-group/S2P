package es.uvigo.ei.sing.s2p.gui.spots.condition;

import static es.uvigo.ei.sing.hlfernandez.ui.UIUtils.setOpaqueRecursive;
import static es.uvigo.ei.sing.s2p.gui.UISettings.BG_COLOR;
import static javax.swing.BorderFactory.createEmptyBorder;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToggleButton;

import es.uvigo.ei.sing.hlfernandez.input.RangeInputPanel;
import es.uvigo.ei.sing.s2p.core.entities.Condition;
import es.uvigo.ei.sing.s2p.core.entities.MascotEntry;
import es.uvigo.ei.sing.s2p.core.entities.MascotIdentifications;
import es.uvigo.ei.sing.s2p.core.entities.Sample;
import es.uvigo.ei.sing.s2p.core.entities.SpotsCount;
import es.uvigo.ei.sing.s2p.gui.UISettings;
import es.uvigo.ei.sing.s2p.gui.mascot.LoadMascotIdentificationsDialog;
import es.uvigo.ei.sing.s2p.gui.mascot.MascotIdentificationsDialog;
import es.uvigo.ei.sing.s2p.gui.samples.SamplesComparisonTable;
import es.uvigo.ei.sing.s2p.gui.spots.comparison.JHeatMapDialog;
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
	private JPanel toolbar;
	
	private Set<String> allSpots;
	private SamplesComparisonTable samplesTable;

	protected Optional<Map<String, MascotIdentifications>> mascotIdentifications = 
		Optional.empty();

	private JToggleButton toggleVisualizationMode;

	private JButton showProteinIdentificationsBtn;

	
	public ConditionComparisonTable(List<Condition> conditions) {
		this.conditions = conditions;
		this.allSpots = getSpots(this.conditions);
		
		this.conditionsCount = new HashMap<Condition, SpotsCount>();
		this.conditionsThreshold = new HashMap<Condition, Range>();
		this.conditionsColors = new HashMap<Condition, Color>();
		this.samplesColors = new HashMap<Sample, Color>();
		this.samplesLabels = new HashMap<Sample, String>();
		this.conditions.forEach(c -> {
			this.conditionsCount.put(c, new SpotsCount(c, allSpots));
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
			this.northPanel = new JPanel(new BorderLayout());
			this.northPanel.setBorder(createEmptyBorder(2, 10, 2, 10));
			this.northPanel.setBackground(Color.WHITE);
			
			this.northPanel.add(getToolbar(), BorderLayout.NORTH);
			this.northPanel.add(getConditionFilteringPanel(), BorderLayout.CENTER);
		}
		return this.northPanel;
	}

	private JPanel getToolbar() {
		if(this.toolbar == null) {
			this.toolbar = new JPanel();
			this.toolbar.setLayout(new BoxLayout(this.toolbar, BoxLayout.X_AXIS));
			this.toolbar.setOpaque(false);
			this.toolbar.setBorder(createEmptyBorder(5, 0, 5, 0));
			
			this.toolbar.add(new JButton(getViewAsHeatmapAction()));
			this.toolbar.add(Box.createHorizontalStrut(10));
			this.toolbar.add(new JButton(getAddMascotIdentificationsAction()));
			this.toolbar.add(getVisualizationModeToggleButton());
			this.toolbar.add(getShowProteinIdentificationsButton());
		}
		return this.toolbar;
	}

	private Action getViewAsHeatmapAction() {
		return new AbstractAction("View as heatmap") {
			private static final long serialVersionUID = 1L;
			
			@Override
			public void actionPerformed(ActionEvent e) {
				viewAsHeatmap();
			}
		};
	}

	private void viewAsHeatmap() {
		JHeatMapDialog dialog = new JHeatMapDialog(null, this.samplesTable.getVisibleData());
		dialog.setVisible(true);
	}

	private Action getAddMascotIdentificationsAction() {
		return new AbstractAction("Add Mascot identifications") {
			private static final long serialVersionUID = 1L;
			
			@Override
			public void actionPerformed(ActionEvent e) {
				addMascotIdentifications();
			}
		};
	}
	
	protected void addMascotIdentifications() {
		LoadMascotIdentificationsDialog dialog = getMascotIdentificationsDialog();
		dialog.setVisible(true);
		if(!dialog.isCanceled()) {
			this.mascotIdentifications = 
				Optional.of(dialog.getMascotIdentifications());
			sortMascotIdentifications();
			this.samplesTable.setMascotIdentifications(
				this.mascotIdentifications.get());
			this.toggleVisualizationMode.setEnabled(true);
			this.showProteinIdentificationsBtn.setEnabled(true);
		}
	}

	protected LoadMascotIdentificationsDialog getMascotIdentificationsDialog() {
		return new LoadMascotIdentificationsDialog(null);
	}

	private void sortMascotIdentifications() {
		this.mascotIdentifications.get().values().forEach(list -> {
			Collections.sort(list, new Comparator<MascotEntry>() {

				@Override
				public int compare(MascotEntry o1, MascotEntry o2) {
					return o2.getMascotScore() - o1.getMascotScore();
				}
			});
		});
	}
	
	private JToggleButton getVisualizationModeToggleButton() {
		if(this.toggleVisualizationMode == null) {
			toggleVisualizationMode = new JToggleButton(
				"Show protein identifications", false);
			toggleVisualizationMode.setToolTipText(
				"Select this option to show protein identifications instead "
				+ "of spot numbers in the table");
			toggleVisualizationMode.setEnabled(false);
			toggleVisualizationMode
				.addItemListener(this::toggleVisualizationMode);
		}
		return toggleVisualizationMode;
	}
	
	private void toggleVisualizationMode(ItemEvent e) {
		this.samplesTable.setShowProteinIdentifications(
			toggleVisualizationMode.isSelected()
		);
	}
	
	private JButton getShowProteinIdentificationsButton() {
		if(this.showProteinIdentificationsBtn == null) {
			this.showProteinIdentificationsBtn = new JButton(
				new AbstractAction("View detail") {
				private static final long serialVersionUID = 1L;

				@Override
				public void actionPerformed(ActionEvent e) {
					showProteinIdentifications();
				}
			});
		}
		this.showProteinIdentificationsBtn.setEnabled(false);
		return this.showProteinIdentificationsBtn;
	}

	private void showProteinIdentifications() {
		MascotIdentificationsDialog dialog = new MascotIdentificationsDialog(
			null, getVisibleSpots(), mascotIdentifications.get());
		dialog.setVisible(true);
	}

	private JPanel getConditionFilteringPanel() {
		JPanel toret = new JPanel();
		toret.setLayout(new GridLayout(0, 2));
		
		getConditions().forEach(c -> {
			if (c.getSamples().size() > 1) {
				toret.add(createConditionFilteringPanel(c));
			}
		});
		
		return toret;
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
		StringBuilder sb = new StringBuilder();
		sb
			.append("Show spots present in at least ")
			.append(min)
			.append(" (")
			.append(String.format("%.2f%n", 100 * (double) min / (double) samples))
			.append("%) and at maximum ")
			.append(max)
			.append(" (of ")
			.append(samples)
			.append(") samples:");
		return sb.toString();
	}
	
	private static Set<String> getSpots(List<Condition> conditions) {
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
		
		this.samplesTable.setVisibleProteins(getVisibleSpots());
	}
	
	protected Set<String> getVisibleSpots() {
		Set<String> visibleSpots = new HashSet<String>(allSpots);

		conditions.forEach(c -> {
			Set<String> proteins = conditionsCount.get(c).getSpots(
				conditionsThreshold.get(c).getMin(), 
				conditionsThreshold.get(c).getMax()
			);
			visibleSpots.retainAll(proteins);
		});
		
		return visibleSpots;
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
