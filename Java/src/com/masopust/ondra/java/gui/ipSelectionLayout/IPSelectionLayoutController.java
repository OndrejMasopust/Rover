package com.masopust.ondra.java.gui.ipSelectionLayout;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.masopust.ondra.java.gui.Main;
import com.masopust.ondra.java.gui.preloader.PreloaderController;
import com.masopust.ondra.java.tcpCommunication.RoverConnection;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

/**
 * The  <i>IPSelectionLayoutController</i> class wraps the logic behind the
 * IPSelection layout.
 * 
 * @author Ondrej Masopust
 *
 */
public class IPSelectionLayoutController implements Initializable {

	@FXML
	TextField ipField;

	@FXML
	TextField portField;

	@FXML
	Button cancelBut;

	@FXML
	Button connectBut;

	/**
	 * Instance of the {@link Scene} class that is used for this layout.
	 */
	public static Scene ipSelectionScene;

	/**
	 * Instance of the {@link IPSelectionLayoutController} class. This variable is
	 * used to access non-static methods in this class.
	 */
	public static IPSelectionLayoutController ipSelectionLayoutConstroller;

	private static FXMLLoader ipSelectionLayoutLoader;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		ipSelectionLayoutConstroller = ipSelectionLayoutLoader.getController();
	}

	/**
	 * The <i>handleCancel</i> method wraps the code to be executed after the
	 * <i>Cancel</i> button is pressed.
	 */
	public void handleCancel() {
		Main.mainStage.close();
	}

	/**
	 * The <i>handleConnect</i> method wraps the code to be executed after the
	 * <i>Connect</i> button is pressed.
	 */
	public void handleConnect() throws IOException {
		RoverConnection.startRoverConnectionThread();
		PreloaderController.loadPreloaderScene();
	}

	/**
	 * The <i>loadIPSelectionScene</i> method loads the IPSelection layout from the
	 * <i>IPSelectionLayout.fxml</i> and creates new scene with this layout.
	 * 
	 * @return instance of {@link Scene} with the IPSelection layout
	 * @throws IOException
	 *             if there is a problem loading the <i>IPSelectionLayout.fxml</i>
	 */
	public static Scene loadIPSelectionScene() throws IOException {
		ipSelectionLayoutLoader = new FXMLLoader();
		ipSelectionLayoutLoader.setLocation(IPSelectionLayoutController.class
				.getResource("/com/masopust/ondra/java/gui/ipSelectionLayout/IPSelectionLayout.fxml"));

		Parent ipSelectionLayout = ipSelectionLayoutLoader.load();

		ipSelectionScene = new Scene(ipSelectionLayout);
		ipSelectionScene.getStylesheets().add("/com/masopust/ondra/java/gui/ipSelectionLayout/IPSelectionLayoutStyle.css");

		return ipSelectionScene;
	}

	/**
	 * @return ipField instance of {@link TextField}
	 */
	public TextField getIPField() {
		return ipField;
	}

	/**
	 * @return portField instance of {@link TextField}
	 */
	public TextField getPortField() {
		return portField;
	}

}
