package sherlock;

import sherlock.commit.Repository;

import java.util.Map;

import static com.google.common.collect.Sets.newHashSet;

public class Main {

	public static void main(String[] args) {
		if (args.length != 5) {
			System.out.println("Usage: java -jar sherlock.jar r100 r120 http://svn.company.com/project/trunk/ username password");
			System.exit(0);
		}
		Map<String, Repository.CommitDetails> commitsVocabularies =
			new Repository(args[2], args[3], args[4], newHashSet("buildbot", "pba-builder"))
				.getCommitsVocabulary(args[0], args[1]);

		commitsVocabularies.entrySet().stream().forEach(System.out::println);
	}
}
