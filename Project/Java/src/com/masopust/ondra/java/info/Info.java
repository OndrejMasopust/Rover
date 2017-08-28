package com.masopust.ondra.java.info;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

/**
 * The {@code Info} class wraps one static method that reads the content of the
 * <b>Info.txt</b> file.
 * 
 * @author Ondrej Masopust
 *
 */
public class Info {
	// TODO edit Info.txt
	private static File info = new File(Info.class.getResource("/com/masopust/ondra/java/info/Info.txt").getPath()); //"/Users/Ondra/Documents/Programming/Maturita/Project/Java/bin/com/masopust/ondra/java/info/Info.txt"
	private static StringBuilder sb = new StringBuilder();
	private static List<String> lines;

	/**
	 * The {@code readInfo} method reads content of the <b>Info.txt</b> file.
	 * 
	 * @return {@link String} containing the content of the <b>Info.txt</b> file
	 */
	public static String readInfo() {
		if (sb.toString().equals("")) {
			if (info.exists()) {
				try {
					lines = Files.readAllLines(info.toPath());
					for(String line: lines) {
						sb.append(line);
						//sb.append("\n");
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				sb.append("Info not available.");
			}
		}
		return sb.toString();
	}
}
