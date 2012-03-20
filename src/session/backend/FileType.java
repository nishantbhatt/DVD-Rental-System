package session.backend;

/**
 * The different type of files on which transactions are performed in back-end
 * sessions.
 * 
 * @author nishantbhatt
 * 
 */
public enum FileType {
	/**
	 * Master DVD file created after processing of merge transaction file and
	 * old master DVD file.
	 */
	NewMasterDVD("New Master DVD"),
	/**
	 * This DVD file is the subset of NewMasterDVD file.
	 */
	CurrentDVD("Current DVD"),
	/**
	 * This DVD file is the previous version of master DVD file before
	 * performing back-end transactions.
	 */
	OldMasterDVD("Old Master DVD"),
	/**
	 * A single file that contains all the transactions from differnt
	 * transaction files.
	 */
	MergedTransactionFile("Merged Transaction"),
	/**
	 * The transaction file created after end of every front-end session.
	 */
	TransactionFile("Transaction");

	/**
	 * Value of file type.
	 */
	private final String val;

	/**
	 * Public constructor for file type enumerator.
	 * 
	 * @param s
	 *            The value of file type.
	 */
	private FileType(String s) {
		val = s;
	}

	/**
	 * This method gets the name of a file type.
	 * 
	 * @return the name of the file type.
	 */
	public String NAME() {
		return val;
	}
}
