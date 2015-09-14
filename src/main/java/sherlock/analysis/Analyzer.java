package sherlock.analysis;

import sherlock.commit.Repository;

import java.util.Map;
import java.util.Set;

import static com.google.common.collect.Sets.intersection;
import static java.lang.String.format;
import static java.util.stream.Collectors.*;

public class Analyzer {

	public static Map<String, Integer> analyze(Set<String> logVocabulary,
																						 Map<String, Repository.CommitDetails> commitsVocabularies) {
		return commitsVocabularies
			.entrySet()
			.stream()
			.collect(toMap(
				e -> format("%s %s", e.getKey(), e.getValue().author),
				e -> intersection(logVocabulary, e.getValue().words).size()
			));
	}
}
