package es.uvigo.ei.sing.s2p.aibench;

import static es.uvigo.ei.aibench.workbench.inputgui.Common.SINGLE_FILE_CHOOSER;

import java.awt.Color;
import java.util.Locale;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;

import es.uvigo.ei.aibench.workbench.MainWindow;
import es.uvigo.ei.aibench.workbench.Workbench;
import es.uvigo.ei.sing.s2p.gui.util.CommonFileChooser;

public class Lifecycle extends org.platonos.pluginengine.PluginLifecycle {
	
	private static final ImageIcon ICON =
		new ImageIcon(Lifecycle.class.getResource("/icons/s2p-icon.png"));

	@Override
	public void start() {
		SwingUtilities.invokeLater(() -> {
			configureLocale();
			
			MainWindow window = (MainWindow) Workbench.getInstance().getMainFrame();
			window.getDocumentTabbedPane().getParent().setBackground(Color.WHITE);
			
			JFrame mainFrame = Workbench.getInstance().getMainFrame();
			mainFrame.setIconImage(ICON.getImage());
			mainFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
			
			JToolBar toolBar = Workbench.getInstance().getToolBar();
			toolBar.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
			toolBar.setOpaque(true);
			toolBar.setBackground(Color.WHITE);
			toolBar.setFloatable(false);

			CommonFileChooser.getInstance().setFilechooser(SINGLE_FILE_CHOOSER);
		});
	}
	
	private static final void configureLocale() {
		Locale.setDefault(Locale.ENGLISH);
		JComponent.setDefaultLocale(Locale.ENGLISH);
	}
}