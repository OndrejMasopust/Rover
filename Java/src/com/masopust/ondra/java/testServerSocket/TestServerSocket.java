package com.masopust.ondra.java.testServerSocket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class TestServerSocket extends Thread{

	public static void main(String[] args) {
		try (
			ServerSocket serverSocket = new ServerSocket(5321);
			Socket clientSocket = serverSocket.accept();
			PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
			BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			Scanner stdInput = new Scanner(System.in);
		){
			System.out.println("Connection succesful.");
			while (true) {
				
				String message;
				if ((message = stdInput.nextLine()) != null) {
					if (message.equals("q")) {
						break;
					}
					out.println(message);
				}
				
				String input;
				if ((input = in.readLine()) != null) {
					System.out.println(input);
				}
				
				//System.out.println(in.readLine());
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}

}
