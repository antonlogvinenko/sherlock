package sherlock;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class App {

	public static void main(String[] args) throws IOException {
		CommitParser c = new CommitParser(new FileInputStream("/Users/anton/dev/migration-task.diff"));
		Stream<String> s = c.getCommits();
		System.out.println(s.collect(Collectors.toList()));
	}
}
