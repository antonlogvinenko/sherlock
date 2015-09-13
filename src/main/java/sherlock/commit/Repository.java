package sherlock.commit;

import java.io.IOException;
import java.util.Set;

import static java.lang.Runtime.getRuntime;
import static java.lang.String.format;
import static java.util.stream.Collectors.toSet;

public class Repository {

	private final String repository;

	public Repository(String repository) {
		this.repository = repository;
	}

	public Set<String> getCommitVocabulary(String commit1, String commit2) {
		try {
			commit2 = commit2 == null || commit2.isEmpty() ? "HEAD" : commit2;
			String cmd = format("svn diff -r %s:%s %s", commit1, commit2, repository);
			Process p = getRuntime().exec(cmd);
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
