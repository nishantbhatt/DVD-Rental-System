package session.frontend;

/**
 * Provides standard operations interface for DVD Rental System.
 * 
 * @author Developer: 100413064 (Shivam Kalra), Tester: 100400174 (Nishant
 *         Bhatt)
 * 
 */
interface sSession {

	/**
	 * Rents a DVD and stores the transaction information.
	 * 
	 * @param title
	 *            Title of DVD to be rented.
	 * @param quantity
	 *            Number of copies to be rented.
	 */
	public void rent(String title, int quantity);

	/**
	 * Returns a DVD and stores the transaction information.
	 * 
	 * @param title
	 *            Title of DVD to be returned.
	 * @param quantity
	 *            Number of copies of DVDs to be returned.
	 */
	public void _return(String title, int quantity);

	/**
	 * Buys a DVD and stores the transaction information.
	 * 
	 * @param title
	 *            Title of DVD to buy.
	 * @param quantity
	 *            Number of copies to buy.
	 */
	public void buy(String title, int quantity);
}
