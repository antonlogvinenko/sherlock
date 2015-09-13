package sherlock.commit;

import java.io.IOException;
import java.util.*;

import static java.lang.Runtime.getRuntime;
import static java.lang.String.format;
import static java.util.Collections.unmodifiableSet;
import static java.util.stream.Collectors.toSet;
import static sherlock.commit.Info.getIntermediateCommits;

public class Repository {

	public class CommitDetails {

		public final String author;
		public final Set<String> words;

		public CommitDetails(String author, Set<String> words) {
			this.author = author;
			this.words = unmodifiableSet(words);
		}

		public String toString() {
			return format("{author: %s, words: %s}", author, words);
		}
	}

	private final String repository;
	private String username;
	private String password;
	private final Set<String> ignoreUsers;

	public Repository(String repository, String username, String password) {
		this(repository, username, password, null);
	}

	public Repository(String repository, String username, String password, Set<String> ignoreUsers) {
		this.repository = repository;
		this.username = username;
		this.password = password;
		this.ignoreUsers = ignoreUsers;
	}

	public Map<String, CommitDetails> getCommitsVocabulary(String commit1, String commit2) {
		try {
			commit2 = commit2 == null || commit2.isEmpty() ? "HEAD" : commit2;

			String infoCmd = format("svn log --username %s --password %s -r %s:%s %s",
				username, password, commit1, commit2, repository);
			Process p = getRuntime().exec(infoCmd);

			//r12345 -> author
			SortedMap<String, String> commits = getIntermediateCommits(p.getInputStream());
			//r12345-> details
			Map<String, CommitDetails> result = new TreeMap<>();

			List<String> commitNumbers = new ArrayList<>(commits.keySet());
			for (int i = 0; i < commitNumbers.size() - 1; i++) {
				String author = commits.get(commitNumbers.get(i + 1));
				if (ignoreUsers != null && ignoreUsers.contains(author)) {
					continue;
				}
				String from = commitNumbers.get(i);
				String to = commitNumbers.get(i + 1);
				String cmd = format("svn diff --username %s --password %s -r %s:%s %s",
					username, password, from, to, repository);
				p = getRuntime().exec(cmd);
				Set<String> words = new Commit(p.getInputStream())
					.getCommitChunks()
					.flatMap(CommitSignificantContent::getSignificantLines)
					.flatMap(CommitSignificantContent::getSignificantIdentifiers)
					.filter(CommitKeywordsFilter::test)
					.flatMap(CommitSignificantContent::enrich)
					.collect(toSet());
				result.put(to, new CommitDetails(commits.get(to), words));
			}

			return result;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
