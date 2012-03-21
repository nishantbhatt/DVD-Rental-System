package iodvd.exception;

import java.util.zip.DataFormatException;
/**
 * This class stores information regarding DVD Format related exceptions.
 * @author nishantbhatt
 *
 */
public class DVDFormatException extends DataFormatException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Public constructor for this class.
	 * @param error the error message to be printed.
	 */
	public DVDFormatException(String error) {
		super(error);
	}
}
