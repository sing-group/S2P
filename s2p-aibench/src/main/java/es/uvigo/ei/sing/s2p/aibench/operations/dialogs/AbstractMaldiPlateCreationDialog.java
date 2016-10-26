package es.uvigo.ei.sing.s2p.aibench.operations.dialogs;

import javax.swing.JComboBox;

import es.uvigo.ei.aibench.core.operation.annotation.Port;
import es.uvigo.ei.aibench.workbench.inputgui.ParamProvider;
import es.uvigo.ei.aibench.workbench.inputgui.ParamsWindow;
import es.uvigo.ei.sing.s2p.core.entities.MaldiPlate.Positions;

public class AbstractMaldiPlateCreationDialog extends ParamsWindow {
	private static final long serialVersionUID = 1L;

	public static final String ROWS_POSITIONS = "Rows type"; 
	public static final String COLUMNS_POSITIONS = "Columns type"; 

	protected ParamProvider getParamProvider(final Port arg0,
		final Class<?> arg1, final Object arg2
	) {
		
		ParamProvider toret = super.getParamProvider(arg0, arg1, arg2);
		
		if (arg0.name().equals(ROWS_POSITIONS)) {
			JComboBox<?> checkbox = (JComboBox<?>) toret.getComponent();
			checkbox.setSelectedItem(Positions.LETTERS);
		} else if (arg0.name().equals(COLUMNS_POSITIONS)) {
			JComboBox<?> checkbox = (JComboBox<?>) toret.getComponent();
			checkbox.setSelectedItem(Positions.NUMBERS);
		} 

		return toret;
	}
}
