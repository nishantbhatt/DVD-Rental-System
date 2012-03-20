package session.backend;

import java.io.IOException;
/**
 * The class holds information about the exceptions that occurs during the back-end transaction 
 * @author nishantbhatt
 *
 */
public class FatalBackEndException extends IOException {
	/**
	 * The type of file.
	 */
	FileType fileType;
	/**
	 * This method specifies the type of file.
	 * @return the file type in which the fatal error has occurred
	 */
	public FileType getFileType() {
		return fileType;
	}
	
	private static final long serialVersionUID = 1L;
	/**
	 * Public constructor for this class.
	 * @param message The error message to be printed.
	 * @param fileType The type of file in which error has ocurred.
	 */
	public FatalBackEndException(String message, FileType fileType) {
		super(message);
		this.fileType = fileType;
	}
}
