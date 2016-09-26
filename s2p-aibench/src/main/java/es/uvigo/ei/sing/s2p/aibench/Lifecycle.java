package es.uvigo.ei.sing.s2p.aibench;

import java.awt.Color;
import java.util.Locale;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;

import es.uvigo.ei.aibench.core.Core;
import es.uvigo.ei.aibench.core.clipboard.ClipboardItem;
import es.uvigo.ei.aibench.core.clipboard.ClipboardListener;
import es.uvigo.ei.aibench.workbench.Workbench;
import es.uvigo.ei.sing.s2p.aibench.datatypes.SameSpotsAnalysisDatatype;
import es.uvigo.ei.sing.s2p.aibench.datatypes.SpotsDataDatatype;
import es.uvigo.ei.sing.s2p.aibench.datatypes.VennDiagramDesignDatatype;

public class Lifecycle extends org.platonos.pluginengine.PluginLifecycle {
	
	private static final ImageIcon ICON =
		new ImageIcon(Lifecycle.class.getResource("/icons/s2p-icon.png"));
	
	private static final Class<?>[] AUTO_OPEN_CLASSES = new Class<?>[] { 
		SpotsDataDatatype.class, 
		VennDiagramDesignDatatype.class,
		SameSpotsAnalysisDatatype.class
	};

	@Override
	public void start() {
		SwingUtilities.invokeLater(() -> {
			configureLocale();
			
			JFrame mainFrame = Workbench.getInstance().getMainFrame();
			mainFrame.setIconImage(ICON.getImage());
			mainFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
			
			JToolBar toolBar = Workbench.getInstance().getToolBar();
			toolBar.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
			toolBar.setOpaque(true);
			toolBar.setBackground(Color.WHITE);
			toolBar.setFloatable(false);

			Core.getInstance().getClipboard().addClipboardListener(
				new BringViewListener(AUTO_OPEN_CLASSES));
		});
	}
	
	private static final void configureLocale() {
		Locale.setDefault(Locale.ENGLISH);
		JComponent.setDefaultLocale(Locale.ENGLISH);
	}
	
	private class BringViewListener implements ClipboardListener {
		private Class<?>[] autoOpenClasses;

		public BringViewListener(Class<?>[] autoOpenClasses) {
			this.autoOpenClasses = autoOpenClasses;
		}

		public void elementAdded(ClipboardItem item) {
			for (Class<?> autoOpen : this.autoOpenClasses) {
				if (autoOpen.isAssignableFrom(item.getUserData().getClass())) {
					Workbench.getInstance().showData(item);
					break;
				}
			}
		}

		public void elementRemoved(ClipboardItem item) {

		}
	}
}