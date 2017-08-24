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

public class PreloaderController implements Initializable {

	@FXML
	Button closeButton; // must halt all of the threads (some of them are set as Daemon so they will
						// halt automatically)
	
	@FXML
	Text text;
	
	@FXML
	ScrollPane scrollPane;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		while (RoverConnection.roverConnection == null);
		text.textProperty().bind(RoverConnection.roverConnection.valueProperty());
		
		//autoscroll ScrollPane to bottom
		RoverConnection.roverConnection.progressProperty().addListener((observable, oldValue, newValue) -> {	
			scrollPane.setVvalue(1);
		});
	}
	
	public static void loadPreloaderScene() throws IOException {
		FXMLLoader preloader = new FXMLLoader();
		preloader.setLocation(PreloaderController.class.getResource("/com/masopust/ondra/java/gui/preloader/Preloader.fxml"));

		Parent preloaderLayout = preloader.load();

		Scene preloaderScene = new Scene(preloaderLayout);
		preloaderScene.getStylesheets().add("/com/masopust/ondra/java/gui/preloader/PreloaderStyle.css");
		
		Main.mainStage.setScene(preloaderScene);
		Main.mainStage.centerOnScreen();
	}

}
