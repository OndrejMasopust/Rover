package com.masopust.ondra.java.gui;

import java.util.ArrayList;

import com.masopust.ondra.java.gui.mainLayout.MainLayoutController;

import javafx.application.Application;
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

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage mainStage) throws Exception {
		mainStage.setTitle("Rover Control Panel");

		numberOfLines = 30; // FIXME

		lines = new ArrayList<>();
		endDots = new ArrayList<>();

		Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();

		FXMLLoader preloader = new FXMLLoader();
		preloader.setLocation(getClass().getResource("preloader/Preloader.fxml"));

		Parent preloaderLayout = preloader.load();

		Scene preloaderScene = new Scene(preloaderLayout);

		mainStage.setScene(preloaderScene);
		mainStage.show();

		// TODO: connect to rover while in the preloader

		FXMLLoader mainStageLoader = new FXMLLoader();
		mainStageLoader.setLocation(getClass().getResource("mainLayout/MainLayout.fxml"));

		Parent mainStageLayout = mainStageLoader.load();

		Scene mainScene = new Scene(mainStageLayout);

		mainStage.setScene(mainScene);

		// set dimensions of the main window
		mainStage.setX(screenBounds.getMinX());
		mainStage.setY(screenBounds.getMinY());
		mainStage.setWidth(screenBounds.getWidth());
		mainStage.setHeight(screenBounds.getHeight());

		// using this object, it is possible to access objects in the FXML
		// mainStageController
		// provided that they have setters or getters
		MainLayoutController controller = mainStageLoader.getController();

	}
}
