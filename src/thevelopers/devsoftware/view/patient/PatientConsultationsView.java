package thevelopers.devsoftware.view.patient;

import thevelopers.devsoftware.controller.patient.PatientConsultationsController;
import thevelopers.devsoftware.model.SystemModel;
import thevelopers.devsoftware.model.appointment.Consultation;
import thevelopers.devsoftware.model.user.types.Patient;
import thevelopers.devsoftware.utility.ClassUtility;
import thevelopers.devsoftware.utility.SwingUtility;
import thevelopers.devsoftware.view.View;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class PatientConsultationsView extends View {
    //Atributos da classe PatientConsultationsView
    private final SystemModel model;
    private final PatientConsultationsController controller;

    private JTable table;

    //Construtor da classe PatientConsultationsView
    public PatientConsultationsView(SystemModel model) {
        this.model = model;
        this.controller = new PatientConsultationsController(this.model, this);
        this.model.attachObserver(this);
    }

    //Método build da classe PatientConsultationsView
    @Override
    public void build() {
        getContentPane().setBackground(ClassUtility.BACKGROUND_ALMOST_WHITE_COLOR);

        SwingUtility.createPanel(this, 0, 0, 100, 800, ClassUtility.BACKGROUND_BLUE_COLOR);
        SwingUtility.createPanel(this, 800, 0, 100, 800, ClassUtility.BACKGROUND_BLUE_COLOR);
        SwingUtility.createPanel(this, 10, 0, 800, 30, ClassUtility.BACKGROUND_BLUE_COLOR);
        SwingUtility.createPanel(this, 10, 560, 800, 30, ClassUtility.BACKGROUND_BLUE_COLOR);

        SwingUtility.createImage(this, "src/" + ClassUtility.MAIN_PATH + "/assets/medical_icon.png", ((this.getWidth() / 2) - 30), 50, 60, 60);
        SwingUtility.createLabel(this, "Consultas", 420, 70, 100, 100, 12, null);

        SwingUtility.createButton(this, "Voltar", 380, 500, this.controller::handleEvent);

        Patient patient = (Patient) this.model.getCurrentUser();
        List<Consultation> consultations = patient.getConsultations();
        consultations.addAll(patient.getMedicalRecord().getConsultations());

        if (consultations.size() == 0) {
            SwingUtility.createLabel(this, "Você não possui consultas marcadas.", 300, 230, 300, 100, 16, null);
            return;
        }

        this.table = SwingUtility.createTable(this, new String[]{"Data", "Médico", "Especialidade", "Estado"}, 0, 0, 0, 0, false);
        DefaultTableModel model = (DefaultTableModel) table.getModel();

        for (Consultation consultation : consultations) {
            model.addRow(new Object[]{
                    consultation.getDateAsString(),
                    consultation.getDoctor().getName(),
                    consultation.getDoctor().getSpeciality(),
                    consultation.isActive() ? "❌" : "✔"
            });
        }

        JScrollPane scrollPane = new JScrollPane(this.table);
        scrollPane.setBounds((this.getWidth() / 2 - 300), 150, 600, 300);

        add(scrollPane);

        SwingUtility.createButton(this, "Visualizar", 550, 500, this.controller::handleEvent);
    }

    //Método update da classe PatientConsultationsView
    @Override
    public void update() {

    }

    //Método getTable da classe PatientConsultationsView
    public JTable getTable() {
        return table;
    }
}
