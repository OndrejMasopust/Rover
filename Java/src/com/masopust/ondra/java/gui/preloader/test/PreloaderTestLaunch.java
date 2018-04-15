package com.masopust.ondra.java.gui.preloader.test;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * The <i>PreloaderTestLaunch</i> class is only for testing purposes of the preloader.
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
 * @author Ondrej Maopust
 *
 */
public class PreloaderTestLaunch extends Application{

	public static PreloaderTestWorkingThread wt = new PreloaderTestWorkingThread();
	public static Stage primaryStage;
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws IOException {
		primaryStage = stage;
		FXMLLoader preloader = new FXMLLoader();
		preloader.setLocation(PreloaderTestLaunch.class.getResource("/com/masopust/ondra/java/gui/preloader/Preloader.fxml"));
		
		Parent preloaderLayout = preloader.load();
		
		Scene preloaderScene = new Scene(preloaderLayout);
		preloaderScene.getStylesheets().add("/com/masopust/ondra/java/gui/preloader/PreloaderStyle.css");
		
		primaryStage.setScene(preloaderScene);
		primaryStage.setTitle("Preloader Testing");
		primaryStage.show();
		
		Thread thread = new Thread(wt, "Working thread");
		thread.setDaemon(true);
		thread.start();
		
	}

}
