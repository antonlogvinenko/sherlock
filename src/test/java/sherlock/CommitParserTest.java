package sherlock;

import org.junit.Test;
import sherlock.commit.Commit;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

import static junit.framework.Assert.assertEquals;

public class CommitParserTest {

	@Test
	public void testParserRun() throws IOException {
		InputStream is = this.getClass().getClassLoader().getResourceAsStream("commit.diff");
		List<String> commits = new Commit(is).getCommitChunks().collect(Collectors.toList());
		assertEquals(4, commits.size());
	}
}
