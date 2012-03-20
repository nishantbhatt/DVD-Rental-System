package session.backend;

import iodvd.DVDTransaction;
/**
 * 
 * @author nishantbhatt
 *
 */
public class ConstraintFailedException extends IllegalAccessException {

	/**
	 * 
	 */
	DVDTransaction transaction;
	/**
	 * 
	 * @return
	 */
	public DVDTransaction getTransaction() {
		return transaction;
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 * @param message
	 * @param cause
	 */
	public ConstraintFailedException(String message, DVDTransaction cause) {
		super(message);
		this.transaction = cause;
	}
}
