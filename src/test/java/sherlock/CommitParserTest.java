package sherlock;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

public class CommitParserTest {

	public void testA() throws IOException {
		InputStream is = this.getClass().getClassLoader().getResourceAsStream("migration-task.diff");
		List<String> commits = new CommitParser(is).getCommits().collect(Collectors.toList());
		System.out.println(commits);
	}
}
