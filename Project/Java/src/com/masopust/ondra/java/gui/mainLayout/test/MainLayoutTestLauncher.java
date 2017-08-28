package com.masopust.ondra.java.gui.mainLayout.test;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * The {@code MainLayoutTestLauncher} class is only for testing purposes of the
 * main layout.
 * 
 * @author Ondrej Masopust
 *
 */
public class MainLayoutTestLauncher extends Application{

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		FXMLLoader mainLoader = new FXMLLoader();
		mainLoader.setLocation(MainLayoutTestLauncher.class.getResource("/com/masopust/ondra/java/gui/mainLayout/MainLayout.fxml"));
		
		Parent mainLayout = mainLoader.load();
		
		Scene mainLayoutScene = new Scene(mainLayout);
		mainLayoutScene.getStylesheets().add("/com/masopust/ondra/java/gui/mainLayout/MainLayoutStyle.css");
		/*
		Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();

		primaryStage.setX(screenBounds.getMinX());
		primaryStage.setY(screenBounds.getMinY());
		primaryStage.setWidth(screenBounds.getWidth());
		primaryStage.setHeight(screenBounds.getHeight());
		 */
		
		primaryStage.setScene(mainLayoutScene);
		primaryStage.setTitle("Main Layout Testing");
		primaryStage.show();
	}

}
