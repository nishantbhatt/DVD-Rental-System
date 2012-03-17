package iodvd;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.HashSet;
import java.util.zip.DataFormatException;

public class MasterDVDReader extends BufferedReader implements
		iFileReader<MasterDVD> {

	HashSet<String> dvds;
	public MasterDVDReader(Reader arg0) {
		super(arg0);
		dvds = new HashSet<String>();
	}

	@Override
	public MasterDVD readNext() throws IOException, DataFormatException {
		String _next;
		if ((_next = super.readLine()) == null)
			return null;

		/* Check if every line is exactly 39 characters long */
		if (_next.length() != 50)
			throw new DataFormatException("Wrong format of Master DVD File.");

		/*
		 * Extract information as per the format specified in requirement
		 * documentation.
		 */
		String dvd_id = _next.substring(0, 5);
		String rem_quantity = _next.substring(6, 10);
		String tot_quantity = _next.substring(11, 15);
		String status = _next.substring(16, 17);
		String price = _next.substring(18, 24);
		String title = _next.substring(25, 50).trim();
		
		if(dvds.contains(title))
			throw new IOException("DVD title \"" + title + "\" has duplicated values.");
		dvds.add(title);
		
		double _price = 0;
		int _rem_quantity, _tot_quantity, _dvd_id;
		DVDStatus _status;
		
		/* Try and parse all the above extracted information */
		try {
			_dvd_id = Integer.parseInt(dvd_id);
			_rem_quantity = Integer.parseInt(rem_quantity);
			_tot_quantity = Integer.parseInt(tot_quantity);

			if (status.equalsIgnoreCase("R"))
				_status = DVDStatus.RENTAL;
			else if (status.equalsIgnoreCase("S"))
				_status = DVDStatus.SALE;
			else
				throw new Exception();

			_price = Double.parseDouble(price);
		} catch (Exception ex) {
			throw new DataFormatException("Wrong format of Master DVD File.");
		}

		/* return a new DVD */
		return new MasterDVD(_dvd_id, _rem_quantity, _tot_quantity, _status, _price, title);
	}

}
