package sherlock.commit;

import java.io.*;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.stream.Stream;

import static com.google.common.io.Closeables.closeQuietly;
import static java.util.Spliterators.spliteratorUnknownSize;
import static java.util.stream.StreamSupport.stream;

public class CommitParser {

	private final BufferedReader bis;
	private StringBuilder header = new StringBuilder();

	public CommitParser(InputStream is) {
		this.bis = new BufferedReader(new InputStreamReader(is));
		readHeader();
	}

	private void readHeader() {
		String line = null;
		try {
			header.append(bis.readLine()).append("\n");
			header.append(line = bis.readLine()).append("\n");
			if (line == null) {
				throw new IOException("Premature end of commit");
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			if (line == null) {
				closeQuietly(bis);
			}
		}
	}

	private String readNext() {
		StringBuilder sb = header;
		//Previous scan didn't find header, no more files in diff
		if (header.length() == 0) {
			return null;
		}
		header = new StringBuilder();
		String line = null;
		try {
			while ((line = bis.readLine()) != null) {
				if (line.startsWith("Index: ")) {
					header.append(line).append("\n");
					header.append(line = bis.readLine()).append("\n");
					if (line.startsWith("=======")) {
						return sb.toString();
					} else {
						sb.append(header);
						header = new StringBuilder();
					}
				} else
					sb.append(line).append("\n");
			}
			return sb.toString();
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			if (line == null) {
				closeQuietly(bis);
			}
		}
	}

	public Stream<String> getCommitChunks() {
		Spliterator<String> sp = spliteratorUnknownSize(new Iterator<String>() {

			private String next = readNext();

			@Override
			public boolean hasNext() {
				return next != null;
			}

			@Override
			public String next() {
				if (next == null)
					return null;
				String n = next;
				next = readNext();
				return n;
			}
		}, 0);

		return stream(sp, false);
	}
}
