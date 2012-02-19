package iodvd;

/**
 * Types of DVD statuses supported by DVD Rental System.
 * 
 * @author 100413064
 * 
 */
public enum DVDStatus {
	/**
	 * Rental status represented by "R".
	 */
	RENTAL("R"), /**
	 * 
	 * SALE status represented by "S"
	 */
	SALE("S"), /**
	 * 
	 * Undefined status represented by empty character.
	 */
	NA(" ");

	/**
	 * Stores the symbol used to represent a status.
	 */
	private final String val;

	/**
	 * Public constructor for an ENUM.
	 * 
	 * @param s
	 *            Symbol representing the ENUM literal.
	 */
	private DVDStatus(String s) {
		val = s;
	}

	/**
	 * Provides the symbol representation of an ENUM.
	 * 
	 * @return a character-string representing an ENUM.
	 */
	public String SYMBOL() {
		return val;
	}
}
