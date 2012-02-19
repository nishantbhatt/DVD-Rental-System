package iodvd;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Writer;
import java.text.DecimalFormat;

/**
 * Class provides functionality to write an Transaction information in correct format.
 * @author 100413064
 *
 */
public class DVDTransactionWriter extends BufferedWriter implements
		iFileWriter<DVDTransaction> {

	/**
	 * Public constructor for the class.
	 * @param arg0 Writer to write the Transaction information on the desired source. 
	 */
	public DVDTransactionWriter(Writer arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void writeObject(DVDTransaction obj) throws IOException {

		/* Writes the transaction information as per format mentioned in requirement document */
		DecimalFormat money_format = new DecimalFormat("000.00");
		super.write(String.format("%02d %-25s %03d %1s %s", obj.trans_id.ID(),
				obj.dvd_title, obj.quantity, obj.status.SYMBOL(),
				money_format.format(obj.price)));
		super.newLine();
	}
}
