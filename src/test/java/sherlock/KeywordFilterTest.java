package sherlock;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

public class KeywordFilterTest {

	public void testKeywordFilter() {
		CommitKeywordsFilter f = new CommitKeywordsFilter();
		assertFalse(f.filter("class"));
		assertFalse(f.filter("synchronized"));
		assertFalse(f.filter("final"));
		assertTrue(f.filter("DnsBean"));
		assertTrue(f.filter("keywords"));
		assertTrue(f.filter("ipAddress"));

		assertFalse(f.filter("coproc"));
		assertFalse(f.filter("typename"));
		assertFalse(f.filter("extern"));
		assertFalse(f.filter("die()"));
		assertFalse(f.filter("typeof"));
		assertFalse(f.filter("lambda"));
		assertFalse(f.filter("strictfp"));
	}
}
