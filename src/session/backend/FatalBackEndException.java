package session.backend;

import java.io.IOException;
/**
 * 
 * @author nishantbhatt
 *
 */
public class FatalBackEndException extends IOException {
	/**
	 * 
	 */
	FileType fileType;
	/**
	 * 
	 * @return
	 */
	public FileType getFileType() {
		return fileType;
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 * @param message
	 * @param fileType
	 */
	public FatalBackEndException(String message, FileType fileType) {
		super(message);
		this.fileType = fileType;
	}
}
