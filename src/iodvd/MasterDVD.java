package iodvd;

import iodvd.exception.DVDFormatException;

import java.text.DecimalFormat;

/**
 * Class stores information related to DVD in Master DVD File.
 * @author shivam
 *
 */
public class MasterDVD  {
	
	/**
	 * Current DVD file.
	 */
	CurrentDVD cui = null;
	/**
	 * Get total number of DVDs in inventory.
	 * @return total number of DVD
	 */
	public int getTotal_quantity() {
		return this.total_quantity;
	}

	/**
	 * Set total quantities of DVDs.
	 * @param total_quantity number of DVDs count
	 * @throws DVDFormatException if quantity is not between 0-999.
	 */
	public void setTotal_quantity(int total_quantity) throws DVDFormatException {
		if(total_quantity < 0 || total_quantity > 9999)
			throw new DVDFormatException("DVD count must be between 0 - 9999.");
		this.total_quantity = total_quantity;
	}

	/**
	 * Get remaining number of DVDs.
	 * @return count of remaining number of DVDs.
	 */
	public int getRemaining_quantity() {
		return cui.getCount();
	}

	/**
	 * Set the remaining number of DVDs.
	 * @param remaining_quantity count of remaining number of DVDs.
	 * @throws DVDFormatException if violates the bounds.
	 */
	public void setRemaining_quantity(int remaining_quantity) throws DVDFormatException {
		cui.setCount(remaining_quantity);
	}

	/**
	 * Get ID of DVDs.
	 * @return ID of DVD.
	 */
	public int getId() {
		return id;
	}

	/**
	 * Total number copies of a DVD in inventory.
	 */
	int total_quantity;
	/**
	 * ID of DVD.
	 */
	int id;

	/**
	 * Public constructor for master DVD.
	 * @param id ID of DVD
	 * @param remaining_quantity Total copies of DVD available for rent.
	 * @param total_quantity Total copies of DVD.
	 * @param status Status of DVD.
	 * @param price Price of DVD.
	 * @param title Title of DVD.
	 * @throws DVDFormatException If DVD bounds are violated.
	 */
	public MasterDVD(int id, int remaining_quantity, int total_quantity,
			DVDStatus status, double price, String title)
			throws DVDFormatException {
		cui = new CurrentDVD(title, price, remaining_quantity, status);
		this.id = id;
		this.total_quantity = total_quantity;
	}

	/**
	 * Writes the DVD in the format as per the requirement document.
	 */
	public String toString() {
		/* writes the master DVD information as per the requirement document */
		DecimalFormat money_format = new DecimalFormat("000.00");
		String _return = String.format("%05d %04d %04d %1s %s %-25s", cui.count,
				getRemaining_quantity(), getTotal_quantity(), cui.status.SYMBOL(),
				money_format.format(cui.price), cui.title);

		return _return;
	}
	
	/**
	 * Get current DVD
	 * @return
	 */
	public CurrentDVD get_cdvd() {
		return cui;
	}
}