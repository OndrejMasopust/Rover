package com.masopust.ondra.java.gui.preloader.test;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * The {@code PreloaderTestLaunch} class is only for testing purposes of the preloader.
 * 
 * @author Ondrej Maopust
 *
 */
public class PreloaderTestLaunch extends Application{

	public static PreloaderTestWorkingThread wt = new PreloaderTestWorkingThread();
	public static Stage primaryStage;
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws IOException {
		primaryStage = stage;
		FXMLLoader preloader = new FXMLLoader();
		preloader.setLocation(PreloaderTestLaunch.class.getResource("/com/masopust/ondra/java/gui/preloader/Preloader.fxml"));
		
		Parent preloaderLayout = preloader.load();
		
		Scene preloaderScene = new Scene(preloaderLayout);
		preloaderScene.getStylesheets().add("/com/masopust/ondra/java/gui/preloader/PreloaderStyle.css");
		
		primaryStage.setScene(preloaderScene);
		primaryStage.setTitle("Preloader Testing");
		primaryStage.show();
		
		Thread thread = new Thread(wt, "Working thread");
		thread.setDaemon(true);
		thread.start();
		
	}

}
