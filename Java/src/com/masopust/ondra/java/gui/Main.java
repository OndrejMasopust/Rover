package com.masopust.ondra.java.gui;

import java.io.IOException;

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
		RoverConnection.roverConnection.sendData("stop");
		while (RoverConnection.roverConnection.getWaitForAck())
			;
		try {
			RoverConnection.roverConnection.cancel();
			RoverConnection.roverConnection.getClientSocket().close(); // TODO check if only this is sufficient
		} catch (NullPointerException e) {
			// TODO
			System.out.println("NullPointerException thrown while terminating this program:");
			System.out.println(e.getMessage());
		} catch (IOException e) {
			// TODO
			System.out.println("IOException thrown while terminating this program:");
			System.out.println(e.getMessage());
		}
		super.stop();
	}

}
