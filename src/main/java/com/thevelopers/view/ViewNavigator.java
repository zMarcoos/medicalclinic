package com.thevelopers.view;

import com.thevelopers.model.SystemFacade;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import lombok.Setter;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.Objects;
import java.util.function.Consumer;

public class ViewNavigator {

  private static final SystemFacade systemFacade = new SystemFacade();
  private static final String DEFAULT_TITLE = "⚕ Clínica Médica";

  @Setter
  private static Stage mainStage;

  public static void loadView(String fxmlPath) {
    loadView(fxmlPath, DEFAULT_TITLE, null);
  }

  public static <T> void loadView(String fxmlPath, Consumer<T> controllerSetup) {
    loadView(fxmlPath, DEFAULT_TITLE, controllerSetup);
  }

  public static <T> void loadView(String fxmlPath, String title, Consumer<T> controllerSetup) {
    try {
      if (mainStage == null) {
        throw new IllegalStateException("Stage não configurado! Chame setMainStage no início da aplicação.");
      }

      FXMLLoader loader = new FXMLLoader(ViewNavigator.class.getResource(fxmlPath));

      loader.setControllerFactory(controllerClass -> {
        try {
          Constructor<?> constructor = controllerClass.getConstructor(SystemFacade.class);
          return constructor.newInstance(systemFacade);
        } catch (NoSuchMethodException ignored) {
          try {
            return controllerClass.getConstructor().newInstance();
          } catch (Exception exception) {
            throw new RuntimeException("Erro ao criar controller padrão: " + controllerClass.getName(), exception);
          }
        } catch (Exception exception) {
          throw new RuntimeException("Erro ao injetar dependência no controller: " + controllerClass.getName(), exception);
        }
      });

      Parent root = loader.load();

      T controller = loader.getController();
      if (controllerSetup != null) controllerSetup.accept(controller);

      Scene scene = new Scene(root);

      mainStage.getIcons().add(new Image(Objects.requireNonNull(ViewNavigator.class.getResourceAsStream("/images/icon.png"))));
      mainStage.setResizable(false);
      mainStage.setTitle(title);
      mainStage.setScene(scene);
      mainStage.centerOnScreen();
      mainStage.show();
    } catch (IOException exception) {
      System.err.println("❌ Erro ao navegar para: " + fxmlPath);
      exception.printStackTrace();
    }
  }
}