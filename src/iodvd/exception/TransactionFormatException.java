package iodvd.exception;

import java.util.zip.DataFormatException;

public class TransactionFormatException extends DataFormatException {
	
	private static final long serialVersionUID = 1L;
	
	public TransactionFormatException(String error){
		super(error);
	}

}
