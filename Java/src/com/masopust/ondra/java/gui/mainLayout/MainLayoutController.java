package com.masopust.ondra.java.gui.mainLayout;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import com.masopust.ondra.java.gui.Main;
import com.masopust.ondra.java.gui.mainLayout.batteryPercentage.BatteryPercentageManager;
import com.masopust.ondra.java.info.Info;
import com.masopust.ondra.java.tcpCommunication.RoverConnection;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
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
import javafx.scene.text.TextFlow;

/**
 * This class holds the logic behind the main scene.
 * 
 * @author Ondrej Masopust
 *
 */

public class MainLayoutController implements Initializable {

	// TODO check if these variables are used and if not, delete them
	@FXML
	StackPane stackPane;

	@FXML
	BorderPane controlPane;

	@FXML
	ScrollPane infoSPane;

	@FXML
	TextFlow infoTF;

	@FXML
	Pane centerSectionPane;

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

	private static StringBuilder consoleTextSB = new StringBuilder();
	private static Label previousConsoleText;
	private static ArrayList<HBox> consoleMessageList = new ArrayList<>();
	private static int consoleMessageIndex = -1;
	private static VBox consoleOutputTextBox;
	private static Group centerSectionGroup;
	private static Text percentage;
	private double zoomScale = 0.2;

	/**
	 * This boolean tells if the previous record in the console output was from the
	 * Rover.
	 */
	private static Boolean previousMessageFromRover = null;

	/**
	 * This variable defines number of lines that are generated in the center
	 * section. It defines the resolution of the visualization.
	 */
	private static int numberOfLines = 50; // FIXME initialize after Rover finishes it's initialization

	/**
	 * This {@code double} holds the angle between two neighbor lines in the center
	 * section.
	 */
	private static double baseAngle;

	/**
	 * This list holds all of the lines starting in the middle that are generated in
	 * the center section.
	 */
	private static ArrayList<Line> lines = new ArrayList<>(numberOfLines);

	/**
	 * This list holds all of the dots that are at the end of each of the lines.
	 */
	private static ArrayList<Rectangle> dots = new ArrayList<>(numberOfLines);

	/**
	 * This float holds the size of the edge of the {@link Rectangle} end dots.
	 */
	private static float dotSize = 3;

	/**
	 * This map connects a {@link Rectangle} from {@code dots} {@link List} and a
	 * {@link Line} that is between the given dot and the previous dot if visible.
	 */
	private static Map<Rectangle, Line> dotLines = new HashMap<>(numberOfLines);

	/**
	 * This variable is instance of the {@link BatteryPercentageManager} class and
	 * is used to write and read data from the <b>BatteryPercentage.txt</b> file.
	 */
	private static BatteryPercentageManager percentageFile;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO put credit to the author of the send arrow icon (text in css file)
		baseAngle = (double) 360 / numberOfLines;

		consoleOutputTextBox = consoleOutputTextBoxFXML;
		centerSectionGroup = centerSectionGroupFXML;
		percentage = percentageFXML;
		buildCenterSection(); // FIXME start with empty center section only with message that "Rover is
								// initializing sensors" and start to build it after the Rover sends the first
								// set of data to be visualized

		// This code automatically scrolls down the console when new message is added
		consoleOutputTextBox.heightProperty().addListener((observable, oldValue, newValue) -> {
			consoleOutputSP.setVvalue(1);
		});

		stackPane.addEventFilter(KeyEvent.KEY_RELEASED, (keyEvent) -> {
			switch (keyEvent.getCode()) {
			case ESCAPE:
				if (stackPane.getChildren().get(1).equals(infoSPane))
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
					RoverConnection.roverConnection.sendData("tr1500");
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
				RoverConnection.roverConnection.sendData("mr180"); // FIXME adjust the number
				keyEvent.consume();
				break;
			case DOWN:
				RoverConnection.roverConnection.sendData("mr20"); // FIXME adjust the number
				keyEvent.consume();
				break;
			case LEFT:
				if (!consoleInput.isFocused()) {
					RoverConnection.roverConnection.sendData("tr1400"); // FIXME adjust the number
					keyEvent.consume();
				}
				break;
			case RIGHT:
				if (!consoleInput.isFocused()) {
					RoverConnection.roverConnection.sendData("tr1600"); // FIXME adjust the number
					keyEvent.consume();
				}
				break;
			default:
				break;
			}
		});

		initPercentage();
	}

	/**
	 * The {@code initPercentage} method is used to load data from the file that
	 * stores the percentage and sets it in the application.
	 */
	private void initPercentage() {
		percentageFile = new BatteryPercentageManager();
		setPercentage(percentageFile.read());
	}

	/**
	 * The {@code handleSubmit} method wraps code that is executed if the submit
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
			} else {
				// FIXME uncomment:
				RoverConnection.roverConnection.sendData(text);
			}

			addMessageToConsole(false, text);
		}

		consoleInput.setText("");
		consoleSubmit.requestFocus();
	}

	/**
	 * The {@code localCommands} class wraps static {@link String} constants that
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
	 * The {@code receiveMessage} method writes the given message to the console
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
		case RoverCommands.DATA:
			updatePoint(Integer.parseInt(input.substring(2, 4)), Integer.parseInt(input.substring(4)));
			break;
		case RoverCommands.READY:
			addMessageToConsole(true, "Sensors initialized. Rover is ready.");
			break;
		case RoverCommands.RESOLUTION:
			setBaseAngle(Integer.parseInt(input.substring(2)));
			break;
		case RoverCommands.ERROR:
			addMessageToConsole(true, input.substring(2));
			break;
		case RoverCommands.BATTERY:
			setPercentage(Integer.valueOf(input.substring(2)));
			break;
		default:
			addMessageToConsole(true, input);
			break;
		}
	}

	/**
	 * The {@code RoverCommands} class wraps static {@link String} constants that
	 * contain the command representation as it is send from the Rover
	 * 
	 * @author Ondrej Masopust
	 *
	 */
	private class RoverCommands {
		private static final String DATA = "dt";
		private static final String READY = "rd";
		private static final String ERROR = "er";
		private static final String BATTERY = "bt";
		private static final String RESOLUTION = "ro";
	}

	/**
	 * The {@code addMessageToConsole} method adds the message given in the
	 * {@code input} parameter to the console output aligned to the right or left
	 * depending on the {@code fromPi} {@code boolean}.
	 * 
	 * @param fromRover
	 *            {@code boolean} value that tells if the current message is from
	 *            the Rover
	 * @param input
	 *            {@link String} containing the message
	 */
	private static void addMessageToConsole(boolean fromRover, String input) {
		try {
			if (previousMessageFromRover.booleanValue() ^ fromRover) {
				consoleTextSB = new StringBuilder();

				consoleMessageList.add(new HBox());
				consoleMessageIndex++;
				previousConsoleText = new Label();
				HBox message = consoleMessageList.get(consoleMessageIndex);
				previousConsoleText.setTextAlignment(TextAlignment.LEFT);
				previousConsoleText.setWrapText(true);
				previousConsoleText.setMinWidth(consoleOutputTextBox.getWidth() / 2);
				previousConsoleText.setMaxWidth(consoleOutputTextBox.getWidth() * 2 / 3);
				previousConsoleText.getStyleClass().add("textList");
				message.getChildren().add(previousConsoleText);
				if (fromRover) {
					message.setAlignment(Pos.CENTER_LEFT);
					previousConsoleText.getStyleClass().add("textListRover");
				} else {
					message.setAlignment(Pos.CENTER_RIGHT);
					previousConsoleText.getStyleClass().add("textListYou");
				}
				message.setPrefWidth(previousConsoleText.getWidth() * 2);
				message.getStyleClass().add("messageList");
			}

			if (!input.equals("")) {
				consoleTextSB.append(input);
				consoleTextSB.append("\n");
				previousConsoleText.setText(consoleTextSB.toString());
			}

			if (previousMessageFromRover.booleanValue() ^ fromRover)
				consoleOutputTextBox.getChildren().add(consoleMessageList.get(consoleMessageIndex));

			previousMessageFromRover = fromRover;

		} catch (NullPointerException e) {
			// this exception is thrown for the first time, when there is no message in the
			// console and previousMessageFromRover is thus null
			previousMessageFromRover = !fromRover;
			addMessageToConsole(fromRover, input);
		}
	}

	/**
	 * The {@code handleZoomIn} method wraps code that is executed when the zoom in
	 * button is pressed. The visualization in the center section is zoomed in to.
	 */
	public void handleZoomIn() {
		centerSectionGroup.setScaleX(centerSectionGroup.getScaleX() + zoomScale);
		centerSectionGroup.setScaleY(centerSectionGroup.getScaleY() + zoomScale);
	}

	/**
	 * The {@code handleZoomIn} method wraps code that is executed when the zoom out
	 * button is pressed. The visualization in the center section is zoomed out of.
	 */
	public void handleZoomOut() {
		if ((centerSectionGroup.getScaleX() - zoomScale) > 0) {
			centerSectionGroup.setScaleX(centerSectionGroup.getScaleX() - zoomScale);
			centerSectionGroup.setScaleY(centerSectionGroup.getScaleY() - zoomScale);
		}
	}

	/**
	 * The {@code handleInfo} method wraps code that is executed when the info
	 * button is pressed. The {@link ScrollPane} with the info is brought to the
	 * front.
	 */
	public void handleInfo() {
		if (infoTF.getChildren().size() == 0) {
			infoTF.setTextAlignment(TextAlignment.LEFT);
			infoTF.setPadding(new Insets(8, 10, 8, 10));
			infoTF.setLineSpacing(5.0);
			infoTF.getChildren().addAll(Info.getInfo());
		}
		infoSPane.toFront();
	}

	/**
	 * This method loads the main scene from MainLayout.fxml and sets the scene in
	 * the main stage.
	 * 
	 * @throws IOException
	 * 
	 * @return void
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
	 * The {@code updatePoint} method moves the point in the {@code dots} list on
	 * the given {@code index} to the given {@code distance} or sets it invisible if
	 * the distance can not be measured.
	 * 
	 * @param index
	 *            of the point in the {@code dots} list
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
				centerSectionGroup.getChildren().add(dotLines.get(dot));
			}
		}
	}

	/**
	 * This method builds the lines and dots in the center section.
	 * 
	 * @return void
	 */
	private void buildCenterSection() {
		Image tank = new Image("/com/masopust/ondra/java/gui/mainLayout/tank.png", 100, 200, true, false);
		ImageView tankIV = new ImageView(tank);
		tankIV.xProperty().bind(centerSectionPane.widthProperty().divide(2).subtract(tank.getWidth() / 2));
		tankIV.yProperty().bind(centerSectionPane.heightProperty().divide(2).subtract(tank.getHeight() / 2));

		centerSectionGroup.getChildren().add(tankIV);

		int lineLength = 200; // FIXME

		for (int i = 0; i < numberOfLines; i++) {
			lines.add(new Line());
			Line line = lines.get(i);

			updateLineEndPoint(lineLength, i);

			// set start and end points to be relative to the pane dimensions
			line.startXProperty().bind(centerSectionPane.widthProperty().divide(2));
			line.startYProperty().bind(centerSectionPane.heightProperty().divide(2));

			line.setVisible(false);
			centerSectionGroup.getChildren().add(line);

			dots.add(new Rectangle(dotSize, dotSize, Color.web("#0092CC")));
			Rectangle dot = dots.get(i);

			dot.xProperty().bind(line.endXProperty().subtract(dotSize / 2));
			dot.yProperty().bind(line.endYProperty().subtract(dotSize / 2));
			centerSectionGroup.getChildren().add(dot);

			/*
			 * System.out.printf("Angle: %f\n", angleInDeg);
			 * System.out.printf("sin(angle): %f\n", Math.sin(angleInDeg));
			 * System.out.printf("cos(angle): %f\n", Math.cos(angleInDeg));
			 * System.out.printf("xSide: %f\n", xSide); System.out.printf("ySide: %f\n",
			 * ySide); System.out.println();
			 */
		}
	}

	/**
	 * The {@code updateLineEndPoint} method sets binding of the {@link Line} in the
	 * {@code lines} {@link List} at the given index to match the given length.
	 * 
	 * @param lineLength
	 *            in cm
	 * @param index
	 *            of the {@link Line} in the {@code lines} {@link List}
	 */
	private static void updateLineEndPoint(int lineLength, int index) {
		double xSide = lineLength * Math.sin(Math.toRadians(baseAngle * index));
		// the minus is there in so that the dot with index 0 is at the top and not at the bottom 
		double ySide = - lineLength * Math.cos(Math.toRadians(baseAngle * index));
		lines.get(index).endXProperty().bind(lines.get(index).startXProperty().add(xSide));
		lines.get(index).endYProperty().bind(lines.get(index).startYProperty().add(ySide));
	}

	/**
	 * The {@code setNumberOfLines} method sets the value of the
	 * {@code numberOfLines} integer.
	 *
	 * @param value
	 *            to be assigned to the numberOfLines integer
	 */
	@SuppressWarnings("unused")
	private static void setNumberOfLines(int value) {
		// TODO delete this method?
		numberOfLines = value;
	}
	
	/**
	 * This method sets the angle between two neighbor lines
	 * 
	 * @param resolution
	 *            The total number of lines
	 */
	private static void setBaseAngle(int resolution) {
		baseAngle = (double) 360 / resolution;
	}

	/**
	 * The {@code setPercentage} method sets the text of the {@code percentage}
	 * {@link Text} variable.
	 * 
	 * @param value
	 *            to be written to the {@link Text}
	 */
	private static void setPercentage(int value) {
		percentage.setText(Integer.toString(value) + "%");
		percentageFile.write(value);
	}
}
