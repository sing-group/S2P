package es.uvigo.ei.sing.s2p.gui.components;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import java.util.stream.Stream;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class RangeInputPanel2 extends JPanel {
	private static final long serialVersionUID = 1L;
	private int min;
	private int max;
	private JLabel minValueLabel;
	private JSlider minValueSlider;
	private JLabel maxValueLabel;
	private JSlider maxValueSlider;

	public RangeInputPanel2(int min, int max) {
		this.min = min;
		this.max = max;
		
		this.initComponent();
	}

	private void initComponent() {
		this.setLayout(new GridLayout(1, 2));
		this.setOpaque(false);
		this.add(this.getMinPanel(), BorderLayout.WEST);
		this.add(this.getMaxPanel(), BorderLayout.EAST);
		this.initializeSliders();
	}

	private Component getMinPanel() {
		JPanel minPanel = new JPanel(new BorderLayout());
		minPanel.setOpaque(false);
		
		minValueLabel = new JLabel("Minimum value");
		minValueSlider = new JSlider(min, max, min);
		
		minPanel.add(minValueLabel, BorderLayout.NORTH);
		minPanel.add(minValueSlider, BorderLayout.CENTER);
		return minPanel;
	}
	
	private Component getMaxPanel() {
		JPanel maxPanel = new JPanel(new BorderLayout());
		maxPanel.setOpaque(false);
		
		maxValueLabel = new JLabel("Maximum value");
		maxValueSlider = new JSlider(min, max, max);
		
		maxPanel.add(maxValueLabel, BorderLayout.NORTH);
		maxPanel.add(maxValueSlider, BorderLayout.CENTER);
		return maxPanel;
	}
	
	private void initializeSliders() {
		minValueSlider.setPaintTicks(true);
		minValueSlider.setPaintLabels(true);
		minValueSlider.setMajorTickSpacing(1);
		minValueSlider.setMinorTickSpacing(1);
		minValueSlider.addChangeListener((e) -> {
			if(minValueSlider.getValue() > maxValueSlider.getValue()) {
				maxValueSlider.setValue(minValueSlider.getValue());
			}
			rangeChanged();
		});
		
		maxValueSlider.setPaintTicks(true);
		maxValueSlider.setPaintLabels(true);
		maxValueSlider.setMajorTickSpacing(1);
		maxValueSlider.setMinorTickSpacing(1);
		maxValueSlider.addChangeListener((e) -> {
			if(maxValueSlider.getValue() < minValueSlider.getValue()) {
				minValueSlider.setValue(maxValueSlider.getValue());
			}
			rangeChanged();
		});
	}
	
	public int getMinValue() {
		return minValueSlider.getValue();
	}
	
	public int getMaxValue() {
		return maxValueSlider.getValue();
	}
	
	private void rangeChanged() {
		Stream.of(getListeners(ChangeListener.class)).forEach(l -> {
			l.stateChanged(new ChangeEvent(this));
		});
	}
	
	public void addChangeListener(ChangeListener c) {
		this.listenerList.add(ChangeListener.class, c);
	}
}
