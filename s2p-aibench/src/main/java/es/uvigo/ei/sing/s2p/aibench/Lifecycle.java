/*
 * #%L
 * S2P
 * %%
 * Copyright (C) 2016 José Luis Capelo Martínez, José Eduardo Araújo, Florentino Fdez-Riverola, Miguel
 * 			Reboiro-Jato, Hugo López-Fernández, and Daniel Glez-Peña
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */
package es.uvigo.ei.sing.s2p.aibench;

import static es.uvigo.ei.aibench.workbench.inputgui.Common.SINGLE_FILE_CHOOSER;
import static es.uvigo.ei.aibench.workbench.utilities.ClearClipboardAction.ICON_24;
import static java.lang.System.getProperty;
import static javax.swing.SwingUtilities.invokeLater;
import static es.uvigo.ei.sing.s2p.gui.util.S2pIcons.ICON_HELP;

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

import org.sing_group.aibench.plugins.recentfiles.RecentFilesHistory;

import es.uvigo.ei.aibench.core.Core;
import es.uvigo.ei.aibench.workbench.MainWindow;
import es.uvigo.ei.aibench.workbench.Workbench;
import es.uvigo.ei.aibench.workbench.utilities.ClearClipboardAction;
import es.uvigo.ei.aibench.workbench.utilities.HelpButton;
import org.sing_group.gc4s.demo.DemoUtils;
import es.uvigo.ei.sing.s2p.aibench.gui.AboutFrame;
import es.uvigo.ei.sing.s2p.gui.util.CommonFileChooser;

public class Lifecycle extends org.platonos.pluginengine.PluginLifecycle {

	private static final String[] RECENT_FILES_OPERATIONS = new String[] {
		"operations.importsamespotsreport",
		"operations.importsamespotscsvfiles",
		"operations.loadspotsdata",
		"operations.importmascotidentifications",
		"operations.importmascotquantifications",
		"operations.loadmascotidentifications",
		"operations.loadspotmascotidentifications",
		"operations.loadmaldiplate"
	};

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
		configureHistoryListener();
	}

	private static final void configureLocale() {
		Locale.setDefault(Locale.ENGLISH);
		JComponent.setDefaultLocale(Locale.ENGLISH);
	}

	private void configureMainWindow() {
		DemoUtils.setNimbusKeepAlternateRowColor();

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
		toolBar.add(new HelpButton(ICON_HELP));
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

	private void configureHistoryListener() {
		RecentFilesHistory recentFilesHistory = new RecentFilesHistory(
			RECENT_FILES_OPERATIONS, getPlugin().getUID(),
			Workbench.getInstance().getMenuBar(), "File",
			new File(System.getProperty("user.home"), ".s2p-history"), 20
		);

		Core.getInstance().getHistory().addHistoryListener(recentFilesHistory);
	}
}
