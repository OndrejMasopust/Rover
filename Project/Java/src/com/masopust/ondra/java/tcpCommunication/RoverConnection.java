package com.masopust.ondra.java.tcpCommunication;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;

import javafx.concurrent.Task;

import com.masopust.ondra.java.gui.ipSelectionLayout.IPSelectionLayoutController;
import com.masopust.ondra.java.gui.mainLayout.MainLayoutController;
import com.masopust.ondra.java.tcpCommunication.ipHostPrompt.IPHostPrompt;

public class RoverConnection extends Task<String> {

	public static RoverConnection roverConnection;

	private int port = 5321;
	private String host = "192.168.0.1";
	private Socket clientSocket;
	private int timeout = 1000;

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

	public void connect(StringBuilder errors, int errorCounter) {
		try {
			clientSocket.connect(new InetSocketAddress(host, port), timeout);
		} catch (SocketTimeoutException e) {
			this.handleConnectionException(e, errors, errorCounter);
		} catch (IOException e) {
			this.handleConnectionException(e, errors, errorCounter);
		} catch (Exception e) {
			this.handleConnectionException(e, errors, errorCounter);
		}
	}

	private void handleConnectionException(Exception e, StringBuilder errors, int errorCounter) {
		String error = errorCounter + ". Connection attempt failed: " + e.getMessage() + " \n";
		System.out.print(error);
		errors.append(error);
		clientSocket = new Socket();
		// write the message to the preloader
		this.updateValue(errors.toString());
	}

	public boolean connectionEstablished() {
		return clientSocket.isConnected();
	}

	public static void startRoverConnectionThread() {
		roverConnection = new RoverConnection(
				IPSelectionLayoutController.ipSelectionLayoutConstroller.getIPField().getText(),
				Integer.parseInt(IPSelectionLayoutController.ipSelectionLayoutConstroller.getPortField().getText()));

		Thread connectionThread = new Thread(roverConnection, "Rover Connection Thread");
		connectionThread.setDaemon(true);
		connectionThread.start();
	}

	public int getPort() {
		return port;
	}

	public String getHost() {
		return host;
	}

	@Override
	protected String call() throws Exception {
		int i = 0; // FIXME delete this before sharp run
		StringBuilder errors = new StringBuilder();
		int errorCounter = 1;
		while (true) {
			if (this.isCancelled())
				break;
			// try to establish connection
			roverConnection.connect(errors, errorCounter);
			errorCounter++;

			if (roverConnection.connectionEstablished() || i == 1) {
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
		IPHostPrompt.writeOptions();
		MainLayoutController.loadMainScene();
		return null;
	}
}
