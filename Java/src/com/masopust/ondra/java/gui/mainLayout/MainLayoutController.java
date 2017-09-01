package com.masopust.ondra.java.gui.mainLayout;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import com.masopust.ondra.java.gui.Main;
import com.masopust.ondra.java.gui.mainLayout.test.MainLayoutTestLauncher;
import com.masopust.ondra.java.info.Info;
import com.masopust.ondra.java.tcpCommunication.RoverConnection;

import javafx.application.Platform;
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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
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
	Group centerSectionGroupp;

	@FXML
	VBox leftSection;

	@FXML
	ScrollPane consoleOutputSP;

	@FXML
	VBox consoleOutputTextBoxx;

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
	Button info;

	private static StringBuilder sb = new StringBuilder();
	private static ArrayList<HBox> messageList = new ArrayList<>();
	private static ArrayList<Label> textList = new ArrayList<>();
	private static int messageIndex;
	private static VBox consoleOutputTextBox;
	private static Group centerSectionGroup;
	private double zoomScale = 0.2;

	/**
	 * This boolean tells if the previous record in the console output was form you.
	 */
	private static Boolean previousMessageFromRover = null;

	/**
	 * This variable defines number of lines that are generated in the center
	 * section. It defines the resolution of the visualization.
	 */
	private static int numberOfLines = 30; // FIXME initialize after Rover finishes it's initialization
	
	/**
	 * This {@code double} holds the angle between two neighbour lines in the center
	 * section.
	 */
	private static double baseAngle;
	
	/**
	 * This list holds all of the lines starting in the middle that are generated in the center section.
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

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		baseAngle = (double) 360 / numberOfLines;
				
		consoleOutputTextBox = consoleOutputTextBoxx;
		centerSectionGroup = centerSectionGroupp;
		buildCenterSection(); // FIXME start with empty center section and start to build it after the Rover sends the first set of data to be visualized

		consoleOutputTextBox.heightProperty().addListener((observable, oldValue, newValue) -> {
			consoleOutputSP.setVvalue(1);
		});

		// FIXME bind to Main.mainstage
		MainLayoutTestLauncher.mainStage.addEventHandler(KeyEvent.KEY_RELEASED, (keyEvent) -> {
			if (stackPane.getChildren().get(1).equals(infoSPane)) {
				if (keyEvent.getCode() == KeyCode.ESCAPE) {
					controlPane.toFront();
				}
			}
		});
	}

	/**
	 * The {@code handleSubmit} method wraps code that is executed if the submit
	 * button is pressed or enter is hit while in the console text input. The
	 * message in the input text field (if not empty String) is sent to the rover
	 * and written to the console output.
	 */
	public void handleSubmit() {
		// FIXME uncomment:
		// RoverConnection.roverConnection.sendData(consoleInput.getText());
		addMessageToConsole(false, consoleInput.getText());

		/*
		 * System.out.println(consoleOutputSP.getWidth());
		 * System.out.println(consoleOutputTextBox.getWidth());
		 * System.out.println(messageList.get(i).getWidth());
		 */

		consoleInput.setText("");
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
		// TODO check for data from the sensor and don't write them to the console
		if (input == null)
			return;
		switch (input.substring(0, 2)) {
		case RoverCommands.DATA:
			// TODO update visualization
			updatePoint(Integer.parseInt(input.substring(2, 4)), Integer.parseInt(input.substring(4, 7))); // FIXME fix the index parameter to fit the dots.size
			break;
		case RoverCommands.READY:
			// TODO write to console that Rover is ready
			setNumberOfLines(Integer.valueOf(input.substring(2, 4))); // FIXME set the parameters in the substring method to match the maximum possible number of lines 
			break;
		case RoverCommands.ERROR:
			// TODO write error to the console with red fill
			break;
		// TODO finish all cases
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
	}

	/**
	 * The {@code addMessageToConsole} method adds the message given in the
	 * {@code input} parameter to the console output aligned to the right or left
	 * depending on the {@code fromPi} {@code boolean}.
	 * 
	 * @param fromRover
	 *            {@code boolean} value that tells if the message is from the Rover
	 * @param input
	 *            {@link String} containing the message
	 */
	private static void addMessageToConsole(boolean fromRover, String input) {
		try {
			if (previousMessageFromRover.booleanValue() ^ fromRover) {
				sb = new StringBuilder();

				messageList.add(new HBox());
				messageIndex = messageList.size() - 1;
				textList.add(new Label());
				textList.get(messageIndex).setTextAlignment(TextAlignment.LEFT);
				textList.get(messageIndex).setWrapText(true);
				textList.get(messageIndex).setPrefWidth(consoleOutputTextBox.getWidth() / 2);
				textList.get(messageIndex).getStyleClass().add("textList");
				messageList.get(messageIndex).getChildren().add(textList.get(messageIndex));
				if (fromRover)
					messageList.get(messageIndex).setAlignment(Pos.CENTER_LEFT);
				else
					messageList.get(messageIndex).setAlignment(Pos.CENTER_RIGHT);
				messageList.get(messageIndex).setPrefWidth(textList.get(messageIndex).getWidth() * 2);
				messageList.get(messageIndex).getStyleClass().add("messageList");
			}

			if (!input.equals("")) {
				sb.append(input);
				sb.append("\n");
				textList.get(messageIndex).setText(sb.toString());
			}

			if (previousMessageFromRover.booleanValue() ^ fromRover)
				consoleOutputTextBox.getChildren().add(messageList.get(messageIndex));

			previousMessageFromRover = fromRover;

		} catch (NullPointerException e) {
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
	 * button is pressed. The {@link ScrollPane} with the info is brought to front.
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

		Platform.runLater(() -> {
			Main.mainStage.setScene(mainScene);
			Main.mainStage.centerOnScreen();
		});
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

			dots.add(new Rectangle(dotSize, dotSize, Color.BLUE));
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
		double ySide = lineLength * Math.cos(Math.toRadians(baseAngle * index));
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
	private static void setNumberOfLines(int value) {
		numberOfLines = value;
	}
}
