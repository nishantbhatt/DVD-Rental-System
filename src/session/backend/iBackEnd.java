package session.backend;
/**
 * The interface for back-end session.
 * @author nishantbhatt
 *
 */
public interface iBackEnd {
	/**
	 * This method process all the transaction files in back-end session.
	 * @param transacFile The transaction file which is created after each transaction.
	 * @param masterDVDFile The DVD file that holds all the data.
	 * @throws FatalBackEndException  All the exceptions that occurs during the back-end transaction.
	 * @throws ConstraintFailedException All the constrain exceptions that occurs during that back-end session. 
	 */
	public void process(String transacFile, String masterDVDFile) throws FatalBackEndException, ConstraintFailedException;
	/**
	 * 
	 * @param currentDVDFile The DVD file that holds data about the remaining DVDs. 
	 * @param masterDVDFile The DVD file that contains information about all the DVDs (including the ones that are removed after transactions).
	 * @throws FatalBackEndException All the exceptions that occurs during the back-end transaction.
	 */
	public void write(String currentDVDFile, String masterDVDFile) throws FatalBackEndException;
	
}
