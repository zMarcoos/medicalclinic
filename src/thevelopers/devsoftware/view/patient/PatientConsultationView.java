package thevelopers.devsoftware.view.patient;

import thevelopers.devsoftware.controller.patient.PatientConsultationController;
import thevelopers.devsoftware.model.SystemModel;
import thevelopers.devsoftware.model.appointment.Consultation;
import thevelopers.devsoftware.utility.ClassUtility;
import thevelopers.devsoftware.utility.SwingUtility;
import thevelopers.devsoftware.view.View;

import javax.swing.*;

public class PatientConsultationView extends View {
    //Atributos da classe PatientConsultationView
    private final SystemModel model;
    private final PatientConsultationController controller;

    private Consultation consultation;

    //Construtor da classe PatientConsultationView
    public PatientConsultationView(SystemModel model) {
        this.model = model;
        this.controller = new PatientConsultationController(this.model, this);
        this.model.attachObserver(this);
    }

    //Método build da classe PatientConsultationView
    //Responsável por construir a tela de histórico de consultas
    @Override
    public void build() {
        getContentPane().setBackground(ClassUtility.BACKGROUND_ALMOST_WHITE_COLOR);

        SwingUtility.createPanel(this, 0, 0, 200, 800, ClassUtility.BACKGROUND_BLUE_COLOR);
        SwingUtility.createPanel(this, 700, 0, 200, 800, ClassUtility.BACKGROUND_BLUE_COLOR);
        SwingUtility.createPanel(this, 10, 0, 800, 30, ClassUtility.BACKGROUND_BLUE_COLOR);
        SwingUtility.createPanel(this, 10, 560, 800, 30, ClassUtility.BACKGROUND_BLUE_COLOR);

        SwingUtility.createImage(this, "src/" + ClassUtility.MAIN_PATH + "/assets/medical_icon.png", ((this.getWidth() / 2) - 30), 50, 60, 60);
        SwingUtility.createLabel(this, "Histórico", 420, 80, 100, 100, 12, null);

        SwingUtility.createLabel(this, "Histórico da Consulta", 360, 140, 300, 130, 14, null);
        SwingUtility.createLabel(this, "Data: " + this.consultation.getDateAsString(), 360, 180, 300, 100, 12, null);
        SwingUtility.createLabel(this, "Doutor(a): " + this.consultation.getDoctor().getName(), 360, 200, 300, 100, 12, null);
        SwingUtility.createLabel(this, "Descrição:", 360, 220, 250, 100, 12, null);
        JTextArea descriptionArea = SwingUtility.createTextArea(this, "", 360, 285, 300, 150);
        descriptionArea.setText(this.consultation.getDescription());
        descriptionArea.setEditable(false);

        SwingUtility.createButton(this, "Exames", 500, 500, this.controller::handleEvent);
        SwingUtility.createButton(this, "Voltar", 260, 500, this.controller::handleEvent);
    }

    //Método update da classe PatientConsultationView
    //Responsável por atualizar a tela de histórico de consultas
    @Override
    public void update() {

    }

    //Método getConsultation da classe PatientConsultationView
    public Consultation getConsultation() {
        return consultation;
    }

    //Método setConsultation da classe PatientConsultationView
    public void setConsultation(Consultation consultation) {
        this.consultation = consultation;
    }
}
