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
 * MIT License
 *
 * Copyright (c) 2018 Ondřej Masopust; Gymnázium Praha 6, Nad Alejí 1952
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
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
