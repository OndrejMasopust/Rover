package com.masopust.ondra.java.gui.mainLayout;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.masopust.ondra.java.gui.Main;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

public class MainLayoutController implements Initializable {

	@FXML
	Pane centerSection;
	
	private static int numberOfLines = 30; // FIXME
	public static ArrayList<Line> lines = new ArrayList<>();
	public static ArrayList<Rectangle> dots = new ArrayList<>();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		buildCenterSection();
		Main.setFullScreen();
	}

	public static void loadMainScene() throws IOException {
		FXMLLoader mainStageLoader = new FXMLLoader();
		mainStageLoader.setLocation(MainLayoutController.class.getResource("/com/masopust/ondra/java/gui/mainLayout/MainLayout.fxml"));
		
		Parent mainStageLayout = mainStageLoader.load();
		
		Scene mainScene = new Scene(mainStageLayout);
		Platform.runLater(() -> {
			Main.mainStage.setScene(mainScene);
		});
	}
	
	private void buildCenterSection() {
		int lineLength = 200; // FIXME
		double startX = centerSection.getWidth() / 2;
		double startY = centerSection.getHeight() / 2;
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
			lines.get(i).startXProperty().bind(centerSection.widthProperty().divide(2));
			lines.get(i).startYProperty().bind(centerSection.heightProperty().divide(2));
			lines.get(i).endXProperty().bind(lines.get(i).startXProperty().add(xSide));
			lines.get(i).endYProperty().bind(lines.get(i).startYProperty().add(ySide));
			centerSection.getChildren().add(lines.get(i));

			dots.add(new Rectangle(dotSize, dotSize, Color.BLUE));
			dots.get(i).setX(endX - dotSize / 2);
			dots.get(i).setY(endY - dotSize / 2);
			dots.get(i).xProperty().bind(lines.get(i).endXProperty().subtract(dotSize / 2));
			dots.get(i).yProperty().bind(lines.get(i).endYProperty().subtract(dotSize / 2));
			centerSection.getChildren().add(dots.get(i));

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
