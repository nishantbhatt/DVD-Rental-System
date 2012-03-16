package iodvd;

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

	public void setRemaining_quantity(int remaining_quantity) {
		super.setCount(remaining_quantity);
	}

	public int getId() {
		return id;
	}
	
	int total_quantity;
	int id;
	
	public MasterDVD(int id, int remaining_quantity, int total_quantity, DVDStatus status, double price, String title) {
		super(title, price, remaining_quantity, status);
		this.id = id;
		this.total_quantity = total_quantity;
	}
}
