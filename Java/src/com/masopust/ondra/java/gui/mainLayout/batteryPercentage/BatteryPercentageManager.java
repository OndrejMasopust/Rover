package com.masopust.ondra.java.gui.mainLayout.batteryPercentage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;

/**
 * The {@code BatteryPercentageManager} class holds the logic that is needed to
 * store the battery percentage value even after the program is shutted down.
 * This class works with the <b>BatteryPercentage.txt</b> file.
 * 
 * @author Ondrej Masopust
 *
 */
public class BatteryPercentageManager {
	private File battPerFile;
	/**
	 * This list holds only one item but is needed because the {@code Files.write}
	 * method needs some {@link Iterable} child.
	 */
	private ArrayList<String> line;

	/**
	 * This is the constructor of this class. It creates the
	 * <b>BatteryPercentage.txt</b> file and writes {@code "0"} to it if it does not
	 * exists.
	 */
	public BatteryPercentageManager() {
		battPerFile = new File("com/masopust/ondra/java/gui/mainLayout/batteryPercentage/BatteryPercentage.txt");
		line = new ArrayList<>();
		line.add("0");
		if (!battPerFile.exists()) {
			write(0);
		}
	}

	/**
	 * This methods reads all the lines from the <b>BatteryPercentage.txt</b> file
	 * and returns the first line as an int.
	 * 
	 * @return int - the number that is on the first line of the
	 *         <b>BatteryPercentage.txt</b> file
	 */
	public int read() {
		try {
			line = (ArrayList<String>) Files.readAllLines(battPerFile.toPath());
		} catch (IOException e) {
			System.out.println("IOException was thrown while reading the BatteryPercentage.txt file:");
			e.printStackTrace();
		}
		return Integer.valueOf(line.get(0));
	}

	/**
	 * This method writes the given value into the <b>BatteryPercentage.txt</b>
	 * file.
	 * 
	 * @param value
	 *            to be written to the <b>BatteryPercentage.txt</b> file
	 */
	public void write(int value) {
		line.set(0, Integer.toString(value));
		try {
			Files.write(battPerFile.toPath(), line, StandardOpenOption.WRITE, StandardOpenOption.CREATE);
		} catch (IOException e) {
			System.out.println("IOException was thrown while writing to the BatteryPercentage.txt file:");
			e.printStackTrace();
		}
	}

}
