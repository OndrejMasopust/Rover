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

/**
 * The <i>TCPCommunication</i> class was used to test TCP communication with
 * the rover. The client side.
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
