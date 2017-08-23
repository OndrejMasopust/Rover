package com.masopust.ondra.java.gui.preloader;

import java.net.URL;
import java.util.ResourceBundle;

import com.masopust.ondra.java.gui.Main;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

public class PreloaderController implements Initializable {

	@FXML
	TextArea errorsTextArea; // FIXME do enlarging of the textarea somehow

	@FXML
	Button closeButton; // must halt all of the threads (some of them are set as Daemon so they will
						// halt automatically)

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		/*
		 * this is the correct code
		errorsTextArea.textProperty().bind(Main.roverConnection.valueProperty());

		// autoscroll errorsTextArea to bottom when text is added
		Main.roverConnection.valueProperty().addListener((observable, oldValue, newValue) -> {
			errorsTextArea.setScrollTop(Double.MAX_VALUE);
		});
		*/
		
		errorsTextArea.textProperty().bind(PreloaderTestLaunch.wt.valueProperty());

		// autoscroll errorsTextArea to bottom when text is added
		PreloaderTestLaunch.wt.valueProperty().addListener((observable, oldValue, newValue) -> {
			errorsTextArea.setScrollTop(Double.MAX_VALUE);
		});
		
	}

}
