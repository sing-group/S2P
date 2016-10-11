package es.uvigo.ei.sing.s2p.gui.spots;

import static es.uvigo.ei.sing.hlfernandez.ui.UIUtils.setOpaqueRecursive;
import static es.uvigo.ei.sing.hlfernandez.utilities.builder.JButtonBuilder.newJButtonBuilder;
import static es.uvigo.ei.sing.s2p.gui.UISettings.BG_COLOR;
import static es.uvigo.ei.sing.s2p.gui.util.ColorUtils.getSoftColor;
import static javax.swing.BorderFactory.createEmptyBorder;
import static javax.swing.BorderFactory.createTitledBorder;
import static javax.swing.Box.createHorizontalStrut;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Window;
import java.awt.event.ItemEvent;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.swing.Action;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.SwingUtilities;

import es.uvigo.ei.sing.hlfernandez.input.RangeInputPanel;
import es.uvigo.ei.sing.hlfernandez.utilities.ExtendedAbstractAction;
import es.uvigo.ei.sing.s2p.core.entities.Condition;
import es.uvigo.ei.sing.s2p.core.entities.MascotIdentifications;
import es.uvigo.ei.sing.s2p.core.entities.Sample;
import es.uvigo.ei.sing.s2p.core.entities.SpotsCount;
import es.uvigo.ei.sing.s2p.core.entities.SpotsData;
import es.uvigo.ei.sing.s2p.gui.UISettings;
import es.uvigo.ei.sing.s2p.gui.components.ExtendedJTabbedPane;
import es.uvigo.ei.sing.s2p.gui.event.ProteinDataComparisonEvent;
import es.uvigo.ei.sing.s2p.gui.event.ProteinDataComparisonListener;
import es.uvigo.ei.sing.s2p.gui.mascot.LoadMascotIdentificationsDialog;
import es.uvigo.ei.sing.s2p.gui.mascot.MascotIdentificationsDialog;
import es.uvigo.ei.sing.s2p.gui.mascot.MascotIdentificationsSummaryDialog;
import es.uvigo.ei.sing.s2p.gui.samples.SampleComparisonView;
import es.uvigo.ei.sing.s2p.gui.spots.comparison.ConditionVsConditionComparisonView;
import es.uvigo.ei.sing.s2p.gui.spots.condition.ConditionComparisonTable;
import es.uvigo.ei.sing.s2p.gui.spots.summary.ConditionsSummaryTable;

public class SpotsDataViewer extends JPanel implements
		ProteinDataComparisonListener {
	private static final long serialVersionUID = 1L;

	private static final ImageIcon VERSUS = new ImageIcon(
		SpotsDataViewer.class.getResource("icons/versus.png"));
	private static final ImageIcon TABLE = new ImageIcon(
		SpotsDataViewer.class.getResource("icons/table.png"));
	private static final ImageIcon CONDITION = new ImageIcon(
		SpotsDataViewer.class.getResource("icons/condition.png"));

	private ExtendedJTabbedPane tabbedPane;
	private JPanel northPanel;
	private JPanel toolbar;

	private JToggleButton toggleVisualizationMode;
	private JButton showProteinIdentificationsBtn;
	private JButton showProteinIdentificationsSummaryBtn;
	private JButton clearIdentificationsBtn;

	protected SpotsData data;
	private Set<String> allSpots;
	private Map<Condition, SpotsCount> conditionsCount;
	private Map<Condition, Range> conditionsThreshold;
	private Map<Condition, Color> conditionsColors;
	private Map<Sample, Color> samplesColors;
	private Map<Sample, String> samplesLabels;

	protected Optional<Map<String, MascotIdentifications>> mascotIdentifications = 
		Optional.empty();

	private ConditionComparisonTable conditionComparisonsTable;
	private ConditionsSummaryTable conditionsSummaryTable;
	private ConditionVsConditionComparisonView proteinComparisonView;

	public SpotsDataViewer(SpotsData data) {
		this.data = data;
		
		this.initData();
		this.initComponent();
	}

	private void initData() {
		this.allSpots = getSpots(getConditions());
		
		this.conditionsCount = new HashMap<Condition, SpotsCount>();
		this.conditionsThreshold = new HashMap<Condition, Range>();
		this.conditionsColors = new HashMap<Condition, Color>();
		this.samplesColors = new HashMap<Sample, Color>();
		this.samplesLabels = new HashMap<Sample, String>();

		getConditions().forEach(c -> {
			this.conditionsCount.put(c, new SpotsCount(c, allSpots));
			this.conditionsThreshold.put(c, new Range(0, c.getSamples().size()));
			Color conditionColor = getSoftColor(getConditions().indexOf(c));
			this.conditionsColors.put(c, conditionColor);
			
			c.getSamples().forEach(s -> {
				samplesColors.put(s, conditionColor);
				samplesLabels.put(s, c.getName());
			});
		});
	}

	private List<Condition> getConditions() {
		return this.data.getConditions();
	}

	private void initComponent() {
		this.setLayout(new BorderLayout());
		this.setBackground(BG_COLOR);

		this.add(getNorthPanel(), BorderLayout.NORTH);
		this.add(getTabbedPane(), BorderLayout.CENTER);
	}
	
	private JPanel getNorthPanel() {
		if(this.northPanel == null) {
			this.northPanel = new JPanel(new BorderLayout());
			this.northPanel.setBorder(createEmptyBorder(2, 10, 2, 10));
			this.northPanel.setBackground(BG_COLOR);
			
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
			
			this.toolbar.add(createHorizontalStrut(10));
			this.toolbar.add(getAddMascotIdentificationsButton());
			this.toolbar.add(getClearMascotIdentificationsButton());
			this.toolbar.add(createHorizontalStrut(5));
			this.toolbar.add(getVisualizationModeToggleButton());
			this.toolbar.add(getShowProteinIdentificationsButton());
			this.toolbar.add(getShowProteinIdentificationsSummaryButton());
		}
		return this.toolbar;
	}

	private JButton getAddMascotIdentificationsButton() {
		return 	newJButtonBuilder()
				.thatDoes(getAddMascotIdentificationsAction()).build();
	}

	private JButton getClearMascotIdentificationsButton() {
		this.clearIdentificationsBtn = newJButtonBuilder()
			.thatDoes(getClearMascotIdentificationsAction()).disabled().build();
		return this.clearIdentificationsBtn;
	}

	private Action getClearMascotIdentificationsAction() {
		return new ExtendedAbstractAction(
			"Clear identifications", this::clearMascotIdentifications);
	}
	
	private void clearMascotIdentifications() {
		this.mascotIdentifications = Optional.empty();
		this.setMascotIdentifications(Collections.emptyMap());
		this.enableIdentificationButtons(false);
	}

	private Action getAddMascotIdentificationsAction() {
		return new ExtendedAbstractAction(
			"Add identifications",
			this::addMascotIdentifications
		);
	}

	protected void addMascotIdentifications() {
		LoadMascotIdentificationsDialog dialog = getMascotIdentificationsDialog();
		dialog.setVisible(true);
		if(!dialog.isCanceled()) {
			this.setMascotIdentifications(dialog.getMascotIdentifications());
		}
	}

	private void setMascotIdentifications(
			Map<String, MascotIdentifications> identifications) {
		this.mascotIdentifications = Optional.of(identifications);
		sortMascotIdentifications();
			
		this.conditionComparisonsTable.setMascotIdentifications(
			identifications);
		this.conditionsSummaryTable.setMascotIdentifications(
			identifications);
		this.proteinComparisonView.setMascotIdentifications(
			identifications);
			
		this.enableIdentificationButtons(true);
	}

	private void enableIdentificationButtons(boolean enabled) {
		this.toggleVisualizationMode.setEnabled(enabled);
		this.showProteinIdentificationsBtn.setEnabled(enabled);
		this.showProteinIdentificationsSummaryBtn.setEnabled(enabled);
		this.clearIdentificationsBtn.setEnabled(enabled);
	}

	protected LoadMascotIdentificationsDialog getMascotIdentificationsDialog() {
		return new LoadMascotIdentificationsDialog(getDialogParent());
	}

	private void sortMascotIdentifications() {
		this.mascotIdentifications.get().values().forEach(list -> {
			Collections.sort(list, (o1, o2) -> {
					return o2.getMascotScore() - o1.getMascotScore();
				}
			);
		});
	}
	
	private JToggleButton getVisualizationModeToggleButton() {
		if(this.toggleVisualizationMode == null) {
			toggleVisualizationMode = new JToggleButton(
				"Show identifications", false);
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
		boolean show = toggleVisualizationMode.isSelected();
		this.conditionComparisonsTable.setShowProteinIdentifications(show);
		this.conditionsSummaryTable.setShowProteinIdentifications(show);
		this.proteinComparisonView.setShowProteinIdentifications(show);
	}
	
	private JButton getShowProteinIdentificationsButton() {
		if(this.showProteinIdentificationsBtn == null) {
			this.showProteinIdentificationsBtn = new JButton(
				new ExtendedAbstractAction(
					"View detail", this::showProteinIdentifications
				)
			);
		}
		this.showProteinIdentificationsBtn.setEnabled(false);
		return this.showProteinIdentificationsBtn;
	}

	private void showProteinIdentifications() {
		MascotIdentificationsDialog dialog = new MascotIdentificationsDialog(
			getDialogParent(), getVisibleSpots(), mascotIdentifications.get());
		dialog.setVisible(true);
	}

	private Window getDialogParent() {
		return SwingUtilities.getWindowAncestor(this);
	}

	private JButton getShowProteinIdentificationsSummaryButton() {
		if(this.showProteinIdentificationsSummaryBtn == null) {
			this.showProteinIdentificationsSummaryBtn = new JButton(
				new ExtendedAbstractAction(
					"View summary", this::showProteinIdentificationsSummary)
				);
		}
		this.showProteinIdentificationsSummaryBtn.setEnabled(false);
		return this.showProteinIdentificationsSummaryBtn;
	}
	
	private void showProteinIdentificationsSummary() {
		MascotIdentificationsSummaryDialog dialog = new MascotIdentificationsSummaryDialog(
			getDialogParent(), this.allSpots, this.mascotIdentifications.get());
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
		panel.setBorder(createTitledBorder(condition.getName()));
		panel.setBackground(BG_COLOR);
		
		RangeInputPanel rangeInput = new RangeInputPanel(0, 
			condition.getSamples().size(), "Minimum number of samples",
			"Maximum number of samples"
		);
		setOpaqueRecursive(rangeInput, false);
		rangeInput.setBorder(createEmptyBorder(2, 0, 2, 0));
		
		rangeInput.setBackground(UISettings.BG_COLOR);
		
		int numSamples = condition.getSamples().size();
		JLabel label = new JLabel(getLabelText(0, numSamples, numSamples));
		label.setFont(label.getFont().deriveFont(Font.BOLD));
		
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
		
		Set<String> visibleSpots = getVisibleSpots();
		this.conditionComparisonsTable.setVisibleProteins(visibleSpots);
		this.conditionsSummaryTable.setVisibleSpots(visibleSpots);
	}
	
	protected Set<String> getVisibleSpots() {
		Set<String> visibleSpots = new HashSet<String>(allSpots);

		getConditions().forEach(c -> {
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

	private Component getTabbedPane() {
		this.tabbedPane = new ExtendedJTabbedPane();
		this.tabbedPane.setHideTabBarWhenSingleTab(true);
		this.addTabs();
		
		return this.tabbedPane;
	}

	private void addTabs() {
		this.addFullComparisonTable();
		this.addConditionsSummaryTable();
		this.addConditionComparisonView();
	}

	private void addFullComparisonTable() {
		this.tabbedPane.addTab("", getConditionsComparisonTable());
		this.tabbedPane.setTabComponentAt(0, label("Table view", TABLE));
	}

	private void addConditionsSummaryTable() {
		this.tabbedPane.addTab("", getConditionsSummaryTable());
		this.tabbedPane.setTabComponentAt(1, label("Conditions summary", CONDITION));
	}

	private void addConditionComparisonView() {
		this.proteinComparisonView = 
			new ConditionVsConditionComparisonView(this.data);
		this.proteinComparisonView.addTableListener(this);
		this.tabbedPane.addTab("", proteinComparisonView);
		this.tabbedPane.setTabComponentAt(2, label("Comparison view", VERSUS));
	}
	
	private static Component label(String string, ImageIcon table2) {
		JLabel toret = new JLabel(string);
		toret.setIcon(table2);
		toret.setIconTextGap(5);
		return toret;
	}

	private Component getConditionsComparisonTable() {
		this.conditionComparisonsTable = new ConditionComparisonTable(
			getConditions(), this.samplesColors, this.samplesLabels
		); 
		return this.conditionComparisonsTable;
	}
	
	private Component getConditionsSummaryTable() {
		this.conditionsSummaryTable = new ConditionsSummaryTable(this.data);
		return this.conditionsSummaryTable;
	}

	@Override
	public void onSampleSelection(ProteinDataComparisonEvent event) {
		Sample[] samples = (Sample[]) event.getSource();
		this.tabbedPane.addTab(
			getSampleComparisonTabTitle(samples[0], samples[1]), 
			new SampleComparisonView(samples[0], samples[1]), true
		);
		this.tabbedPane.setSelectedIndex(this.tabbedPane.getComponentCount()-2);
	}

	private String getSampleComparisonTabTitle(Sample sample1, Sample sample2) {
		return sample1.getName() + " vs. " + sample2.getName();
	}

	@Override
	public void onSampleSelectionCleared(ProteinDataComparisonEvent event) { }
}
