package es.uvigo.ei.sing.s2p.gui.quantification;

import static es.uvigo.ei.sing.hlfernandez.ui.icons.Icons.ICON_TABLE_24;
import static es.uvigo.ei.sing.s2p.gui.UISettings.BG_COLOR;
import static java.awt.BorderLayout.CENTER;
import static java.awt.BorderLayout.EAST;
import static java.awt.BorderLayout.SOUTH;
import static java.lang.String.format;
import static javax.swing.BorderFactory.createEmptyBorder;
import static javax.swing.BorderFactory.createTitledBorder;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import es.uvigo.ei.sing.hlfernandez.visualization.ColorLegend;
import es.uvigo.ei.sing.s2p.core.entities.quantification.QuantificationDataset;
import es.uvigo.ei.sing.s2p.core.operations.quantification.comparison.ComparisonMode;
import es.uvigo.ei.sing.s2p.gui.components.ExtendedJTabbedPane;

public class QuantificationDatasetViewer extends JPanel {
	private static final long serialVersionUID = 1L;
	private static final String STATISTICAL_TESTS_MESSAGE = 
		"Condition comparison columns show both p-value and corrected p-value "
		+ "(or q-value) of the t-test between %s "
		+ "values in each condition. Correction of p-values is performed by "
		+ "applying the Benjamini-Hochberg FDR procedure.";

	private QuantificationDataset dataset;

	private ExtendedJTabbedPane tabbedPane;
	private QuantificationTable quantificationTable;
	private QuantificationReplicatesTable quantificationReplicatesTable;
	private QuantificationReplicatesByProteinTable quantificationReplicatesByProteinTable;
	private ComparisonMode comparisonMode;

	public QuantificationDatasetViewer(QuantificationDataset dataset,
		ComparisonMode comparisonMode
	) {
		this.dataset = dataset;
		this.comparisonMode = comparisonMode;

		this.initComponent();
	}

	private void initComponent() {
		this.setLayout(new BorderLayout());
		this.setBackground(BG_COLOR);
		this.add(getTabbedPane(), CENTER);
		this.add(getSouthPane(), SOUTH);
	}

	private Component getTabbedPane() {
		this.tabbedPane = new ExtendedJTabbedPane();
		this.tabbedPane.setHideTabBarWhenSingleTab(true);
		this.addTabs();

		return this.tabbedPane;
	}

	private void addTabs() {
		this.addQuantificationReplicatesTable();
		this.addQuantificationReplicatesByProteinTable();
		this.addQuantificationTable();
	}

	private void addQuantificationReplicatesTable() {
		this.tabbedPane.addTab("", getQuantificationReplicatesTable());
		this.tabbedPane.setTabComponentAt(0, label("Replicates", ICON_TABLE_24));
	}

	private Component getQuantificationReplicatesTable() {
		this.quantificationReplicatesTable =
			new QuantificationReplicatesTable(this.dataset);
		return new JScrollPane(this.quantificationReplicatesTable);
	}

	private static Component label(String string, ImageIcon icon) {
		JLabel toret = new JLabel(string);
		toret.setIcon(icon);
		toret.setIconTextGap(5);
		return toret;
	}

	private void addQuantificationReplicatesByProteinTable() {
		this.tabbedPane.addTab("", getQuantificationReplicatesByProteinTable());
		this.tabbedPane.setTabComponentAt(1, label("Replicates (by protein)", 
			ICON_TABLE_24));
	}
	
	private Component getQuantificationReplicatesByProteinTable() {
		this.quantificationReplicatesByProteinTable = new QuantificationReplicatesByProteinTable(this.dataset);
		return new JScrollPane(this.quantificationReplicatesByProteinTable);
	}

	private void addQuantificationTable() {
		this.tabbedPane.addTab("", getQuantificationTable());
		this.tabbedPane.setTabComponentAt(2, label("Conditions", 
			ICON_TABLE_24));
	}
	
	private Component getQuantificationTable() {
		this.quantificationTable =
			new QuantificationTable(this.dataset, comparisonMode.getTest());
		return new JScrollPane(this.quantificationTable);
	}

	private Component getSouthPane() {
		JPanel pane = new JPanel(new BorderLayout());
		pane.setBorder(createEmptyBorder(5, 0, 5, 0));
		pane.setOpaque(true);
		pane.setBackground(BG_COLOR);

		pane.add(getStatisticalTetsMessage(), CENTER);
		pane.add(getColorLegend(), EAST);

		return pane;
	}

	private Component getStatisticalTetsMessage() {
		JTextArea toret = new JTextArea();
		toret.setEditable(false);
		toret.setBorder(
			createTitledBorder(createEmptyBorder(0, 2, 0, 2), "Note")
		);
		toret.setLineWrap(true);
		toret.setWrapStyleWord(true);
		toret.setRows(2);
		toret.setText(getStatisticalTestsMessage());
		return toret;
	}

	private Component getColorLegend() {
		JPanel colorLegend = new ColorLegend(getConditionColorMap());
		colorLegend.setBorder(
			createTitledBorder(createEmptyBorder(0, 2, 0, 2), "Color legend")
		);
		colorLegend.setBackground(BG_COLOR);
		return colorLegend;
	}

	private Map<String, Color> getConditionColorMap() {
		Map<String, Color> conditionToColor = new HashMap<>();
		this.quantificationTable.getConditionColors()
			.forEach((condition, color) -> {
				conditionToColor.put(condition.getName(), color);
			});
		return conditionToColor;
	}

	private String getStatisticalTestsMessage() {
		return format(STATISTICAL_TESTS_MESSAGE, comparisonMode.getLabel());
	}
}
