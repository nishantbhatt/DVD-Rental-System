package iodvd;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Writer;
import java.text.DecimalFormat;

public class MasterDVDWriter extends BufferedWriter implements
iFileWriter<MasterDVD> {

	int count = 0;
	public MasterDVDWriter(Writer arg0) {
		super(arg0);
	}

	@Override
	public void writeObject(MasterDVD obj) throws IOException {
		/* writes the master DVD information as per the requirement document */
		DecimalFormat money_format = new DecimalFormat("000.00");
		super.write(String.format("%05d %04d %04d %1s %s %-25s", ++count, obj.getRemaining_quantity(), obj.getTotal_quantity(),
				obj.status.SYMBOL(), money_format.format(obj.price), obj.title));
		super.newLine();
	}
}
