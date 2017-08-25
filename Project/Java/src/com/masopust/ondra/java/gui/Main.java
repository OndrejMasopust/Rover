package com.masopust.ondra.java.gui;

import java.io.IOException;

import com.masopust.ondra.java.gui.ipSelectionLayout.IPSelectionLayoutController;
import com.masopust.ondra.java.tcpCommunication.RoverConnection;

import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;
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
		// TODO adjust build.xml not to include test classes and to work properly
		// TODO consider making preloader as described in docs.oracle so that the app launches more smoothly
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
		try {
			RoverConnection.roverConnection.getClientSocket().close(); // TODO check if only this is sufficient
		} catch (NullPointerException e) {
			// TODO
			System.out.println("NullPointerException thrown.");
		} catch (IOException e) {
			// TODO
			System.out.println("IOException thrown.");
		}
		super.stop();
	}

	/**
	 * This method sets the mainStage to be full screen wide and high.
	 * TODO delete this method if really not used
	 * 
	 * @deprecated
	 * 
	 * @return void
	 */
	public static void setFullScreen() {
		Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();

		mainStage.setX(screenBounds.getMinX());
		mainStage.setY(screenBounds.getMinY());
		mainStage.setWidth(screenBounds.getWidth());
		mainStage.setHeight(screenBounds.getHeight());
	}
}
