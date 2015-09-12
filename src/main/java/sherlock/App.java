package sherlock;

import sherlock.commit.CommitKeywordsFilter;

import java.io.IOException;

public class App {

	public static void main(String[] args) throws IOException {
		CommitKeywordsFilter f = new CommitKeywordsFilter();
		System.out.println(f.filter("class"));
		System.out.println(f.filter("DnsBean"));
	}
}
