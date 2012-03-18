package session.backend;

import iodvd.DVDTransaction;

public class ConstraintFailedException extends IllegalAccessException {

	DVDTransaction transaction;
	
	public DVDTransaction getTransaction() {
		return transaction;
	}

	private static final long serialVersionUID = 1L;
	
	public ConstraintFailedException(String message, DVDTransaction cause) {
		super(message);
		this.transaction = cause;
	}
}
