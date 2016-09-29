package es.uvigo.ei.sing.s2p.aibench.operations.dialogs;

import es.uvigo.ei.aibench.core.operation.annotation.Port;
import es.uvigo.ei.aibench.workbench.inputgui.ParamProvider;
import es.uvigo.ei.aibench.workbench.inputgui.ParamsWindow;

public class LoadSpotsDataDialog extends ParamsWindow {
	private static final long serialVersionUID = 1L;

	protected ParamProvider getParamProvider(final Port arg0,
		final Class<?> arg1, final Object arg2
	) {
		
		if (arg0.name().equals("CSV format")) {
			return new CsvParamProvider(arg0);
		}

		return super.getParamProvider(arg0, arg1, arg2);
	}
}
