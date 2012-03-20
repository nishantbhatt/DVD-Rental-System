package session.backend;

import java.io.IOException;

public class FatalBackEndException extends IOException {
	
	FileType fileType;
	
	public FileType getFileType() {
		return fileType;
	}

	private static final long serialVersionUID = 1L;
	
	public FatalBackEndException(String message, FileType fileType, String filename) {
		super(message);
		this.fileType = fileType;
	}
}
