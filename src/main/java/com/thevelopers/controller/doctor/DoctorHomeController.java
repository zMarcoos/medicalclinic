package com.thevelopers.controller.doctor;

import com.thevelopers.model.SystemFacade;
import com.thevelopers.model.user.User;
import com.thevelopers.model.user.role.Doctor;
import com.thevelopers.view.ViewNavigator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DoctorHomeController {

  private final SystemFacade systemFacade;

  @FXML
  private Label doctorNameLabel;

  @FXML
  private Label specialityLabel;

  @FXML
  public void initialize() {
    User currentUser = systemFacade.getCurrentUser();
    if (currentUser == null) return;

    String doctorFirstName = currentUser.getName().split(" ")[0];
    Doctor doctorRole = (Doctor) currentUser.getRole();

    doctorNameLabel.setText("Dr(a). " + doctorFirstName);
    specialityLabel.setText(doctorRole.getSpeciality());
  }

  @FXML
  public void onAction(ActionEvent event) {
    if (!(event.getSource() instanceof Button button)) return;
    String buttonName = button.getText();

    String choosedButtonName = switch (buttonName) {
      case "Pacientes", "Abrir lista de pacientes" -> "/views/AppointmentsView.fxml";
      case "Sair" -> {
        this.systemFacade.deauthenticateUser();
        yield "/views/LoginView.fxml";
      }
      default -> "/views/LoginView.fxml";
    };

    ViewNavigator.loadView(choosedButtonName);
  }
}