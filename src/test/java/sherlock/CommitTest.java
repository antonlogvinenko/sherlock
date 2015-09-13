package sherlock;

import org.junit.Test;
import sherlock.commit.Commit;
import sherlock.commit.CommitKeywordsFilter;
import sherlock.commit.CommitSignificantContent;

import java.util.Set;

import static java.util.stream.Collectors.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CommitTest {

	@Test
	public void testCommit() {
		Commit commit = new Commit(App.class.getClassLoader().getResourceAsStream("commit.diff"));
		CommitKeywordsFilter keywordsFilter = new CommitKeywordsFilter();

		Set<String> vocabulary = commit
			.getCommitChunks()
			.flatMap(CommitSignificantContent::getSignificantLines)
			.flatMap(CommitSignificantContent::getSignificantIdentifiers)
			.filter(CommitKeywordsFilter::test)
			.flatMap(CommitSignificantContent::enrich)
			.collect(toSet());

		assertEquals(29, vocabulary.size());
		assertTrue(vocabulary.contains("configure"));
		assertTrue(vocabulary.contains("bird"));
		assertTrue(vocabulary.contains("string"));
	}
}
