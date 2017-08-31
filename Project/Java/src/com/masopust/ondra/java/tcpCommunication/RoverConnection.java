package com.masopust.ondra.java.tcpCommunication;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;

import javafx.application.Platform;
import javafx.concurrent.Task;

import com.masopust.ondra.java.gui.ipSelectionLayout.IPSelectionLayoutController;
import com.masopust.ondra.java.gui.mainLayout.MainLayoutController;
import com.masopust.ondra.java.tcpCommunication.ipHostPrompt.IPHostPrompt;

/**
 * The {@code RoverConnection} class wraps all methods needed to handle the
 * connection between the computer and the Rover.
 * 
 * @author Ondrej Masopust
 *
 */

public class RoverConnection extends Task<String> {

	/**
	 * Instance of the {@code RoverConnection} class.
	 */
	public static RoverConnection roverConnection;

	private int port = 5321;
	private String host = "192.168.0.1";
	private Socket clientSocket;
	private int timeout = 1000;
	private int errorCounter = 1;
	private StringBuilder errors = new StringBuilder();
	private BufferedWriter outputTCP;
	private BufferedReader inputTCP;

	/**
	 * Creates instance of the {@code RoverConnection} class with default values:
	 * <ul>
	 * <li>port = 5321</li>
	 * <li>host = 192.168.0.1</li>
	 * </ul>
	 */
	public RoverConnection() {
		clientSocket = new Socket();
	}

	/**
	 * Creates instance of the {@code RoverConnection} class with default value of
	 * the host IP address:
	 * <ul>
	 * <li>host = 192.168.0.1</li>
	 * </ul>
	 * 
	 * @param port
	 *            to be used in the connection
	 */
	public RoverConnection(int port) {
		this.port = port;
		clientSocket = new Socket();
	}

	/**
	 * Creates instance of the {@code RoverConnection} class with default value of
	 * the port:
	 * <ul>
	 * <li>port = 5321</li>
	 * </ul>
	 * 
	 * @param host
	 *            IP address to connect to
	 */
	public RoverConnection(String host) {
		this.host = host;
		clientSocket = new Socket();
	}

	/**
	 * Creates instance of the {@code RoverConnection} class.
	 * 
	 * @param host
	 *            IP address to connect to
	 * @param port
	 *            to be used in the connection
	 */
	public RoverConnection(String host, int port) {
		this.port = port;
		this.host = host;
		clientSocket = new Socket();
	}

	/**
	 * The {@code connect} method tries to establish a connection to a host.
	 */
	public void connect() {
		try {
			clientSocket.connect(new InetSocketAddress(host, port), timeout);
		} catch (SocketTimeoutException e) {
			this.handleConnectionException(e);
		} catch (IOException e) {
			this.handleConnectionException(e);
		} catch (Exception e) {
			this.handleConnectionException(e);
		}
	}

	/**
	 * The {@code handleConnectionException} method wraps the code to handle
	 * exceptions that are thrown during the attempt to connect to the host.
	 * 
	 * @param e
	 *            exception that was thrown
	 */
	private void handleConnectionException(Exception e) {
		String error = errorCounter + ". Connection attempt failed: " + e.getMessage() + " \n";
		System.out.print(error);
		errors.append(error);
		clientSocket = new Socket();
		// write the message to the preloader
		this.updateValue(errors.toString());
	}

	/**
	 * 
	 * @return <b>true</b> if connection to the host is established
	 */
	public boolean connectionEstablished() {
		return clientSocket.isConnected();
	}

	/**
	 * The {@code startRoverConnectionThread} method creates new instance of the
	 * {@code RoverConnection} class and starts a new thread with this instance.
	 */
	public static void startRoverConnectionThread() {
		roverConnection = new RoverConnection(
				IPSelectionLayoutController.ipSelectionLayoutConstroller.getIPField().getText(),
				Integer.parseInt(IPSelectionLayoutController.ipSelectionLayoutConstroller.getPortField().getText()));

		Thread connectionThread = new Thread(roverConnection, "Rover Connection Thread");
		connectionThread.setDaemon(true);
		connectionThread.start();
	}

	/**
	 * @return port number
	 */
	public int getPort() {
		return port;
	}

	/**
	 * @return host IP address
	 */
	public String getHost() {
		return host;
	}

	/**
	 * @return {@link Socket} instance that is used in the connection
	 */
	public Socket getClientSocket() {
		return clientSocket;
	}

	/**
	 * The {@code sendData} method sends message to the Rover
	 * 
	 * @param message
	 *            {@link String} that is to be sent
	 */
	public void sendData(String message) {
		try {
			outputTCP.write(message);
			outputTCP.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * The {@code readLine} method reads a line from the Rover. For more info, see
	 * {@link BufferedReader}.readLine.
	 * 
	 * @return {@link String} containing the received line
	 */
	public String readLine() {
		String message = "";
		try {
			message = inputTCP.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			message = e.getMessage();
		}
		return message;
	}

	@Override
	protected String call() throws IOException {
		int i = 0; // FIXME delete this before sharp run
		while (true) {
			if (this.isCancelled())
				break;
			// try to establish connection
			roverConnection.connect();
			errorCounter++;

			if (roverConnection.connectionEstablished() || i == 1) {
				/* FIXME uncomment
				try {
					outputTCP = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
					inputTCP = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
				} catch (IOException e) {
					// TODO
					e.printStackTrace();
				}
				*/
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
		
		/* testing code
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Platform.runLater(() -> {
			MainLayoutController.writeConsoleRPi("Ahooooj jak se máš? já dovře.");
		});
		*/
		
		/* FIXME uncomment
		while (true) {
			if (this.isCancelled())
				break;
			String input = inputTCP.readLine();
			Platform.runLater(() -> {
				if (!input.equals(""))
					MainLayoutController.writeConsoleRPi(input);
			});
		}
		*/
		return null;
	}
}
