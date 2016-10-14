package es.uvigo.ei.sing.s2p.gui.spots.condition;

import static es.uvigo.ei.sing.s2p.gui.UISettings.BG_COLOR;
import static es.uvigo.ei.sing.s2p.gui.components.dialog.SpotRendererDialog.getSpotRenderer;
import static java.util.stream.Collectors.toList;
import static javax.swing.BorderFactory.createEmptyBorder;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.swing.Action;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import org.jdesktop.swingx.action.AbstractActionExt;

import es.uvigo.ei.sing.hlfernandez.utilities.ExtendedAbstractAction;
import es.uvigo.ei.sing.s2p.core.entities.Condition;
import es.uvigo.ei.sing.s2p.core.entities.MascotIdentifications;
import es.uvigo.ei.sing.s2p.core.entities.Sample;
import es.uvigo.ei.sing.s2p.gui.samples.SamplesComparisonTable;
import es.uvigo.ei.sing.s2p.gui.spots.comparison.JHeatMapDialog;
import es.uvigo.ei.sing.s2p.gui.spots.heatmap.SpotRenderer;

public class ConditionComparisonTable extends JPanel {
	private static final long serialVersionUID = 1L;

	private List<Condition> conditions;
	private Map<Sample, Color> samplesColors;
	private Map<Sample, String> samplesLabels;

	private SamplesComparisonTable samplesTable;

	private boolean showProteinIdentifications = false;
	protected Optional<Map<String, MascotIdentifications>> mascotIdentifications
		= Optional.empty();

	public ConditionComparisonTable(List<Condition> conditions,
		Map<Sample, Color> samplesColors, Map<Sample, String> samplesLabels
	) {
		this.conditions = conditions;
		this.samplesColors = samplesColors;
		this.samplesLabels = samplesLabels;

		this.initComponent();
	}

	private void initComponent() {
		this.setLayout(new BorderLayout());

		this.add(getSamplesTable(), BorderLayout.CENTER);
	}

	private Component getSamplesTable() {
		this.samplesTable = new SamplesComparisonTable(
			getSamples(), getSampleColors(), getSampleLabels());

		this.samplesTable.setBorder(createEmptyBorder(1, 5, 1, 5));
		this.samplesTable.setBackground(BG_COLOR);

		this.samplesTable.addTableAction(getViewAsHeatmapAction());

		this.createHideConditionsActions();

		return this.samplesTable;
	}

	private void createHideConditionsActions() {
		this.conditions.forEach(c -> {
			samplesTable.addTableAction(getHideConditionAction(c));
		});
	}

	private Action getHideConditionAction(Condition c) {
		return new ConditionControlAction(c);
	}

	public class ConditionControlAction extends AbstractActionExt {
		private static final long serialVersionUID = 1L;
		private Condition condition;

		public ConditionControlAction(Condition c) {
			super("Condition: " + c.getName());
			this.condition = c;
			this.setSelected(true);
			setStateAction();
		}

		@Override
		public void actionPerformed(ActionEvent e) {
		}

		@Override
		public void itemStateChanged(final ItemEvent e) {
			setConditionVisibility(condition,
				e.getStateChange() == ItemEvent.SELECTED);
		}
	}

	private void setConditionVisibility(Condition c, boolean visible) {
		List<Integer> columnIndices = c.getSamples().stream()
			.map(getSamples()::indexOf).map(i -> (i + 1)).collect(toList());

		this.samplesTable.setColumnsVisibility(columnIndices, visible);
	}

	private List<Sample> getSamples() {
		return getConditions().stream().map(Condition::getSamples)
				.flatMap(Collection::stream).collect(Collectors.toList());
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

	private Action getViewAsHeatmapAction() {
		return new ExtendedAbstractAction(
			"View as heatmap", this::viewAsHeatmap);
	}

	private void viewAsHeatmap() {
		Optional<SpotRenderer> renderer = 
			getSpotRenderer(getRoot(), this.showProteinIdentifications);
		if (renderer.isPresent()) {
			JHeatMapDialog dialog = new JHeatMapDialog(getRoot(),
				this.samplesTable.getHeatMapModel(renderer.get()));
			dialog.setVisible(true);
		}
	}

	private Window getRoot() {
		return SwingUtilities.getWindowAncestor(this);
	}

	public void setMascotIdentifications(
			Map<String, MascotIdentifications> mascotIdentifications) {
		this.samplesTable.setMascotIdentifications(mascotIdentifications);
	}

	public void setShowProteinIdentifications(boolean show) {
		this.showProteinIdentifications = show;
		this.samplesTable.setShowProteinIdentifications(show);
	}

	public void setVisibleProteins(Set<String> visibleSpots) {
		this.samplesTable.setVisibleSpots(visibleSpots);
	}

	public void removeMascotIdentifications() {
		this.samplesTable.removeMascotIdentifications();
	}
}
