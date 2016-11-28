/*
 * #%L
 * S2P GUI
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
package es.uvigo.ei.sing.s2p.gui.spots.comparison;

import static es.uvigo.ei.sing.s2p.gui.UISettings.BG_COLOR;
import static java.util.stream.Collectors.toList;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ItemEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

import es.uvigo.ei.sing.s2p.core.entities.Condition;
import es.uvigo.ei.sing.s2p.core.entities.MascotIdentifications;
import es.uvigo.ei.sing.s2p.core.entities.Sample;
import es.uvigo.ei.sing.s2p.core.entities.SpotsData;
import es.uvigo.ei.sing.s2p.gui.event.ProteinDataComparisonEvent;
import es.uvigo.ei.sing.s2p.gui.event.ProteinDataComparisonListener;

public class ConditionVsConditionComparisonView extends JPanel 
	implements ProteinDataComparisonListener 
{
	private static final long serialVersionUID = 1L;
	
	private List<Condition> conditions;

	private JPanel northPane;
	private JPanel centerPane;
	private JComboBox<Object> condition1Cmb;
	private JComboBox<Object> condition2Cmb;
	private ProteinDataComparisonTable comparisonTable;
	private JPanel southPane;
	private ProteinDataComparisonInformationPanel comparisonInformationPanel;

	private JButton viewButton;
	private Sample[] sampleSelection;

	public ConditionVsConditionComparisonView(SpotsData data) {
		this.conditions = data.getConditions();
		this.init();
	}

	private void init() {
		this.setLayout(new BorderLayout());
		this.setBackground(BG_COLOR);
		
		this.add(getSplitPane(), BorderLayout.CENTER);
	}

	private JSplitPane getSplitPane() {
		JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		splitPane.setTopComponent(getTopComponent());
		splitPane.setBottomComponent(getBottomComponent());
		splitPane.setDividerLocation(250);
		splitPane.setOneTouchExpandable(true);
		return splitPane;
	}

	private Component getTopComponent() {
		JPanel topComponent = new JPanel(new BorderLayout());
		topComponent.add(getNorthPane(), BorderLayout.NORTH);
		topComponent.add(getCenterPane(), BorderLayout.CENTER);
		return topComponent;
	}

	private JPanel getNorthPane() {
		if(this.northPane == null) {
			this.northPane = new JPanel(new FlowLayout());
			this.northPane.setBackground(BG_COLOR);
			
			condition1Cmb = new JComboBox<>(getConditionsNames().toArray());
			condition2Cmb = new JComboBox<>(getConditionsNames().toArray());
			condition1Cmb.setSelectedIndex(0);
			condition2Cmb.setSelectedIndex(getConditionsNames().size() - 1);
			condition1Cmb.addItemListener(this::selectionChanged);
			condition2Cmb.addItemListener(this::selectionChanged);
			
			this.northPane.add(condition1Cmb);
			this.northPane.add(condition2Cmb);
			this.northPane.add(Box.createHorizontalGlue());
			this.northPane.add(getViewButton());
		}
		return this.northPane;
	}
	
	private Component getViewButton() {
		if (this.viewButton == null) {
			this.viewButton = new JButton("Compare samples");
			this.viewButton.setEnabled(false);
			this.viewButton.addMouseListener(new MouseAdapter() {

				@Override
				public void mouseClicked(MouseEvent e) {
					onViewButtonClick();
				}

			});
		}
		return this.viewButton;
	}
	
	private void onViewButtonClick() {
		fireSampleSelectionEvent(
			new ProteinDataComparisonEvent(
				this.sampleSelection, 
				ProteinDataComparisonEvent.Type.SAMPLE_SELECTION
			)
		);
	}
	
	private void fireSampleSelectionEvent(
			ProteinDataComparisonEvent proteinDataComparisonTableEvent) {
		Arrays.asList(getTableListeners()).stream()
			.forEach(l -> l.onSampleSelection(proteinDataComparisonTableEvent));
	}

	private void selectionChanged(ItemEvent e) {
		if (e.getStateChange() == ItemEvent.SELECTED) {
			Condition condition1 = getCondition1();
			Condition condition2 = getCondition2();
			
			this.comparisonTable.setComparison(condition1 , condition2);
			this.comparisonInformationPanel.setComparison(condition1, condition2);
		}
	}
	
	private Condition getCondition(int conditionIndex) {
		return this.conditions.get(conditionIndex);
	}
	
	public List<String> getConditionsNames() {
		return conditions.stream().map(Condition::getName).collect(toList());
	}
	
	private JPanel getCenterPane() {
		if(this.centerPane == null) {
			this.centerPane = new JPanel(new BorderLayout());
			this.comparisonTable = new ProteinDataComparisonTable(this.conditions);
			this.comparisonTable.addTableListener(this);
			this.centerPane.add(comparisonTable);
		}
		return this.centerPane;
	}
	
	private Component getBottomComponent() {
		if(this.southPane == null) {
			this.southPane = new JPanel();
			this.southPane.setBackground(BG_COLOR);
			
			Box box = new Box(BoxLayout.Y_AXIS);
			box.add(Box.createHorizontalGlue());
			box.add(getProteinDataInformationPanel());
			box.add(Box.createHorizontalGlue());
			this.southPane.add(box);
		}
		return this.southPane;
	}

	private Component getProteinDataInformationPanel() {
		this.comparisonInformationPanel = 
			new ProteinDataComparisonInformationPanel(getCondition1(), getCondition2());
		return this.comparisonInformationPanel;
	}

	private Condition getCondition1() {
		return getCondition(this.condition1Cmb.getSelectedIndex());
	}
	
	private Condition getCondition2() {
		return getCondition(this.condition2Cmb.getSelectedIndex());
	}

	@Override
	public void onSampleSelection(ProteinDataComparisonEvent event) {
		this.viewButton.setEnabled(true);
		this.sampleSelection = (Sample[]) event.getSource();
	}
	

	@Override
	public void onSampleSelectionCleared(ProteinDataComparisonEvent event) {
		this.viewButton.setEnabled(false);
		this.sampleSelection = null;
	}
	
	/**
	 * Adds the specified sample listener to receive sample events from this
	 * component. If listener {@code l} is  {@code null}, no exception is
	 * thrown and no action is performed.
	 * 
	 * @param l the sample listener.
	 */
	public void addTableListener(ProteinDataComparisonListener l) {
		this.listenerList.add(ProteinDataComparisonListener.class, l);
	}

	/**
	 * Removes the specified sample listener so that it no longer receives
	 * sample events from this component. This method performs no function, nor
	 * does it throw an exception, if the listener specified by the argument 
	 * was not previously added to this component. If listener {@code l} is 
	 * {@code null}, no exception is thrown and no action is performed.
	 * 
	 * @param l the sample listener.
	 */
	public void removeTableListener(ProteinDataComparisonListener l) {
		this.listenerList.remove(ProteinDataComparisonListener.class, l);
	}

	/**	
	 * Returns an array of all the sample listeners registered on this
	 * component.
	 * 
	 * @return all of this component's @{code SampleListener} or an empty
	 * array if no sample listeners are currently registered.
	 */
	public ProteinDataComparisonListener[] getTableListeners() {
		return this.listenerList.getListeners(ProteinDataComparisonListener.class);
	}

	public void setMascotIdentifications(
			Map<String, MascotIdentifications> identifications
	) {
		this.comparisonInformationPanel.setMascotIdentifications(identifications);
		this.comparisonTable.setMascotIdentifications(identifications);
	}

	public void setShowProteinIdentifications(boolean show) {
		this.comparisonInformationPanel.setShowProteinIdentifications(show);
		this.comparisonTable.setShowProteinIdentifications(show);
	}

	public void removeMascotIdentifications() {
		this.comparisonInformationPanel.removeMascotIdentifications();
		this.comparisonTable.removeMascotIdentifications();
	}
}
