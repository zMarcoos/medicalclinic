package com.thevelopers.controller.patient;

import com.thevelopers.model.SystemFacade;
import com.thevelopers.view.ViewNavigator;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PatientHomeController {

  private final SystemFacade systemFacade;

  public void onAction(ActionEvent event) {
    if (!(event.getSource() instanceof Button button)) return;
    String buttonName = button.getText();

    String choosedButtonName = switch (buttonName) {
      case "Agendar" -> "/views/PatientAppointmentView.fxml";
      case "Consultas" ->  "/views/AppointmentsView.fxml";
      case "Sair" -> {
        this.systemFacade.deauthenticateUser();
        yield "/views/LoginView.fxml";
      }
      default -> "/views/LoginView.fxml";
    };

    ViewNavigator.loadView(choosedButtonName);
  }
}
