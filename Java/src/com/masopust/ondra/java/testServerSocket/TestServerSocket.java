package com.masopust.ondra.java.testServerSocket;

import java.io.BufferedReader;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

/**
 * The {@code TCPCommunication} class was used to test TCP communication with
 * the rover.
 * 
 * @author Ondrej Masopust
 *
 */
public class TestServerSocket extends Thread {

	private static Socket clientSocket;

	public TestServerSocket(String name) {
		super(name);
	}

	@Override
	public void run() {
		BufferedReader inputTCP;
		try {
			inputTCP = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

			while (true) {
				String input = inputTCP.readLine();
				if (input != null)
					System.out.println("Control Panel: " + input);
			}
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}

	}

	public static void main(String[] args) throws UnknownHostException, IOException {
		ServerSocket serverSocket = new ServerSocket(5321);
		clientSocket = serverSocket.accept();
		System.out.println("Server launched correctly.");

		Scanner stdInput = new Scanner(System.in);

		//BufferedWriter outputTCP = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
		PrintWriter outputTCP = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream()), true);

		TestServerSocket TCPreader = new TestServerSocket("TCPreader");
		TCPreader.start();

		while (true) {
			String message = stdInput.nextLine();
			if (message.equals("q")) {
				break;
			}

			outputTCP.println(message);
		}
		outputTCP.close();
		stdInput.close();
		clientSocket.close();
		serverSocket.close();
		if (clientSocket.isClosed()) {
			System.out.println("Server socket closed correctly.");
		}
	}

}
