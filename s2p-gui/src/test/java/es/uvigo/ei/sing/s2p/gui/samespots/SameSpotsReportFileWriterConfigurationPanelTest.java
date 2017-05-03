package es.uvigo.ei.sing.s2p.gui.samespots;

import static org.sing_group.gc4s.demo.DemoUtils.showComponent;
import static org.sing_group.gc4s.demo.DemoUtils.setNimbusLookAndFeel;

import java.io.IOException;

public class SameSpotsReportFileWriterConfigurationPanelTest {
	
	public static void main(String[] args) throws IOException {
		setNimbusLookAndFeel();
		showComponent(new SameSpotsReportFileWriterConfigurationPanel());
	}
}
