package sessiondvd;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.HashMap;
import java.util.zip.DataFormatException;

import utilsdvd.GlobalFiles;
import iodvd.*;

/**
 * This class contains all functionality that is command between ADMIN and
 * STANDARD sessions. This includes, buying, renting, returning, creating
 * Transaction File, reading Current DVD File and etc.
 * 
 * @author 100413064 (Shivam Kalra), 100400174 (Nishant Bhatt)
 * 
 */
class StandardSession implements sSession {

	/**
	 * Maximum number of copies of a DVD.
	 */
	protected static int CREATE_COPIES_LIMIT = 999;
	
	/**
	 * Copies limit for renting a DVD.
	 */
	private static int RENT_COPIES_LIMIT = 3;

	/**
	 * Copies limit for buying a DVD.
	 */
	private static int BUY_COPIES_LIMIT = 5;

	/**
	 * Transactions made during current sessions.
	 */
	protected LinkedList<DVDTransaction> transactions = null;

	/**
	 * DVD inventory read from Current DVD File.
	 */
	protected Map<String, CurrentDVD> dvd_collection = null;

	/**
	 * Public constructor for the class.
	 * 
	 * @throws IOException
	 *             When I/O exception occurs in reading the Current DVD File.
	 * @throws DataFormatException
	 *             When Current DVD file is in the wrong format.
	 */
	public StandardSession() throws IOException, DataFormatException {
		dvd_collection = new HashMap<String, CurrentDVD>();
		transactions = new LinkedList<DVDTransaction>();

		iFileReader<CurrentDVD> cdvd_reader;
		try {
			cdvd_reader = new CurrentDVDReader(new FileReader(
					GlobalFiles.CURRENT_DVD_FILEPATH));
		} catch (FileNotFoundException e) {
			throw new IOException(SessionErrors.CURRENT_DVD_FILE_NOT_FOUND);
		}
		CurrentDVD cdvd = null;

		while ((cdvd = cdvd_reader.readNext()) != null)
			dvd_collection.put(cdvd.getTitle(), cdvd);
	}

	/**
	 * Get price of the DVD.
	 * 
	 * @param title
	 *            Title of the DVD.
	 * @return Price of the DVD.
	 */
	public double getDVDPrice(String title) {
		/* title does not exist */
		if(!dvd_collection.containsKey(title))
			throw new IllegalArgumentException(SessionErrors.DVD_NOT_FOUND);
		if(dvd_collection.get(title).getStatus() != DVDStatus.SALE)
			throw new IllegalArgumentException(SessionErrors.DVD_NOT_FOR_SALE);
		
		return dvd_collection.get(title).getPrice();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see sessiondvd.sSession#rent(java.lang.String, int)
	 */
	@Override
	public void rent(String title, int quantity) {
		CurrentDVD cdvd = dvd_collection.get(title);

		/* title does not exist */
		if (cdvd == null)
			throw new IllegalArgumentException(SessionErrors.DVD_NOT_FOUND);
		if (quantity <= 0)
			throw new IllegalArgumentException(
					SessionErrors.INVALID_DVD_QUANTITY);
		if (quantity > RENT_COPIES_LIMIT)
			throw new IllegalArgumentException(
					SessionErrors.EXCEEDING_DVD_QUANTITY.replaceAll("\\{1\\}",
							"" + RENT_COPIES_LIMIT));
		if (cdvd.getStatus() != DVDStatus.RENTAL)
			throw new IllegalArgumentException(SessionErrors.DVD_NOT_FOR_RENT);
		if (cdvd.getCount() < quantity)
			throw new IllegalArgumentException(SessionErrors.NOT_ENOUGH_DVDS);

		/* Subtract rented quantity from total */
		cdvd.setCount(cdvd.getCount() - quantity);

		/* add to transaction history */
		transactions.add(new DVDTransaction(TransactionID.RENT, title,
				quantity, DVDStatus.RENTAL, 0));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see sessiondvd.sSession#_return(java.lang.String, int)
	 */
	@Override
	public void _return(String title, int quantity) {

		CurrentDVD cdvd = dvd_collection.get(title);

		/* title does not exist */
		if (cdvd == null)
			throw new IllegalArgumentException(SessionErrors.DVD_NOT_FOUND);
		if (cdvd.getCount() + quantity > StandardSession.CREATE_COPIES_LIMIT)
			throw new IllegalArgumentException(SessionErrors.EXCEEDING_DVD_QUANTITY.replaceAll("\\{1\\}", "" + CREATE_COPIES_LIMIT));

		/* add rented quantity to total */
		cdvd.setCount(cdvd.getCount() + quantity);

		/* add to transaction history */
		transactions.add(new DVDTransaction(TransactionID.RETURN, title,
				quantity, DVDStatus.RENTAL, 0));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see sessiondvd.sSession#buy(java.lang.String, int)
	 */
	@Override
	public void buy(String title, int quantity) {
		_buy(title, quantity, true);
	}

	/**
	 * This is protected method that is called with different parameters from
	 * inherited class. As per the requirement specification, ADMIN can buy any
	 * numbers of DVD(s), therefore, variable <I>limit_check</I> must be false
	 * when method is called from ADMIN session.
	 * 
	 * @param title
	 *            DVDs title
	 * @param quantity
	 *            Number of DVD(s) for a particular title.
	 * @param limit_check
	 *            If there is restriction of number of DVD(s) to buy.
	 */
	protected void _buy(String title, int quantity, boolean limit_check) {

		CurrentDVD cdvd = dvd_collection.get(title);

		/* title does not exist */
		if (cdvd == null)
			throw new IllegalArgumentException(SessionErrors.DVD_NOT_FOUND);
		if (quantity <= 0)
			throw new IllegalArgumentException(
					SessionErrors.INVALID_DVD_QUANTITY);
		if (limit_check) {
			if (quantity > BUY_COPIES_LIMIT)
				throw new IllegalArgumentException(
						SessionErrors.EXCEEDING_DVD_QUANTITY.replaceAll(
								"\\{1\\}", "" + BUY_COPIES_LIMIT));
		} else {
			if (cdvd.getCount() - quantity < 0)
				throw new IllegalArgumentException(
						SessionErrors.NOT_ENOUGH_DVDS);
		}
		if (cdvd.getStatus() != DVDStatus.SALE)
			throw new IllegalArgumentException(SessionErrors.DVD_NOT_FOR_SALE);
		if (cdvd.getCount() < quantity)
			throw new IllegalArgumentException(SessionErrors.NOT_ENOUGH_DVDS);

		/* subtract brought copies from total */
		cdvd.setCount(cdvd.getCount() - quantity);

		/* add to transaction history */
		transactions.add(new DVDTransaction(TransactionID.BUY, title, quantity,
				DVDStatus.SALE, cdvd.getPrice()));
	}

	/**
	 * This methods writes current DVD transactions into a transaction file for
	 * a particular session.
	 * 
	 * @throws IOException
	 *             When I/O exception occurs in reading Current DVD File.
	 */
	public void writeTransactionFile() throws IOException {
		transactions.add(new DVDTransaction(TransactionID.LOGOUT, "", 0,
				DVDStatus.NA, 0.0));

		String unique_transac_path = GlobalFiles.DVD_TRANSACTION_FILEPATH;
		File transac_file = new File(unique_transac_path);

		if (!transac_file.exists())
			transac_file.createNewFile();

		iFileWriter<DVDTransaction> transac_writer = new DVDTransactionWriter(
				new FileWriter(transac_file));
		try {
			Iterator<DVDTransaction> transac_iterator = transactions.iterator();
			while (transac_iterator.hasNext())
				transac_writer.writeObject(transac_iterator.next());
		} catch (Exception ex) {
			transac_file.delete();
		} finally {
			transac_writer.close();
		}
	}
}
