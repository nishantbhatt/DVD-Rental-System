package session.backend;

import iodvd.DVDTransaction;
/**
 * This class holds information about the constrain exceptions that occurs during that back-end session.   
 * @author nishantbhatt
 *
 */
public class ConstraintFailedException extends IllegalAccessException {

	/**
	 * Transaction made on DVDTransaction
	 */
	DVDTransaction transaction;
	/**
	 * This method gets the transactions made during back-end session.
	 * @return The transaction during back-end session.
	 */
	public DVDTransaction getTransaction() {
		return transaction;
	}

	private static final long serialVersionUID = 1L;
	/**
	 * Public constructor for this class
	 * @param message the constrain error message to be printed
	 * @param cause the cause of constrain error.
	 */
	public ConstraintFailedException(String message, DVDTransaction cause) {
		super(message);
		this.transaction = cause;
	}
}
