package com.thevelopers.controller.common;

import com.thevelopers.dto.AppointmentRow;
import com.thevelopers.model.SystemFacade;
import com.thevelopers.model.appointment.Consultation;
import com.thevelopers.model.appointment.MedicalRecord;
import com.thevelopers.model.user.User;
import com.thevelopers.model.user.role.Doctor;
import com.thevelopers.model.user.role.Patient;
import com.thevelopers.observer.Observer;
import com.thevelopers.util.FXUtil;
import com.thevelopers.view.ViewNavigator;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class AppointmentsController implements Observer {

  private final SystemFacade systemFacade;
  private final boolean isLoggedUserADoctor;

  @FXML
  private TableView<AppointmentRow> appointmentsTable;

  @FXML
  private TableColumn<AppointmentRow, String> firstColumn, secondColumn, thirdColumn, fourthColumn;

  @FXML
  private Button viewOrEditButton;

  public AppointmentsController(SystemFacade systemFacade) {
    this.systemFacade = systemFacade;
    this.isLoggedUserADoctor = systemFacade.getCurrentUser().getRole() instanceof Doctor;

    this.systemFacade.attachObserver(this);
  }

  @FXML
  public void initialize() {
    setupColumns();
    update();
  }

  @FXML
  public void onAction(ActionEvent event) {
    if (!(event.getSource() instanceof Button button)) return;
    String buttonName = button.getText();

    switch (buttonName) {
      case "Voltar":
        this.systemFacade.detachObserver(this);
        ViewNavigator.loadView(
          isLoggedUserADoctor ?
            "/views/DoctorHomeView.fxml" :
            "/views/PatientHomeView.fxml"
        );
        break;
      case "Visualizar", "Editar paciente":
        AppointmentRow lastSelectedItem = appointmentsTable.getSelectionModel().getSelectedItem();
        if (lastSelectedItem == null) {
          FXUtil.showAlert("Erro", "Selecione uma consulta para interagir.", Alert.AlertType.ERROR);
          return;
        }

        Consultation consultation = getConsultation(lastSelectedItem);
        if (consultation == null) {
          FXUtil.showAlert("Erro", "Não foi possível carregar os detalhes desta consulta.", Alert.AlertType.ERROR);
          return;
        }

        this.systemFacade.detachObserver(this);

        ViewNavigator.loadView("/views/AppointmentHistoryView.fxml", (AppointmentHistoryController controller) -> controller.setConsultation(consultation));
        break;
    }
  }

  @Override
  public void update() {
    Platform.runLater(this::loadData);
  }

  private void setupColumns() {
    firstColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().firstColumn()));
    secondColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().secondColumn()));
    thirdColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().thirdColumn()));
    fourthColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().fourthColumn()));

    if (isLoggedUserADoctor) {
      secondColumn.setText("Paciente");
      thirdColumn.setText("Login");
      viewOrEditButton.setText("Editar paciente");
    } else {
      secondColumn.setText("Médico");
      thirdColumn.setText("Especialidade");
      viewOrEditButton.setText("Visualizar");
    }
  }

  private void loadData() {
    User currentUser = this.systemFacade.getCurrentUser();
    List<AppointmentRow> rows = new ArrayList<>();

    if (isLoggedUserADoctor) {
      List<User> patients = this.systemFacade.getPatientsByDoctor(currentUser.getLogin());

      for (User patient : patients) {
        Patient patientRole = (Patient) patient.getRole();
        MedicalRecord patientMedicalRecord = patientRole.getMedicalRecord();

        Consultation patientConsultation = patientMedicalRecord.getConsultationWithDoctor(currentUser);
        if (patientConsultation == null) continue;

        AppointmentRow row = new AppointmentRow(
          patientConsultation.getId().toString(),
          patient.getName(),
          patient.getLogin(),
          "✔"
        );
        rows.add(row);
      }

    } else {
      Patient patientRole = (Patient) currentUser.getRole();
      Map<UUID, Consultation> consultations = patientRole.getMedicalRecord().getConsultations();

      for (Consultation consultation : consultations.values()) {
        User doctor = consultation.getDoctor();
        Doctor doctorRole = (Doctor) doctor.getRole();

        AppointmentRow row = new AppointmentRow(
          consultation.getId().toString(),
          "Dr(a). " + doctor.getName(),
          doctorRole.getSpeciality(),
          consultation.isActive() ? "✔" : "✖"
        );
        rows.add(row);
      }
    }

    appointmentsTable.getItems().addAll(rows);
  }

  private Consultation getConsultation(AppointmentRow row) {
    try {
      UUID consultationId = UUID.fromString(row.firstColumn());

      if (isLoggedUserADoctor) {
        String loginPaciente = row.thirdColumn();

        return systemFacade.getUser(loginPaciente)
          .map(user -> (Patient) user.getRole())
          .map(patientRole -> patientRole.getMedicalRecord().getConsultations().get(consultationId))
          .orElse(null);
      } else {
        Patient patientRole = (Patient) systemFacade.getCurrentUser().getRole();
        return patientRole.getMedicalRecord().getConsultations().get(consultationId);
      }
    } catch (Exception exception) {
      exception.printStackTrace();
      return null;
    }
  }
}
