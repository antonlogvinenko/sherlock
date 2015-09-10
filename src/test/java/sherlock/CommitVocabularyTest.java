package sherlock;

import java.util.HashSet;
import java.util.Set;

import static com.google.common.collect.Sets.newHashSet;
import static junit.framework.Assert.assertEquals;

public class CommitVocabularyTest {

	public void testVocabularyBuild() {
		HashSet<Set<String>> sets = new HashSet<Set<String>>() {{
			add(new HashSet<String>() {{
				add("DnsZoneMutatorBean");
				add("MN_IP");
				add("SubscriptionParameter");
			}});
			add(new HashSet<String>() {{
				add("dnz_zone_ip");
				add("minus-One-");
				add("plus_two__-_A45O98_42");
				add("distrib_path");
			}});
		}};

		assertEquals(
			newHashSet("minus", "one", "bean", "distrib", "dns", "dnz", "ip", "mn",
				"mutator", "parameter", "path", "subscription", "zone", "plus", "two", "a45", "o98", "42"),
			CommitVocabulary.commitVocabularyFrom(sets)
		);
		System.out.println(CommitVocabulary.commitVocabularyFrom(sets));
	}
}
