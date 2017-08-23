package com.masopust.ondra.java.gui.ipSelectionLayout;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.List;
import java.util.ResourceBundle;

import com.masopust.ondra.java.gui.Main;
import com.masopust.ondra.java.tcpCommunication.RoverConnection;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class IPSelectionLayoutController implements Initializable {
	
	@FXML
	TextField ipField;
	
	@FXML
	TextField portField;
	
	@FXML
	Button cancelBut;
	
	@FXML
	Button connectBut;
	
	public static List<String> lines;
	public static File ipHostPrompt;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		ipHostPrompt = new File("com/masopust/ondra/java/gui/ipSelectionLayout/IPHostPrompt.txt");
		if (ipHostPrompt.exists()) {
			try {
				lines = Files.readAllLines(ipHostPrompt.toPath());
				ipField.setText(lines.get(0));
				portField.setText(lines.get(1));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void handleCancel() {
		// TODO
	}
	
	public void handleConnect() throws IOException {
		Main.roverConnection = new RoverConnection(ipField.getText(), Integer.parseInt(portField.getText()));

		FXMLLoader preloader = new FXMLLoader();
		preloader.setLocation(IPSelectionLayoutController.class.getResource("/com/masopust/ondra/java/gui/preloader/Preloader.fxml")); // FIXME link

		Parent preloaderLayout = preloader.load();

		Scene preloaderScene = new Scene(preloaderLayout);
		preloaderScene.getStylesheets().add("/com/masopust/ondra/java/gui/preloader/PreloaderStyle.css"); // FIXME link
		
		Main.mainStage.setScene(preloaderScene);
		Main.mainStage.centerOnScreen();
		
		Thread connectionThread = new Thread(Main.roverConnection, "connectionThread");
		connectionThread.setDaemon(true);
		connectionThread.start();
	}

}
