package sherlock;

import sherlock.commit.Repository;

import java.io.IOException;

public class App {

	public static void main(String[] args) throws IOException {
		Repository repo = new Repository("svn://svn.code.sf.net/p/codeblocks/code");
		System.out.println(repo.getCommitVocabulary("9800", "9803"));
	}
}
