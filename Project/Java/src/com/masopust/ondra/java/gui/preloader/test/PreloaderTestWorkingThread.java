package com.masopust.ondra.java.gui.preloader.test;

import javafx.concurrent.Task;

/**
 * Class only for testing purposes of the preloader 
 *
 */
public class PreloaderTestWorkingThread extends Task<String>{

	@Override
	protected String call() throws Exception {
		String value = "";
		for (int i = 0; i < 6; i++) {
			value += i + ". Test\n";
			this.updateValue(value);
			this.updateProgress(i, 5);
			Thread.sleep(500);
		}
		return null;
	}

}
