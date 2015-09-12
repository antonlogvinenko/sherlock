package sherlock;

import org.junit.Test;
import sherlock.commit.CommitKeywordsFilter;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

public class KeywordFilterTest {

	@Test
	public void testKeywordsFilter() {
		CommitKeywordsFilter f = new CommitKeywordsFilter();
		assertFalse(f.test("class"));
		assertFalse(f.test("synchronized"));
		assertFalse(f.test("final"));
		assertTrue(f.test("DnsBean"));
		assertTrue(f.test("keywords"));
		assertTrue(f.test("ipAddress"));

		assertFalse(f.test("coproc"));
		assertFalse(f.test("typename"));
		assertFalse(f.test("extern"));
		assertFalse(f.test("die()"));
		assertFalse(f.test("typeof"));
		assertFalse(f.test("lambda"));
		assertFalse(f.test("strictfp"));
	}
}
