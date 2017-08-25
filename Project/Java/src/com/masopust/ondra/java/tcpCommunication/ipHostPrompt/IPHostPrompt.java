package com.masopust.ondra.java.tcpCommunication.ipHostPrompt;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

import com.masopust.ondra.java.gui.ipSelectionLayout.IPSelectionLayoutController;
import com.masopust.ondra.java.tcpCommunication.RoverConnection;

/**
 * The {@code IPHostPrompt} class wraps static methods used to work with the
 * <b>IPHostPrompt.txt</b> file.
 * 
 * @author Ondrej Masopust
 *
 */
public class IPHostPrompt {

	/**
	 * Instance of the {@link File} class which holds the path to the
	 * <b>IPHostPrompt.txt</b> file.
	 */
	public static File ipHostPrompt;

	/**
	 * This {@link List} implementation holds the lines connected with the
	 * <b>IPHostPrompt.txt</b> file:
	 * <ul>
	 * <li>after the {@link IPHostPrompt}.{@code setIPHostText} method is called,
	 * this variable holds the lines that are read from the <b>IPHostPrompt.txt</b>
	 * file</li>
	 * <li>after the {@link IPHostPrompt}.{@code writeOptions} method is called,
	 * this variable holds the lines that are to be written to the
	 * <b>IPHostPrompt.txt</b> file</li>
	 * </ul>
	 */
	public static List<String> lines;

	/**
	 * The {@code setIPHostText} method reads the <b>IPHostPrompt.txt</b> file and
	 * sets the <b>IP address</b> and <b>host</b> {@code TextFields} in the <b>IP
	 * Selection Layout</b> with the lines read from the <b>IPHostPrompt.txt</b>
	 * file.
	 */
	public static void setIPHostText() {
		ipHostPrompt = new File("com/masopust/ondra/java/tcpCommunication/ipHostPrompt/IPHostPrompt.txt");
		if (ipHostPrompt.exists()) {
			try {
				lines = Files.readAllLines(ipHostPrompt.toPath());
				IPSelectionLayoutController.ipSelectionLayoutConstroller.getIPField().setText(lines.get(0));
				IPSelectionLayoutController.ipSelectionLayoutConstroller.getPortField().setText(lines.get(1));
			} catch (IOException e) {
				// TODO print message to the layout
				e.printStackTrace();
			}
		}
	}

	/**
	 * The {@code writeOptions} method checks if the inserted options in the
	 * {@link IPSelectionLayoutController} {@code TextFields} differ from those
	 * written in the <b>IPHostPrompt.txt</b> file</li> and if needed, it creates
	 * the <b>IPHostPrompt.txt</b> file</li> and/or writes in the new options.
	 * 
	 * @throws IOException
	 *             if there is a problem with the <b>IPHostPrompt.txt</b> file</li>
	 */
	public static void writeOptions() throws IOException {
		boolean needToChangeFile = false;
		boolean fileExists = true;
		String host = RoverConnection.roverConnection.getHost();
		int port = RoverConnection.roverConnection.getPort();
		ArrayList<String> newLines = new ArrayList<>();
		newLines.add(host);
		newLines.add(Integer.toString(port));
		try {
			if (!lines.get(0).equals(host) || !lines.get(1).equals(String.valueOf(0)))
				needToChangeFile = true;
		} catch (NullPointerException e) {
			fileExists = false;
		}

		if (needToChangeFile || !fileExists) {
			Files.write(ipHostPrompt.toPath(), newLines, StandardOpenOption.WRITE, StandardOpenOption.CREATE);
		}

	}
}
