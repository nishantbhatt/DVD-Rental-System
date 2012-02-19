package iodvd;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Writer;
import java.text.DecimalFormat;

/**
 * Writes a DVD information to current 
 * @author 100413064
 *
 */
public class CurrentDVDWriter extends BufferedWriter implements
		iFileWriter<CurrentDVD> {

	/**
	 * Public constructor for the class.
	 * @param arg0 Writer object used to write Current DVD information on desired source.
	 */
	public CurrentDVDWriter(Writer arg0) {
		super(arg0);
	}

	/* (non-Javadoc)
	 * @see iodvd.iFileWriter#writeObject(java.lang.Object)
	 */
	@Override
	public void writeObject(CurrentDVD obj) throws IOException {
		
		/* writes the Current DVD information as per the requirement document */
		DecimalFormat money_format = new DecimalFormat("000.00");
		super.write(String.format("%-25s %04d %1s %1s", obj.title, obj.count,
				obj.status.SYMBOL(), money_format.format(obj.price)));
		super.newLine();
	}
}
