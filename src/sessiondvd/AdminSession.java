package sessiondvd;

import iodvd.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.zip.DataFormatException;

/**
 * Class provides access to privileged commands through pSession interface.
 * 
 * @author 100413064
 */
class AdminSession extends StandardSession implements pSession {

	/**
	 * Maximum price for a DVD.
	 */
	private static double DVD_PRICE_LIMIT = 150.00;

	/**
	 * Keeps track of DVDs removed during ADMIN session.
	 */
	private Set<String> removed_dvds;

	/**
	 * keeps track of DVDs created during ADMIN session.
	 */
	private Set<String> created_dvds;

	/**
	 * Keeps track of DVDs and their respective quantities added during ADMIN
	 * session.
	 */
	private Map<String, Integer> added_dvds;

	/**
	 * Constructor for AdminSession class.
	 */
	public AdminSession() throws IOException, DataFormatException {
		super();
		removed_dvds = new HashSet<String>();
		created_dvds = new HashSet<String>();
		added_dvds = new HashMap<String, Integer>();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see sessiondvd.pSession#create(java.lang.String, int)
	 */
	@Override
	public void create(String title, int quantity) {
		/* check if title is contained within removed transactions */
		if (removed_dvds.contains(title))
			throw new IllegalArgumentException(
					SessionErrors.TRANSACTIONS_NOT_ALLOWED);
		/* check if DVD title exists in inventory */
		if (dvd_collection.containsKey(title))
			throw new IllegalArgumentException(SessionErrors.DVD_ALREADY_EXISTS);
		/* check if quantity specified is wrong */
		if (quantity <= 0)
			throw new IllegalArgumentException(
					SessionErrors.INVALID_DVD_QUANTITY);
		/* check if quantity is increasing the MAX LIMIT */
		if (quantity > CREATE_COPIES_LIMIT)
			throw new IllegalArgumentException(
					SessionErrors.EXCEEDING_DVD_QUANTITY.replaceFirst(
							"\\{1\\}", "" + CREATE_COPIES_LIMIT));

		/* Add DVDs to create DVD set */
		created_dvds.add(title);
		/* Put the DVD in DVD collection for current session */
		this.dvd_collection.put(title, new CurrentDVD(title, 0.0, quantity,
				DVDStatus.RENTAL));

		/* add to transaction history */
		transactions.add(new DVDTransaction(TransactionID.CREATE, title,
				quantity, DVDStatus.RENTAL, 0));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see sessiondvd.pSession#add(java.lang.String, int)
	 */
	@Override
	public void add(String title, int quantity) {
		/* extract current DVD from DVD collection of current session */
		CurrentDVD cdvd = dvd_collection.get(title);

		/* check if DVD is within remove DVDs list */
		if (removed_dvds.contains(title))
			throw new IllegalArgumentException(
					SessionErrors.TRANSACTIONS_NOT_ALLOWED);
		/*
		 * check if DVD to be added is not found in master DVD collect for
		 * current session
		 */
		if (cdvd == null)
			throw new IllegalArgumentException(SessionErrors.DVD_NOT_FOUND);
		/* check if quantity is less than 0 */
		if (quantity <= 0)
			throw new IllegalArgumentException(
					SessionErrors.INVALID_DVD_QUANTITY);
		/* check if COPIES LIMIT is not violated by added extra copies */
		if (cdvd.getCount() + quantity > CREATE_COPIES_LIMIT)
			throw new IllegalArgumentException(
					SessionErrors.EXCEEDING_DVD_QUANTITY.replaceFirst(
							"\\{1\\}", "" + CREATE_COPIES_LIMIT));

		/* increase the count */
		cdvd.setCount(cdvd.getCount() + quantity);
		/*
		 * Make sure to carefully update added_dvds if "add" command is called
		 * for same DVD title multiple times during same session
		 */
		if (added_dvds.containsKey(title))
			added_dvds.put(title, added_dvds.get(title) + quantity);
		else
			added_dvds.put(title, quantity);

		/* add to transaction history */
		transactions.add(new DVDTransaction(TransactionID.ADD, title, quantity,
				cdvd.getStatus(), cdvd.getPrice()));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see sessiondvd.pSession#sell(java.lang.String, double)
	 */
	@Override
	public void sell(String title, double price) {
		if (removed_dvds.contains(title))
			throw new IllegalArgumentException(
					SessionErrors.TRANSACTIONS_NOT_ALLOWED);
		if (!dvd_collection.containsKey(title))
			throw new IllegalArgumentException(SessionErrors.DVD_NOT_FOUND);
		if (price <= 0)
			throw new IllegalArgumentException(SessionErrors.INVALID_DVD_PRICE);
		if (price > DVD_PRICE_LIMIT)
			throw new IllegalArgumentException(
					SessionErrors.EXCEEDING_DVD_PRICE.replaceAll("\\{1\\}", ""
							+ DVD_PRICE_LIMIT));
		
		CurrentDVD dvdInfo = dvd_collection.get(title);
		if(dvdInfo.getStatus() == DVDStatus.SALE)
			throw new IllegalArgumentException(SessionErrors.DVD_ALREADY_ON_SALE);
		dvdInfo.setPrice(price);
		dvdInfo.setStatus(DVDStatus.SALE);

		if (created_dvds.contains(title))
			created_dvds.remove(title);
		if (added_dvds.containsKey(title))
			added_dvds.remove(title);

		/* add to transaction history */
		transactions.add(new DVDTransaction(TransactionID.SELL, title, dvdInfo
				.getCount(), DVDStatus.SALE, dvdInfo.getPrice()));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see sessiondvd.pSession#remove(java.lang.String)
	 */
	@Override
	public void remove(String title) {

		/* get DVD information from master DVD collection */
		CurrentDVD cdvd = dvd_collection.get(title);
		/* check if DVD exists in removed DVD titles */
		if (removed_dvds.contains(title))
			throw new IllegalArgumentException(
					SessionErrors.TRANSACTIONS_NOT_ALLOWED);
		/* check if DVD does not exists in master DVD collection */
		if (cdvd == null)
			throw new IllegalArgumentException(SessionErrors.DVD_NOT_FOUND);

		/* check if removing the created DVD */
		if (created_dvds.contains(title))
			created_dvds.remove(title);
		/* check if removing the added DVDs */
		if (added_dvds.containsKey(title))
			added_dvds.remove(title);
		
		/* add DVD title to list of removed DVDs */
		removed_dvds.add(title);
		/* add to transactions */
		transactions.add(new DVDTransaction(TransactionID.REMOVE, title, cdvd
				.getCount(), cdvd.getStatus(), cdvd.getPrice()));
	}

	@Override
	public void buy(String title, int quantity) {
		if (removed_dvds.contains(title))
			throw new IllegalArgumentException(
					SessionErrors.TRANSACTIONS_NOT_ALLOWED);
		super._buy(title, quantity, false);
	}
	
	@Override
	public void writeTransactionFile() throws IOException{
		/* removing pending transaction */
		Iterator<DVDTransaction> transac_iter = transactions.iterator();
		while(transac_iter.hasNext()) {
			DVDTransaction tr = transac_iter.next();
			if(removed_dvds.contains(tr.getDvd_title()) && tr.getTrans_id() != TransactionID.REMOVE)
				transac_iter.remove();
		}
		
		super.writeTransactionFile();
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see sessiondvd.StandardSession#rent(java.lang.String, int)
	 */
	@Override
	public void rent(String title, int quantity) {
		if (removed_dvds.contains(title))
			throw new IllegalArgumentException(
					SessionErrors.TRANSACTIONS_NOT_ALLOWED);
		/* renting not allowed for newly create dDVDs */
		if (created_dvds.contains(title))
			throw new IllegalArgumentException(
					SessionErrors.CREATED_DVD_NOT_FOR_RENTAL);

		Integer addedCount = added_dvds.get(title);

		/* making sure that rent transaction is denied if renting from added DVDs */
		if (addedCount != null) {
			CurrentDVD cdvd = this.dvd_collection.get(title);
			if (cdvd != null) {
				if (cdvd.getCount() - addedCount < quantity)
					throw new IllegalArgumentException(
							SessionErrors.ADDED_DVD_NOT_FOR_RENTAL);
			} else
				throw new IllegalArgumentException(SessionErrors.DVD_NOT_FOUND);
		}

		/* execute standard rent */
		super.rent(title, quantity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see sessiondvd.StandardSession#_return(java.lang.String, int)
	 */
	@Override
	public void _return(String title, int quantity) {
		if (removed_dvds.contains(title))
			throw new IllegalArgumentException(
					SessionErrors.TRANSACTIONS_NOT_ALLOWED);
		super._return(title, quantity);
	}
}
