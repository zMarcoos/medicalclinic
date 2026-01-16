package com.thevelopers;

import com.thevelopers.view.ViewNavigator;
import javafx.application.Application;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class MedicalClinic extends Application {

  public static void main(String[] arguments) {
    launch();
  }

  @Override
  public void start(Stage stage) {
    Font.loadFont(getClass().getResourceAsStream("/fonts/JetBrainsMono-Regular.ttf"), 10);
    Font.loadFont(getClass().getResourceAsStream("/fonts/JetBrainsMono-Bold.ttf"), 10);
    Font.loadFont(getClass().getResourceAsStream("/fonts/JetBrainsMono-ExtraBold.ttf"), 10);
    Font.loadFont(getClass().getResourceAsStream("/fonts/JetBrainsMono-Medium.ttf"), 10);
    Font.loadFont(getClass().getResourceAsStream("/fonts/JetBrainsMono-ExtraLight.ttf"), 10);

    ViewNavigator.setMainStage(stage);
    ViewNavigator.loadView("/views/LoginView.fxml");
  }
}
