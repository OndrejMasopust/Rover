package com.masopust.ondra.java.gui.mainLayout.test;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * The <i>MainLayoutTestLauncher</i> class is only for testing purposes of the
 * main layout.
 * 
 * @author Ondrej Masopust
 *
 */
public class MainLayoutTestLauncher extends Application{

	public static Stage mainStage;
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		mainStage = primaryStage;
		
		FXMLLoader mainLoader = new FXMLLoader();
		mainLoader.setLocation(MainLayoutTestLauncher.class.getResource("/com/masopust/ondra/java/gui/mainLayout/MainLayout.fxml"));
		
		Parent mainLayout = mainLoader.load();
		
		Scene mainLayoutScene = new Scene(mainLayout);
		mainLayoutScene.getStylesheets().add("/com/masopust/ondra/java/gui/mainLayout/MainLayoutStyle.css");
		
		mainStage.setScene(mainLayoutScene);
		mainStage.setTitle("Main Layout Testing");
		mainStage.show();
	}

}
