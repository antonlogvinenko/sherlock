package sherlock;

import sherlock.commit.Repository;

import java.io.*;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import static com.google.common.base.Charsets.US_ASCII;
import static com.google.common.collect.Sets.newHashSet;
import static com.google.common.io.Files.readLines;
import static java.lang.String.format;
import static java.util.Comparator.comparingInt;
import static sherlock.analysis.Analyzer.analyze;
import static sherlock.log.LogVocabulary.*;

public class Main {

	public static void main(String[] args) throws FileNotFoundException {
		if (args.length != 6) {
			System.out.println("Usage: java -jar sherlock.jar error.log r100 r120 http://svn.company.com/project/trunk/ username passwordFile");
			System.exit(0);
		}
		String password = "nopassword";
		try {
			password = readLines(new File(args[5]), US_ASCII).get(0);
		} catch (IOException e) {
			System.out.println("File with password not found; specify correct file to use password");
		}
		Map<String, Repository.CommitDetails> commitsVocabularies =
			new Repository(args[3], args[4], password, newHashSet("buildbot", "pba-builder", "builder", "integrator"))
				.getCommitsVocabulary(args[1], args[2]);

		Set<String> logVocabulary = getVocabulary(new FileInputStream(args[0]));

		Map<String, Integer> analysis = analyze(logVocabulary, commitsVocabularies);

		int norm = logVocabulary.size();

		System.out.println("Probability\tCommit&author");
		analysis.entrySet().stream()
			.forEach(e -> System.out.println(format("%s\t\t%s", prettyPrintNormalized(norm, e.getValue()), e.getKey())));
	}

	private static String prettyPrintNormalized(int normalizer, Integer value) {
		return format("%.1f", value.doubleValue() / normalizer * 100);
	}
}
