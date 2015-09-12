package sherlock;

import org.junit.Test;
import sherlock.commit.CommitSignificantContent;

import java.io.*;
import java.util.HashSet;

import static com.google.common.collect.Sets.newHashSet;
import static java.util.stream.Collectors.*;
import static junit.framework.Assert.assertEquals;
import static sherlock.commit.CommitSignificantContent.getSignificantIdentifiers;

public class CommitSignificantContentTest {

	@Test
	public void testEnrich() {
		HashSet<String> set = new HashSet<String>() {{
			add("DnsZoneMutatorBean");
			add("MN_IP");
			add("SubscriptionParameter");
			add("dnz_zone_ip");
			add("minus-One-");
			add("plus_two__-_A45O98_42");
			add("distrib_path");
		}};

		assertEquals(
			newHashSet("minus", "one", "bean", "distrib", "dns", "dnz", "ip", "mn",
				"mutator", "parameter", "path", "subscription", "zone", "plus", "two", "a45", "o98", "42"),
			set.stream().flatMap(CommitSignificantContent::enrich).collect(toSet()));
	}

	@Test
	public void testGetSignificantIdentifiers() {
		String line = "for (int ident = 0; ident < bigNum; i++) {writeString(ident + blaVar)}";
		assertEquals(
			newHashSet("for", "int", "ident", "0", "bigNum", "i", "writeString", "blaVar"),
			getSignificantIdentifiers(line).collect(toSet()));

	}

	@Test
	public void testGetSignificantLines() throws IOException {
		StringBuilder sb = new StringBuilder();
		String chunk;
		Reader source = new InputStreamReader(this.getClass().getClassLoader().getResourceAsStream("chunk.diff"));
		try (BufferedReader br = new BufferedReader(source)) {
			br.lines().forEach(line -> sb.append(line).append("\n"));
			chunk = sb.toString();
		}

		assertEquals(
			newHashSet("test-draw/draw_bird_config.py", "me_ip = '22.22.22.22'", "ff_oop = True"),
			CommitSignificantContent.getSignificantLines(chunk).collect(toSet())
		);
	}
}
