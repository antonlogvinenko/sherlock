package sherlock.commit;

import java.io.IOException;
import java.util.Set;
import java.util.SortedMap;

import static java.lang.Runtime.getRuntime;
import static java.lang.String.format;
import static java.util.stream.Collectors.toSet;
import static sherlock.commit.Info.getIntermediateCommits;

public class Repository {

	private final String repository;

	public Repository(String repository) {
		this.repository = repository;
	}

	public Set<String> getCommitsVocabulary(String commit1, String commit2) {
		try {
			commit2 = commit2 == null || commit2.isEmpty() ? "HEAD" : commit2;

			String infoCmd = format("svn log -r %s:%s %s", commit1, commit2, repository);
			Process p = getRuntime().exec(infoCmd);
			SortedMap<String, String> commits = getIntermediateCommits(p.getInputStream());

			String cmd = format("svn diff -r %s:%s %s", commit1, commit2, repository);
			p = getRuntime().exec(cmd);
			return new Commit(p.getInputStream())
				.getCommitChunks()
				.flatMap(CommitSignificantContent::getSignificantLines)
				.flatMap(CommitSignificantContent::getSignificantIdentifiers)
				.filter(CommitKeywordsFilter::test)
				.flatMap(CommitSignificantContent::enrich)
				.collect(toSet());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
