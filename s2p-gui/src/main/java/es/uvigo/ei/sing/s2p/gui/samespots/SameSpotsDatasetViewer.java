package es.uvigo.ei.sing.s2p.gui.samespots;

import static es.uvigo.ei.sing.hlfernandez.input.csv.CsvFormat.FileFormat.CUSTOM;
import static es.uvigo.ei.sing.s2p.gui.UISettings.BG_COLOR;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import es.uvigo.ei.sing.hlfernandez.dialog.ExportCsvDialog;
import es.uvigo.ei.sing.s2p.core.entities.Sample;
import es.uvigo.ei.sing.s2p.core.io.csv.CsvFormat;
import es.uvigo.ei.sing.s2p.core.io.samespots.SameSpotsCsvWriter;
import es.uvigo.ei.sing.s2p.gui.samples.SamplesComparisonTable;

public class SameSpotsDatasetViewer extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private List<Sample> samples;
	private List<String> conditionLabels = Collections.emptyList();
	
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
			this.northPanel.setLayout(new BoxLayout(this.northPanel, BoxLayout.X_AXIS));
			this.northPanel.setOpaque(false);
			this.northPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
			this.northPanel.add(getEditConditionsButton());
			this.northPanel.add(getEditSamplesButton());
			this.northPanel.add(Box.createHorizontalGlue());
			this.northPanel.add(getExportToCsvButton());
		}
		return this.northPanel ;
	}

	private JButton getEditConditionsButton() {
		if(this.editConditionsBtn == null) {
			this.editConditionsBtn = new JButton(
				new AbstractAction("Edit conditions") {
					private static final long serialVersionUID = 1L;
		
					@Override
					public void actionPerformed(ActionEvent e) {
						editConditions();
					}
			});
		}
		return this.editConditionsBtn;
	}
	
	private JButton getEditSamplesButton() {
		if(this.editSamplesBtn == null) {
			this.editSamplesBtn = new JButton(
				new AbstractAction("Edit samples") {
					private static final long serialVersionUID = 1L;
					
					@Override
					public void actionPerformed(ActionEvent e) {
						editSamples();
					}
				});
			this.editSamplesBtn.setEnabled(false);
		}
		return this.editSamplesBtn;
	}
	
	private JButton getExportToCsvButton() {
		JButton toret = new JButton(
				new AbstractAction("Export to CSV") {
					private static final long serialVersionUID = 1L;
					
					@Override
					public void actionPerformed(ActionEvent e) {
						exportToCsv();
					}
				});
		return toret;
	}

	private Component getSamplesTable() {
		if (this.samplesTable == null) {
			this.samplesTable = new SamplesComparisonTable(this.samples);
			this.samplesTable.setOpaque(false);
			this.samplesTable.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		}
		return this.samplesTable;
	}
	
	private void editConditions() {
		ConditionLabelInputDialog inputDialog = new ConditionLabelInputDialog(null, 
			this.conditionLabels);
		inputDialog.setVisible(true);
		if (!inputDialog.isCanceled()) {
			this.conditionLabels = inputDialog.getConditionLabels();
			getEditSamplesButton().setEnabled(!this.conditionLabels.isEmpty());
		}
	}
	
	private void editSamples() {
		SampleEditorDialog editor = new SampleEditorDialog(null,
			this.samples, this.conditionLabels);
		editor.setVisible(true);
		if (!editor.isCanceled()) {
			sampleConditions = editor.getSampleConditions();
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
		ExportCsvDialog exportCsv = new ExportCsvDialog(null);
		exportCsv.setSelectedCsvFileFormat(CUSTOM);
		exportCsv.setVisible(true);
		if (!exportCsv.isCanceled()) {
			try {
				SameSpotsCsvWriter.write(
					exportCsv.getSelectedFile(), 
					samples,
					csvFormat(exportCsv.getSelectedCsvFormat()),
					sampleConditions
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

	private CsvFormat csvFormat(
		es.uvigo.ei.sing.hlfernandez.input.csv.CsvFormat source
	) {
		return new CsvFormat(
			source.getColumnSeparator(),
			source.getDecimalSeparator(), 
			source.isQuoteHeaders(),
			source.getLineBreak()
		);
	}
}
