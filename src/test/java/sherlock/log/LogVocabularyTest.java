package sherlock.log;

import org.junit.Test;

import java.io.InputStream;

import static com.google.common.collect.Sets.newHashSet;
import static org.junit.Assert.assertEquals;
import static sherlock.log.LogVocabulary.getVocabulary;

public class LogVocabularyTest {

	@Test
	public void testLineWords() {
		InputStream is = this.getClass().getClassLoader().getResourceAsStream("error.log");

		assertEquals(
			newHashSet("app", "ff", "errorhandler", "noproperty", "data", "ifconfig", "exist",
				"not", "bla", "bla", "does", "by", "host", "caused", "property", "font"),
			getVocabulary(is)
		);
	}
}
