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
import iodvd.exception.DVDFormatException;
import iodvd.exception.TransactionFormatException;

/**
 * This class is the heart of back-end session that processes all the back end
 * transactions.
 * 
 * @author nishantbhatt
 * 
 */
public class BackEndEngine implements iBackEnd {

	/**
	 * Hashmap structure that stores Master DVD data.
	 */
	HashMap<String, MasterDVD> masterList = new HashMap<String, MasterDVD>();

	/**
	 * 
	 * @param transac_files
	 *            Transaction files that holds all back-end transactions.
	 * @param merged_transac_file
	 *            Transaction files after they have been merged.
	 * @throws FatalBackEndException
	 *             Exceptions that occur during back-end transactions.
	 */
	public static void merge(String[] transac_files, String merged_transac_file)
			throws FatalBackEndException {
		iFileReader<DVDTransaction> tfr;
		FileWriter fwrt;
		try {
			fwrt = new FileWriter(merged_transac_file);
		} catch (IOException e) {
			throw new FatalBackEndException(e.getMessage(),
					FileType.MergedTransactionFile, merged_transac_file);
		}
		for (int i = 0; i < transac_files.length; i++) {
			try {
				tfr = new DVDTransactionReader(new FileReader(transac_files[i]));
			} catch (FileNotFoundException e) {
				throw new FatalBackEndException(e.getMessage(),
						FileType.TransactionFile, transac_files[i]);
			}
			DVDTransaction transac;
			try {
				while ((transac = tfr.readNext()) != null) {
					try {
						fwrt.write(transac.toString() + "\n");
					} catch (IOException e) {
						throw new FatalBackEndException(e.getMessage(),
								FileType.MergedTransactionFile,
								merged_transac_file);
					}
				}
			} catch (IOException ex) {
				throw new FatalBackEndException(ex.getMessage(),
						FileType.TransactionFile, transac_files[i]);
			} catch (DataFormatException exx) {
				throw new FatalBackEndException(exx.getMessage(),
						FileType.TransactionFile, transac_files[i]);
			}
		}
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
					FileType.OldMasterDVD, masterDVDFile);
		}
		try {
			tfr = new DVDTransactionReader(new FileReader(transacFile));
		} catch (FileNotFoundException ex) {
			throw new FatalBackEndException(
					"Merged Transaction file not found.",
					FileType.MergedTransactionFile, transacFile);
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
					FileType.OldMasterDVD, masterDVDFile);
		}

		DVDTransaction transac = null;
		/* read and process the transaction */
		try {
			Queue<Integer> removeMasterDVDs = new LinkedList<Integer>();

			while ((transac = tfr.readNext()) != null) {
				MasterDVD mdvd = masterList.get(transac.getDvd_title());
				if (transac.getTrans_id() == TransactionID.CREATE) {

					if (mdvd != null)
						throw new ConstraintFailedException(
								"Cannot create a DVD title \""
										+ transac.getDvd_title()
										+ "\". "
										+ "It is already in the Old Master DVD file.",
								transac);

					int id = 0;
					if (!removeMasterDVDs.isEmpty())
						id = removeMasterDVDs.poll();
					else
						id = masterList.size();

					/* add master DVD */
					masterList
							.put(transac.getDvd_title(),
									new MasterDVD(id, transac.getQuantity(),
											transac.getQuantity(), transac
													.getStatus(), transac
													.getPrice(), transac
													.getDvd_title()));
				} else {

					/* check if DVD title exist in old master DVD file */
					if (mdvd == null)
						throw new FatalBackEndException("Title \""
								+ transac.getDvd_title()
								+ "\" does not exist in Old Master DVD file.",
								FileType.OldMasterDVD, masterDVDFile);

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
					FileType.MergedTransactionFile, transacFile);
		} catch (DVDFormatException exdvd) {
			throw new ConstraintFailedException(exdvd.getMessage(), transac);
		} catch (TransactionFormatException dex) {
			throw new FatalBackEndException(dex.getMessage(),
					FileType.MergedTransactionFile, transacFile);
		} catch (DataFormatException impex) {
			throw new IllegalArgumentException(impex.getMessage());
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
					FileType.CurrentDVD, currentDVDFile);
		}

		try {
			mdf = new FileWriter(masterDVDFile);
		} catch (IOException ex) {
			throw new FatalBackEndException(ex.getMessage(),
					FileType.NewMasterDVD, masterDVDFile);
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
				throw new FatalBackEndException(e1.getMessage(),
						FileType.NewMasterDVD, masterDVDFile);
			}
			if (mdvd.getCount() != 0) {
				try {
					cdf.write(((CurrentDVD) mdvd).toString() + "\n");
				} catch (IOException e) {
					throw new FatalBackEndException(e.getMessage(),
							FileType.CurrentDVD, currentDVDFile);
				}
			}
		}
	}
}