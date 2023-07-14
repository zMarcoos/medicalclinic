package thevelopers.devsoftware.view.doctor;

import thevelopers.devsoftware.controller.doctor.DoctorPatientsController;
import thevelopers.devsoftware.model.SystemModel;
import thevelopers.devsoftware.model.user.types.Doctor;
import thevelopers.devsoftware.model.user.types.Patient;
import thevelopers.devsoftware.utility.ClassUtility;
import thevelopers.devsoftware.utility.SwingUtility;
import thevelopers.devsoftware.view.View;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class DoctorPatientsView extends View {

    // Atributos privados da classe DoctorPatientsView (View do médico que mostra os pacientes)
    private final SystemModel model;
    private final DoctorPatientsController controller;

    private JTable table;

    // Construtor da classe DoctorPatientsView
    public DoctorPatientsView(SystemModel model) {
        this.model = model;
        this.controller = new DoctorPatientsController(this.model, this);
        this.model.attachObserver(this);
    }

    /* Método que constrói a View
    Esse método é chamado no construtor da classe DoctorPatientsController
    Esse método é chamado no método handleEvent da classe DoctorPatientsController */
    @Override
    public void build() {
        getContentPane().setBackground(ClassUtility.BACKGROUND_ALMOST_WHITE_COLOR);

        SwingUtility.createImage(this, "src/" + ClassUtility.MAIN_PATH + "/assets/medical_icon.png", ((this.getWidth() / 2) - 30), 50, 60, 60);
        SwingUtility.createLabel(this, "Pacientes", 420, 70, 100, 100, 12, null);

        SwingUtility.createPanel(this, 10, 0, 800, 30, ClassUtility.BACKGROUND_BLUE_COLOR);
        SwingUtility.createPanel(this, 10, 560, 800, 30, ClassUtility.BACKGROUND_BLUE_COLOR);

        SwingUtility.createButton(this, "Voltar", 380, 500, this.controller::handleEvent);

        Doctor doctor = (Doctor) this.model.getCurrentUser();
        if (doctor.getPatients().size() == 0) {
            SwingUtility.createLabel(this, "Você não possui pacientes no momento!", 310, 210, 300, 100, 14, null);

            SwingUtility.createPanel(this, 0, 0, 200, 800, ClassUtility.BACKGROUND_BLUE_COLOR);
            SwingUtility.createPanel(this, 700, 0, 200, 800, ClassUtility.BACKGROUND_BLUE_COLOR);
            return;
        }

        SwingUtility.createPanel(this, 0, 0, 100, 800, ClassUtility.BACKGROUND_BLUE_COLOR);
        SwingUtility.createPanel(this, 800, 0, 100, 800, ClassUtility.BACKGROUND_BLUE_COLOR);

        this.table = SwingUtility.createTable(this, new String[]{"Paciente", "Login", "Data"}, 0, 0, 0, 0, false);
        DefaultTableModel model = (DefaultTableModel) this.table.getModel();

        for (Patient patient : doctor.getPatients()) {
            model.addRow(new String[]{
                    patient.getName(),
                    patient.getLogin(),
                    patient.getConsultationWithDoctor(doctor).getDateAsString()
            });
        }

        SwingUtility.createButton(this, "Editar Paciente", 380, 460, this.controller::handleEvent);

        JScrollPane scrollPane = new JScrollPane(this.table);
        scrollPane.setBounds(150, 150, 600, 300);

        add(scrollPane);
    }

    // Método que atualiza a View
    @Override
    public void update() {

    }

    // Método que retorna a tabela da View
    public JTable getTable() {
        return table;
    }
}