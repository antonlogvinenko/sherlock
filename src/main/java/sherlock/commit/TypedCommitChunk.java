package sherlock.commit;

public class TypedCommitChunk {

	private FileType type;

	private TypedCommitChunk(FileType type) {
		this.type = type;
	}

	public FileType getType() {
		return type;
	}

	public static TypedCommitChunk fromCommitChunk(String commitChunk) {

	}
}
