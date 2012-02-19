package sessiondvd;

import java.io.IOException;
import java.util.zip.DataFormatException;

/**
 * This class acts as a MEDIATOR to manage the ADMIN and STANDARD sessions.
 * 
 * @author 100413064 (Shivam Kalra), 100400174 (Nishant Bhatt)
 * 
 */
public class SessionManager implements iSession {

	/**
	 * Object of <I>THIS</I> class to provide singleton pattern.
	 */
	private static SessionManager _singleton = null;

	/**
	 * Current open session.
	 */
	sSession current_session = null;

	/**
	 * Private constructor of the class.
	 */
	private SessionManager() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see sessiondvd.pSession#create(java.lang.String, int)
	 */
	@Override
	public void create(String title, int quantity)
			throws IllegalAccessException {
		if (current_session == null)
			throw new IllegalArgumentException(SessionErrors.SESSION_NOT_OPEN);

		if (!(current_session instanceof AdminSession))
			throw new IllegalAccessException(SessionErrors.PRIVILEGED_VIOLATION);

		((pSession) current_session).create(title, quantity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see sessiondvd.pSession#remove(java.lang.String)
	 */
	@Override
	public void remove(String title) throws IllegalAccessException {
		if (current_session == null)
			throw new IllegalArgumentException(SessionErrors.SESSION_NOT_OPEN);

		if (!(current_session instanceof AdminSession))
			throw new IllegalAccessException(SessionErrors.PRIVILEGED_VIOLATION);

		((pSession) current_session).remove(title);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see sessiondvd.pSession#add(java.lang.String, int)
	 */
	@Override
	public void add(String title, int quantity) throws IllegalAccessException {
		if (current_session == null)
			throw new IllegalArgumentException(SessionErrors.SESSION_NOT_OPEN);

		if (!(current_session instanceof AdminSession))
			throw new IllegalAccessException(SessionErrors.PRIVILEGED_VIOLATION);

		((pSession) current_session).add(title, quantity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see sessiondvd.pSession#sell(java.lang.String, double)
	 */
	@Override
	public void sell(String title, double price) throws IllegalAccessException {
		if (current_session == null)
			throw new IllegalArgumentException(SessionErrors.SESSION_NOT_OPEN);

		if (!(current_session instanceof AdminSession))
			throw new IllegalAccessException(SessionErrors.PRIVILEGED_VIOLATION);

		((pSession) current_session).sell(title, price);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see sessiondvd.sSession#rent(java.lang.String, int)
	 */
	@Override
	public void rent(String title, int quantity) {
		if (current_session == null)
			throw new IllegalArgumentException(SessionErrors.SESSION_NOT_OPEN);

		current_session.rent(title, quantity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see sessiondvd.sSession#_return(java.lang.String, int)
	 */
	@Override
	public void _return(String title, int quantity) {
		if (current_session == null)
			throw new IllegalArgumentException(SessionErrors.SESSION_NOT_OPEN);

		current_session._return(title, quantity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see sessiondvd.sSession#buy(java.lang.String, int)
	 */
	@Override
	public void buy(String title, int quantity) {
		if (current_session == null)
			throw new IllegalArgumentException(SessionErrors.SESSION_NOT_OPEN);

		current_session.buy(title, quantity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see sessiondvd.iSession#login(sessiondvd.SessionType)
	 */
	@Override
	public void login(SessionType stype) throws IOException,
			DataFormatException {
		if (current_session != null)
			throw new IllegalArgumentException(
					SessionErrors.ONE_LOGIN_PER_SESSION);

		switch (stype) {
		case ADMIN:
			current_session = new AdminSession();
			break;
		case STANDARD:
			current_session = new StandardSession();
			break;
		}

		if (current_session == null)
			throw new IllegalArgumentException("Invalid session argument.");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see sessiondvd.iSession#logout()
	 */
	@Override
	public void logout() throws IOException {
		if (current_session == null)
			throw new NullPointerException(SessionErrors.SESSION_NOT_OPEN);

		try {
			((StandardSession) current_session).writeTransactionFile();
		} catch (Exception ex) {
			throw new IOException("Error writing a transaction file. "
					+ ex.getMessage());
		} finally {
			current_session = null;
		}
	}

	/**
	 * This is a factory method to create object of <I>THIS</I>. The class is
	 * singleton class, therefore, object of <I>THIS</I> could be created only
	 * once.
	 * 
	 * @return the same session that is currently running.
	 */
	public static iSession createSession() {
		if (_singleton == null)
			_singleton = new SessionManager();

		return _singleton;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see sessiondvd.iSession#isRunning()
	 */
	@Override
	public boolean isRunning() {
		return current_session != null;
	}

}
