package sherlock.commit;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

import static com.google.common.collect.Sets.newHashSet;

public class CommitKeywordsFilter {

	private static final Set<String> keywords = new HashSet<>();

	public CommitKeywordsFilter() {
		try {
			try (BufferedReader keywordsReader = new BufferedReader(
				new InputStreamReader(
					this.getClass().getClassLoader().getResourceAsStream("keywords.txt")))) {

				String line;
				while ((line = keywordsReader.readLine()) != null) {
					if (!line.isEmpty() && !line.startsWith("#")) {
						keywords.addAll(newHashSet(line.split("\\s")));
					}
				}
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}


	public boolean filter(String word) {
		return !keywords.contains(word);
	}
}
