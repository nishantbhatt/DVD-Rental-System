package session.backend;
/**
 * 
 * @author nishantbhatt
 *
 */
public interface iBackEnd {
	/**
	 * 
	 * @param transacFile
	 * @param masterDVDFile
	 * @throws FatalBackEndException
	 * @throws ConstraintFailedException
	 */
	public void process(String transacFile, String masterDVDFile) throws FatalBackEndException, ConstraintFailedException;
	/**
	 * 
	 * @param currentDVDFile
	 * @param masterDVDFile
	 * @throws FatalBackEndException
	 */
	public void write(String currentDVDFile, String masterDVDFile) throws FatalBackEndException;
	
}
