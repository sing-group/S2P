package es.uvigo.ei.sing.s2p.core.entities;

import static java.util.stream.Collectors.toList;
import static java.util.stream.IntStream.range;
import static java.util.stream.IntStream.rangeClosed;

import java.util.List;

public class MaldiPlate {

	private int rows;
	private int cols;
	private List<String> rowNames;
	private List<Object> colNames;
	private String[][] data;

	public MaldiPlate(int rows, int cols) {
		this.rows = rows;
		this.cols = cols;
		
		this.initData();
	}
	
	private void initData() {
		this.rowNames = range(0, this.rows).boxed().map(MaldiPlate::str).collect(toList());
		this.colNames = rangeClosed(1, this.cols).boxed().map(String::valueOf).collect(toList());
		this.data = new String[this.rows][this.cols];
	}

	private static String str(int i) {
		return i < 0 ? "" : str((i / 26) - 1) + (char) (65 + i % 26);
	}
	
	public String[][] getData() {
		return data;
	}
	
	public List<String> getRowNames() {
		return rowNames;
	}
	
	public List<Object> getColNames() {
		return colNames;
	}
}
