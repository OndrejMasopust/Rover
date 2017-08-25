package com.masopust.ondra.java.gui;

import java.io.IOException;

import com.masopust.ondra.java.gui.ipSelectionLayout.IPSelectionLayoutController;

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
	}

	@Override
	public void start(Stage stage) throws IOException {
		mainStage = stage;
		mainStage.setTitle("Rover Control Panel");
		mainStage.setScene(IPSelectionLayoutController.loadIPSelectionScene());
		mainStage.show();

		// TODO close rover connection socket on closing the window
	}

	/**
	 * This method sets the mainStage to be full screen wide and high.
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
