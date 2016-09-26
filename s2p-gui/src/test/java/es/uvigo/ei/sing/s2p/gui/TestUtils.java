package es.uvigo.ei.sing.s2p.gui;

import java.awt.Dimension;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

public class TestUtils {
	
	/**
	 * Shows a JFrame containing the specified <code>component</code>.
	 * 
	 * @param component JComponent to show
	 * @param size the frame size.
	 */
	public static final void showComponent(JComponent component, Dimension size) {
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(component);
		frame.pack();
		frame.setVisible(true);
		frame.setMinimumSize(size);
	}
	
	/**
	 * Shows a JFrame containing the specified <code>component</code>.
	 * 
	 * @param component
	 *            JComponent to show
	 */
	public static final void showComponent(JComponent component, int state) {
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(component);
		frame.pack();
		frame.setVisible(true);
		frame.setExtendedState(state);
	}
	
	public static final void setNimbusLookAndFeel() {
		try {
		    for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
		        if ("Nimbus".equals(info.getName())) {
		            UIManager.setLookAndFeel(info.getClassName());
		            break;
		        }
		    }
		} catch (Exception e) {
		    // If Nimbus is not available, you can set the GUI to another look and feel.
		}
	}
}
