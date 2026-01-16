package com.thevelopers.controller.common;

import com.thevelopers.model.SystemFacade;
import com.thevelopers.model.appointment.Consultation;
import com.thevelopers.model.appointment.Exam;
import com.thevelopers.model.user.role.Doctor;
import com.thevelopers.observer.Observer;
import com.thevelopers.util.FXUtil;
import com.thevelopers.view.ViewNavigator;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class AppointmentExamsController implements Observer {

  private final SystemFacade systemFacade;
  private final boolean isLoggedUserADoctor;

  @FXML
  private TableView<Exam> examsTable;

  @FXML
  private TableColumn<Exam, String> fileNameColumn;

  private Consultation consultation;

  @FXML
  private Button uploadButton;

  public AppointmentExamsController(SystemFacade systemFacade) {
    this.systemFacade = systemFacade;
    this.isLoggedUserADoctor = systemFacade.getCurrentUser().getRole() instanceof Doctor;

    this.systemFacade.attachObserver(this);
  }

  @FXML
  public void initialize() {
    if (fileNameColumn != null) fileNameColumn.setCellValueFactory(new PropertyValueFactory<>("originalFileName"));

    this.uploadButton.setDisable(isLoggedUserADoctor);
  }

  @FXML
  public void onAction(ActionEvent event) {
    if (!(event.getSource() instanceof Button button)) return;
    String buttonName = button.getText();

    switch (buttonName) {
      case "Voltar":
        ViewNavigator.loadView("/views/AppointmentHistoryView.fxml",
          (AppointmentHistoryController controller) -> controller.setConsultation(this.consultation));
        break;
      case "Anexar":
        handleUpload(button);
        break;
      case "Baixar":
        handleDownload(button);
        break;
    }
  }

  private void handleUpload(Button sourceButton) {
    if (isLoggedUserADoctor) return;

    Stage stage = (Stage) sourceButton.getScene().getWindow();
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Selecione o Exame");
    fileChooser.getExtensionFilters().addAll(
      new FileChooser.ExtensionFilter("Documentos e Imagens", "*.pdf", "*.png", "*.jpg", "*.jpeg"),
      new FileChooser.ExtensionFilter("Todos os arquivos", "*.*")
    );

    File selectedFile = fileChooser.showOpenDialog(stage);
    if (selectedFile != null) {
      try {
        Exam newExam = new Exam(selectedFile);

        this.consultation.addExam(newExam);
        this.systemFacade.notifyObservers();

        FXUtil.showAlert("Sucesso", "Exame anexado com sucesso!", Alert.AlertType.INFORMATION);
      } catch (Exception exception) {
        FXUtil.showAlert("Erro", "Falha ao anexar exame: " + exception.getMessage(), Alert.AlertType.ERROR);
      }
    }
  }

  private void handleDownload(Button sourceButton) {
    Exam selectedExam = examsTable.getSelectionModel().getSelectedItem();

    if (selectedExam == null) {
      FXUtil.showAlert("Aviso", "Selecione um exame na tabela para baixar.", Alert.AlertType.WARNING);
      return;
    }

    Stage stage = (Stage) sourceButton.getScene().getWindow();
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Salvar Exame");

    fileChooser.setInitialFileName(selectedExam.getOriginalFileName());

    File destinationFile = fileChooser.showSaveDialog(stage);
    if (destinationFile != null) {
      try {
        selectedExam.exportTo(destinationFile);

        FXUtil.showAlert("Sucesso", "Exame baixado com sucesso!", Alert.AlertType.INFORMATION);
      } catch (IOException exception) {
        FXUtil.showAlert("Erro", "Erro ao salvar arquivo: " + exception.getMessage(), Alert.AlertType.ERROR);
      }
    }
  }

  @Override
  public void update() {
    Platform.runLater(this::refreshTable);
  }

  public void setConsultation(Consultation consultation) {
    this.consultation = consultation;

    refreshTable();
  }

  private void refreshTable() {
    if (consultation != null) examsTable.getItems().setAll(consultation.getExams());
  }
}