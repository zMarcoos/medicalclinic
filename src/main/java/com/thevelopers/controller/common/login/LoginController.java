package com.thevelopers.controller.common.login;

import com.thevelopers.model.SystemFacade;
import com.thevelopers.model.user.User;
import com.thevelopers.model.user.role.Doctor;
import com.thevelopers.util.FXUtil;
import com.thevelopers.view.ViewNavigator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class LoginController {

  private final SystemFacade systemFacade;

  @FXML
  private TextField loginField;

  @FXML
  private PasswordField passwordField;

  @FXML
  public void onAction(ActionEvent event) {
    doLogin();
  }

  @FXML
  public void onKeyPressed(KeyEvent event) {
    if (event.getCode() != KeyCode.ENTER) return;

    doLogin();
  }

  @FXML
  public void onMouseClicked(MouseEvent event) {
    ViewNavigator.loadView("/views/RegisterView.fxml");
  }

  @FXML
  public void onMouseEntered(MouseEvent event) {
    Object source = event.getSource();

    Node node = (Node) source;
    node.setOpacity(0.7);
  }

  @FXML
  public void onMouseExited(MouseEvent event) {
    Object source = event.getSource();

    Node node = (Node) source;
    node.setOpacity(1.0);
  }

  private void doLogin() {
    String login = loginField.getText();
    String password = passwordField.getText();

    if (login.isEmpty() || password.isEmpty()) {
      FXUtil.showAlert("Erro", "Preencha todos os campos!", Alert.AlertType.ERROR);
      return;
    }

    Optional<User> optionalUser = this.systemFacade.getUser(login);
    optionalUser.ifPresentOrElse(
      user -> {
        if (!user.getPassword().equals(password)) {
          FXUtil.showAlert("Erro", "Senha incorreta!", Alert.AlertType.ERROR);
          return;
        }

        this.systemFacade.authenticateUser(user);
        FXUtil.showAlert("Boas-vindas", "Boas vindas de volta!", Alert.AlertType.INFORMATION);

        ViewNavigator.loadView(
          user.getRole() instanceof Doctor ?
            "/views/DoctorHomeView.fxml" :
            "/views/PatientHomeView.fxml"
        );
      },
      () -> FXUtil.showAlert("Erro", "Usuário ou senha incorretos!", Alert.AlertType.ERROR)
    );
  }
}
