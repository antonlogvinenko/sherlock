package sherlock;

import org.junit.Test;

import java.util.SortedMap;
import java.util.TreeMap;

import static org.junit.Assert.assertEquals;
import static sherlock.commit.Info.getIntermediateCommits;

public class InfoTest {

	@Test
	public void testInfo() {
		SortedMap<String, String> commits =
			getIntermediateCommits(App.class.getClassLoader().getResourceAsStream("info.txt"));

		SortedMap<String, String> expected = new TreeMap<String, String>() {{
			put("r9800", "ollydbg");
			put("r9801", "ollydbg");
			put("r9802", "ollydbg");
			put("r9803", "mortenmacfly");
		}};

		assertEquals(expected, commits);
	}
}
