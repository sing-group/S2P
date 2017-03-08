package es.uvigo.ei.sing.s2p.gui.samespots;

import static es.uvigo.ei.sing.hlfernandez.demo.DemoUtils.showComponent;
import static es.uvigo.ei.sing.hlfernandez.demo.DemoUtils.setNimbusLookAndFeel;

import java.io.IOException;

public class SameSpotsReportFileWriterConfigurationPanelTest {
	
	public static void main(String[] args) throws IOException {
		setNimbusLookAndFeel();
		showComponent(new SameSpotsReportFileWriterConfigurationPanel());
	}
}
