package cuidvd;

import java.util.Scanner;

import session.backend.BackEndEngine;
import session.backend.ConstraintFailedException;
import session.backend.FatalBackEndException;
import session.backend.iBackEnd;

/**
 * This class provides command-line user interface to DVD Rental System's back
 * end. This class is responsible for taking user inputs and performing desired
 * actions.
 * 
 * 
 * @author 100413064 (Shivam Kalra), 100400174 (Nishant Bhatt)
 */
public class BackEnd {

	/**
	 * Entry point of the DVD Rental System's back end.
	 * 
	 * @param args Command line arguments.
	 * 
	 */
	public static void main(String[] args) {

		Scanner s_in = new Scanner(System.in);
		iBackEnd session = new BackEndEngine();
		String _next;
		boolean _error = false;

		while (true) {
			/* take the user command */
			System.out.print("Enter command: ");
			_next = s_in.nextLine();

			String[] temp_commands = _next.split(" ");
			String action = temp_commands.length == 0 ? "" : temp_commands[0];
			String[] commands = new String[temp_commands.length - 1 < 0 ? 0
					: temp_commands.length - 1];

			for (int i = 0; i < commands.length; i++)
				commands[i] = temp_commands[i + 1];
			try {
				/* perform action as per the user command */
				if (action.equalsIgnoreCase("merge")) {
					if (commands.length < 3)
						throw new IllegalArgumentException(
								"Merge: merge <tf1> <tf2> <tf3> <tf4> .... <tfn> <mtf>");

					String transactionFiles[] = new String[commands.length - 1];
					for (int i = 0; i < transactionFiles.length; i++)
						transactionFiles[i] = commands[i];

					/* merge all the files */
					BackEndEngine.merge(transactionFiles,
							commands[commands.length - 1]);
					System.out.println("Merge command completed.");
				} else if (action.equalsIgnoreCase("process")) {
					if (commands.length != 2)
						throw new IllegalArgumentException(
								"Process: process <Merged Transaction File> <Old Master DVD File>");
					session.process(commands[0], commands[1]);
					System.out.println("Process command completed.");
				} else if (action.equalsIgnoreCase("write")) {
					if (commands.length != 2)
						throw new IllegalArgumentException(
								"Write: write <Current DVD File> <New Master DVD File>");
					session.write(commands[0], commands[1]);
					System.out.println("Write command completed.");
				} else if (_next.equalsIgnoreCase("quit")) {
					System.exit(_error ? 1 : 0);
				} else
					throw new IllegalArgumentException("Undefined command: "
							+ action);
			} catch (FatalBackEndException ex) {
				_error = true;
				System.err
						.println("Error: Error Type = Fatal Back End, Message = "
								+ ex.getMessage()
								+ ", File Type = "
								+ ex.getFileType().NAME()
								+ ", File Name = "
								+ ex.getFileName());
			} catch (ConstraintFailedException exx) {
				_error = true;
				System.err
						.println("Error: Error Type = Constrained Failed, Message = "
								+ exx.getMessage());
			} catch (Exception exxx) {
				_error = true;
				System.err.println(exxx.getMessage());
			}
		}
	}
}
