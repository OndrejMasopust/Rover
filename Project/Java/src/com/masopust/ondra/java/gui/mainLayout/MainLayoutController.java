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
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.TextAlignment;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

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
	Label infoLabel;
	
	@FXML
	BorderPane controlPane;
	
	@FXML
	ScrollPane infoSPane;
	
	@FXML
	Pane centerSectionPane;

	@FXML
	Group centerSectionGroup;

	@FXML
	VBox leftSection;

	@FXML
	ScrollPane consoleOutputSP;

	@FXML
	VBox consoleOutputTextBox;

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

	private StringBuilder sb = new StringBuilder();;
	private ArrayList<HBox> messageList = new ArrayList<>();
	private ArrayList<Label> textList = new ArrayList<>();
	private int i;
	private double scale = 0.2;

	/**
	 * This boolean tells if the previous record in the console output was form you.
	 */
	private static boolean previousTextYou = false;

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
		// FIXME delete Main.setFullScreen();
		buildCenterSection();
	}

	/**
	 * The {@code handleSubmit} method wraps code that is executed if the submit
	 * button is pressed or enter is hit while in the console text input.
	 */
	public void handleSubmit() {
		// RoverConnection.roverConnection.sendData(consoleInput.getText());

		if (!previousTextYou) {
			sb = new StringBuilder();

			messageList.add(new HBox());
			i = messageList.size() - 1;
			textList.add(new Label(consoleInput.getText()));
			textList.get(i).setTextAlignment(TextAlignment.LEFT);
			textList.get(i).setWrapText(true);
			textList.get(i).setPrefWidth(consoleOutputTextBox.getWidth() / 2);
			textList.get(i).getStyleClass().add("textList");
			messageList.get(i).getChildren().add(textList.get(i));
			messageList.get(i).setAlignment(Pos.CENTER_RIGHT);
			messageList.get(i).setPrefWidth(consoleOutputTextBox.getWidth());
			messageList.get(i).getStyleClass().add("messageList");
		}

		if (!consoleInput.getText().equals("")) {
			sb.append(consoleInput.getText());
			sb.append("\n");
			textList.get(i).setText(sb.toString());
		}

		if (!previousTextYou) {
			consoleOutputTextBox.getChildren().add(messageList.get(i));
			previousTextYou = true;
		}

		/*
		 * System.out.println(consoleOutputSP.getWidth());
		 * System.out.println(consoleOutputTextBox.getWidth());
		 * System.out.println(messageList.get(i).getWidth());
		 */

		consoleInput.setText("");
	}

	/**
	 * The {@code handleZoomIn} method wraps code that is executed when the zoom in
	 * button is pressed.
	 */
	public void handleZoomIn() {
		centerSectionGroup.setScaleX(centerSectionGroup.getScaleX() + scale);
		centerSectionGroup.setScaleY(centerSectionGroup.getScaleY() + scale);
	}

	/**
	 * The {@code handleZoomIn} method wraps code that is executed when the zoom out
	 * button is pressed.
	 */
	public void handleZoomOut() {
		if ((centerSectionGroup.getScaleX() - scale) > 0) {
			centerSectionGroup.setScaleX(centerSectionGroup.getScaleX() - scale);
			centerSectionGroup.setScaleY(centerSectionGroup.getScaleY() - scale);
		}
	}

	/**
	 * The {@code handleInfo} method wraps code that is executed when the info
	 * button is pressed.
	 */
	public void handleInfo() {
		//infoLabel.setText(Info.readInfo());
		infoSPane.toFront();
	}

	public static boolean getPreviousTextBol() {
		return previousTextYou;
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
		Platform.runLater(() -> {
			Main.mainStage.setScene(mainScene);
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
