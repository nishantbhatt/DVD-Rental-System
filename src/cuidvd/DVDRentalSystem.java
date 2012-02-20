package cuidvd;

import java.io.IOException;
import java.util.Scanner;
import java.util.zip.DataFormatException;
import sessiondvd.*;
import utilsdvd.GlobalFiles;

/**
 * This class provides command-line user interface to DVD Rental System. This
 * class is responsible for taking user inputs and performing desired actions.
 * 
 * @author 100413064 (Shivam Kalra), 100400174 (Nishant Bhatt)
 */
public class DVDRentalSystem {

	/**
	 * Error string.
	 */
	private static String INVALID_INPUT = "Invalid Input: {1}";
	/**
	 * Session manager object.
	 */
	private static iSession session = null;

	/**
	 * Scanner object to take user inputs.
	 */
	private static Scanner s_in;

	/**
	 * Entry point of the DVD Rental System.
	 * 
	 * @param args
	 *            Command line arguments.
	 * 
	 */
	public static void main(String[] args) {
		session = SessionManager.createSession();
		if (args.length != 2) {
			System.out
					.println("Transaction file and Current DVD file is not provided as command line arguments.");
			return;
		}

		GlobalFiles.CURRENT_DVD_FILEPATH = args[0];
		GlobalFiles.DVD_TRANSACTION_FILEPATH = args[1];

		s_in = new Scanner(System.in);

		String _next;

		while (true) {
			/* take the user command */
			System.out.print("Enter command: ");
			_next = s_in.next();

			try {
				/* perform action as per the user command */
				if (_next.equalsIgnoreCase("create"))
					create_m();
				else if (_next.equalsIgnoreCase("rent"))
					rent_m();
				else if (_next.equalsIgnoreCase("add"))
					add_m();
				else if (_next.equalsIgnoreCase("remove"))
					remove_m();
				else if (_next.equalsIgnoreCase("return"))
					return_m();
				else if (_next.equalsIgnoreCase("sell"))
					sell_m();
				else if (_next.equalsIgnoreCase("buy"))
					buy_m();
				else if (_next.equalsIgnoreCase("login"))
					login_m();
				else if (_next.equalsIgnoreCase("create"))
					create_m();
				else if (_next.equalsIgnoreCase("logout")) {
					session.logout();
					System.out.println("Session closed.");
				} else if (_next.equalsIgnoreCase("quit")) {
					if (session.isRunning())
						throw new Exception(
								"Cannot quit in middle of running session.");
					break;
				} else
					throw new Exception("Invalid command.");

			} catch (Exception ex) {
				System.out.println("Error: " + ex.getMessage());
			}
		}
	}

	/**
	 * This method displays required command to create DVD(s) and checks whether
	 * the user input is correctly formatted.
	 * 
	 * @throws IllegalAccessException
	 *             When standard user tries to create DVD(s).
	 * 
	 */
	public static void create_m() throws IllegalAccessException {

		System.out.print("DVD title: ");
		String title = s_in.next();
		title = title.trim();

		System.out.print("Number of copies: ");
		String copies_count_s = s_in.next();

		if (title.isEmpty())
			throw new IllegalArgumentException(INVALID_INPUT.replaceFirst(
					"\\{1\\}", "Title"));
		int quantity;
		try {
			quantity = Integer.parseInt(copies_count_s);
		} catch (Exception ex) {
			throw new IllegalArgumentException(INVALID_INPUT.replaceFirst(
					"\\{1\\}", "Number of Copies"));
		}

		session.create(title, quantity);
		System.out.println("Created successfully.");
	}

	/**
	 * This method displays required command to create a session and checks
	 * whether the user input is correctly formatted.
	 * 
	 * @throws IOException
	 *             When I/O exception occurs in reading Current DVD File.
	 * @throws DataFormatException
	 *             When Current DVD file is in wrong format.
	 */
	public static void login_m() throws IOException, DataFormatException {

		System.out.print("Session type (standard or admin): ");
		String _stype = s_in.next();

		SessionType stype = null;
		if (_stype.equalsIgnoreCase("admin"))
			stype = SessionType.ADMIN;
		else if (_stype.equalsIgnoreCase("standard"))
			stype = SessionType.STANDARD;
		else
			throw new IllegalArgumentException(INVALID_INPUT.replaceFirst(
					"\\{1\\}", "Session Type"));

		session.login(stype);
		System.out.println("Login accepted.");
	}

	/**
	 * This method displays required command to rent DVD(s) and checks whether
	 * the user input is correctly formatted..
	 */
	public static void rent_m() {

		System.out.print("DVD title: ");
		String title = s_in.next();
		title = title.trim();

		System.out.print("Number of copies: ");
		String copies_count_s = s_in.next();

		if (title.isEmpty())
			throw new IllegalArgumentException(INVALID_INPUT.replaceFirst(
					"\\{1\\}", "Title"));
		int quantity;
		try {
			quantity = Integer.parseInt(copies_count_s);
		} catch (Exception ex) {
			throw new IllegalArgumentException(INVALID_INPUT.replaceFirst(
					"\\{1\\}", "Number of Copies"));
		}

		session.rent(title, quantity);
		System.out.println("Rented successfully.");
	}

	/**
	 * This method displays required command to add DVD(s) and checks whether
	 * the user input is correctly formatted.
	 * 
	 * @throws IllegalAccessException
	 *             When standard user tries to add DVD(s).
	 */
	public static void add_m() throws IllegalAccessException {

		System.out.print("DVD title: ");
		String title = s_in.next();
		title = title.trim();

		System.out.print("Number of copies: ");
		String copies_count_s = s_in.next();

		if (title.isEmpty())
			throw new IllegalArgumentException(INVALID_INPUT.replaceFirst(
					"\\{1\\}", "Title"));
		int quantity;
		try {
			quantity = Integer.parseInt(copies_count_s);
		} catch (Exception ex) {
			throw new IllegalArgumentException(INVALID_INPUT.replaceFirst(
					"\\{1\\}", "Number of Copies"));
		}

		session.add(title, quantity);
		System.out.println("Added successfully.");
	}

	/**
	 * This method displays required command to remove DVD(s) and checks whether
	 * the user input is correctly formatted.
	 * 
	 * @throws IllegalAccessException
	 *             When standard user tries to remove DVD(s).
	 */
	public static void remove_m() throws IllegalAccessException {

		System.out.print("DVD title: ");
		String title = s_in.next();
		title = title.trim();

		if (title.isEmpty())
			throw new IllegalArgumentException(INVALID_INPUT.replaceFirst(
					"\\{1\\}", "Title"));

		session.remove(title);
		System.out.println("Removed successfully.");
	}

	/**
	 * This method displays required command to create DVD(s) and checks whether
	 * the user input is correctly formatted.
	 */
	public static void return_m() {

		System.out.print("DVD title: ");
		String title = s_in.next();
		title = title.trim();

		System.out.print("Number of copies: ");
		String copies_count_s = s_in.next();

		if (title.isEmpty())
			throw new IllegalArgumentException(INVALID_INPUT.replaceFirst(
					"\\{1\\}", "Title"));
		int quantity;
		try {
			quantity = Integer.parseInt(copies_count_s);
		} catch (Exception ex) {
			throw new IllegalArgumentException(INVALID_INPUT.replaceFirst(
					"\\{1\\}", "Number of Copies"));
		}

		session._return(title, quantity);
		System.out.println("Returned successfully.");
	}

	/**
	 * This method displays required command to sell DVD(s) and checks whether
	 * the user input is correctly formatted.
	 * 
	 * @throws IllegalAccessException
	 *             When standard user tries to sell DVD(s).
	 */
	public static void sell_m() throws IllegalAccessException {

		System.out.print("DVD title: ");
		String title = s_in.next();
		title = title.trim();

		System.out.print("DVD Sale Price: ");
		String sale_price = s_in.next();

		if (title.isEmpty())
			throw new IllegalArgumentException(INVALID_INPUT.replaceFirst(
					"\\{1\\}", "Title"));

		int price;
		try {
			price = Integer.parseInt(sale_price);
		} catch (NumberFormatException nFE) {
			throw new IllegalArgumentException(INVALID_INPUT.replaceFirst(
					"\\{1\\}", "Not an Integer"));
		}

		session.sell(title, price);
		System.out.println("DVD status changed to SALE successfully.");
	}

	/**
	 * This method displays required command to buy DVD(s) and checks whether
	 * the user input is correctly formatted.
	 */
	public static void buy_m() {
		System.out.print("DVD title: ");
		String title = s_in.next();
		title = title.trim();

		System.out.print("Number of copies: ");
		String copies_count_s = s_in.next();

		System.out.print("User Confirmation (yes/no): ");
		String _confirmation = s_in.next();
		_confirmation = _confirmation.trim();

		if (_confirmation.equalsIgnoreCase("no")) {
			System.out.println("Action is cancelled.");
			return;
		}

		if (title.isEmpty())
			throw new IllegalArgumentException(INVALID_INPUT.replaceFirst(
					"\\{1\\}", "Title"));
		if (!_confirmation.equalsIgnoreCase("yes"))
			throw new IllegalArgumentException(INVALID_INPUT.replaceFirst(
					"\\{1\\}", "Sale Confirmation"));

		int quantity;
		try {
			quantity = Integer.parseInt(copies_count_s);
		} catch (Exception ex) {
			throw new IllegalArgumentException(INVALID_INPUT.replaceFirst(
					"\\{1\\}", "Number of Copies"));
		}

		session.buy(title, quantity);
		System.out.println("Brought successfully.");
	}

}
