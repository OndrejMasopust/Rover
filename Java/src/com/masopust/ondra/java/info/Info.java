package com.masopust.ondra.java.info;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

/**
 * The {@code Info} class wraps one static method that is used in the Info
 * layout.
 * 
 * @author Ondrej Masopust
 *
 */
public class Info {
	// TODO edit getInfo()
	// TODO delete not used elements
	/**
	 * @deprecated
	 */
	private static File info = new File(Info.class.getResource("/com/masopust/ondra/java/info/Info.txt").getPath()); // "/Users/Ondra/Documents/Programming/Maturita/Project/Java/bin/com/masopust/ondra/java/info/Info.txt"
	/**
	 * @deprecated
	 */
	private static StringBuilder sb = new StringBuilder();
	/**
	 * @deprecated
	 */
	private static List<String> lines;
	private static ArrayList<Text> texts = new ArrayList<>();

	/**
	 * The {@code readInfo} method reads content of the <b>Info.txt</b> file.
	 * 
	 * @return {@link String} containing the content of the <b>Info.txt</b> file
	 * @deprecated
	 */
	public static String readInfo() {
		if (sb.toString().equals("")) {
			if (info.exists()) {
				try {
					lines = Files.readAllLines(info.toPath());
					for (String line : lines) {
						sb.append(line);
						// sb.append("\n");
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

	/**
	 * 
	 * @return {@link ArrayList} instance containing the {@link Text} instances that
	 *         are to be added to the {@link TextFlow} in the info layout.
	 */
	public static ArrayList<Text> getInfo() {
		texts.add(new Text("Info\n"));
		texts.get(0).getStyleClass().addAll("infoText", "H1");
		texts.add(new Text("Second Line"));
		texts.get(1).getStyleClass().add("infoText");
		return texts;
	}
}
