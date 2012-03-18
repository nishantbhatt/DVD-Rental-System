package session.backend;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.TreeMap;
import java.util.zip.DataFormatException;

import iodvd.*;

public class BackEndEngine implements iBackEnd {

	HashMap<String, MasterDVD> masterList = new HashMap<String, MasterDVD>();

	public static void merge(String[] transac_files, String merged_transac_file)
			throws FatalBackEndException {
		// TODO Auto-generated method stub
	}

	@Override
	public void process(String transacFile, String masterDVDFile)
			throws FatalBackEndException, ConstraintFailedException {
		masterList = new HashMap<String, MasterDVD>();
		iFileReader<MasterDVD> ofr;
		iFileReader<DVDTransaction> tfr;

		/* create reader for both transaction file and old master DVD file */
		try {
			ofr = new MasterDVDReader(new FileReader(masterDVDFile));
		} catch (FileNotFoundException ex) {
			throw new FatalBackEndException("Old Master DVD file not found.",
					FileType.OldMasterDVD);
		}
		try {
			tfr = new DVDTransactionReader(new FileReader(transacFile));
		} catch (FileNotFoundException ex) {
			throw new FatalBackEndException(
					"Merged Transaction file not found.",
					FileType.MergedTransactionFile);
		}

		/* read and store old master file into hash map */
		try {
			MasterDVD mdf;
			masterList = new HashMap<String, MasterDVD>();
			while ((mdf = ofr.readNext()) != null)
				masterList.put(mdf.getTitle(), mdf);
		} catch (Exception ex) {
			masterList = null;
			throw new FatalBackEndException(ex.getMessage(),
					FileType.OldMasterDVD);
		}

		/* read and process the transaction */
		try {
			DVDTransaction transac;
			Queue<Integer> removeMasterDVDs = new LinkedList<Integer>();

			while ((transac = tfr.readNext()) != null) {
				MasterDVD mdvd = masterList.get(transac.getDvd_title());
				if (transac.getTrans_id() == TransactionID.CREATE) {

					if (mdvd != null)
						throw new ConstraintFailedException("Cannot create a DVD title \"" + transac.getDvd_title() + "\". " +
								"It is already in the Old Master DVD file.", transac);

					int id = 0;
					if (!removeMasterDVDs.isEmpty())
						id = removeMasterDVDs.poll();
					/* add master DVD */
					masterList.put(transac.getDvd_title(), new MasterDVD(id, transac.getQuantity(), transac.getQuantity(), 
							transac.getStatus(), transac.getPrice(), transac.getDvd_title()));
				} else {

					/* check if DVD title exist in old master DVD file */
					if (mdvd == null)
						throw new FatalBackEndException("Title \"" + transac.getDvd_title() + 
								"\" does not exist in Old Master DVD file.", FileType.OldMasterDVD);

					/* process all the transactions */
					switch (transac.getTrans_id()) {
					case RENT:
						mdvd.setCount(mdvd.getCount() - transac.getQuantity());
						break;
					case RETURN:
						mdvd.setCount(mdvd.getCount() + transac.getQuantity());
						break;
					case REMOVE:
						masterList.remove(transac.getDvd_title());
						removeMasterDVDs.add(mdvd.getId());
						break;
					case BUY:
						mdvd.setCount(mdvd.getCount() - transac.getQuantity());
						break;
					case ADD:
						mdvd.setCount(mdvd.getCount() + transac.getQuantity());
						break;
					case SELL:
						mdvd.setStatus(DVDStatus.SALE);
						mdvd.setPrice(transac.getPrice());
						break;
					}
				}
			}
		} catch (IOException ex) {
			throw new FatalBackEndException(ex.getMessage(),
					FileType.MergedTransactionFile);
		} catch (DataFormatException dex) {
			throw new FatalBackEndException(dex.getMessage(),
					FileType.MergedTransactionFile);
		}
	}

	@Override
	public void write(String currentDVDFile, String masterDVDFile)
			throws FatalBackEndException {
		FileWriter cdf, mdf;
		try {
			cdf = new FileWriter(currentDVDFile);
		} catch (IOException ex) {
			throw new FatalBackEndException(ex.getMessage(),
					FileType.CurrentDVD);
		}

		try {
			mdf = new FileWriter(masterDVDFile);
		} catch (IOException ex) {
			throw new FatalBackEndException(ex.getMessage(),
					FileType.NewMasterDVD);
		}

		TreeMap<Integer, MasterDVD> sorted_list = new TreeMap<Integer, MasterDVD>();
		Iterator<MasterDVD> iter = masterList.values().iterator();
		while (iter.hasNext()) {
			MasterDVD temp = iter.next();
			sorted_list.put(temp.getId(), temp);
		}

		for (Integer i : sorted_list.keySet()) {
			MasterDVD mdvd = sorted_list.get(i);
			try {
				mdf.write(mdvd.toString() + "\n");
			} catch (IOException e1) {
				throw new FatalBackEndException(e1.getMessage(), FileType.NewMasterDVD);
			}
			if (mdvd.getCount() != 0) {
				try {
					cdf.write(((CurrentDVD) mdvd).toString() + "\n");
				} catch (IOException e) {
					throw new FatalBackEndException(e.getMessage(), FileType.CurrentDVD);
				}
			}
		}
	}
}