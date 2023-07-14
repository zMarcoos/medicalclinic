package thevelopers.devsoftware.view.patient;

import thevelopers.devsoftware.controller.patient.PatientAppointmentController;
import thevelopers.devsoftware.model.SystemModel;
import thevelopers.devsoftware.utility.ClassUtility;
import thevelopers.devsoftware.utility.SwingUtility;
import thevelopers.devsoftware.view.View;

import javax.swing.*;

public class PatientAppointmentView extends View {
    // Atributos da classe PatientAppointmentView.java
    private final SystemModel model;
    private final PatientAppointmentController controller;

    private JTextField dateField;
    private JComboBox<String> doctorsSelection;

    // Construtor da classe PatientAppointmentView.java
    public PatientAppointmentView(SystemModel model) {
        this.model = model;
        this.controller = new PatientAppointmentController(this.model, this);
        this.model.attachObserver(this);
    }

    // Métodos da classe PatientAppointmentView.java
    @Override
    public void build() {
        getContentPane().setBackground(ClassUtility.BACKGROUND_ALMOST_WHITE_COLOR);

        SwingUtility.createPanel(this, 0, 0, 200, 800, ClassUtility.BACKGROUND_BLUE_COLOR);
        SwingUtility.createPanel(this, 700, 0, 200, 800, ClassUtility.BACKGROUND_BLUE_COLOR);
        SwingUtility.createPanel(this, 10, 0, 800, 30, ClassUtility.BACKGROUND_BLUE_COLOR);
        SwingUtility.createPanel(this, 10, 560, 800, 30, ClassUtility.BACKGROUND_BLUE_COLOR);

        SwingUtility.createImage(this, "src/" + ClassUtility.MAIN_PATH + "/assets/medical_icon.png", ((this.getWidth() / 2) - 30), 50, 60, 60);
        SwingUtility.createLabel(this, "Agendamentos", 405, 70, 100, 100, 12, null);

        SwingUtility.createLabel(this, "Data (dd/MM/yyyy HH:mm)", 300, 190, 300, 30, 12, null);
        SwingUtility.createLabel(this, "Selecione o médico", 300, 290, 300, 30, 12, null);

        this.dateField = SwingUtility.createTextField(this, "", 300, 220, 300, 30);

        String[] doctorsName = this.model.getAllDoctors().stream()
                .map(doctor -> doctor.getName() + " (" + doctor.getSpeciality() + ")")
                .toArray(String[]::new);
        this.doctorsSelection = SwingUtility.createComboBox(this, doctorsName, 300, 320, 300, 30, null);

        SwingUtility.createButton(this, "Agendar", 460, 450, this.controller::handleEvent);
        SwingUtility.createButton(this, "Voltar", 300, 450, this.controller::handleEvent);
    }

    // Método update() da classe PatientAppointmentView.java
    @Override
    public void update() {

    }

    // Getters da classe PatientAppointmentView.java
    public JTextField getDateField() {
        return dateField;
    }

    // Getters da classe PatientAppointmentView.java
    public JComboBox<String> getDoctorsSelection() {
        return doctorsSelection;
    }
}
