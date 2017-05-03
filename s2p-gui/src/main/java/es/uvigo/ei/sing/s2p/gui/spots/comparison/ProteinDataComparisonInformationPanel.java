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

import static es.uvigo.ei.sing.s2p.core.entities.Util.getConditionProteins;
import static es.uvigo.ei.sing.s2p.gui.UISettings.BG_COLOR;
import static java.util.Collections.emptySet;
import static javax.swing.BorderFactory.createTitledBorder;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import org.sing_group.gc4s.list.ExtendedDefaultListModel;
import es.uvigo.ei.sing.s2p.core.entities.Condition;
import es.uvigo.ei.sing.s2p.core.entities.SpotMascotIdentifications;
import es.uvigo.ei.sing.s2p.core.operations.StringSetComparison;
import es.uvigo.ei.sing.s2p.gui.components.VennDiagram;

public class ProteinDataComparisonInformationPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private Condition condition1;
	private Condition condition2;

	private JLabel informationLabel;
	private VennDiagram vennDiagram;
	private JRadioButton condition1Rbtn;
	private JRadioButton condition2Rbtn;
	private JRadioButton commonRbtn;
	private ExtendedDefaultListModel<String> itemsListModel;

	private boolean showProteinIdentifications = false;
	private Optional<SpotMascotIdentifications> mascotIdentifications =
		Optional.empty();

	private StringSetComparison setComparison;

	public ProteinDataComparisonInformationPanel(Condition condition1,
		Condition condition2
	) {
		this.condition1 = condition1;
		this.condition2 = condition2;
		
		this.initComponent();
		this.updateComponents();
	}

	private void initComponent() {
		this.setBorder(createTitledBorder("Condition comparison"));
		this.setBackground(BG_COLOR);
		
		this.setLayout(new BorderLayout());
		this.add(getVennDiagramPanel(), BorderLayout.CENTER);
		this.add(getItemsListPanel(), BorderLayout.EAST);
	}

	private Component getVennDiagramPanel() {
		JPanel vennDiagramPanel = new JPanel(new BorderLayout());
		vennDiagramPanel.add(getInformationLabel(), BorderLayout.NORTH);
		vennDiagramPanel.add(getVennDiagram(), BorderLayout.SOUTH);
		vennDiagramPanel.setOpaque(false);
		
		return vennDiagramPanel;
	}

	private JLabel getInformationLabel() {
		if (this.informationLabel == null) {
			this.informationLabel = new JLabel("", SwingConstants.LEFT);
			this.informationLabel.setFont(this.informationLabel.getFont().deriveFont(Font.PLAIN));
		}
		return this.informationLabel;
	}
	
	private VennDiagram getVennDiagram() {
		if (this.vennDiagram == null) {
			this.vennDiagram = new VennDiagram(
				this.condition1.getName(), this.condition2.getName(), 
				0d, 0d, 0d
			);
		}
		return this.vennDiagram;
	}

	private Component getItemsListPanel() {
		JPanel itemsListPanel = new JPanel(new BorderLayout());
		itemsListPanel.setOpaque(false);
		
		condition1Rbtn = new JRadioButton(this.condition1.getName());
		condition2Rbtn = new JRadioButton(this.condition2.getName());
		commonRbtn = new JRadioButton("Common");
		commonRbtn.setSelected(true);
		
		final ItemListener itemListener = e -> {
			if (e.getStateChange() == ItemEvent.SELECTED ) {
				modeChanged();
			}
		};
		condition1Rbtn.addItemListener(itemListener);
		condition2Rbtn.addItemListener(itemListener);
		commonRbtn.addItemListener(itemListener);
		
		ButtonGroup group = new ButtonGroup();
		group.add(condition1Rbtn);
		group.add(condition2Rbtn);
		group.add(commonRbtn);
		
		JPanel selectionPanel = new JPanel(new GridLayout(1, 3));
		selectionPanel.setBackground(BG_COLOR);
		selectionPanel.add(condition1Rbtn);
		selectionPanel.add(condition2Rbtn);
		selectionPanel.add(commonRbtn);
		
		itemsListModel = new ExtendedDefaultListModel<String>();
		itemsListModel.addElements(getCommonElements());
		JList<String> itemsList = new JList<String>(itemsListModel);
		itemsList.setFixedCellWidth(200);
		
		itemsListPanel.add(selectionPanel, BorderLayout.NORTH);
		itemsListPanel.add(new JScrollPane(itemsList), BorderLayout.CENTER);
		
		return itemsListPanel;
	}

	
	private void modeChanged() {
		updateItemsList();
	}

	private void updateItemsList() {
		List<String> items = new ArrayList<String>();
		
		if(condition1Rbtn.isSelected()) {
			items.addAll(getConditionUniqueElements(this.condition1));
		} else if(condition2Rbtn.isSelected()) {
			items.addAll(getConditionUniqueElements(this.condition2));
		} else {
			items.addAll(getCommonElements());	
		}
		
		this.itemsListModel.clear();
		this.itemsListModel.addElements(items);
		this.updateUI();
	}

	private Collection<? extends String> getConditionUniqueElements(
			Condition condition) {
		Set<String> conditionElemnents = getConditionElements(condition);
		conditionElemnents.removeAll(getCommonElements());
		return conditionElemnents;
	}

	private List<String> getCommonElements() {
		Set<String> condition1Proteins = getConditionElements(this.condition1);
		Set<String> condition2Proteins = getConditionElements(this.condition2);
		Set<String> intersection = new HashSet<String>(condition1Proteins);
		intersection.retainAll(condition2Proteins);
		return new ArrayList<String>(intersection);
	}

	private void updateComponents() {
		this.setComparison = new StringSetComparison(
			getConditionElements(this.condition1), 
			getConditionElements(this.condition2)
		);
		
		this.updateRadioButtons();
		this.updateItemsList();
		this.updateLabels();
		this.updateUI();
	}

	private void updateLabels() {
		StringBuilder sb = new StringBuilder();
		sb
			.append("<html>")
			.append(getItemLabel())
			.append(" present in any sample of: <ul><li>Both conditions: ")
			.append(this.setComparison.getIntersectionSize())
			.append(" </li><li> ")
			.append(this.condition1.getName())
			.append(" (only): ")
			.append(this.setComparison.getSet1UniqueSize())
			.append("  </li><li>  ")
			.append(this.condition2.getName())
			.append(" (only): ")
			.append(this.setComparison.getSet2UniqueSize())
			.append("</li></ul></html>");
		
		getInformationLabel().setText(sb.toString());
		getInformationLabel().updateUI();
		
		int max = Math.max(this.setComparison.getSet1Size(), this.setComparison.getSet2Size());
		getVennDiagram().setAreas(
			this.condition1.getName(), 
			this.condition2.getName(), 
			(double) this.setComparison.getSet1Size() / (double) max, 
			(double) this.setComparison.getSet2Size() / (double) max, 
			(double) this.setComparison.getIntersectionSize() / (double) max
		);
	}

	private void updateRadioButtons() {
		this.condition1Rbtn.setText(this.condition1.getName());
		this.condition2Rbtn.setText(this.condition2.getName());
		
		this.commonRbtn.setSelected(true);
	}

	private String getItemLabel() {
		return showProteinIdentifications ? "Proteins" : "Spots";
	}

	private Set<String> getConditionElements(Condition condition) {
		if(this.showProteinIdentifications) {
			if(this.mascotIdentifications.isPresent()) {
				return getConditionProteins(condition, this.mascotIdentifications.get());
			} else {
				return emptySet();
			}
		} else {
			return condition.getSpots();
		}
	}

	public void setComparison(Condition condition1, Condition condition2) {
		this.condition1 = condition1;
		this.condition2 = condition2;
		
		this.updateComponents();
	}

	public void setMascotIdentifications(
		SpotMascotIdentifications identifications
	) {
		this.mascotIdentifications  = Optional.of(identifications);
	}

	public void setShowProteinIdentifications(boolean show) {
		this.showProteinIdentifications  = show;
		
		this.updateComponents();
	}

	public void removeMascotIdentifications() {
		this.mascotIdentifications = Optional.empty();
	}
}
