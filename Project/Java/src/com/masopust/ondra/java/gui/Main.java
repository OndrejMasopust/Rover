package com.masopust.ondra.java.gui;

import java.util.ArrayList;

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

	public static Scene scene;
	public static int numberOfLines;
	public static Rectangle2D screenBounds;
	public static ArrayList<Line> lines;
	public static ArrayList<Rectangle> endDots;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage loadingStage) throws Exception {
		// TODO: Splash screen

		Stage mainStage = new Stage();
		mainStage.setTitle("Rover Control Panel");

		numberOfLines = 30; // FIXME

		lines = new ArrayList<>();
		endDots = new ArrayList<>();
		screenBounds = Screen.getPrimary().getVisualBounds();

		Parent root = FXMLLoader.load(getClass().getResource("windowView.fxml"));

		scene = new Scene(root, screenBounds.getWidth(), screenBounds.getHeight());

		// set dimensions of the main window
		mainStage.setX(screenBounds.getMinX());
		mainStage.setY(screenBounds.getMinY());
		mainStage.setWidth(screenBounds.getWidth());
		mainStage.setHeight(screenBounds.getHeight());

		mainStage.setScene(scene);
		mainStage.show();
	}
}
