package com.masopust.ondra.java.gui;

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

public class RoverConnection extends Task<Void>{

	private int port;
	private String host;
	private Socket clientSocket;

	public RoverConnection() {
		this(5321, "192.168.0.1");
	}

	public RoverConnection(int port) {
		this(port, "192.168.0.1");
	}

	public RoverConnection(String host) {
		this(5321, host);
	}

	public RoverConnection(int port, String host) {
		this.port = port;
		this.host = host;
		clientSocket = new Socket();
	}

	public void connect() {
		try {
			clientSocket.connect(new InetSocketAddress(host, port), 1000);
		} catch (SocketTimeoutException e) {
			System.out.print("Connection attempt failed: ");
			System.out.println(e.getMessage());
			clientSocket = new Socket();
			// TODO write the message to the TextArea in the preloader
		} catch (IOException e) {
			// TODO Auto-generated catch block
			// TODO write the message to the TextArea in the preloader
			e.printStackTrace();
		}
	}

	public boolean connectionEstablished() {
		return clientSocket.isConnected();
	}

	@Override
	protected Void call() throws Exception {
		// TODO check if task is cancelled
		int i = 0;
		while (true) {
			// try to establish connection
			Main.roverConnection.connect();

			if (Main.roverConnection.connectionEstablished() || i == 5) {
				break;
			} else {
				// wait 300ms
				try {
					Thread.sleep(300);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println("Slept for 300ms");
				i++;
			}
		}

		// change the scene
		Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();

		Main.mainStageLoader = new FXMLLoader();
		Main.mainStageLoader.setLocation(getClass().getResource("mainLayout/MainLayout.fxml"));

		
		Platform.runLater(() -> {
			try {
				Main.mainStageLayout = Main.mainStageLoader.load();
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
