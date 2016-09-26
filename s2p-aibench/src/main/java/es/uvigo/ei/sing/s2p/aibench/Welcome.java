package es.uvigo.ei.sing.s2p.aibench;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class Welcome extends JPanel {
	private static final long serialVersionUID = 1L;

	public Welcome() {
		this.initComponent();
	}

	private void initComponent() {
		this.add(new JLabel("S2P"));
	}
}
