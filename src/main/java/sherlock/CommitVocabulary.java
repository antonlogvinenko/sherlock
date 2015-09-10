package sherlock;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.lang.Character.isUpperCase;

public class CommitVocabulary {

	private CommitVocabulary() {
	}

	public static Set<String> commitVocabularyFrom(Set<Set<String>> wordSets) {
		return wordSets.stream()
			.map(CommitVocabulary::enrich)
			.reduce(CommitVocabulary::join)
			.get();
	}

	private static Set<String> enrich(Set<String> set) {
		return set.stream()
			.flatMap(CommitVocabulary::withSimilarWords)
			.collect(Collectors.toSet());
	}

	private static Stream<String> withSimilarWords(String word) {
		Set<String> words = new HashSet<>();
		StringBuilder sb = new StringBuilder();
		boolean previousUpperCase = false;
		boolean upperCase = false;
		for (int i = 0; i < word.length(); i++) {
			char c = word.charAt(i);
			upperCase = isUpperCase(c);
			if (upperCase && !previousUpperCase || c == '_' || c == '-') {
				String finished = sb.toString();
				if (!finished.isEmpty()) {
					words.add(finished);
				}
				sb = new StringBuilder();
			}
			if (c != '_' && c != '-')
				sb.append(Character.toLowerCase(c));

			if (i == word.length() - 1 && sb.length() != 0)
				words.add(sb.toString());

			previousUpperCase = upperCase;
		}

		return words.stream();
	}

	private static Set<String> join(Set<String> set1, Set<String> set2) {
		Set<String> joined = new TreeSet<String>(set1);
		joined.addAll(set2);
		return joined;
	}
}
