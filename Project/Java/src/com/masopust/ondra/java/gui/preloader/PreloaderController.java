package com.masopust.ondra.java.gui.preloader;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.text.TextFlow;

public class PreloaderController implements Initializable {

	@FXML
	TextFlow consolArea;

	@FXML
	Button closeButton; //must halt all of the threads

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}

}
