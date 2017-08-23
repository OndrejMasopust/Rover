package com.masopust.ondra.java.tcpCommunication;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.stage.Screen;

import com.masopust.ondra.java.gui.Main;
import com.masopust.ondra.java.gui.ipSelectionLayout.IPSelectionLayoutController;

public class RoverConnection extends Task<String> {

	private int port = 5321;
	private String host = "192.168.0.1";
	private Socket clientSocket;

	public RoverConnection() {
		clientSocket = new Socket();
	}

	public RoverConnection(int port) {
		this.port = port;
		clientSocket = new Socket();
	}

	public RoverConnection(String host) {
		this.host = host;
		clientSocket = new Socket();
	}

	public RoverConnection(String host, int port) {
		this.port = port;
		this.host = host;
		clientSocket = new Socket();
	}

	public void connect(StringBuilder errors, int counter) {
		try {
			clientSocket.connect(new InetSocketAddress(host, port), 1000);
		} catch (SocketTimeoutException e) {
			this.handleConnectionException(e, errors, counter);
		} catch (IOException e) {
			this.handleConnectionException(e, errors, counter);
		} catch (Exception e) {
			this.handleConnectionException(e, errors, counter);
		}
	}

	private void handleConnectionException(Exception e, StringBuilder errors, int counter) {
		// counter++; FIXME counter does not increment larger than 1
		String error = counter + ". Connection attempt failed: " + e.getMessage() + " \n";
		System.out.print(error);
		errors.append(error);
		clientSocket = new Socket();
		// write the message to the preloader
		this.updateValue(errors.toString());
	}

	public boolean connectionEstablished() {
		return clientSocket.isConnected();
	}

	@Override
	protected String call() throws Exception {
		this.updateTitle("Connection thread");
		int i = 0; // FIXME delete this before sharp run
		StringBuilder errors = new StringBuilder();
		int errorCounter = 1;
		while (true) {
			if (this.isCancelled())
				break;
			// try to establish connection
			Main.roverConnection.connect(errors, errorCounter);
			errorCounter++;

			if (Main.roverConnection.connectionEstablished() || i == 5) {
				break;
			} else {
				// wait 500ms
				int sleepTime = 500;
				try {
					Thread.sleep(sleepTime);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					if (this.isCancelled())
						break;
				}
				System.out.printf("Slept for %dms\n", sleepTime);
				i++;
			}
		}

		// write the IP and Port to txt file
		boolean needToChangeFile = false;
		boolean fileExists = true;
		ArrayList<String> newLines = new ArrayList<>();
		newLines.add(host);
		newLines.add(Integer.toString(port));
		try {
			if (!IPSelectionLayoutController.lines.get(0).equals(host)
					|| !IPSelectionLayoutController.lines.get(1).equals(String.valueOf(0)))
				needToChangeFile = true;
		} catch (NullPointerException e) {
			fileExists = false;
		}

		if (needToChangeFile || !fileExists) {
			Files.write(IPSelectionLayoutController.ipHostPrompt.toPath(), newLines, StandardOpenOption.WRITE,
					StandardOpenOption.CREATE);
		}

		// change the scene of the mainStage
		Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();

		Main.mainStageLoader = new FXMLLoader();
		Main.mainStageLoader.setLocation(getClass().getResource("mainLayout/MainLayout.fxml"));

		Platform.runLater(() -> {
			try {
				Main.mainStageLayout = Main.mainStageLoader.load(new FileInputStream(
						"/Users/Ondra/Documents/Programming/Maturita/Project/Java/src/com/masopust/ondra/java/gui/mainLayout/MainLayout.fxml")); // FIXME
				Scene mainScene = new Scene(Main.mainStageLayout);
				Main.mainStage.setScene(mainScene);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// set dimensions of the main window
			Main.mainStage.setX(screenBounds.getMinX());
			Main.mainStage.setY(screenBounds.getMinY());
			Main.mainStage.setWidth(screenBounds.getWidth());
			Main.mainStage.setHeight(screenBounds.getHeight());
		});
		return null;
	}

}
