package session.backend;

public interface iBackEnd {
	public void process(String transacFile, String masterDVDFile) throws FatalBackEndException, ConstraintFailedException;
	public void write(String currentDVDFile, String masterDVDFile) throws FatalBackEndException;
	
}
