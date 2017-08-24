package com.masopust.ondra.java.gui.ipSelectionLayout;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.masopust.ondra.java.gui.preloader.PreloaderController;
import com.masopust.ondra.java.tcpCommunication.RoverConnection;
import com.masopust.ondra.java.tcpCommunication.ipHostPrompt.IPHostPrompt;

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
	
	public static Scene ipSelectionScene;
	public static IPSelectionLayoutController ipSelectionLayoutConstroller;
	
	private static FXMLLoader ipSelectionLayoutLoader;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		ipSelectionLayoutConstroller = ipSelectionLayoutLoader.getController();
		IPHostPrompt.setIPHostText();
	}
	
	public void handleCancel() {
		// TODO
	}
	
	public void handleConnect() throws IOException {
		RoverConnection.startRoverConnectionThread();
		PreloaderController.loadPreloaderScene();
	}
	
	public static Scene loadIPSelectionScene() throws IOException {
		ipSelectionLayoutLoader = new FXMLLoader();
		ipSelectionLayoutLoader.setLocation(IPSelectionLayoutController.class.getResource("/com/masopust/ondra/java/gui/ipSelectionLayout/IPSelectionLayout.fxml"));
		
		Parent ipSelectionLayout = ipSelectionLayoutLoader.load();
		
		ipSelectionScene = new Scene(ipSelectionLayout);
		
		return ipSelectionScene;
	}
	
	public TextField getIPField() {
		return ipField;
	}
	
	public TextField getPortField() {
		return portField;
	}

}
