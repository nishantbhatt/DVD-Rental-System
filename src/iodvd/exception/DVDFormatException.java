package iodvd.exception;

import java.util.zip.DataFormatException;

public class DVDFormatException extends DataFormatException {

	private static final long serialVersionUID = 1L;

	public DVDFormatException(String error) {
		super(error);
	}
}
