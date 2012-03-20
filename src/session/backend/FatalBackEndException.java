package session.backend;

import java.io.IOException;

public class FatalBackEndException extends IOException {
	
	FileType fileType;
	String fileName;
	
	public FileType getFileType() {
		return fileType;
	}

	private static final long serialVersionUID = 1L;
	
	public FatalBackEndException(String message, FileType fileType, String fileName) {
		super(message);
		this.fileType = fileType;
		this.fileName = fileName;
	}

	public String getFileName() {
		return fileName;
	}
}
