package com.masopust.ondra.java.gui;

import java.io.IOException;

import com.masopust.ondra.java.gui.ipSelectionLayout.IPSelectionLayoutController;
import com.masopust.ondra.java.tcpCommunication.RoverConnection;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * This class is the starting class of the whole application.
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

public class Main extends Application {

	/**
	 * The stage that is used in the whole GUI
	 */
	public static Stage mainStage;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws IOException {
		mainStage = stage;
		mainStage.setTitle("Rover Control Panel");
		mainStage.setScene(IPSelectionLayoutController.loadIPSelectionScene());
		mainStage.show();
	}

	@Override
	public void stop() throws Exception {
		RoverConnection.roverConnection.sendData("halt");
		try {
			RoverConnection.roverConnection.cancel();
			RoverConnection.roverConnection.getClientSocket().close();
		} catch (NullPointerException e) {
			System.out.println("NullPointerException thrown while terminating this program.");
		} catch (IOException e) {
			System.out.println("IOException thrown while terminating this program.");
		}
		super.stop();
	}

}
