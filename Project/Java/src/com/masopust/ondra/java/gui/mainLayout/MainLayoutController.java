package com.masopust.ondra.java.gui.mainLayout;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.masopust.ondra.java.gui.Main;
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
	Group centerSectionGroup;

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

	/*
	 * This variable defines number of lines that are generated in the center
	 * section.
	 */
	private int numberOfLines = 30; // FIXME

	private static StringBuilder sb = new StringBuilder();;
	private static ArrayList<HBox> messageList = new ArrayList<>();
	private static ArrayList<Label> textList = new ArrayList<>();
	private static int i;
	private double zoomScale = 0.2;
	private static VBox consoleOutputTextBox;

	/**
	 * This boolean tells if the previous record in the console output was form you.
	 */
	private static Boolean previousMessageFromRPi = null;

	/**
	 * This list holds all of the lines that are generated in the center section.
	 */
	public static ArrayList<Line> lines = new ArrayList<>();

	/**
	 * This list holds all of the dots that are at the end of each of the lines.
	 */
	public static ArrayList<Rectangle> dots = new ArrayList<>();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		buildCenterSection();
		consoleOutputTextBox = consoleOutputTextBoxx;
		consoleOutputTextBox.heightProperty().addListener((observable, oldValue, newValue) -> {
			consoleOutputSP.setVvalue(1);
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
	 * The {@code writeConsoleRPi} method writes the given message to the console
	 * output in the main layout aligned to the left side.
	 * 
	 * @param input
	 *            {@link String} that contains the message that is to be written to
	 *            the console output
	 */
	public static void writeConsoleRPi(String input) {
		addMessageToConsole(true, input);
	}

	private static void addMessageToConsole(boolean fromRPi, String input) {
		try {
			if (previousMessageFromRPi.booleanValue() ^ fromRPi) {
				sb = new StringBuilder();

				messageList.add(new HBox());
				i = messageList.size() - 1;
				textList.add(new Label());
				textList.get(i).setTextAlignment(TextAlignment.LEFT);
				textList.get(i).setWrapText(true);
				textList.get(i).setPrefWidth(consoleOutputTextBox.getWidth() / 2);
				textList.get(i).getStyleClass().add("textList");
				messageList.get(i).getChildren().add(textList.get(i));
				if (fromRPi)
					messageList.get(i).setAlignment(Pos.CENTER_LEFT);
				else
					messageList.get(i).setAlignment(Pos.CENTER_RIGHT);
				messageList.get(i).setPrefWidth(textList.get(i).getWidth() * 2);
				messageList.get(i).getStyleClass().add("messageList");
			}

			if (!input.equals("")) {
				sb.append(input);
				sb.append("\n");
				textList.get(i).setText(sb.toString());
			}

			if (previousMessageFromRPi.booleanValue() ^ fromRPi)
				consoleOutputTextBox.getChildren().add(messageList.get(i));

			previousMessageFromRPi = fromRPi;

		} catch (NullPointerException e) {
			previousMessageFromRPi = !fromRPi;
			addMessageToConsole(fromRPi, input);
		}
	}

	/**
	 * The {@code handleZoomIn} method wraps code that is executed when the zoom in
	 * button is pressed.
	 */
	public void handleZoomIn() {
		centerSectionGroup.setScaleX(centerSectionGroup.getScaleX() + zoomScale);
		centerSectionGroup.setScaleY(centerSectionGroup.getScaleY() + zoomScale);
	}

	/**
	 * The {@code handleZoomIn} method wraps code that is executed when the zoom out
	 * button is pressed.
	 */
	public void handleZoomOut() {
		if ((centerSectionGroup.getScaleX() - zoomScale) > 0) {
			centerSectionGroup.setScaleX(centerSectionGroup.getScaleX() - zoomScale);
			centerSectionGroup.setScaleY(centerSectionGroup.getScaleY() - zoomScale);
		}
	}

	/**
	 * The {@code handleInfo} method wraps code that is executed when the info
	 * button is pressed.
	 */
	public void handleInfo() {
		if (infoTF.getChildren().size() == 0) {
			infoTF.setTextAlignment(TextAlignment.LEFT);
			infoTF.setPadding(new Insets(8, 10, 8, 10));
			infoTF.setLineSpacing(5.0);
			infoTF.getChildren().addAll(Info.getInfo());
			infoSPane.toFront();
		}
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
	 * This method builds the lines and dots in the center section.
	 * 
	 * @return void
	 */
	private void buildCenterSection() {
		int lineLength = 200; // FIXME
		double startX = centerSectionPane.getWidth() / 2;
		double startY = centerSectionPane.getHeight() / 2;
		double baseAngle = (double) 360 / numberOfLines;
		float dotSize = 3;

		for (int i = 0; i < numberOfLines; i++) {
			lines.add(new Line());
			lines.get(i).setStartX(startX);
			lines.get(i).setStartY(startY);
			double angleInDeg = baseAngle * i;

			// count the deflection of the end point
			double xSide = lineLength * Math.sin(Math.toRadians(angleInDeg));
			double ySide = lineLength * Math.cos(Math.toRadians(angleInDeg));
			double endX = startX + xSide;
			double endY = startY + ySide;
			lines.get(i).setEndX(endX);
			lines.get(i).setEndY(endY);

			// set start and end points to be relative to the pane dimensions
			lines.get(i).startXProperty().bind(centerSectionPane.widthProperty().divide(2));
			lines.get(i).startYProperty().bind(centerSectionPane.heightProperty().divide(2));
			lines.get(i).endXProperty().bind(lines.get(i).startXProperty().add(xSide));
			lines.get(i).endYProperty().bind(lines.get(i).startYProperty().add(ySide));
			centerSectionGroup.getChildren().add(lines.get(i));

			dots.add(new Rectangle(dotSize, dotSize, Color.BLUE));
			dots.get(i).setX(endX - dotSize / 2);
			dots.get(i).setY(endY - dotSize / 2);
			dots.get(i).xProperty().bind(lines.get(i).endXProperty().subtract(dotSize / 2));
			dots.get(i).yProperty().bind(lines.get(i).endYProperty().subtract(dotSize / 2));
			centerSectionGroup.getChildren().add(dots.get(i));

			/*
			 * System.out.printf("Angle: %f\n", angleInDeg);
			 * System.out.printf("sin(angle): %f\n", Math.sin(angleInDeg));
			 * System.out.printf("cos(angle): %f\n", Math.cos(angleInDeg));
			 * System.out.printf("xSide: %f\n", xSide); System.out.printf("ySide: %f\n",
			 * ySide); System.out.println();
			 */
		}
	}
}
