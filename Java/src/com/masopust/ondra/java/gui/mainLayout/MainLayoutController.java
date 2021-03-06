package com.masopust.ondra.java.gui.mainLayout;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import com.masopust.ondra.java.gui.Main;
import com.masopust.ondra.java.tcpCommunication.RoverConnection;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.web.WebView;

/**
 * This class holds the logic behind the main scene.
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

public class MainLayoutController implements Initializable {
	@FXML
	StackPane stackPane;

	@FXML
	BorderPane controlPane;
	
	@FXML
	WebView webView;

	@FXML
	Pane centerSectionPaneFXML;

	@FXML
	Group centerSectionGroupFXML;

	@FXML
	VBox leftSection;

	@FXML
	ScrollPane consoleOutputSP;

	@FXML
	VBox consoleOutputTextBoxFXML;

	@FXML
	HBox consoleInputBox;

	@FXML
	TextField consoleInput;

	@FXML
	Button consoleSubmit;

	@FXML
	BorderPane rightSection;

	@FXML
	VBox zoomBox;

	@FXML
	Button zoomIn;

	@FXML
	Button zommOut;

	@FXML
	Text percentageFXML;

	@FXML
	Button info;

	private static ArrayList<HBox> consoleMessageList;
	private static int consoleMessageIndex;
	private static VBox consoleOutputTextBox;
	private static Group centerSectionGroup;
	private static Pane centerSectionPane;
	private static Text percentage;
	private double zoomScale;

	/**
	 * This variable defines number of lines that are generated in the center
	 * section. It defines the resolution of the visualization.
	 */
	private static int numberOfLines;

	/**
	 * This double holds the angle between two neighbor lines in the center
	 * section.
	 */
	private static double baseAngle;
	
	/**
	 * This int contains the index of the dot that is to be refreshed on the screen.
	 */
	private static int dotCounter;

	/**
	 * This list holds all of the lines starting in the middle that are generated in
	 * the center section.
	 */
	private static ArrayList<Line> lines;

	/**
	 * This list holds all of the dots that are at the end of each of the lines.
	 */
	private static ArrayList<Rectangle> dots;

	/**
	 * This float holds the size of the edge of the {@link Rectangle} end dots.
	 */
	private static float dotSize;

	/**
	 * This map connects a {@link Rectangle} from the <i>dots</i> {@link List} and a
	 * {@link Line} that is between the given dot and the previous dot if visible.
	 */
	private static Map<Rectangle, Line> dotLines;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		consoleOutputTextBox = consoleOutputTextBoxFXML;
		centerSectionGroup = centerSectionGroupFXML;
		percentage = percentageFXML;
		centerSectionPane = centerSectionPaneFXML;
		
		consoleMessageList = new ArrayList<>();
		consoleMessageIndex = -1;
		dotCounter = 0;
		zoomScale = 0.2;
		dotSize = 3;
		
		// place the tank image
		Image tank = new Image("/com/masopust/ondra/java/gui/mainLayout/tank.png", 40, 60, true, false);
		ImageView tankIV = new ImageView(tank);
		tankIV.xProperty().bind(centerSectionPane.widthProperty().divide(2).subtract(tank.getWidth() / 2));
		tankIV.yProperty().bind(centerSectionPane.heightProperty().divide(2).subtract(tank.getHeight() / 2));
		centerSectionGroup.getChildren().add(tankIV);

		// This code automatically scrolls down the console when new message is added
		consoleOutputTextBox.heightProperty().addListener((observable, oldValue, newValue) -> {
			consoleOutputSP.setVvalue(1);
		});

		stackPane.addEventFilter(KeyEvent.KEY_RELEASED, (keyEvent) -> {
			switch (keyEvent.getCode()) {
			case ESCAPE:
				if (stackPane.getChildren().get(1).equals(webView))
					controlPane.toFront();
				break;
			case UP:
			case DOWN:
				// send motor stop command
				RoverConnection.roverConnection.sendData("ms");
				keyEvent.consume();
				break;
			case LEFT:
			case RIGHT:
				if (!consoleInput.isFocused()) {
					// send turn command
					RoverConnection.roverConnection.sendData("tr1430");
					keyEvent.consume();
				}
				break;
			default:
				break;
			}
		});

		stackPane.addEventFilter(KeyEvent.KEY_PRESSED, (keyEvent) -> {
			switch (keyEvent.getCode()) {
			case UP:
				RoverConnection.roverConnection.sendData("mr200");
				keyEvent.consume();
				break;
			case DOWN:
				RoverConnection.roverConnection.sendData("mr0");
				keyEvent.consume();
				break;
			case LEFT:
				if (!consoleInput.isFocused()) {
					RoverConnection.roverConnection.sendData("tr1620");
					keyEvent.consume();
				}
				break;
			case RIGHT:
				if (!consoleInput.isFocused()) {
					RoverConnection.roverConnection.sendData("tr1200");
					keyEvent.consume();
				}
				break;
			default:
				break;
			}
		});

		webView.getEngine().load(getClass().getResource("Info.html").toExternalForm());
	}

	/**
	 * The <i>handleSubmit</i> method wraps code that is executed if the submit
	 * button is pressed or enter is hit while in the console text input. The
	 * message in the input text field (if not empty String) is sent to the rover
	 * and written to the console output.
	 */
	public void handleSubmit() {
		String text = consoleInput.getText();
		if (!text.equals("")) {
			if (text.substring(0, 1).equals("$")) {
				switch (text) {
				case localCommands.RESETPERCENTAGE:
					setPercentage(100);
					break;
				}
			} else
				RoverConnection.roverConnection.sendData(text);
			boolean command = (text.equals(RoverOutcomeCommands.STARTMEASURING)
					|| text.equals(RoverOutcomeCommands.STOPMEASURING) || text.equals(RoverOutcomeCommands.CHECK));
			addMessageToConsole(false, false, command, text);
			consoleInput.setText("");
		}

		consoleSubmit.requestFocus();
	}

	/**
	 * The <i>localCommands</i> class wraps static {@link String} constants that
	 * contain the command representation that are used to control the Rover control
	 * desktop program
	 * 
	 * @author Ondrej Masopust
	 *
	 */
	private class localCommands {
		private static final String RESETPERCENTAGE = "$resPer";
	}

	/**
	 * The <i>receiveMessage</i> method writes the given message to the console
	 * output in the main layout aligned to the left side.
	 * 
	 * @param input
	 *            {@link String} that contains the message that is to be written to
	 *            the console output
	 */
	public static void receiveMessage(String input) {
		if (input == null)
			return;
		switch (input.substring(0, 2)) {
		case RoverIncomeCommands.DATA:
			updatePoint(dotCounter, Integer.parseInt(input.substring(2)));
			dotCounter++;
			break;
		case RoverIncomeCommands.RESETDOTCOUNTER:
			dotCounter = 0;
			break;
		case RoverIncomeCommands.READY:
			addMessageToConsole(true, false, false, "Sensors initialized. Rover is ready.");
			break;
		case RoverIncomeCommands.RESOLUTION:
			numberOfLines = Integer.parseInt(input.substring(2));
			addMessageToConsole(true, false, false, "Resolution is set to be " + numberOfLines + " dots");
			lines = new ArrayList<>(numberOfLines);
			dots = new ArrayList<>(numberOfLines);
			dotLines = new HashMap<>(numberOfLines);
			// remove all the dots and lines from the center section group
			centerSectionGroup.getChildren().remove(1, centerSectionGroup.getChildren().size());
			countBaseAngle(numberOfLines);
			buildCenterSection();
			break;
		case RoverIncomeCommands.ERROR:
			addMessageToConsole(true, true, false, input.substring(2));
			break;
		case RoverIncomeCommands.BATTERY:
			setPercentage(Integer.valueOf(input.substring(2)));
			break;
		default:
			addMessageToConsole(true, false, false, input);
			break;
		}
	}

	/**
	 * The <i>RoverCommands</i> class wraps static {@link String} constants that
	 * contain the command representation as it is send from the Rover.
	 * 
	 * @author Ondrej Masopust
	 *
	 */
	private class RoverIncomeCommands {
		private static final String DATA = "dt";
		private static final String RESETDOTCOUNTER = "rc";
		private static final String READY = "rd";
		private static final String ERROR = "er";
		private static final String BATTERY = "bt";
		private static final String RESOLUTION = "ro";
	}

	/**
	 * This class contains public static constant {@link String}s containing
	 * messages, that can be sent to the Rover through the console an will be
	 * treated as commands.
	 * 
	 * @author Ondrej Masopust
	 *
	 */
	public class RoverOutcomeCommands {
		public static final String STARTMEASURING = "startMeasure";
		public static final String STOPMEASURING = "stopMeasure";
		public static final String CHECK = "check";
	}

	/**
	 * The <i>addMessageToConsole</i> method adds the message given in the
	 * <i>input</i> parameter to the console output.
	 * 
	 * @param fromRover
	 *            <i>boolean</i> value that tells if the current message is from
	 *            the Rover and thus should be aligned left. If this parameter is
	 *            <i>false</i> then the message is aligned to the right.
	 * @param error
	 *            <i>boolean</i> value that tells if the message should be
	 *            displayed with red background
	 * 
	 * @param command
	 *            <i>boolean</i> value that tells if the text should be displayed in
	 *            yellow
	 * 
	 * @param input
	 *            {@link String} containing the message
	 */
	private static void addMessageToConsole(boolean fromRover, boolean error, boolean command, String input) {
		consoleMessageList.add(new HBox());
		consoleMessageIndex++;
		Label consoleText = new Label();
		HBox message = consoleMessageList.get(consoleMessageIndex);
		consoleText.setTextAlignment(TextAlignment.LEFT);
		consoleText.setWrapText(true);
		consoleText.setMinWidth(consoleOutputTextBox.getWidth() / 2);
		consoleText.setMaxWidth(consoleOutputTextBox.getWidth() * 2 / 3);
		consoleText.getStyleClass().add("textList");
		message.getChildren().add(consoleText);
		if (fromRover) {
			message.setAlignment(Pos.CENTER_LEFT);
			if (error)
				consoleText.getStyleClass().add("textListError");
			else
				consoleText.getStyleClass().add("textListRover");
		} else {
			message.setAlignment(Pos.CENTER_RIGHT);
			if (error)
				consoleText.getStyleClass().add("textListError");
			else
				consoleText.getStyleClass().add("textListYou");
			if (command)
				consoleText.getStyleClass().add("textListCommand");
		}
		message.setPrefWidth(consoleText.getWidth() * 2);

		if (!input.equals("")) {
			consoleText.setText(input);
			consoleOutputTextBox.getChildren().add(consoleMessageList.get(consoleMessageIndex));
		}
	}

	/**
	 * The <i>handleZoomIn</i> method wraps code that is executed when the zoom in
	 * button is pressed. The visualization in the center section is zoomed in to.
	 */
	public void handleZoomIn() {
		centerSectionGroup.setScaleX(centerSectionGroup.getScaleX() + zoomScale);
		centerSectionGroup.setScaleY(centerSectionGroup.getScaleY() + zoomScale);
	}

	/**
	 * The <i>handleZoomIn</i> method wraps code that is executed when the zoom out
	 * button is pressed. The visualization in the center section is zoomed out of.
	 */
	public void handleZoomOut() {
		if ((centerSectionGroup.getScaleX() - zoomScale) > 0) {
			centerSectionGroup.setScaleX(centerSectionGroup.getScaleX() - zoomScale);
			centerSectionGroup.setScaleY(centerSectionGroup.getScaleY() - zoomScale);
		}
	}

	/**
	 * The <i>handleInfo</i> method wraps code that is executed when the info
	 * button is pressed. The {@link WebView} with the info is brought to the
	 * front.
	 */
	public void handleInfo() {
		webView.toFront();
	}

	/**
	 * This method loads the main scene from MainLayout.fxml and sets the scene in
	 * the main stage.
	 * 
	 * @throws IOException
	 */
	public static void loadMainScene() throws IOException {
		FXMLLoader mainStageLoader = new FXMLLoader();
		mainStageLoader.setLocation(
				MainLayoutController.class.getResource("/com/masopust/ondra/java/gui/mainLayout/MainLayout.fxml"));

		Parent mainStageLayout = mainStageLoader.load();

		Scene mainScene = new Scene(mainStageLayout);
		mainScene.getStylesheets().add("/com/masopust/ondra/java/gui/mainLayout/MainLayoutStyle.css");

		Main.mainStage.setScene(mainScene);
		Main.mainStage.centerOnScreen();
	}

	/**
	 * The <i>updatePoint</i> method moves the point in the <i>dots</i> {@link List} on
	 * the given <i>index to the given <i>distance</i> or sets it invisible if
	 * the distance can not be measured.
	 * 
	 * @param index
	 *            of the point in the <i>dots</i> {@link List}
	 * @param distance
	 *            in centimeters, -1 if the sensors can't measure it
	 */
	private static void updatePoint(int index, int distance) {
		Rectangle dot = dots.get(index);
		if (distance < 0)
			dot.setVisible(false);
		else {
			updateLineEndPoint(distance, index);

			dot.setVisible(true);

			int previousIndex = index - 1;
			if (previousIndex < 0)
				previousIndex = dots.size() - 1;

			Rectangle prevDot = dots.get(previousIndex);
			if (prevDot.isVisible()) {
				dotLines.put(dot, new Line());
				Line line = dotLines.get(dot);
				line.startXProperty().bind(prevDot.xProperty().add((double) dotSize / 2));
				line.startYProperty().bind(prevDot.yProperty().add((double) dotSize / 2));
				line.endXProperty().bind(dot.xProperty().add((double) dotSize / 2));
				line.endYProperty().bind(dot.yProperty().add((double) dotSize / 2));
				line.setStroke(Color.web("#F0F0F0"));
				centerSectionGroup.getChildren().add(dotLines.get(dot));
			}
		}
	}

	/**
	 * This method initializes the lines and dots in the center section based on the
	 * <i>numberOfLines</i> variable.
	 */
	private static void buildCenterSection() {
		for (int i = 0; i < numberOfLines; i++) {
			lines.add(new Line());
			Line line = lines.get(i);

			updateLineEndPoint(200, i);

			// set start and end points to be relative to the pane dimensions
			line.startXProperty().bind(centerSectionPane.widthProperty().divide(2));
			line.startYProperty().bind(centerSectionPane.heightProperty().divide(2));

			line.setVisible(false);
			centerSectionGroup.getChildren().add(line);

			dots.add(new Rectangle(dotSize, dotSize, Color.web("#DCD427")));
			Rectangle dot = dots.get(i);

			dot.setVisible(false);
			dot.xProperty().bind(line.endXProperty().subtract(dotSize / 2));
			dot.yProperty().bind(line.endYProperty().subtract(dotSize / 2));
			centerSectionGroup.getChildren().add(dot);
		}
	}

	/**
	 * The <i>updateLineEndPoint</i> method sets binding of the {@link Line} in the
	 * <i>lines</i> {@link List} at the given index to match the given length.
	 * 
	 * @param lineLength
	 *            in cm
	 * @param index
	 *            of the {@link Line} in the <i>lines</i> {@link List}
	 */
	private static void updateLineEndPoint(int lineLength, int index) {
		double xSide = lineLength * -Math.sin(Math.toRadians(baseAngle * index));
		double ySide = lineLength * Math.cos(Math.toRadians(baseAngle * index));
		lines.get(index).endXProperty().bind(lines.get(index).startXProperty().add(xSide));
		lines.get(index).endYProperty().bind(lines.get(index).startYProperty().add(ySide));
	}

	/**
	 * This method sets the angle between two neighbor lines
	 * 
	 * @param resolution
	 *            The total number of lines
	 */
	private static void countBaseAngle(int resolution) {
		baseAngle = (double) 360 / resolution;
	}

	/**
	 * The <i>setPercentage</i> method sets the text of the <i>percentage</i>
	 * {@link Text} variable.
	 * 
	 * @param value
	 *            to be written to the {@link Text}
	 */
	private static void setPercentage(int value) {
		percentage.setText(Integer.toString(value) + "%");
	}
}
