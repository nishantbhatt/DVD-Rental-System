package sessiondvd;

/**
 * Provides privileged-operations interface for a DVD Rental System.
 * 
 * @author Developer: Shivam Kalra (100413064), Tester: Nishant Bhatt
 *         (100400174)
 * 
 */
interface pSession {

	/**
	 * Creates a DVDs and stores the transaction information.
	 * 
	 * @param title
	 *            Title of DVD to be created.
	 * @param quantity
	 *            Number of copies of DVD to be created.
	 * @throws IllegalAccessException
	 *             When this method is called from STANDARD session.
	 */
	public void create(String title, int quantity)
			throws IllegalAccessException;

	/**
	 * Add more copies of an existing DVD and stores the transaction
	 * information.
	 * 
	 * @param title
	 *            Title of an existing DVD.
	 * @param quantity
	 *            Number of copies to be added to an existing DVD.
	 * @throws IllegalAccessException
	 *             When this method is called from STANDARD session.
	 */
	public void add(String title, int quantity) throws IllegalAccessException;

	/**
	 * Changes the status of a DVD from RENTAL to SALE and stores the
	 * transaction information.
	 * 
	 * @param title
	 *            Title of an existing DVD.
	 * @param price
	 *            Price of DVD for sale.
	 * @throws IllegalAccessException
	 *             When this method is called from STANDARD session.
	 */
	public void sell(String title, double price) throws IllegalAccessException;

	/**
	 * Removes a DVD from the inventory and stores the transaction information.
	 * 
	 * @param title
	 *            Title of an existing DVD to be removed.
	 * @throws IllegalAccessException
	 *             When this method is called from STANDARD session.
	 */
	public void remove(String title) throws IllegalAccessException;
}
