package es.uvigo.ei.sing.s2p.aibench;

import static es.uvigo.ei.aibench.workbench.inputgui.Common.SINGLE_FILE_CHOOSER;
import static es.uvigo.ei.aibench.workbench.utilities.ClearClipboardAction.ICON_24;
import static java.lang.System.getProperty;
import static javax.swing.SwingUtilities.invokeLater;

import java.awt.Color;
import java.io.File;
import java.util.Locale;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JSeparator;
import javax.swing.JToolBar;

import es.uvigo.ei.aibench.workbench.MainWindow;
import es.uvigo.ei.aibench.workbench.Workbench;
import es.uvigo.ei.aibench.workbench.utilities.ClearClipboardAction;
import es.uvigo.ei.sing.s2p.aibench.gui.AboutFrame;
import es.uvigo.ei.sing.s2p.gui.util.CommonFileChooser;

public class Lifecycle extends org.platonos.pluginengine.PluginLifecycle {
	
	private static final ImageIcon ICON =
		new ImageIcon(Lifecycle.class.getResource("/icons/s2p-icon.png"));

	@Override
	public void start() {
		invokeLater(this::configureApplication);
	}

	private final void configureApplication() {
		configureLocale();
		configureMainWindow();
		configureAIBenchToolbar();
		configureAIBenchMenu();
		configureFileChooser();
	}

	private static final void configureLocale() {
		Locale.setDefault(Locale.ENGLISH);
		JComponent.setDefaultLocale(Locale.ENGLISH);
	}

	private void configureMainWindow() {
		MainWindow window = (MainWindow) Workbench.getInstance().getMainFrame();
		window.getDocumentTabbedPane().getParent().setBackground(Color.WHITE);

		JFrame mainFrame = Workbench.getInstance().getMainFrame();
		mainFrame.setIconImage(ICON.getImage());
		mainFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
	}

	private void configureAIBenchToolbar() {
		JToolBar toolBar = Workbench.getInstance().getToolBar();
		toolBar.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
		toolBar.setOpaque(true);
		toolBar.setBackground(Color.WHITE);
		toolBar.setFloatable(false);
		toolBar.add(Box.createHorizontalGlue());
		toolBar.add(new ClearClipboardAction(ICON_24));
		AboutFrame.getInstance().addToJToolbar(toolBar);
	}

	private void configureAIBenchMenu() {
		JMenuBar menuBar = Workbench.getInstance().getMenuBar();
		menuBar.getMenu(0).add(new JSeparator());
		menuBar.getMenu(0).add(new ClearClipboardAction("Clear clipboard", ICON_24));
	}

	private void configureFileChooser() {
		SINGLE_FILE_CHOOSER.setCurrentDirectory(new File(getProperty("user.home")));
		CommonFileChooser.getInstance().setFilechooser(SINGLE_FILE_CHOOSER);
	}
}
