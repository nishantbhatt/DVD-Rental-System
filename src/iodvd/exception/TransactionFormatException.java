package iodvd.exception;

import java.util.zip.DataFormatException;
/**
 * This class holds information about transaction format related exceptions.
 * @author nishantbhatt
 *
 */
public class TransactionFormatException extends DataFormatException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Public constructor for this class.
	 * @param error The error message to be printed.
	 */
	public TransactionFormatException(String error){
		super(error);
	}

}
