package es.uvigo.ei.sing.s2p.aibench.operations.dialogs;

import static es.uvigo.ei.sing.s2p.gui.util.CsvUtils.csvFormat;

import java.util.LinkedList;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.SwingUtilities;

import es.uvigo.ei.aibench.core.CoreUtils;
import es.uvigo.ei.aibench.core.ParamSpec;
import es.uvigo.ei.aibench.core.operation.annotation.Port;
import es.uvigo.ei.aibench.workbench.inputgui.AbstractParamProvider;
import es.uvigo.ei.sing.commons.csv.entities.CsvFormat;
import es.uvigo.ei.sing.s2p.gui.util.CsvPanel;

class CsvParamProvider extends AbstractParamProvider {
	private CsvPanel csvPanel = new CsvPanel();
	private Port arg0;

	public CsvParamProvider(final Port arg0) {
		this.arg0 = arg0;
		this.csvPanel.addCsvListener(e -> {
			SwingUtilities.invokeLater(() -> {
				this.setChanged();
				this.notifyObservers();
			});
		});
	}

	public JComponent getComponent() {
		return this.csvPanel;
	}

	public ParamSpec getParamSpec() throws IllegalArgumentException {
		List<CsvFormat> params = new LinkedList<>();
		params.add(csvFormat(csvPanel.getCsvFormat()));

		return CoreUtils.createParams(params)[0];
	}

	public Port getPort() {
		return arg0;
	}

	@Override
	public boolean isValidValue() {
		return this.csvPanel.isValidFormat();
	}
};