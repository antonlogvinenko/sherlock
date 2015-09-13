package sherlock.log;

import sherlock.commit.CommitSignificantContent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Set;

import static java.util.stream.Collectors.toSet;

public class LogVocabulary {

	public static Set<String> getVocabulary(InputStream is) {
		try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
			return br.lines()
				.flatMap(CommitSignificantContent::getSignificantIdentifiers)
				.map(String::toLowerCase)
				.collect(toSet());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
