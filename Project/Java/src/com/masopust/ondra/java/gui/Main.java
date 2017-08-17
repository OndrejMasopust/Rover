package com.masopust.ondra.java.gui;

import java.io.IOException;
import java.util.ArrayList;

import com.masopust.ondra.java.tcpCommunication.RoverConnection;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

public class Main extends Application {

	public static int numberOfLines;
	public static ArrayList<Line> lines;
	public static ArrayList<Rectangle> endDots;
	public static RoverConnection roverConnection;
	public static Stage mainStage;
	public static Parent mainStageLayout;
	public static FXMLLoader mainStageLoader;

	public static void main(String[] args) {
		launch(args);

	}

	@Override
	public void start(Stage stage) throws IOException {
		mainStage = stage;
		mainStage.setTitle("Rover Control Panel");

		numberOfLines = 30; // FIXME

		lines = new ArrayList<>();
		endDots = new ArrayList<>();

		FXMLLoader preloader = new FXMLLoader();
		preloader.setLocation(getClass().getResource("preloader/Preloader.fxml"));

		Parent preloaderLayout = preloader.load();

		Scene preloaderScene = new Scene(preloaderLayout);

		mainStage.setScene(preloaderScene);
		mainStage.show();

		// connect to rover while in the preloader
		roverConnection = new RoverConnection();
		connectToRover();
		/*
		 * // using this object, it is possible to access objects in the FXML
		 * mainStageController provided that they have setters or getters
		 * 
		 * MainLayoutController controller = mainStageLoader.getController();
		 * 
		 * // close rover connection socket on closing the window
		 */
	}

	private void connectToRover() {
		Thread connectionThread = new Thread(roverConnection, "connectionThread");
		connectionThread.setDaemon(true);
		connectionThread.start();
	}
}
