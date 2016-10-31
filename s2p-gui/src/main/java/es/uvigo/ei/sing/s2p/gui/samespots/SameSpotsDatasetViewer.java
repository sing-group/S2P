package es.uvigo.ei.sing.s2p.gui.samespots;

import static es.uvigo.ei.sing.hlfernandez.input.csv.CsvFormat.FileFormat.CUSTOM;
import static es.uvigo.ei.sing.hlfernandez.ui.icons.Icons.ICON_EDIT_16;
import static es.uvigo.ei.sing.hlfernandez.ui.icons.Icons.ICON_EXPORT_16;
import static es.uvigo.ei.sing.hlfernandez.utilities.builder.JButtonBuilder.newJButtonBuilder;
import static es.uvigo.ei.sing.s2p.core.io.samespots.SameSpotsCsvWriter.write;
import static es.uvigo.ei.sing.s2p.gui.UISettings.BG_COLOR;
import static es.uvigo.ei.sing.s2p.gui.util.CsvUtils.csvFormat;
import static java.util.Collections.emptyList;
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
import es.uvigo.ei.sing.hlfernandez.dialog.ExportCsvDialog;
import es.uvigo.ei.sing.hlfernandez.utilities.ExtendedAbstractAction;
import es.uvigo.ei.sing.s2p.core.entities.Sample;
import es.uvigo.ei.sing.s2p.gui.samples.SamplesComparisonTable;

public class SameSpotsDatasetViewer extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private List<Sample> samples;
	private List<String> conditionLabels = emptyList();
	
	private SamplesComparisonTable samplesTable;
	private JPanel northPanel;
	private JButton editSamplesBtn;
	private JButton editConditionsBtn;

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
