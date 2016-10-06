package es.uvigo.ei.sing.s2p.gui.util;

import javax.swing.JFileChooser;

/**
 * 
 * A singleton class that provides a common file chooser to all the gui
 * components that request it. It is useful when integrating the application
 * into AIBench since the file chooser can be set using the
 * {@code setFileChooser} method.
 * 
 * @author hlfernandez
 *
 */
public class CommonFileChooser {
	private static final JFileChooser DEFAULT_FILECHOOSER = new JFileChooser(".");
	private static CommonFileChooser INSTANCE = null;
	private JFileChooser filechooser; 
    
	private CommonFileChooser() {
		filechooser = DEFAULT_FILECHOOSER;
	}
 
    private synchronized static void createInstance() {
        if (INSTANCE == null) { 
            INSTANCE = new CommonFileChooser();
        }
    }
 
    /**
     * Returns an instance of {@code CommonFileChooser}.
     * 
     * @return an instance of {@code CommonFileChooser}.
     */
	public static CommonFileChooser getInstance() {
		if (INSTANCE == null) {
			createInstance();
		}
		return INSTANCE;
	}

	/**
	 * Returns a {@code JFileChooser}.
	 * @return a {@code JFileChooser}.
	 */
	public JFileChooser getFilechooser() {
		return filechooser;
	}

	/**
	 * Establishes the {@code JFileChooser}.
	 * @param filechooser the {@code JFileChooser} to use.
	 */
	public void setFilechooser(JFileChooser filechooser) {
		this.filechooser = filechooser;
	}
}
