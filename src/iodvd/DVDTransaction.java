package iodvd;

/**
 * Class stores the information related to transactions made in DVD Rental System.
 * @author 100413064
 *
 */
public class DVDTransaction {
	/**
	 * Identifies the particular transaction.
	 */
	TransactionID trans_id;
	/**
	 * Identifies the DVD title involved in transaction.
	 */
	String dvd_title;
	/**
	 * Number of copies involved within transaction.
	 */
	int quantity;
	/**
	 * Status of DVD involved in the transaction.
	 */
	DVDStatus status;
	/**
	 * Price of DVD involved within transaction.
	 */
	double price;
	
	/**
	 * Public constructor for the class.
	 * @param trans_id Transaction Code.
	 * @param dvd_title Title of the DVD.
	 * @param quantity Number of copies of the DVD.
	 * @param status Status of the DVD.
	 * @param price Price of the DVD.
	 */
	public DVDTransaction(TransactionID trans_id, String dvd_title,
			int quantity, DVDStatus status, double price) {
		this.trans_id = trans_id;
		this.dvd_title = dvd_title;
		this.quantity = quantity;
		this.status = status;
		this.price = price;
	}

	/**
	 * Returns the Transaction ID.
	 * @return Transaction ID
	 */
	public TransactionID getTrans_id() {
		return trans_id;
	}

	/**
	 * Returns the title of the DVD involved in transaction.
	 * @return Title of the DVD.
	 */
	public String getDvd_title() {
		return dvd_title;
	}

	/**
	 * Returns the number of the DVD's copies involved in transaction.
	 * @return Number of copies of the DVD.
	 */
	public int getQuantity() {
		return quantity;
	}

	/**
	 * returns the status of the DVD involved in transaction.
	 * @return Status of the DVD.
	 */
	public DVDStatus getStatus() {
		return status;
	}

	/**
	 * Returns the price of DVD involved in the transaction.
	 * @return Price of the DVD.
	 */
	public double getPrice() {
		return price;
	}
}
