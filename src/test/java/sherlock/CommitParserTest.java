package sherlock;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

import static junit.framework.Assert.assertEquals;

public class CommitParserTest {

	public void testParserRun() throws IOException {
		InputStream is = this.getClass().getClassLoader().getResourceAsStream("migration-task.diff");
		List<String> commits = new CommitParser(is).getCommits().collect(Collectors.toList());
		assertEquals(4, commits.size());
	}
}
