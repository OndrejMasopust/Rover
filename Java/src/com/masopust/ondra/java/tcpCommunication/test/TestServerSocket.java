package com.masopust.ondra.java.tcpCommunication.test;

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
 * The <i>TestServerSocket</i> class was used to test TCP communication with
 * the rover. The server side.
 *
 * MIT License
 *
 * Copyright (c) 2018 Ondřej Masopust; Gymnázium Praha 6, Nad Alejí 1952
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
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
