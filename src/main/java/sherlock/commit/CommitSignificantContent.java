package sherlock.commit;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

import static java.lang.Character.isLetterOrDigit;
import static java.lang.Character.isUpperCase;
import static java.lang.Character.toLowerCase;
import static java.util.Arrays.stream;

public class CommitSignificantContent {

	public static Stream<String> getSignificantLines(String commitChunk) {
		try {
			BufferedReader chunkReader = new BufferedReader(new StringReader(commitChunk));
			//File path is significant
			String pathParts[] = chunkReader.readLine().split("\\s")[1].split("\\\\");

			//Skip rest of the header
			for (int i = 0; i < 4; chunkReader.readLine(), i++) ;

			//Scan for lines of actual diff starting with '+' or '-'
			Stream<String> significantLines = chunkReader.lines()
				.filter(line -> line.startsWith("+") || line.startsWith("-"))
				.map(line -> line.substring(1))
				.filter(line -> !line.isEmpty());

			//Add file path as words
			return Stream.concat(significantLines, stream(pathParts));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static Stream<String> getSignificantIdentifiers(String line) {
		Set<String> identifiers = new HashSet<>();
		StringBuilder sb = new StringBuilder();
		for (char c : line.toCharArray()) {
			if (c == '_' || c == '-' || isLetterOrDigit(c)) {
				sb.append(c);
			} else if (sb.length() != 0) {
				identifiers.add(sb.toString());
				sb = new StringBuilder();
			}
		}
		if (sb.length() != 0) {
			identifiers.add(sb.toString());
		}
		return identifiers.stream();
	}

	public static Stream<String> enrich(String word) {
		Set<String> words = new HashSet<>();
		StringBuilder sb = new StringBuilder();
		boolean previousUpperCase = false;
		boolean upperCase;

		for (int i = 0; i < word.length(); i++) {
			char c = word.charAt(i);
			upperCase = isUpperCase(c);
			if (upperCase && !previousUpperCase || c == '_' || c == '-') {
				String finished = sb.toString();
				if (!finished.isEmpty()) {
					words.add(finished);
					sb = new StringBuilder();
				}
			}
			if (c != '_' && c != '-')
				sb.append(toLowerCase(c));

			if (i == word.length() - 1 && sb.length() != 0)
				words.add(sb.toString());

			previousUpperCase = upperCase;
		}

		return words.stream();
	}
}