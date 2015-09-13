package sherlock.commit;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

import static java.util.Arrays.stream;

public class CommitKeywordsFilter {

	private static final Set<String> keywords = new HashSet<>();

	static {
		try (BufferedReader keywordsReader = new BufferedReader(
			new InputStreamReader(
				CommitKeywordsFilter.class.getClassLoader().getResourceAsStream("keywords.txt")))) {

			keywordsReader
				.lines()
				.filter(line -> !line.isEmpty() && !line.startsWith("#"))
				.flatMap(line -> stream(line.split("\\s")))
				.forEach(keywords::add);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static boolean test(String word) {
		return !keywords.contains(word);
	}
}
