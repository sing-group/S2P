/*
 * #%L
 * S2P GUI
 * %%
 * Copyright (C) 2016 - 2017 José Luis Capelo Martínez, José Eduardo Araújo, Florentino Fdez-Riverola, Miguel
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
package es.uvigo.ei.sing.s2p.gui.components;

import java.awt.CardLayout;
import java.awt.Component;
import java.awt.LayoutManager;

import javax.swing.Icon;
import javax.swing.JTabbedPane;

/**
 * An extension of {@code JTabbedPane} that adds useful features such as the 
 * possibility of tell the tabbed pane to hide the tab bar when there is
 * only one tab.
 * 
 * @author hlfernandez
 *
 */
public class ExtendedJTabbedPane extends JTabbedPane {
	private static final long serialVersionUID = 1L;
	
	private boolean hideTabBarWhenSingleTab = false;

	private LayoutManager defaultLayout;

	/**
	 * Sets whether the tab bar should be shown when there is only one tab.
	 * 
	 * @param hide {@code true} if the tab bar should be hidden when there is
	 * only one tab and {@code false} otherwise.
	 */
	public void setHideTabBarWhenSingleTab(boolean hide) {
		if (hideTabBarWhenSingleTab != hide) {
			this.hideTabBarWhenSingleTab = hide;
			defaultLayout = this.getLayout();
			this.updateLayout();
		}
	}

	private void updateLayout() {
		this.setLayout(getProperLayout());
	}

	private LayoutManager getProperLayout() {
		if (getTabCount() == 1 && hideTabBarWhenSingleTab) {
			return new CardLayout();
		} else {
			return defaultLayout;
		}
	}
	
	public void addTab(String title, Component component, boolean closeable) {
		super.addTab(title, component);
		if (closeable) {
			addCloseButton(component);
		}
		updateLayout();
	}
	
	private void addCloseButton(Component component) {
		super.setTabComponentAt(
				super.indexOfComponent(component), 
				new ButtonTabComponent(this)
			);
	}
	
	@Override
	public void addTab(String title, Component component) {
		addTab(title, component, false);
	}
	
	@Override
	public void addTab(String title, Icon icon, Component component) {
		super.addTab(title, icon, component);
		updateLayout();
	}
	
	@Override
	public void addTab(String title, Icon icon, Component component, String tip) {
		super.addTab(title, icon, component, tip);
		updateLayout();
	}
	
	@Override
	public void removeTabAt(int index) {
		super.removeTabAt(index);
		updateLayout();
	}
}