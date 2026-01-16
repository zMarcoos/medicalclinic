package com.thevelopers.controller.common;

import com.thevelopers.model.SystemFacade;
import com.thevelopers.model.appointment.Consultation;
import com.thevelopers.model.user.role.Doctor;
import com.thevelopers.observer.Observer;
import com.thevelopers.util.FXUtil;
import com.thevelopers.view.ViewNavigator;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.format.DateTimeFormatter;

public class AppointmentHistoryController implements Observer {

  private final SystemFacade systemFacade;
  private final boolean isLoggedUserADoctor;

  private Consultation consultation;

  @FXML
  private Label dateTimeLabel, doctorNameLabel;

  @FXML
  private CheckBox activeCheckBox;

  @FXML
  private TextArea descriptionTextArea;

  public AppointmentHistoryController(SystemFacade systemFacade) {
    this.systemFacade = systemFacade;
    this.isLoggedUserADoctor = systemFacade.getCurrentUser().getRole() instanceof Doctor;

    this.systemFacade.attachObserver(this);
  }

  public void setConsultation(Consultation consultation) {
    this.consultation = consultation;

    this.descriptionTextArea.setEditable(isLoggedUserADoctor);
    this.activeCheckBox.setDisable(!isLoggedUserADoctor);
    this.activeCheckBox.setSelected(consultation.isActive());

    refreshScreen();
  }

  @Override
  public void update() {
    Platform.runLater(() -> {
      if (this.consultation != null) refreshScreen();
    });
  }

  private void refreshScreen() {
    if (this.consultation == null) return;

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    this.dateTimeLabel.setText("Data: " + this.consultation.getDateTime().format(formatter));
    this.doctorNameLabel.setText("Dr(a). " + this.consultation.getDoctor().getName());

    String description = this.consultation.getDescription();

    if (description != null && !description.equals(this.descriptionTextArea.getText())) {
      this.descriptionTextArea.setText(description);
    } else if (description == null) {
      this.descriptionTextArea.setText("");
    }
  }

  @FXML
  public void onAction(ActionEvent event) {
    if (!(event.getSource() instanceof Button button)) return;
    String buttonName = button.getText();

    switch (buttonName) {
      case "Voltar":
        saveChanges();

        this.systemFacade.detachObserver(this);
        ViewNavigator.loadView("/views/AppointmentsView.fxml");
        break;
      case "Exames":
        saveChanges();

        this.systemFacade.detachObserver(this);
        ViewNavigator.loadView("/views/AppointmentExamsView.fxml", (AppointmentExamsController controller) -> controller.setConsultation(this.consultation));
        break;
    }
  }

  private void saveChanges() {
    if (!isLoggedUserADoctor || consultation == null) return;

    boolean sameDescription = consultation.getDescription().equals(descriptionTextArea.getText());
    boolean sameActiveStatus = consultation.isActive() == activeCheckBox.isSelected();
    if (sameDescription && sameActiveStatus) return;

    try {
      String newText = descriptionTextArea.getText();
      consultation.setActive(activeCheckBox.isSelected());
      consultation.setDescription(newText);

      systemFacade.notifyObservers();

      FXUtil.showAlert("Sucesso", "Observação atualizada!", Alert.AlertType.INFORMATION);
    } catch (Exception exception) {
      FXUtil.showAlert("Erro", "Falha ao salvar: " + exception.getMessage(), Alert.AlertType.ERROR);
    }
  }
}