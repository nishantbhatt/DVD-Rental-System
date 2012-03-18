package iodvd;

import iodvd.exception.DVDCountFormatException;

import java.text.DecimalFormat;

public class MasterDVD extends CurrentDVD {

	// TODO: JAVADOCS

	public int getTotal_quantity() {
		return super.getCount();
	}

	public void setTotal_quantity(int total_quantity) {
		this.total_quantity = total_quantity;
	}

	public int getRemaining_quantity() {
		return super.getCount();
	}

	public void setRemaining_quantity(int remaining_quantity) throws DVDCountFormatException {
		if(remaining_quantity < 0)
			throw new DVDCountFormatException("Remaning DVD quantity cannot be negative");
		super.setCount(remaining_quantity);
	}

	public int getId() {
		return id;
	}

	int total_quantity;
	int id;

	public MasterDVD(int id, int remaining_quantity, int total_quantity,
			DVDStatus status, double price, String title) {
		super(title, price, remaining_quantity, status);
		this.id = id;
		this.total_quantity = total_quantity;
	}

	public String toString() {
		/* writes the master DVD information as per the requirement document */
		DecimalFormat money_format = new DecimalFormat("000.00");
		String _return = String.format("%05d %04d %04d %1s %s %-25s", ++count,
				getRemaining_quantity(), getTotal_quantity(), status.SYMBOL(),
				money_format.format(price), title);

		return _return;
	}
}
