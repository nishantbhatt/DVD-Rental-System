package iodvd;

import iodvd.exception.TransactionFormatException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;

/**
 * Provides a functionality to read transaction information from DVD Transaction File.
 * @author shivam
 *
 */
public class DVDTransactionReader extends BufferedReader implements
		iFileReader<DVDTransaction> {

	/**
	 * Public constructor
	 * @param arg0 source containing DVD transactions.
	 */
	public DVDTransactionReader(Reader arg0) {
		super(arg0);
	}

	@Override
	public DVDTransaction readNext() throws IOException,
			TransactionFormatException {
		String _next;
		if ((_next = super.readLine()) == null)
			return null;

		/* Check if every line is exactly 39 characters long */
		if (_next.length() != 41)
			throw new TransactionFormatException(
					"Wrong format of DVD Transaction File.");

		/*
		 * Extract information as per the format specified in requirement
		 * documentation.
		 */
		String transaction_code = _next.substring(0, 2);
		String title = (_next.substring(3, 29).trim()).toLowerCase();
		String quantity = _next.substring(29, 32);
		String status = _next.substring(33, 34);
		String price = _next.substring(35, 41);

		double _price;
		int _transaction_code, _quantity;
		DVDStatus _status;
		/* Try and parse all the above extracted information */
		try {
			_transaction_code = Integer.parseInt(transaction_code);
			/* if end of transaction file has reached */
			if(_transaction_code == 0)
				return null;
			_price = Double.parseDouble(price);
			_quantity = Integer.parseInt(quantity);

			if (status.equalsIgnoreCase("R"))
				_status = DVDStatus.RENTAL;
			else if (status.equalsIgnoreCase("S"))
				_status = DVDStatus.SALE;
			else
				throw new Exception();

		} catch (Exception ex) {
			throw new TransactionFormatException(
					"Wrong format of DVD Transaction File.");
		}

		/* return a new Transaction */
		return new DVDTransaction(_transaction_code, title.trim(), _quantity,
				_status, _price);
	}
	
	public void close() throws IOException {
		super.close();
	}

}