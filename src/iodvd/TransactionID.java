package iodvd;
/**
 * Types of transactions supported by DVD Rental System.
 * @author 100413064
 *
 */
public enum TransactionID {
	/**
	 * RENT transaction.
	 */
	RENT(1), /**
	 * RETURN transaction.
	 */
	RETURN(2), /**
	 * CREATE transaction.
	 */
	CREATE(3), /**
	 * ADD transaction.
	 */
	ADD(4), /**
	 * RMEOVE transaction.
	 */
	REMOVE(5), /**
	 * BUY transaction.
	 */
	BUY(6), /**
	 * SELL transaction.
	 */
	SELL(7), /**
	 * LOGOUT transaction.
	 */
	LOGOUT(0);

	/**
	 * Integer code representing value of every transaction.
	 */
	private final int val;

	/**
	 * Public constructor for ENUM literals.
	 * @param v Value of representing transaction type.
	 */
	private TransactionID(int v) {
		val = v;
	}

	/**
	 * Returns the transaction code.
	 * @return Transaction code of the ENUM.
	 */
	public int ID() {
		return val;
	}
}
