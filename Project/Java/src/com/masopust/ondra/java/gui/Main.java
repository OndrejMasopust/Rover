package com.masopust.ondra.java.gui;

import java.io.IOException;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

public class Main extends Application {

	public static int numberOfLines;
	public static ArrayList<Line> lines;
	public static ArrayList<Rectangle> endDots;
	private RoverConnection roverConnection;
	private Stage mainStage;
	private Parent mainStageLayout;
	private FXMLLoader mainStageLoader;

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
		Thread connectionThread = new Thread(() -> {
			int i = 0;
			while (true) {
				// try to establish connection
				roverConnection.connect();

				if (roverConnection.connectionEstablished() || i == 5) {
					break;
				} else {
					// wait 300ms
					try {
						Thread.sleep(300);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					System.out.println("Slept for 300ms");
					i++;
				}
			}

			// change the scene
			Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();

			mainStageLoader = new FXMLLoader();
			mainStageLoader.setLocation(getClass().getResource("mainLayout/MainLayout.fxml"));

			
			Platform.runLater(() -> {
				try {
					mainStageLayout = mainStageLoader.load();
					Scene mainScene = new Scene(mainStageLayout);
					mainStage.setScene(mainScene);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				// set dimensions of the main window
				mainStage.setX(screenBounds.getMinX());
				mainStage.setY(screenBounds.getMinY());
				mainStage.setWidth(screenBounds.getWidth());
				mainStage.setHeight(screenBounds.getHeight());
			});
		}, "connectionThread");
		connectionThread.setDaemon(true);
		connectionThread.start();
	}
}
