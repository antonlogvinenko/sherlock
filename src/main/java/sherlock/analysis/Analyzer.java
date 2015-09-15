package sherlock.analysis;

import sherlock.commit.Repository;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import static com.google.common.collect.Sets.intersection;
import static java.lang.String.format;
import static java.util.Comparator.comparingInt;
import static java.util.stream.Collectors.*;

public class Analyzer {

	public static Map<String, Integer> analyze(Set<String> logVocabulary,
																						 Map<String, Repository.CommitDetails> commitsVocabularies) {
		Map<String, Integer> analysis = commitsVocabularies
			.entrySet()
			.stream()
			.collect(toMap(
				e -> format("%s %s", e.getKey(), e.getValue().author),
				e -> intersection(logVocabulary, e.getValue().words).size()
			));

		Map<String, Integer> sortedAnalysis = new TreeMap<>(comparingInt(analysis::get).reversed());
		sortedAnalysis.putAll(analysis);

		return sortedAnalysis;
	}
}
