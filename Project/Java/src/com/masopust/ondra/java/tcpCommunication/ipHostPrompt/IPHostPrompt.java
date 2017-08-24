package com.masopust.ondra.java.tcpCommunication.ipHostPrompt;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

import com.masopust.ondra.java.gui.ipSelectionLayout.IPSelectionLayoutController;
import com.masopust.ondra.java.tcpCommunication.RoverConnection;

public class IPHostPrompt {
	
	public static File ipHostPrompt;
	public static List<String> lines;
	
	public static void setIPHostText() {
		ipHostPrompt = new File("com/masopust/ondra/java/tcpCommunication/ipHostPrompt/IPHostPrompt.txt");
		if (ipHostPrompt.exists()) {
			try {
				lines = Files.readAllLines(ipHostPrompt.toPath());
				IPSelectionLayoutController.ipSelectionLayoutConstroller.getIPField().setText(lines.get(0));
				IPSelectionLayoutController.ipSelectionLayoutConstroller.getPortField().setText(lines.get(1));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void writeOptions() throws IOException {
		boolean needToChangeFile = false;
		boolean fileExists = true;
		String host = RoverConnection.roverConnection.getHost();
		int port = RoverConnection.roverConnection.getPort();
		ArrayList<String> newLines = new ArrayList<>();
		newLines.add(host);
		newLines.add(Integer.toString(port));
		try {
			if (!lines.get(0).equals(host)
					|| !lines.get(1).equals(String.valueOf(0)))
				needToChangeFile = true;
		} catch (NullPointerException e) {
			fileExists = false;
		}

		if (needToChangeFile || !fileExists) {
			Files.write(ipHostPrompt.toPath(), newLines, StandardOpenOption.WRITE,
					StandardOpenOption.CREATE);
		}

	}
}
