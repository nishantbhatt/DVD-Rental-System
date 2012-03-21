package iodvd;

import iodvd.exception.DVDFormatException;

import java.text.DecimalFormat;

/**
 * Class stores information related to DVD in Current DVD File.
 * 
 * @author Developer: 100413064 (Shivam Kalra), Tester: 100400174 (Nishant
 *         Bhatt)
 * 
 */
public class CurrentDVD {

	/**
	 * Status of DVD.
	 */
	DVDStatus status;
	/**
	 * Price of DVD.
	 */
	double price;
	/**
	 * Number of copies of DVD available.
	 */
	int count;
	/**
	 * Title of DVD.
	 */
	String title;

	/**
	 * Public constructor for the class.
	 * 
	 * @param title
	 *            DVD Title.
	 * @param price
	 *            Price of DVD.
	 * @param count
	 *            Number of copies available.
	 * @param status
	 *            Status of DVD.
	 * @throws DVDFormatException
	 */
	public CurrentDVD(String title, double price, int count, DVDStatus status)
			throws DVDFormatException {

		if (price < 0 || price > 999.99)
			throw new DVDFormatException(
					"DVD count must be between 0 - 999.99.");
		if (title.length() > 25)
			throw new DVDFormatException(
					"DVD title must not be more than 25 characters.");
		if (count < 0 || count > 9999)
			throw new DVDFormatException(
					"DVD quantity must be betwene 0 - 9999");

		this.status = status;
		this.price = price;
		this.count = count;
		this.title = title;
	}

	/**
	 * Sets the status of DVD.
	 * 
	 * @param status
	 *            Status of DVD to be set.
	 */
	public void setStatus(DVDStatus status) {
		this.status = status;
	}

	/**
	 * Sets the price of DVD.
	 * 
	 * @param price
	 *            Price of DVD to be set.
	 * @throws DVDPriceFormatException
	 */
	public void setPrice(double price) throws DVDFormatException {
		if (price < 0 || price > 999.99)
		throw new DVDFormatException(
				"DVD count must be between 0 - 999.99.");
		this.price = price;
	}

	/**
	 * Sets the number of copies of DVD.
	 * 
	 * @param count
	 *            Number of copies of DVD to be set.
	 * @throws DVDCountFormatException
	 */
	public void setCount(int count) throws DVDFormatException {
		if (count < 0 || count > 9999)
			throw new DVDFormatException(
					"DVD quantity must be betwene 0 - 9999");
		this.count = count;
	}

	/**
	 * Returns the status of this DVD.
	 * 
	 * @return Status of DVD.
	 */
	public DVDStatus getStatus() {
		return status;
	}

	/**
	 * Returns the price of DVD.
	 * 
	 * @return Price of DVD.
	 */
	public double getPrice() {
		return price;
	}

	/**
	 * Returns the number of copies of DVD available.
	 * 
	 * @return Number of copies available.
	 */
	public int getCount() {
		return count;
	}

	/**
	 * Returns the title of DVD.
	 * 
	 * @return Title of DVD.
	 */
	public String getTitle() {
		return title;
	}

	public String toString() {
		/* writes the Current DVD information as per the requirement document */
		DecimalFormat money_format = new DecimalFormat("000.00");
		String _return = String.format("%-25s %04d %1s %1s", title, count,
				status.SYMBOL(), money_format.format(price));
		return _return;
	}
}