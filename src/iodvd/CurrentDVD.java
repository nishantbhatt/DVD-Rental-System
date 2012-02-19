package iodvd;

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
	 */
	public CurrentDVD(String title, double price, int count, DVDStatus status) {
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
	 */
	public void setPrice(double price) {
		this.price = price;
	}

	/**
	 * Sets the number of copies of DVD.
	 * 
	 * @param count
	 *            Number of copies of DVD to be set.
	 */
	public void setCount(int count) {
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
}
