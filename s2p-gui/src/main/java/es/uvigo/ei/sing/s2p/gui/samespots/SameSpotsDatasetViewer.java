/*
 * #%L
 * S2P GUI
 * %%
 * Copyright (C) 2016 José Luis Capelo Martínez, José Eduardo Araújo, Florentino Fdez-Riverola, Miguel
 * 			Reboiro-Jato, Hugo López-Fernández, and Daniel Glez-Peña
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */
package es.uvigo.ei.sing.s2p.gui.samespots;

import static org.sing_group.gc4s.input.csv.CsvFormat.FileFormat.CUSTOM;
import static org.sing_group.gc4s.ui.icons.Icons.ICON_EDIT_16;
import static org.sing_group.gc4s.ui.icons.Icons.ICON_EXPORT_16;
import static org.sing_group.gc4s.ui.icons.Icons.ICON_MERGE_16;
import static org.sing_group.gc4s.utilities.builder.JButtonBuilder.newJButtonBuilder;
import static es.uvigo.ei.sing.s2p.core.io.samespots.SameSpotsCsvWriter.write;
import static es.uvigo.ei.sing.s2p.gui.UISettings.BG_COLOR;
import static es.uvigo.ei.sing.s2p.gui.util.CsvUtils.csvFormat;
import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.joining;
import static javax.swing.BorderFactory.createEmptyBorder;
import static javax.swing.Box.createHorizontalGlue;
import static javax.swing.BoxLayout.X_AXIS;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Window;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.Action;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import es.uvigo.ei.sing.commons.csv.entities.CsvFormat;
import org.sing_group.gc4s.utilities.ExtendedAbstractAction;
import es.uvigo.ei.sing.s2p.core.entities.Sample;
import es.uvigo.ei.sing.s2p.gui.components.dialog.ExportCsvDialog;
import es.uvigo.ei.sing.s2p.gui.samples.SamplesComparisonTable;

public class SameSpotsDatasetViewer extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private List<Sample> samples;
	private List<String> conditionLabels = emptyList();
	
	private SamplesComparisonTable samplesTable;
	private JPanel northPanel;
	private JButton editConditionsBtn;
	private JButton editSamplesBtn;
	private JButton mergeSamplesBtn;

	private Map<Sample, String> sampleConditions = new HashMap<Sample, String>();

	public SameSpotsDatasetViewer(List<Sample> samples) {
		this.samples = samples;
		
		this.initComponent();
	}

	private void initComponent() {
		this.setLayout(new BorderLayout());
		this.setBackground(BG_COLOR);
		this.add(getNorthPanel(), BorderLayout.NORTH);
		this.add(getSamplesTable(), BorderLayout.CENTER);
	}

	private Component getNorthPanel() {
		if(this.northPanel == null) {
			this.northPanel = new JPanel();
			this.northPanel.setLayout(new BoxLayout(this.northPanel, X_AXIS));
			this.northPanel.setOpaque(false);
			this.northPanel.setBorder(createEmptyBorder(10, 10, 0, 10));
			this.northPanel.add(getEditConditionsButton());
			this.northPanel.add(getEditSamplesButton());
			this.northPanel.add(getMergeSamplesButton());
			this.northPanel.add(createHorizontalGlue());
			this.northPanel.add(getExportToCsvButton());
		}
		return this.northPanel ;
	}

	private JButton getEditConditionsButton() {
		if(this.editConditionsBtn == null) {
			this.editConditionsBtn = newJButtonBuilder()
				.withTooltip("Edit experiment's conditions.")
				.thatDoes(getEditConditionsAction())
				.build();			
		}
		return this.editConditionsBtn;
	}
	
	private Action getEditConditionsAction() {
		return new ExtendedAbstractAction("Conditions", ICON_EDIT_16, 
			this::editConditions);
	}

	private JButton getEditSamplesButton() {
		if(this.editSamplesBtn == null) {
			this.editSamplesBtn = newJButtonBuilder()
				.disabled()
				.withTooltip("Edit experiment's samples.")
				.thatDoes(getEditSamplesAction())
				.build();
		}
		return this.editSamplesBtn;
	}
	
	private Action getEditSamplesAction() {
		return new ExtendedAbstractAction("Samples", ICON_EDIT_16, 
			this::editSamples);
	}

	private JButton getMergeSamplesButton() {
		if(this.mergeSamplesBtn == null) {
			this.mergeSamplesBtn = newJButtonBuilder()
				.withTooltip("Merge experiment's samples.")
				.thatDoes(getMergeSamplesAction())
				.build();
		}
		return this.mergeSamplesBtn;
	}

	private Action getMergeSamplesAction() {
		return new ExtendedAbstractAction("Merge", ICON_MERGE_16, 
				this::mergeSamples);
	}

	private JButton getExportToCsvButton() {
		return newJButtonBuilder()
			.withTooltip("Export into a .CSV file")
			.thatDoes(getExportToCsvAction())
			.build();
	}

	private Action getExportToCsvAction() {
		return new ExtendedAbstractAction("Export to CSV", ICON_EXPORT_16, 
			this::exportToCsv);
	}

	private Component getSamplesTable() {
		if (this.samplesTable == null) {
			this.samplesTable = new SamplesComparisonTable(this.samples);
			this.samplesTable.setShowComponentPopupMenu(true);
			this.samplesTable.setOpaque(false);
			this.samplesTable.setBorder(createEmptyBorder(10, 10, 10, 10));
		}
		return this.samplesTable;
	}

	private void editConditions() {
		ConditionLabelInputDialog inputDialog = new ConditionLabelInputDialog(
			getDialogParent(), 
			this.conditionLabels
		);
		inputDialog.setVisible(true);
		if (!inputDialog.isCanceled()) {
			this.conditionLabels = inputDialog.getConditionLabels();
			getEditSamplesButton().setEnabled(!this.conditionLabels.isEmpty());
		}
	}
	
	private Window getDialogParent() {
		return SwingUtilities.getWindowAncestor(this);
	}

	private void editSamples() {
		SampleEditorDialog editor = new SampleEditorDialog(
			getDialogParent(),
			this.samples, 
			this.conditionLabels, 
			this.sampleConditions
		);
		editor.setVisible(true);
		if (!editor.isCanceled()) {
			this.sampleConditions = editor.getSampleConditions();
			updateSampleNames(editor.getSampleNames());
		}
	}

	private void updateSampleNames(Map<Sample, String> names) {
		names.keySet().forEach(sample -> {
			String newName = names.get(sample);
			sample.setName(newName);
		});
		this.samplesTable.fireTableStructureChanged();
	}

	private void mergeSamples() {
		SampleMergeDialog merger = new SampleMergeDialog(
			getDialogParent(), this.samples);
		merger.setVisible(true);
		if(!merger.isCanceled()) {
			mergeSamples(merger.getSelectedItems());
		}
	}
	
	private void mergeSamples(List<Sample> toMerge) {
		if (toMerge.isEmpty()) {
			return;
		}

		Sample mergedSample = mergedSample(toMerge);
		int firstSampleIndex = this.samples.indexOf(toMerge.get(0));

		this.samples.removeAll(toMerge);
		this.samples.add(firstSampleIndex, mergedSample);
		this.samplesTable.fireTableStructureChanged();
	}

	private String mergedSampleName(List<Sample> toMerge) {
		return toMerge.stream().map(Sample::getName).collect(joining("+"));
	}

	public Sample mergedSample(List<Sample> toMerge) {
		return new Sample(mergedSampleName(toMerge), mergedSpotValues(toMerge));
	}

	private Map<String, Double> mergedSpotValues(List<Sample> toMerge) {
		Map<String, Double> spotValues = new HashMap<>();
		toMerge.stream().map(Sample::getSpotValues).forEach(spotValues::putAll);
		return spotValues;
	}

	private void exportToCsv() {
		ExportCsvDialog exportCsv = new ExportCsvDialog(getDialogParent());
		exportCsv.setSelectedCsvFileFormat(CUSTOM);
		exportCsv.setVisible(true);
		if (!exportCsv.isCanceled()) {
			try {
				exportToCsv(
					exportCsv.getSelectedFile(), 
					csvFormat(exportCsv.getSelectedCsvFormat())
				);
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null,
					"There was an error while saving data into "
					+ exportCsv.getSelectedFile(), "Error",
					JOptionPane.ERROR_MESSAGE
				);
			}
		}
	}

	protected void exportToCsv(File file, CsvFormat csvFormat)
		throws IOException 
	{
		write(file, samples, csvFormat, sampleConditions);
	}
}
