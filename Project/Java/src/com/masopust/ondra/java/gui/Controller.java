package com.masopust.ondra.java.gui;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;

public class Controller implements Initializable {

	@FXML
	Pane centerSection;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		CenterSectionBuilder.build(centerSection, com.masopust.ondra.java.gui.Main.numberOfLines,
				com.masopust.ondra.java.gui.Main.lines, com.masopust.ondra.java.gui.Main.endDots);

	}

}
