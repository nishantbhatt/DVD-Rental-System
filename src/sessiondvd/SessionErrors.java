package sessiondvd;

/**
 * This class stores all the generic errors that may occur during a particular
 * session.
 * 
 * @author 100413064
 * 
 */
class SessionErrors {
	/**
	 * Static variable that is used to displays error when more than one login
	 * is attempted.
	 */
	public static String ONE_LOGIN_PER_SESSION = "Only one login per session is allowed.";
	/**
	 * Static variable that is used to displays error when a session is not
	 * running.
	 */
	public static String SESSION_NOT_OPEN = "No session is running.";
	/**
	 * Static variable that is used to displays error when current DVD title is
	 * not found.
	 */
	public static String CURRENT_DVD_FILE_NOT_FOUND = "Current DVD file not found.";
	/**
	 * Static variable that is used to displays error when a DVD title is not
	 * found in inventory.
	 */
	public static String DVD_NOT_FOUND = "DVD title does not exists in inventory.";
	/**
	 * Static variable that is used to displays error when specified DVD
	 * quantity input is invalid.
	 */
	public static String INVALID_DVD_QUANTITY = "Specified DVD quantity is invalid.";
	/**
	 * Static variable that is used to displays error when specified DVD
	 * quantity exceeds the alloted quantity.
	 */
	public static String EXCEEDING_DVD_QUANTITY = "Number of copies exceeding maximum limit of {1}";
	/**
	 * Static variable that is used to displays error when user attempts to buy
	 * a DVD that is not on sale.
	 */
	public static String DVD_NOT_FOR_SALE = "DVD title is not for buying.";
	/**
	 * Static variable that is used to displays error when user attempts to rent
	 * a DVD that is not for renting.
	 */
	public static String DVD_NOT_FOR_RENT = "DVD title is not for renting.";
	/**
	 * Static variable that is used to displays error when enough DVD titles are
	 * not available for renting/buying.
	 */
	public static String NOT_ENOUGH_DVDS = "Not enough DVDs in inventory.";
	/**
	 * Static variable that is used to displays error when admin attempts to
	 * create already existing DVD title.
	 */
	public static String DVD_ALREADY_EXISTS = "DVD title already exists.";
	/**
	 * Static variable that is used to displays error when admin attempts to
	 * change DVD status from sale to rent.
	 */
	public static String DVD_STATUS_CHNAGE_NOT_ALLOWED = "DVD status can be change from R to S only.";
	/**
	 * Static variable that is used to displays error when specified DVD price
	 * does not meet price defined in requirements.
	 */
	public static String INVALID_DVD_PRICE = "Specified DVD price is invalid.";
	/**
	 * Static variable that is used to displays error when DVD price exceeds
	 * maximum limit.
	 */
	public static String EXCEEDING_DVD_PRICE = "DVD price exceeding maximum limit of {1}";
	/**
	 * Static variable that is used to displays error when invalid transaction
	 * is made in a session.
	 */
	public static String TRANSACTIONS_NOT_ALLOWED = "No further transactions are allowed on this DVD title.";
	/**
	 * Static variable that is used to displays error when standard user
	 * attempts to access privileged command.
	 */
	public static String PRIVILEGED_VIOLATION = "Illegal access to privileged command.";
	/**
	 * Static variable that is used to displays error when user attempts to rent
	 * currently created DVD that is not for renting.
	 */
	public static String CREATED_DVD_NOT_FOR_RENTAL = "Newly created DVDs are not avaialble for rental.";
	/**
	 * Static variable that is used to displays error when user attempts to rent
	 * currently added DVD that is not for renting.
	 */
	public static String ADDED_DVD_NOT_FOR_RENTAL = "Added copies of DVDs are not available for rental.";
}