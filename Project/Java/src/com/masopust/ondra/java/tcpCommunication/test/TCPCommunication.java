package com.masopust.ondra.java.tcpCommunication.test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class TCPCommunication extends Thread {

	private static Socket clientSocket;

	public TCPCommunication(String name) {
		super(name);
	}

	@Override
	public void run() {
		BufferedReader inputTCP;
		try {
			inputTCP = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

			while (true) {
				System.out.println("RPi3: " + inputTCP.readLine());
			}
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}

	}

	public static void main(String[] args) throws UnknownHostException, IOException {
		int port = 5321;
		String host = "192.168.0.1";

		clientSocket = new Socket();
		clientSocket.connect(new InetSocketAddress(host, port), 1000);
		System.out.println("Client launched correctly.");

		Scanner stdInput = new Scanner(System.in);

		BufferedWriter outputTCP = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
		
		TCPCommunication TCPreader = new TCPCommunication("TCPreader");
		TCPreader.start();

		while (true) {
			String message = stdInput.nextLine();
			if (message.equals("q")) {
				break;
			}

			outputTCP.write(message);
			outputTCP.flush();
		}
		outputTCP.close();
		stdInput.close();
		clientSocket.close();
		if (clientSocket.isClosed()) {
			System.out.println("Client socket closed correctly.");
		}
	}

}
