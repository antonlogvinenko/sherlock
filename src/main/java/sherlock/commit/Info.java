package sherlock.commit;

import java.io.*;
import java.util.SortedMap;
import java.util.TreeMap;

public class Info {

	public static SortedMap<String, String> getIntermediateCommits(InputStream is) {
		SortedMap<String, String> commits = new TreeMap<>();
		try (BufferedReader bf = new BufferedReader(new InputStreamReader(is))) {
			String line;
			while ((line = bf.readLine()) != null) {
				if (line.startsWith("----------") && (line = bf.readLine()) != null) {
					String[] commitHeader = line.split("\\|");
					String commit = commitHeader[0].trim();
					String author = commitHeader[1].trim();
					commits.put(commit, author);
				}
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return commits;
	}
}
