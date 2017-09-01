package com.masopust.ondra.java.gui.preloader;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.masopust.ondra.java.gui.Main;
import com.masopust.ondra.java.tcpCommunication.RoverConnection;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.text.Text;

/**
 * This class holds the logic behind the preloader scene.
 * 
 * @author Ondrej Masopust
 *
 */

public class PreloaderController implements Initializable {

	@FXML
	Button closeButton;

	@FXML
	Text text;

	@FXML
	ScrollPane scrollPane;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		while (RoverConnection.roverConnection == null)
			;
		text.textProperty().bind(RoverConnection.roverConnection.valueProperty());

		// autoscroll ScrollPane to bottom
		RoverConnection.roverConnection.progressProperty().addListener((observable, oldValue, newValue) -> {
			scrollPane.setVvalue(1);
		});
		
		/*
		 * Only for testing purposes:
		 * text.textProperty().bind(PreloaderTestLaunch.wt.valueProperty());
		 */
	}
	
	/**
	 * The {@code handleCancel} method wraps the code to be executed after the
	 * <b>Cancel</b> button is pressed.
	 */
	public void handleCancel() {
		Main.mainStage.close();
	}

	/**
	 * This method loads the preloader layout from Preloader.fxml and sets the scene
	 * in the main stage.
	 * 
	 * @throws IOException
	 * 
	 * @return void
	 */

	public static void loadPreloaderScene() throws IOException {
		FXMLLoader preloader = new FXMLLoader();
		preloader.setLocation(
				PreloaderController.class.getResource("/com/masopust/ondra/java/gui/preloader/Preloader.fxml"));

		Parent preloaderLayout = preloader.load();

		Scene preloaderScene = new Scene(preloaderLayout);
		preloaderScene.getStylesheets().add("/com/masopust/ondra/java/gui/preloader/PreloaderStyle.css");

		Main.mainStage.setScene(preloaderScene);
		Main.mainStage.centerOnScreen();
	}

}
