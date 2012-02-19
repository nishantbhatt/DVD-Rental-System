package sessiondvd;

import java.io.IOException;
import java.util.zip.DataFormatException;

/**
 * Provides all the functionalities of a DVD rental system.
 * 
 * @author Developer: 100413064 (Shivam Kalra), Tester: 100400174 (Nishant
 *         Bhatt)
 * 
 */
public interface iSession extends pSession, sSession {

	/**
	 * Starts a new session.
	 * 
	 * @param stype
	 *            Type of session (ADMIN or STANDARD).
	 * @throws IOException
	 *             When I/O exception occurs in reading Current DVD File.
	 * @throws DataFormatException
	 *             When Current DVD File is in wrong format.
	 */
	public void login(SessionType stype) throws IOException,
			DataFormatException;

	/**
	 * Closes the running session and writes a transaction file.
	 * 
	 * @throws IOException
	 *             When I/O exception occurs while writing the transaction file.
	 */
	public void logout() throws IOException;

	/**
	 * Checks if any session is running.
	 * 
	 * @return Returns TRUE if session is running, otherwise FALSE.
	 */
	public boolean isRunning();

}
