package session.backend;
/**
 * The different type of files on which transactions are performed in back-end sessions.  
 * @author nishantbhatt
 *
 */
public enum FileType {
	NewMasterDVD("New Master DVD"), CurrentDVD("Current DVD"), OldMasterDVD("Old Master DVD"), MergedTransactionFile("Merged Transaction"), TransactionFile("Transaction");
	
	private final String val;
	private FileType(String s) {
		val = s;
	}
	
	public String NAME() {
		return val;
	}
}
