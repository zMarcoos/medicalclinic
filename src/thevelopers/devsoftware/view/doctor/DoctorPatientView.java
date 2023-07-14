package thevelopers.devsoftware.view.doctor;

import thevelopers.devsoftware.controller.doctor.DoctorPatientController;
import thevelopers.devsoftware.model.SystemModel;
import thevelopers.devsoftware.model.appointment.Consultation;
import thevelopers.devsoftware.model.user.types.Doctor;
import thevelopers.devsoftware.model.user.types.Patient;
import thevelopers.devsoftware.utility.ClassUtility;
import thevelopers.devsoftware.utility.SwingUtility;
import thevelopers.devsoftware.view.View;

import javax.swing.*;

public class DoctorPatientView extends View {
    // Atributos da classe DoctorPatientView (visão do paciente)
    private final SystemModel model;
    private final DoctorPatientController controller;

    private Patient patient;

    private JTextArea descriptionArea;
    private JCheckBox activeCheckBox;

    // Construtor da classe DoctorPatientView (visão do paciente)
    public DoctorPatientView(SystemModel model) {
        this.model = model;
        this.controller = new DoctorPatientController(this.model, this);
        this.model.attachObserver(this);
    }

    // Método que constrói a visão do paciente
    // @Override: sobrescreve o método da classe View
    @Override
    public void build() {
        getContentPane().setBackground(ClassUtility.BACKGROUND_ALMOST_WHITE_COLOR);

        SwingUtility.createPanel(this, 0, 0, 100, 800, ClassUtility.BACKGROUND_BLUE_COLOR);
        SwingUtility.createPanel(this, 800, 0, 100, 800, ClassUtility.BACKGROUND_BLUE_COLOR);
        SwingUtility.createPanel(this, 10, 0, 800, 30, ClassUtility.BACKGROUND_BLUE_COLOR);
        SwingUtility.createPanel(this, 10, 560, 800, 30, ClassUtility.BACKGROUND_BLUE_COLOR);

        SwingUtility.createImage(this, "src/" + ClassUtility.MAIN_PATH + "/assets/medical_icon.png", ((this.getWidth() / 2) - 30), 50, 60, 60);
        SwingUtility.createLabel(this, "Prescrições", 410, 70, 100, 100, 12, null);

        SwingUtility.createLabel(this, "Recomendação médica", 380, 150, 200, 100, 12, null);

        Consultation consultation = this.patient.getConsultationWithDoctor((Doctor) this.model.getCurrentUser());
        this.descriptionArea = SwingUtility.createTextArea(this, "", 370, 250, 200, 150);
        this.descriptionArea.setText(consultation.getDescription());
        this.descriptionArea.setColumns(20);

        this.activeCheckBox = SwingUtility.createCheckBox(this, "Ativo", 400, 450, 70, 30);
        this.activeCheckBox.setSelected(consultation.isActive());

        SwingUtility.createButton(this, "Exames", 570, 500, this.controller::handleEvent);
        SwingUtility.createButton(this, "Salvar", 370, 500, this.controller::handleEvent);
        SwingUtility.createButton(this, "Voltar", 170, 500, this.controller::handleEvent);
    }

    // Método que atualiza a visão do paciente
    // @Override: sobrescreve o método da classe View
    @Override
    public void update() {

    }

    // Método que retorna o campo de texto da descrição
    public JCheckBox getActiveCheckBox() {
        return activeCheckBox;
    }
    // Método que retorna o campo de texto da descrição
    public JTextArea getDescriptionArea() {
        return descriptionArea;
    }
    // Método que retorna o paciente
    public Patient getPatient() {
        return patient;
    }
    // Método que define o paciente
    public void setPatient(Patient patient) {
        this.patient = patient;
    }
}
