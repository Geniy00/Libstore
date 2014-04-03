package ua.nau.libstore.util;

import java.io.*;


public class Exec {

	private static boolean verbose = true;

	/**
	 * Determines if the Exec class should print which commands are being
	 * executed, and prints error messages if a problem is found. Default is
	 * true.
	 * 
	 * @param verboseFlag
	 *            true: print messages, false: don’t.
	 */

	public static void setVerbose(boolean verboseFlag) {
		verbose = verboseFlag;
	}

	/** Will Exec print status messages? */

	public static boolean getVerbose() {
		return (verbose);
	}

	/**
	 * Starts a process to execute the command. Returns immediately, even if the
	 * new process is still running.
	 * 
	 * @param command
	 *            The <B>full</B> pathname of the command to be executed. No
	 *            shell built-ins (e.g., "cd") or shell meta-chars (e.g. ">")
	 *            are allowed.
	 * @return false if a problem is known to occur, but since this returns
	 *         immediately, problems aren’t usually found in time. Returns true
	 *         otherwise.
	 */

	public static boolean exec(String command) {
		return (exec(command, false, false));
	}

	/**
	 * Starts a process to execute the command. Waits for the process to finish
	 * before returning.
	 * 
	 * @param command
	 *            The <B>full</B> pathname of the command to be executed. No
	 *            shell built-ins or shell metachars are allowed.
	 * @return false if a problem is known to occur, either due to an exception
	 *         or from the subprocess returning a nonzero value. Returns true
	 *         otherwise.
	 */

	public static boolean execWait(String command) {
		return (exec(command, false, true));
	}

	/**
	 * Starts a process to execute the command. Prints any output the command
	 * produces.
	 * 
	 * @param command
	 *            The <B>full</B> pathname of the command to be executed. No
	 *            shell built-ins or shell meta-chars are allowed.
	 * @return false if a problem is known to occur, either due to an exception
	 *         or from the subprocess returning a nonzero value. Returns true
	 *         otherwise.
	 */

	public static boolean execPrint(String command) {
		return (exec(command, true, false));
	}

	/**
	 * This creates a Process object via Runtime.getRuntime.exec() Depending on
	 * the flags, it may call waitFor on the process to avoid continuing until
	 * the process terminates, and open an input stream from the process to read
	 * the results.
	 */

	private static boolean exec(String command, boolean printResults, boolean wait) {
		if (verbose) {
			printSeparator();
			System.out.println("Executing '" + command + "'.");
		}
		try {
			// Start running command, returning immediately.
			Process p = Runtime.getRuntime().exec(command);

			// Print the output. Since we read until there is no more
			// input, this causes us to wait until the process is
			// completed.
			if (printResults) {
				BufferedReader buffer = new BufferedReader(
						new InputStreamReader(p.getInputStream()));
				String s = null;
				try {
					while ((s = buffer.readLine()) != null) {
						System.out.println("Output: " + s);
					}
					buffer.close();
					if (p.exitValue() != 0) {
						if (verbose) {
							printError(command + " -- p.exitValue() != 0");
						}
						return (false);
					}
				} catch (Exception e) {
					// Ignore read errors; they mean the process is done.
				}

				// If not printing the results, then we should call waitFor
				// to stop until the process is completed.
			} else if (wait) {
				try {
					System.out.println(" ");
					int returnVal = p.waitFor();
					if (returnVal != 0) {
						if (verbose) {
							printError(command);
						}
						return (false);
					}
				} catch (Exception e) {
					if (verbose) {
						printError(command, e);
					}
					return (false);
				}
			}
		} catch (Exception e) {
			if (verbose) {
				printError(command, e);
			}
			return (false);
		}
		return (true);
	}

	private static void printError(String command, Exception e) {
		System.out.println("Error doing exec(" + command + "): " + e.getMessage());
		System.out.println("Did you specify the full " + "pathname?");
	}

	private static void printError(String command) {
		System.out.println("Error executing ’" + command + "’.");
	}

	private static void printSeparator() {
		System.out.println("==============================================");
	}
}