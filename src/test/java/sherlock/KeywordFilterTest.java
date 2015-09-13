package sherlock;

import org.junit.Test;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static sherlock.commit.CommitKeywordsFilter.test;

public class KeywordFilterTest {

	@Test
	public void testKeywordsFilter() {
		assertFalse(test("class"));
		assertFalse(test("synchronized"));
		assertFalse(test("final"));
		assertTrue(test("DnsBean"));
		assertTrue(test("keywords"));
		assertTrue(test("ipAddress"));

		assertFalse(test("coproc"));
		assertFalse(test("typename"));
		assertFalse(test("extern"));
		assertFalse(test("die()"));
		assertFalse(test("typeof"));
		assertFalse(test("lambda"));
		assertFalse(test("strictfp"));
	}
}
