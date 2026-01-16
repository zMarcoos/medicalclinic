package com.thevelopers.controller.patient;

import com.thevelopers.model.SystemFacade;
import com.thevelopers.model.appointment.Consultation;
import com.thevelopers.model.appointment.MedicalRecord;
import com.thevelopers.model.user.User;
import com.thevelopers.model.user.role.Doctor;
import com.thevelopers.model.user.role.Patient;
import com.thevelopers.util.FXUtil;
import com.thevelopers.view.ViewNavigator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class PatientAppointmentController {

  private final SystemFacade systemFacade;

  @FXML
  private DatePicker datePicker;

  @FXML
  private TextField timeField;

  @FXML
  private ComboBox<String> doctorsComboBox;

  @FXML
  public void initialize() {
    var doctorsSet = this.systemFacade.getUsers()
      .values()
      .stream()
      .filter(user -> user.getRole() instanceof Doctor)
      .map(user -> {
        String doctorFirstName = user.getName().split(" ")[0];
        Doctor doctorRole = (Doctor) user.getRole();

        return doctorFirstName + " - " + doctorRole.getSpeciality() + " - " + user.getLogin();
      })
      .collect(Collectors.toSet());

    doctorsComboBox.getItems().addAll(doctorsSet);
  }

  public void onAction(ActionEvent event) {
    if (!(event.getSource() instanceof Button button)) return;
    String buttonName = button.getText();

    switch (buttonName) {
      case "Agendar":
        if (datePicker.getValue() == null || timeField.getText().isEmpty() || doctorsComboBox.getValue() == null) {
          FXUtil.showAlert("Erro", "Preencha todos os campos (Data, Hora e Médico).", Alert.AlertType.ERROR);
          return;
        }

        try {
          String timeFieldText = timeField.getText();
          LocalTime timePickerValue = LocalTime.parse(timeFieldText, DateTimeFormatter.ofPattern("HH:mm"));

          LocalDateTime localDateTimeAppointment = LocalDateTime.of(datePicker.getValue(), timePickerValue);

          if (localDateTimeAppointment.isBefore(LocalDateTime.now())) {
            FXUtil.showAlert("Erro", "Não é possível marcar consultas no passado.", Alert.AlertType.ERROR);
            return;
          }

          User user = this.systemFacade.getCurrentUser();
          Patient patient = (Patient) user.getRole();

          MedicalRecord patientMedicalRecord = patient.getMedicalRecord();

          Consultation consultation = patientMedicalRecord.getConsultationByDate(localDateTimeAppointment);
          if (consultation != null) {
            FXUtil.showAlert("Erro", "Você já tem uma consulta marcada para este horário.", Alert.AlertType.ERROR);
            return;
          }

          String doctorLogin = doctorsComboBox.getValue().split(" - ")[2];
          Optional<User> optionalDoctor = this.systemFacade.getUser(doctorLogin);
          optionalDoctor.ifPresentOrElse(
            (doctor) -> {
              List<User> doctorPatients = this.systemFacade.getPatientsByDoctor(doctorLogin);

              boolean hasAppointmentInThisDateTime = doctorPatients
                .stream()
                .anyMatch(doctorPatient -> {
                  Patient doctorPatientRole = (Patient) doctorPatient.getRole();
                  MedicalRecord doctorPatientMedicalRecord = doctorPatientRole.getMedicalRecord();

                  Consultation doctorPatientConsultation = doctorPatientMedicalRecord.getConsultationByDate(localDateTimeAppointment);
                  if (doctorPatientConsultation == null || !doctorPatientConsultation.isActive()) return false;

                  return doctorPatientConsultation.getDoctor().getLogin().equals(doctorLogin);
                });
              if (hasAppointmentInThisDateTime) {
                FXUtil.showAlert("Erro", "Este médico já tem compromisso nessa data.", Alert.AlertType.ERROR);
                return;
              }

              Consultation newConsultation = new Consultation(localDateTimeAppointment, doctor);
              patientMedicalRecord.addConsultation(newConsultation);

              this.systemFacade.assignPatient(doctor, user);

              FXUtil.showAlert("Sucesso", "Consulta agendada com Dr(a). " + doctor.getName(), Alert.AlertType.INFORMATION);
            },
            () -> FXUtil.showAlert("Erro", "Médico inválido! (Informe um administrador)", Alert.AlertType.ERROR)
          );
        } catch (Exception exception) {
          FXUtil.showAlert("Erro", "Ocorreu um erro ao agendar: " + exception.getMessage(), Alert.AlertType.ERROR);
        }
      case "Voltar":
        ViewNavigator.loadView("/views/PatientHomeView.fxml");
        break;
    }
  }
}
