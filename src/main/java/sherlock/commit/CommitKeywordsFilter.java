package sherlock.commit;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

import static java.util.Arrays.stream;

public class CommitKeywordsFilter implements Predicate<String> {

	private static final Set<String> keywords = new HashSet<>();

	public CommitKeywordsFilter() {
		try {
			try (BufferedReader keywordsReader = new BufferedReader(
				new InputStreamReader(
					this.getClass().getClassLoader().getResourceAsStream("keywords.txt")))) {

				keywordsReader
					.lines()
					.filter(line -> !line.isEmpty() && !line.startsWith("#"))
					.flatMap(line -> stream(line.split("\\s")))
					.forEach(keywords::add);
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public boolean test(String word) {
		return !keywords.contains(word);
	}
}
