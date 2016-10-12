package es.uvigo.ei.sing.s2p.gui.spots.condition;

import static es.uvigo.ei.sing.s2p.gui.UISettings.BG_COLOR;
import static java.util.Optional.empty;
import static java.util.Optional.of;
import static javax.swing.BorderFactory.createEmptyBorder;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import es.uvigo.ei.sing.s2p.core.entities.Condition;
import es.uvigo.ei.sing.s2p.core.entities.MascotIdentifications;
import es.uvigo.ei.sing.s2p.core.entities.Sample;
import es.uvigo.ei.sing.s2p.gui.components.dialog.SpotRendererDialog;
import es.uvigo.ei.sing.s2p.gui.samples.SamplesComparisonTable;
import es.uvigo.ei.sing.s2p.gui.spots.comparison.JHeatMapDialog;
import es.uvigo.ei.sing.s2p.gui.spots.heatmap.SpotRenderer;
import es.uvigo.ei.sing.s2p.gui.spots.heatmap.SpotRenderer.IdentificationsMode;

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
		samplesTable = new SamplesComparisonTable(getSamples(),
				getSampleColors(), getSampleLabels());

		samplesTable.setBorder(createEmptyBorder(1, 5, 1, 5));
		samplesTable.setBackground(BG_COLOR);

		samplesTable.addTableAction(getViewAsHeatmapAction());

		return this.samplesTable;
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
		return new AbstractAction("View as heatmap") {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				viewAsHeatmap();
			}
		};
	}

	private void viewAsHeatmap() {
		Optional<SpotRenderer> renderer = getSpotRenderer();
		if (renderer.isPresent()) {
			JHeatMapDialog dialog = new JHeatMapDialog(getRoot(),
				this.samplesTable.getHeatMapModel(renderer.get()));
			dialog.setVisible(true);
		}
	}

	private Optional<SpotRenderer> getSpotRenderer() {
		if (!this.showProteinIdentifications) {
			return of(new SpotRenderer(IdentificationsMode.NONE, true));
		}

		SpotRendererDialog dialog = new SpotRendererDialog(getRoot());
		dialog.setVisible(true);

		if (!dialog.isCanceled()) {
			return of(dialog.getSelectedSpotRenderer());
		} else {
			return empty();
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
}
