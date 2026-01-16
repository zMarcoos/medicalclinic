package com.thevelopers.controller.common.login;

import com.thevelopers.model.SystemFacade;
import com.thevelopers.model.user.User;
import com.thevelopers.model.user.role.Doctor;
import com.thevelopers.model.user.role.Patient;
import com.thevelopers.model.user.role.UserRole;
import com.thevelopers.util.FXUtil;
import com.thevelopers.util.ValidatorUtil;
import com.thevelopers.view.ViewNavigator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class RegisterController {

  private final SystemFacade systemFacade;

  @FXML
  private TextField nameField;

  @FXML
  private PasswordField passwordField;

  @FXML
  private TextField loginField;

  @FXML
  private TextField specialityField;

  @FXML
  private ToggleGroup typeGroup;

  @FXML
  public void onAction(ActionEvent event) {
    if (event.getSource() instanceof Labeled component) {
      switch (component.getText()) {
        case "Já tem conta? Entrar":
          ViewNavigator.loadView("/views/LoginView.fxml");
          break;
        case "Cadastrar":
          doRegister();
          break;
      }
    }
  }

  @FXML
  public void onKeyPressed(KeyEvent event) {
    if (event.getCode() != KeyCode.ENTER) return;

    doRegister();
  }

  private void doRegister() {
    String name = nameField.getText();
    String password = passwordField.getText();
    String login = loginField.getText();

    RadioButton userTypeSelected = (RadioButton) typeGroup.getSelectedToggle();

    if (name.isEmpty() || login.isEmpty() || password.isEmpty() || userTypeSelected == null) {
      FXUtil.showAlert("Erro", "Preencha todos os campos!", Alert.AlertType.ERROR);
      return;
    }

    String speciality = specialityField.getText();
    String userType = userTypeSelected.getText();

    if (speciality.isEmpty() && userType.equalsIgnoreCase("médico")) {
      FXUtil.showAlert("Erro", "Você precisa informar sua especialidade.", Alert.AlertType.ERROR);
      return;
    }

    boolean userExists = systemFacade.hasUser(login);
    if (userExists) {
      FXUtil.showAlert("Erro", "Login indisponível.", Alert.AlertType.ERROR);
      return;
    }

    UserRole role = speciality.isEmpty() ? new Patient() : new Doctor(speciality);
    User user = new User(name, login, password, role);

    System.out.println(role.getRoleName());

    Optional<String> optionalViolations = ValidatorUtil.validate(user);
    optionalViolations.ifPresentOrElse(
      (violations) -> FXUtil.showAlert("Erro", violations, Alert.AlertType.ERROR),
      () -> {
        this.systemFacade.addUser(user);
        FXUtil.showAlert("Sucesso", "Registrado com sucesso!", Alert.AlertType.INFORMATION);

        ViewNavigator.loadView("/views/LoginView.fxml");
      }
    );
  }
}