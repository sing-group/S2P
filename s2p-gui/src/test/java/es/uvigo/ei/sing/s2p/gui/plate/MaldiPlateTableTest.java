package es.uvigo.ei.sing.s2p.gui.plate;

import static es.uvigo.ei.sing.s2p.gui.TestUtils.setNimbusLookAndFeel;
import static es.uvigo.ei.sing.s2p.gui.TestUtils.showComponent;

import java.io.IOException;

import javax.swing.JFrame;

import es.uvigo.ei.sing.s2p.core.entities.MaldiPlate;
import es.uvigo.ei.sing.s2p.core.entities.MaldiPlate.Positions;

public class MaldiPlateTableTest {
	
	public static void main(String[] args) throws IOException {
		setNimbusLookAndFeel();
		showComponent(
			new MaldiPlateTable(
				new MaldiPlate(16 ,24, Positions.LETTERS, Positions.NUMBERS)
			), JFrame.MAXIMIZED_BOTH
		);
	}
}
