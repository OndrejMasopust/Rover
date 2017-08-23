package com.masopust.ondra.java.gui.preloader;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
		text.textProperty().bind(PreloaderTestLaunch.wt.valueProperty());
		
		//autoscroll ScrollPane to bottom
		PreloaderTestLaunch.wt.progressProperty().addListener((observable, oldValue, newValue) -> {	
			scrollPane.setVvalue(1);
		});
	}

}
