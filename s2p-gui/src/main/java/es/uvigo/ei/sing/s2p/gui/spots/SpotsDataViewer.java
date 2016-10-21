package es.uvigo.ei.sing.s2p.gui.spots;

import static es.uvigo.ei.sing.hlfernandez.ui.UIUtils.setOpaqueRecursive;
import static es.uvigo.ei.sing.hlfernandez.utilities.builder.JButtonBuilder.newJButtonBuilder;
import static es.uvigo.ei.sing.s2p.gui.UISettings.BG_COLOR;
import static es.uvigo.ei.sing.s2p.gui.util.ColorUtils.getSoftColor;
import static java.awt.BorderLayout.CENTER;
import static java.awt.BorderLayout.NORTH;
import static java.util.Collections.emptySet;
import static java.util.stream.Collectors.toSet;
import static javax.swing.BorderFactory.createEmptyBorder;
import static javax.swing.BorderFactory.createTitledBorder;
import static javax.swing.Box.createHorizontalGlue;
import static javax.swing.Box.createHorizontalStrut;
import static javax.swing.BoxLayout.X_AXIS;
import static javax.swing.SwingUtilities.invokeLater;

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
import es.uvigo.ei.sing.hlfernandez.menu.HamburgerMenu;
import es.uvigo.ei.sing.hlfernandez.ui.icons.Icons;
import es.uvigo.ei.sing.hlfernandez.utilities.ExtendedAbstractAction;
import es.uvigo.ei.sing.hlfernandez.utilities.builder.JToggleButtonBuilder;
import es.uvigo.ei.sing.s2p.core.entities.Condition;
import es.uvigo.ei.sing.s2p.core.entities.MascotIdentifications;
import es.uvigo.ei.sing.s2p.core.entities.Pair;
import es.uvigo.ei.sing.s2p.core.entities.Sample;
import es.uvigo.ei.sing.s2p.core.entities.SpotsCount;
import es.uvigo.ei.sing.s2p.core.entities.SpotsData;
import es.uvigo.ei.sing.s2p.core.operations.SpotSummaryOperations;
import es.uvigo.ei.sing.s2p.core.operations.SpotSummaryOperations.DifferentialSpotFunction;
import es.uvigo.ei.sing.s2p.gui.components.ExtendedJTabbedPane;
import es.uvigo.ei.sing.s2p.gui.components.dialog.ConditionSelectionDialog;
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

	private static final String TOOLTIP_VISUALIZATION_UNSELECTED = 
		"Select this option to show protein identifications along with spot "
		+ "numbers in the table view.";
	private static final String TOOLTIP_VISUALIZATION_SELECTED = 
		"Select this option to hide protein identifications along with spot "
			+ "numbers in the table view.";
	private static final String TOOLTIP_IDENTIFIED_SPOTS_UNSELECTED = 
		"Select this option to show only spots with associated Mascot "
		+ "identifications.";
	private static final String TOOLTIP_IDENTIFIED_SPOTS_SELECTED = 
		"Select this option to disable this filter.";
	private static final String TOOLTIP_DIFFERENTIAL_SPOTS_UNSELECTED = 
		"Select this option to show only spots that are expressed differentially"
		+ " in two conditions.";
	private static final String TOOLTIP_DIFFERENTIAL_SPOTS_SELECTED = 
		"Select this option to disable this filter.";
	private static final String TOOLTIP_CONDITION_FILTERS_UNSELECTED = 
		"Select this option to show conditions' presence filters.";
	private static final String TOOLTIP_CONDITION_FILTERS_SELECTED = 
		"Select this option to hide conditions' presence filters.";

	private ExtendedJTabbedPane tabbedPane;
	private JPanel northPanel;
	private JPanel toolbar;
	private JPanel conditionFilteringPanel;

	private JToggleButton toggleVisualizationMode;
	private JToggleButton toggleFilterSpots;
	private JToggleButton toggleViewConditionFilters;
	private Action showProteinIdentificationsAction;
	private JToggleButton togleFilterDiferentialSpots;
	private Action showProteinIdentificationsSummaryAction;
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
	protected Optional<Set<String>> differentialSpots =	Optional.empty();

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

		this.add(getNorthPanel(), NORTH);
		this.add(getTabbedPane(), CENTER);
	}
	
	private JPanel getNorthPanel() {
		if(this.northPanel == null) {
			this.northPanel = new JPanel(new BorderLayout());
			this.northPanel.setBorder(createEmptyBorder(2, 10, 2, 10));
			this.northPanel.setBackground(BG_COLOR);
			
			this.northPanel.add(getToolbar(), NORTH);
			this.northPanel.add(getConditionFilteringPanel(), CENTER);
		}
		return this.northPanel;
	}
	
	private JPanel getToolbar() {
		if(this.toolbar == null) {
			this.toolbar = new JPanel();
			this.toolbar.setLayout(new BoxLayout(this.toolbar, X_AXIS));
			this.toolbar.setOpaque(false);
			this.toolbar.setBorder(createEmptyBorder(5, 0, 5, 0));
			
			this.toolbar.add(createHorizontalStrut(10));
			this.toolbar.add(getAddMascotIdentificationsButton());
			this.toolbar.add(getClearMascotIdentificationsButton());
			this.toolbar.add(createHorizontalStrut(5));
			this.toolbar.add(getVisualizationModeToggleButton());
			this.toolbar.add(getFilterSpotsToggleButton());
			this.toolbar.add(getFilterDifferentialSpotsButton());
			this.toolbar.add(getConditionFiltersVisibilityButton());
			this.toolbar.add(createHorizontalGlue());
			this.toolbar.add(getHamburgerMenu());
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
			"Clear identifications",
			Icons.ICON_TRASH_16,
			this::clearMascotIdentifications
		);
	}
	
	private void clearMascotIdentifications() {
		this.removeMascotIdentifications();
		this.differentialSpots = Optional.empty();
		this.togleFilterDiferentialSpots.setSelected(false);
		this.enableIdentificationButtons(false);
		this.updateVisibleSpots();
	}

	private void removeMascotIdentifications() {
		this.mascotIdentifications = Optional.empty();
		this.conditionComparisonsTable.removeMascotIdentifications();
		this.conditionsSummaryTable.removeMascotIdentifications();
		this.proteinComparisonView.removeMascotIdentifications();
	}

	private Action getAddMascotIdentificationsAction() {
		return new ExtendedAbstractAction(
			"Identifications",
			Icons.ICON_ADD_16,
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
		Map<String, MascotIdentifications> identifications
	) {
		this.mascotIdentifications = Optional.of(identifications);
		sortMascotIdentifications();
			
		this.conditionComparisonsTable.setMascotIdentifications(
			identifications);
		this.conditionsSummaryTable.setMascotIdentifications(
			identifications);
		this.proteinComparisonView.setMascotIdentifications(
			identifications);
			
		this.enableIdentificationButtons(true);
		this.updateVisibleSpots();
	}

	private void enableIdentificationButtons(boolean enabled) {
		this.clearIdentificationsBtn.setEnabled(enabled);
		this.toggleVisualizationMode.setEnabled(enabled);
		this.toggleFilterSpots.setEnabled(enabled);
		this.togleFilterDiferentialSpots.setEnabled(enabled);
		this.showProteinIdentificationsAction.setEnabled(enabled);
		this.showProteinIdentificationsSummaryAction.setEnabled(enabled);
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
			this.toggleVisualizationMode = JToggleButtonBuilder
				.newJToggleButton()
					.withLabel("Identifications")
					.withSelectedIcon(Icons.ICON_EYE_16)
					.withUnselectedIcon(Icons.ICON_EYE_HIDDEN_16)
					.setSelected(false)
					.setEnabled(false)
					.withTooltip(selected -> selected ? 
						TOOLTIP_VISUALIZATION_SELECTED : 
						TOOLTIP_VISUALIZATION_UNSELECTED)
					.thatDoes(this::toggleVisualizationMode)
				.build();
		}
		return this.toggleVisualizationMode;
	}
	
	private void toggleVisualizationMode(ItemEvent e) {
		boolean show = toggleVisualizationMode.isSelected();
		this.conditionComparisonsTable.setShowProteinIdentifications(show);
		this.conditionsSummaryTable.setShowProteinIdentifications(show);
		this.proteinComparisonView.setShowProteinIdentifications(show);
	}
	
	private JToggleButton getFilterSpotsToggleButton() {
		if(this.toggleFilterSpots == null) {
			this.toggleFilterSpots = JToggleButtonBuilder
				.newJToggleButton()
					.withLabel("Identified spots")
					.withSelectedIcon(Icons.ICON_EYE_16)
					.withUnselectedIcon(Icons.ICON_EYE_HIDDEN_16)
					.setSelected(false)
					.setEnabled(false)
					.withTooltip(selected -> selected ? 
						TOOLTIP_IDENTIFIED_SPOTS_SELECTED : 
						TOOLTIP_IDENTIFIED_SPOTS_UNSELECTED)
					.thatDoes(this::toggleFilterSpots)
				.build();
		}
		return this.toggleFilterSpots;
	}
	
	private void toggleFilterSpots(ItemEvent e) {
		this.updateVisibleSpots();
	}
	
	private JToggleButton getFilterDifferentialSpotsButton() {
		if(this.togleFilterDiferentialSpots == null) {
			this.togleFilterDiferentialSpots = JToggleButtonBuilder
				.newJToggleButton()
					.withLabel("Differential spots")
					.withSelectedIcon(Icons.ICON_EYE_16)
					.withUnselectedIcon(Icons.ICON_EYE_HIDDEN_16)
					.setSelected(false)
					.setEnabled(false)
					.withTooltip(selected -> selected ? 
						TOOLTIP_DIFFERENTIAL_SPOTS_SELECTED : 
						TOOLTIP_DIFFERENTIAL_SPOTS_UNSELECTED)
					.thatDoes(this::toggleViewDifferentialSpots)
				.build();
		}
		return this.togleFilterDiferentialSpots;
	}
	
	private void toggleViewDifferentialSpots(ItemEvent e) {
		if(e.getStateChange() == ItemEvent.SELECTED) {
			invokeLater(this::setDifferentialSpotsFilter);
		} else {
			this.setDiferentialSpotsFilter(emptySet());
		}
	}

	private JToggleButton getConditionFiltersVisibilityButton() {
		if(this.toggleViewConditionFilters == null) {
			this.toggleViewConditionFilters = JToggleButtonBuilder
				.newJToggleButton()
					.withLabel("Presence filters")
					.withSelectedIcon(Icons.ICON_EYE_16)
					.withUnselectedIcon(Icons.ICON_EYE_HIDDEN_16)
					.setSelected(true)
					.setEnabled(true)
					.withTooltip(selected -> selected ? 
						TOOLTIP_CONDITION_FILTERS_SELECTED : 
						TOOLTIP_CONDITION_FILTERS_UNSELECTED)
					.thatDoes(this::toggleViewConditionFilters)
				.build();
		}
		return this.toggleViewConditionFilters;
	}

	private void toggleViewConditionFilters(ItemEvent e) {
		boolean visible= e.getStateChange() == ItemEvent.SELECTED;
		this.conditionFilteringPanel.setVisible(visible);
	}

	private void setDifferentialSpotsFilter() {
		ConditionSelectionDialog dialog = 
			new ConditionSelectionDialog(getDialogParent(), getConditions());
		dialog.setVisible(true);
		if(!dialog.isCanceled()) {
			Pair<Condition, Condition> selection = dialog.getSelectedConditions();
			this.setDiferentialSpotsFilter( 
				findDifferentialSpots(
					selection.getFirst(),	selection.getSecond(), 
					dialog.getDiferentialSpotFunction(), 
					dialog.getSelectedPvalue(), 
					dialog.isCorrectPvalue()
				)
			);
		} else {
			this.togleFilterDiferentialSpots.setSelected(false);
		}
	}

	private void setDiferentialSpotsFilter(Set<String> differentialSpots) {
		this.differentialSpots = Optional.of(differentialSpots);
		this.updateVisibleSpots();
	}

	private boolean isDifferentialSpotFilterEnabled() {
		return 		this.mascotIdentifications.isPresent()
				&&	this.togleFilterDiferentialSpots.isSelected()
				&&	this.differentialSpots.isPresent();
	}

	private Set<String> findDifferentialSpots(Condition first, Condition second, 
		DifferentialSpotFunction function, double pValue, boolean correctP
	) {
		return SpotSummaryOperations.findDifferentialSpots(allSpots, first, 
			second, this.conditionsSummaryTable::getSpotSummary, function, pValue, correctP);
	}
	
	private JToggleButton getHamburgerMenu() {
		HamburgerMenu menu = new HamburgerMenu(HamburgerMenu.Size.SIZE16);
		menu.add(getShowProteinIdentificationsButton());
		menu.add(getShowProteinIdentificationsSummaryButton());
		return menu;
	}

	private Action getShowProteinIdentificationsButton() {
		if(this.showProteinIdentificationsAction == null) {
			this.showProteinIdentificationsAction = 
				new ExtendedAbstractAction(
					"Identifications detail", Icons.ICON_LOOKUP_16, 
					this::showProteinIdentifications
			);
		}
		this.showProteinIdentificationsAction.setEnabled(false);
		return this.showProteinIdentificationsAction;
	}

	private void showProteinIdentifications() {
		MascotIdentificationsDialog dialog = new MascotIdentificationsDialog(
			getDialogParent(), getVisibleSpots(), mascotIdentifications.get());
		dialog.setVisible(true);
	}

	private Window getDialogParent() {
		return SwingUtilities.getWindowAncestor(this);
	}

	private Action getShowProteinIdentificationsSummaryButton() {
		if(this.showProteinIdentificationsSummaryAction == null) {
			this.showProteinIdentificationsSummaryAction =
				new ExtendedAbstractAction(
					"Summary", Icons.ICON_INFO_16, 
					this::showProteinIdentificationsSummary
				);
		}
		this.showProteinIdentificationsSummaryAction.setEnabled(false);
		return this.showProteinIdentificationsSummaryAction;
	}
	
	private void showProteinIdentificationsSummary() {
		MascotIdentificationsSummaryDialog dialog =
			new MascotIdentificationsSummaryDialog(
				getDialogParent(), this.allSpots,
				this.mascotIdentifications.get()
			);
		dialog.setVisible(true);
	}

	private JPanel getConditionFilteringPanel() {
		conditionFilteringPanel = new JPanel();
		conditionFilteringPanel.setLayout(new GridLayout(0, 2));
		
		getConditions().forEach(c -> {
			if (c.getSamples().size() > 1) {
				conditionFilteringPanel.add(createConditionFilteringPanel(c));
			}
		});
		
		return conditionFilteringPanel;
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
		
		rangeInput.setBackground(BG_COLOR);
		
		int numSamples = condition.getSamples().size();
		JLabel label = new JLabel(getLabelText(0, numSamples, numSamples));
		label.setFont(label.getFont().deriveFont(Font.BOLD));
		
		rangeInput.addChangeListener(e -> {
			conditionFilterChanged(condition, rangeInput.getMinValue(),
				rangeInput.getMaxValue(), label);
		});
		
		panel.add(label, NORTH);
		panel.add(rangeInput, CENTER);
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

		this.updateVisibleSpots();
	}
	
	private void updateVisibleSpots() {
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
		
		if (isShowOnlyIdentifiedSpots()) {
			visibleSpots.retainAll(getIdentifiedspots());
		}

		if (isDifferentialSpotFilterEnabled()) {
			visibleSpots.retainAll(this.differentialSpots.get());
		}

		return visibleSpots;
	}

	private Set<String> getIdentifiedspots() {
		return 	allSpots.stream()
				.filter(this::isIdentifiedSpot).collect(toSet());
	}

	private boolean isIdentifiedSpot(String spot) {
		return 	this.mascotIdentifications.isPresent() && 
				this.mascotIdentifications.get().containsKey(spot);
	}

	private boolean isShowOnlyIdentifiedSpots() {
		return 	this.mascotIdentifications.isPresent() &&
				toggleFilterSpots.isSelected();
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
