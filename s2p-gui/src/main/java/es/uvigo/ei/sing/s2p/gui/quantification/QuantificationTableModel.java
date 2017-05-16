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
package es.uvigo.ei.sing.s2p.gui.quantification;

import static es.uvigo.ei.sing.s2p.core.operations.quantification.comparison.QuantificationsConditionsTest.compare;
import static java.lang.Double.NaN;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.swing.table.DefaultTableModel;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

import es.uvigo.ei.sing.s2p.core.entities.quantification.ProteinQuantification;
import es.uvigo.ei.sing.s2p.core.entities.quantification.QuantificationCondition;
import es.uvigo.ei.sing.s2p.core.entities.quantification.QuantificationConditionsComparison;
import es.uvigo.ei.sing.s2p.core.entities.quantification.QuantificationConditionsComparisons;
import es.uvigo.ei.sing.s2p.core.entities.quantification.QuantificationDataset;
import es.uvigo.ei.sing.s2p.core.entities.quantification.QuantificationReplicate;
import es.uvigo.ei.sing.s2p.core.entities.quantification.QuantificationSample;
import es.uvigo.ei.sing.s2p.core.operations.quantification.comparison.ProteinSummaryTest;

public class QuantificationTableModel extends DefaultTableModel {
	private static final long serialVersionUID = 1L;

	private static final int COLUMNS_PER_COMPARISON 		= 2;
	private static final int COLUMNS_PER_SAMPLE 			= 9;

	private static final int SAMPLE_PQ_MEAN 			= 0;
	private static final int SAMPLE_PQ_STD 				= 1;
	private static final int SAMPLE_PQ_RSD 				= 2;
	private static final int SAMPLE_NORMALIZED_PQ_MEAN 	= 3;
	private static final int SAMPLE_NORMALIZED_PQ_STD 	= 4;
	private static final int SAMPLE_NORMALIZED_PQ_RSD 	= 5;
	private static final int SAMPLE_MASS_MEAN 			= 6;
	private static final int SAMPLE_MASS_STD 			= 7;
	private static final int SAMPLE_POP					= 8;

	private static final Function<DescriptiveStatistics, Double> MEAN =
		DescriptiveStatistics::getMean;
	private static final Function<DescriptiveStatistics, Double> STD =
		DescriptiveStatistics::getStandardDeviation;
	private static final Function<DescriptiveStatistics, Double> RSD = d -> {
		return d.getStandardDeviation() / d.getMean();
	};

	private static final BiFunction<QuantificationSample, ProteinQuantification, Double> 
		PROTEIN_MASS = (s, pQ) -> {
			return s.getProteinMass() * pQ.getNormalizedValue();
		};

	private List<String> allProteins;
	private QuantificationDataset dataset;
	private QuantificationConditionsComparisons conditionsComparisons;
	private ProteinSummaryTest proteinComparison;

	public QuantificationTableModel(QuantificationDataset dataset,
		ProteinSummaryTest proteinComparison
	) {
		this.dataset = dataset;
		this.proteinComparison = proteinComparison;
		this.createData();
	}

	private void createData() {
		this.allProteins = new LinkedList<>(getAllDatasetProteins());
		this.conditionsComparisons = compare(dataset, proteinComparison);
	}

	private Set<String> getAllDatasetProteins() {
		return dataset.stream()
				.map(QuantificationSample::getReplicates)
				.flatMap(Collection::stream)
				.map(QuantificationReplicate::getProteins)
				.flatMap(Collection::stream)
				.map(ProteinQuantification::getProtein)
				.distinct().collect(Collectors.toSet());
	}

	@Override
	public int getRowCount() {
		return allProteins == null ? 0 : allProteins.size();
	}

	@Override
	public int getColumnCount() {
		return 1 + getSampleColumnsCount() + getComparisonColumnsCount();
	}

	private int getSampleColumnsCount() {
		return this.dataset.size() * COLUMNS_PER_SAMPLE;
	}
	
	private int getComparisonColumnsCount() {
		return getConditionComparisonsCount() * COLUMNS_PER_COMPARISON;
	}

	private int getConditionComparisonsCount() {
		return this.conditionsComparisons.size();
	}

	@Override
	public String getColumnName(int columnIndex) {
		if (columnIndex == 0) {
			return "Protein";
		} else if (isSampleColumn(columnIndex)) {
			return getSampleColumnName(columnIndex);
		} else {
			return getComparisonColumnName(columnIndex);
		}
	}

	private boolean isSampleColumn(int columnIndex) {
		int sampleIndex = getSampleIndex(columnIndex);
		return columnIndex != 0 && sampleIndex < this.dataset.size();
	}

	private int getSampleIndex(int columnIndex) {
		return (columnIndex - 1) / COLUMNS_PER_SAMPLE;
	}

	private String getSampleColumnName(int columnIndex) {
		int sampleIndex = getSampleIndex(columnIndex);
		QuantificationSample sample = this.dataset.get(sampleIndex);
		int sampleColumnIndex = getSampleInformationColumnIndex(columnIndex);

		String condition = "";
		if (getConditionNameAt(columnIndex).isPresent()) {
			condition = " (" + getConditionNameAt(columnIndex).get() + ")";
		}

		switch (sampleColumnIndex) {
		case SAMPLE_PQ_MEAN:
			return sample.getName() + " [mean PQ]" + condition;
		case SAMPLE_PQ_STD:
			return sample.getName() + " [std PQ]" + condition;
		case SAMPLE_PQ_RSD:
			return sample.getName() + " [rsd PQ]" + condition;
		case SAMPLE_NORMALIZED_PQ_MEAN:
			return sample.getName() + " [mean normalized PQ]" + condition;
		case SAMPLE_NORMALIZED_PQ_STD:
			return sample.getName() + " [std normalized PQ]" + condition;
		case SAMPLE_NORMALIZED_PQ_RSD:
			return sample.getName() + " [rsd normalized PQ]" + condition;
		case SAMPLE_MASS_MEAN:
			return sample.getName() + " [mean mass]" + condition;
		case SAMPLE_MASS_STD:
			return sample.getName() + " [std mass]" + condition;
		case SAMPLE_POP:
			return sample.getName() + " [POP]" + condition;
		}
		throw new IllegalStateException();
	}

	private int getSampleInformationColumnIndex(int columnIndex) {
		return (columnIndex - 1) % COLUMNS_PER_SAMPLE;
	}

	private String getComparisonColumnName(int columnIndex) {
		int comparisonIndex = getComparisonIndex(columnIndex);
		String comparisonColunnName = 
			this.conditionsComparisons.get(comparisonIndex).toString();

		int comparisonColumnIndex = getComparisonColumnIndex(columnIndex);
		switch (comparisonColumnIndex) {
		case 0:
			comparisonColunnName = comparisonColunnName + " [p-val]";
			break;
		case 1:
			comparisonColunnName = comparisonColunnName + " [q-val]";
			break;
		}

		return comparisonColunnName ;
	}

	private int getComparisonColumnIndex(int columnIndex) {
		return toComparisonColumnRange(columnIndex) % COLUMNS_PER_COMPARISON;
	}

	private int toComparisonColumnRange(int columnIndex) {
		return (columnIndex - (1 + getSampleColumnsCount()));
	}

	private int getComparisonIndex(int columnIndex) {
		return toComparisonColumnRange(columnIndex) / COLUMNS_PER_COMPARISON;
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return getValueAt(0, columnIndex).getClass();
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		String protein = allProteins.get(rowIndex);
		if (columnIndex == 0) {
			return protein;
		} else if (isSampleColumn(columnIndex)) {
			return getSampleValuesForProtein(protein, columnIndex);
		} else {
			return getComparisonValueForProtein(protein, columnIndex);
		}
	}

	private Object getSampleValuesForProtein(String protein, int columnIndex) {
		int sampleIndex = getSampleIndex(columnIndex);
		final QuantificationSample sample = this.dataset.get(sampleIndex);
		int sampleColumnIndex = (columnIndex - 1) % COLUMNS_PER_SAMPLE;
	
		switch (sampleColumnIndex) {
		case SAMPLE_PQ_MEAN:
			return getValue(sample, protein, MEAN, ProteinQuantification::getValue);
		case SAMPLE_PQ_STD:
			return getValue(sample, protein, STD, ProteinQuantification::getValue);
		case SAMPLE_PQ_RSD:
			return getValue(sample, protein, RSD, ProteinQuantification::getValue);
		case SAMPLE_NORMALIZED_PQ_MEAN:
			return getValue(sample, protein, MEAN, ProteinQuantification::getNormalizedValue);
		case SAMPLE_NORMALIZED_PQ_STD:
			return getValue(sample, protein, STD, ProteinQuantification::getNormalizedValue);
		case SAMPLE_NORMALIZED_PQ_RSD:
			return getValue(sample, protein, RSD, ProteinQuantification::getNormalizedValue);
		case SAMPLE_MASS_MEAN:
			return getValue(sample, protein, MEAN, pQ -> PROTEIN_MASS.apply(sample, pQ));
		case SAMPLE_MASS_STD:
			return getValue(sample, protein, STD, pQ -> PROTEIN_MASS.apply(sample, pQ));
		case SAMPLE_POP:
			return getProteinPop(sample, protein);
		}
		throw new IllegalStateException();
	}
	
	private Stream<ProteinQuantification> getProteinQuantifications(
		QuantificationSample sample, String protein
	) {
		return sample.getReplicates().stream()
				.map(QuantificationReplicate::getProteins)
				.flatMap(Collection::stream)
				.filter(p -> p.getProtein().equals(protein));
	}
	
	public double getValue(QuantificationSample sample, String protein,
		Function<DescriptiveStatistics, Double> collect,
		Function<ProteinQuantification, Double> map
	) {
		DescriptiveStatistics statistics = new DescriptiveStatistics();
		getProteinQuantifications(sample, protein)
			.map(map::apply)
			.forEach(v -> statistics.addValue(v.doubleValue()));
		return collect.apply(statistics);
	}

	public String getProteinPop(QuantificationSample sample, String protein) {
		int count = 0;
		int total = 0;
		for (QuantificationReplicate r : sample.getReplicates()) {
			total++;
			if (r.findProtein(protein).isPresent()) {
				count++;
			}
		}
		return count + "/" + total;
	}

	public Optional<QuantificationCondition> getConditionAt(int columnModel) {
		Optional<String> conditionName = getConditionNameAt(columnModel);
		if (conditionName.isPresent()) {
			return 	this.dataset.getConditions().stream()
					.filter(c -> c.getName().equals(conditionName.get()))
					.findAny();
		} else {
			return Optional.empty();
		}
	}

	private Object getComparisonValueForProtein(String protein,
		int columnIndex
	) {
		int comparisonIndex = getComparisonIndex(columnIndex);
		int comparisonColumnIndex = getComparisonColumnIndex(columnIndex);
		QuantificationConditionsComparison comparison =
			this.conditionsComparisons.get(comparisonIndex);

		switch (comparisonColumnIndex) {
		case 0:
			return comparison.getProteinPvalues().getOrDefault(protein, NaN);
		case 1:
			return comparison.getProteinQvalues().getOrDefault(protein, NaN);
		}
		throw new IllegalStateException();
	}

	public Optional<String> getConditionNameAt(int columnModel) {
		if (isSampleColumn(columnModel)) {
			int sampleIndex = getSampleIndex(columnModel);
			return this.dataset.get(sampleIndex).getCondition();
		} else {
			return Optional.empty();
		}
	}

	public String getProteinAt(int rowModel) {
		return allProteins.get(rowModel);
	}
}
