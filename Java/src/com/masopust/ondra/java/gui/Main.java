package com.masopust.ondra.java.gui;

import java.io.IOException;
import java.net.SocketException;

import com.masopust.ondra.java.gui.ipSelectionLayout.IPSelectionLayoutController;
import com.masopust.ondra.java.tcpCommunication.RoverConnection;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * This class is the starting class of the whole application.
 * 
 * @author Ondrej Masopust
 *
 */

public class Main extends Application {

	/**
	 * The stage that is used in the whole GUI
	 */
	public static Stage mainStage;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws IOException {
		mainStage = stage;
		mainStage.setTitle("Rover Control Panel");
		mainStage.setScene(IPSelectionLayoutController.loadIPSelectionScene());
		mainStage.show();
	}

	@Override
	public void stop() throws Exception {
		RoverConnection.roverConnection.sendData("halt");
		try {
			RoverConnection.roverConnection.cancel();
			RoverConnection.roverConnection.getClientSocket().close();
		} catch (NullPointerException e) {
			System.out.println("NullPointerException thrown while terminating this program.");
		} catch (IOException e) {
			System.out.println("IOException thrown while terminating this program.");
		}
		super.stop();
	}

}
