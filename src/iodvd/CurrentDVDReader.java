package iodvd;

import iodvd.exception.DVDFormatException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;

/**
 * Provides a functionality to read DVD information from Current DVD File.
 * @author 100413064
 *
 */
public class CurrentDVDReader extends BufferedReader implements iFileReader<CurrentDVD> {
	
	/**
	 * Public constructor for the class.
	 * @param arg0 Reader that reads the Current DVD Information from desired source.
	 */
	public CurrentDVDReader(Reader arg0) {
		super(arg0);
	}

	/* (non-Javadoc)
	 * @see iodvd.iFileReader#readNext()
	 */
	@Override
	public CurrentDVD readNext() throws IOException, DVDFormatException {
		String _next = super.readLine();
		
		if(_next == null)
			return null;
		
		/* Check if every line is exactly 39 characters long */
		if(_next.length() != 39)
			throw new DVDFormatException("Wrong format of Current DVD File.");
		
		/* Extract information as per the format specified in requirement domcumentation */
		String title = _next.substring(0, 25);
		String quantity = _next.substring(26, 30);
		String status = _next.substring(31, 32);
		String price = _next.substring(33, 39);
		
		double _price = 0;
		int _quantity = 0;
		DVDStatus _status;
		/* Try and parse all the aboive extracted information */
		try {
			_quantity = Integer.parseInt(quantity);
			
			if(status.equalsIgnoreCase("R"))
				_status = DVDStatus.RENTAL;
			else if(status.equalsIgnoreCase("S"))
				_status = DVDStatus.SALE;
			else
				throw new Exception();
			
			_price = Double.parseDouble(price);
		} catch(Exception ex) {
			throw new DVDFormatException("Wrong format of Current DVD File.");
		}
		
		/* return a new DVD */
		return new CurrentDVD(title.trim(), _price, _quantity, _status);
	}
}
