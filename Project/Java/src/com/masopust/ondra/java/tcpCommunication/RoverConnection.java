package com.masopust.ondra.java.tcpCommunication;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.stage.Screen;

import com.masopust.ondra.java.gui.Main;

public class RoverConnection extends Task<String> {

	private int port = 5321;
	private String host = "192.168.0.2";
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

	public RoverConnection(int port, String host) {
		this.port = port;
		this.host = host;
		clientSocket = new Socket();
	}

	public void connect(StringBuilder errors) {
		try {
			clientSocket.connect(new InetSocketAddress(host, port), 1000);
		} catch (SocketTimeoutException e) {
			String error = "Connection attempt failed: " + e.getMessage() + " \n";
			System.out.print(error);
			errors.append(error);
			clientSocket = new Socket();
			// write the message to the preloader
			this.updateValue(errors.toString());
		} catch (IOException e) {
			String error = e.getMessage() + "\n";
			System.out.print(error);
			errors.append(error);
			clientSocket = new Socket();
			// write the message to the preloader
			this.updateValue(errors.toString());
		} catch (Exception e) {
			e.printStackTrace(System.out);
			errors.append(e.getMessage() + "\n");
			this.updateValue(errors.toString());
		}
	}

	public boolean connectionEstablished() {
		return clientSocket.isConnected();
	}

	@Override
	protected String call() throws Exception {
		this.updateTitle("Connection thread");
		int i = 0; // FIXME delete this before sharp run
		StringBuilder errors = new StringBuilder();
		while (true) {
			if (this.isCancelled())
				break;
			// try to establish connection
			Main.roverConnection.connect(errors);

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
