package es.uvigo.ei.sing.s2p.gui.components.dialog;

import static java.util.Optional.empty;
import static java.util.Optional.of;

import java.awt.Window;
import java.util.Optional;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JPanel;

import es.uvigo.ei.sing.hlfernandez.dialog.AbstractInputJDialog;
import es.uvigo.ei.sing.hlfernandez.input.InputParameter;
import es.uvigo.ei.sing.hlfernandez.input.InputParametersPanel;
import es.uvigo.ei.sing.s2p.gui.spots.heatmap.SpotRenderer;
import es.uvigo.ei.sing.s2p.gui.spots.heatmap.SpotRenderer.IdentificationsMode;

public class SpotRendererDialog extends AbstractInputJDialog {
	private static final long serialVersionUID = 1L;

	private JCheckBox showSpotName;
	private JComboBox<IdentificationsMode> identificationModeCmb;
	
	public SpotRendererDialog(Window parent) {
		super(parent);
	}

	@Override
	protected String getDialogTitle() {
		return "Customize heatmap";
	}

	@Override
	protected String getDescription() {
		return "This dialog allows you to select which information is used "
				+ "for heatmap rows.";
	}

	@Override
	protected JPanel getInputComponentsPane() {
		return new InputParametersPanel(getParameters());
	}

	private InputParameter[] getParameters() {
		InputParameter[] parameters = new InputParameter[2];
		parameters[0] = getShowSpotNameParameter();
		parameters[1] = getShowIdentificationModeParameter();
		return parameters;
	}

	private InputParameter getShowSpotNameParameter() {
		return new InputParameter(
			"Show spot name", 
			getShowSpotNameComponent(), 
			"Wether the spot name must be shown or not."
		);
	}

	private JComponent getShowSpotNameComponent() {
		showSpotName = new JCheckBox();
		showSpotName.setSelected(true);
		return showSpotName;
	}
	
	private InputParameter getShowIdentificationModeParameter() {
		return new InputParameter(
			"Show identifications", 
			getShowIdentificationsComponent(), 
			"Type of identifications to show."
		);
	}
	
	private JComponent getShowIdentificationsComponent() {
		identificationModeCmb = new JComboBox<IdentificationsMode>(
			IdentificationsMode.values()
		);
		return identificationModeCmb;
	}

	public SpotRenderer getSelectedSpotRenderer() {
		return new SpotRenderer(getIdentificationsMode(), isShowSpotName());
	}

	private IdentificationsMode getIdentificationsMode() {
		return (IdentificationsMode) identificationModeCmb.getSelectedItem();
	}

	private boolean isShowSpotName() {
		return showSpotName.isSelected();
	}
	
	@Override
	public void setVisible(boolean b) {
		this.okButton.setEnabled(true);
		this.pack();
		super.setVisible(b);
	}

	public static Optional<SpotRenderer> getSpotRenderer(Window parent,
		boolean showProteinIdentifications
	) {
		if (!showProteinIdentifications) {
			return of(new SpotRenderer(IdentificationsMode.NONE, true));
		}

		SpotRendererDialog dialog = new SpotRendererDialog(parent);
		dialog.setVisible(true);

		if (!dialog.isCanceled()) {
			return of(dialog.getSelectedSpotRenderer());
		} else {
			return empty();
		}
	}
}
