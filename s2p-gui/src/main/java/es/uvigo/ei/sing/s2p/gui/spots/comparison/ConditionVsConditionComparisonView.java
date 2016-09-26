package es.uvigo.ei.sing.s2p.gui.spots.comparison;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ItemEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;

import es.uvigo.ei.sing.s2p.core.entities.Condition;
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

	private JButton viewButton;
	private Sample[] sampleSelection;

	public ConditionVsConditionComparisonView(SpotsData data) {
		this.conditions = data.getConditions();
		this.init();
	}

	private void init() {
		this.setLayout(new BorderLayout());
		this.add(getNorthPane(), BorderLayout.NORTH);
		this.add(getCenterPane(), BorderLayout.CENTER);
	}

	private JPanel getNorthPane() {
		if(this.northPane == null) {
			this.northPane = new JPanel(new FlowLayout());
			this.northPane.setBackground(Color.WHITE);
			
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
			this.viewButton.addMouseListener(new MouseListener() {
				
				@Override
				public void mouseReleased(MouseEvent e) {}
				
				@Override
				public void mousePressed(MouseEvent e) {}
				
				@Override
				public void mouseExited(MouseEvent e) {}
				
				@Override
				public void mouseEntered(MouseEvent e) {}
				
				@Override
				public void mouseClicked(MouseEvent e) {
					onViewButtonClick();
				}

			});
		}
		return this.viewButton;
	}
	
	private void onViewButtonClick() {
		fireSampleSelectionEvent(new ProteinDataComparisonEvent(this.sampleSelection, ProteinDataComparisonEvent.Type.SAMPLE_SELECTION));
	}
	
	private void fireSampleSelectionEvent(
			ProteinDataComparisonEvent proteinDataComparisonTableEvent) {
		Arrays.asList(getTableListeners()).stream()
			.forEach(l -> l.onSampleSelection(proteinDataComparisonTableEvent));
	}

	private void selectionChanged(ItemEvent e) {
		if (e.getStateChange() == ItemEvent.SELECTED) {
			comparisonTable.setComparison(this.condition1Cmb.getSelectedIndex(), this.condition2Cmb.getSelectedIndex());
		}
	}
	
	public List<String> getConditionsNames() {
		return conditions.stream().map(Condition::getName).collect(Collectors.toList());
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
}
