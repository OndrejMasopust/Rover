package com.masopust.ondra.java.gui;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class RoverConnection {

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

}
