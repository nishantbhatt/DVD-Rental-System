package iodvd;

import iodvd.exception.DVDFormatException;
import iodvd.exception.TransactionFormatException;

import java.text.DecimalFormat;

/**
 * Class stores the information related to transactions made in DVD Rental
 * System.
 * 
 * @author 100413064
 * 
 */
public class DVDTransaction {
	/**
	 * Identifies the particular transaction.
	 */
	TransactionID trans_id;
	
	CurrentDVD cdvd = null;

	/**
	 * Public constructor for the class.
	 * 
	 * @param trans_id
	 *            Transaction Code.
	 * @param dvd_title
	 *            Title of the DVD.
	 * @param quantity
	 *            Number of copies of the DVD.
	 * @param status
	 *            Status of the DVD.
	 * @param price
	 *            Price of the DVD.
	 * @throws TransactionFormatException 
	 */
	public DVDTransaction(TransactionID trans_id, String dvd_title,
			int quantity, DVDStatus status, double price) throws TransactionFormatException {
		this.trans_id = trans_id;
		try {
			if(quantity > 999)
				throw new DVDFormatException("Maximum of 999 DVDs in one transaction.");
			cdvd = new CurrentDVD(dvd_title, price, quantity, status);
		} catch (DVDFormatException e) {
			throw new TransactionFormatException(e.getMessage());
		}
	}

	public DVDTransaction(int trans_id, String dvd_title, int quantity,
			DVDStatus status, double price) throws TransactionFormatException {
		switch (trans_id) {
		case 1:
			this.trans_id = TransactionID.RENT;
			break;
		case 2:
			this.trans_id = TransactionID.RETURN;
			break;
		case 3:
			this.trans_id = TransactionID.CREATE;
			break;
		case 4:
			this.trans_id = TransactionID.ADD;
			break;
		case 5:
			this.trans_id = TransactionID.REMOVE;
			break;
		case 6:
			this.trans_id = TransactionID.BUY;
			break;
		case 7:
			this.trans_id = TransactionID.SELL;
			break;
		case 0:
			this.trans_id = TransactionID.LOGOUT;
			break;
		default:
			throw new IllegalArgumentException("Invalid Transaction ID.");
		}
		try {
			if(quantity > 999)
				throw new DVDFormatException("Maximum of 999 DVDs in one transaction.");
			cdvd = new CurrentDVD(dvd_title, price, quantity, status);
		} catch (DVDFormatException e) {
			throw new TransactionFormatException(e.getMessage());
		}
	}

	/**
	 * Returns the Transaction ID.
	 * 
	 * @return Transaction ID
	 */
	public TransactionID getTrans_id() {
		return trans_id;
	}

	/**
	 * Returns the title of the DVD involved in transaction.
	 * 
	 * @return Title of the DVD.
	 */
	public String getDvd_title() {
		return cdvd.getTitle();
	}

	/**
	 * Returns the number of the DVD's copies involved in transaction.
	 * 
	 * @return Number of copies of the DVD.
	 */
	public int getQuantity() {
		return cdvd.getCount();
	}

	/**
	 * returns the status of the DVD involved in transaction.
	 * 
	 * @return Status of the DVD.
	 */
	public DVDStatus getStatus() {
		return cdvd.getStatus();
	}

	/**
	 * Returns the price of DVD involved in the transaction.
	 * 
	 * @return Price of the DVD.
	 */
	public double getPrice() {
		return cdvd.getPrice();
	}
	
	public CurrentDVD getDVDInfo() {
		return cdvd;
	}

	@Override
	public String toString() {
		/*
		 * Writes the transaction information as per format mentioned in
		 * requirement document
		 */
		DecimalFormat money_format = new DecimalFormat("000.00");
		String _return = String.format("%02d %-25s %03d %1s %s", trans_id.ID(),
				getDvd_title(), getQuantity(), getStatus().SYMBOL(),
				money_format.format(getPrice()));
		return _return;
	}
}
