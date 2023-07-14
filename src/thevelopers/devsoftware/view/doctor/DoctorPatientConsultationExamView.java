package thevelopers.devsoftware.view.doctor;

import thevelopers.devsoftware.controller.doctor.DoctorPatientConsultationExamController;
import thevelopers.devsoftware.model.SystemModel;
import thevelopers.devsoftware.model.appointment.Consultation;
import thevelopers.devsoftware.model.appointment.Exam;
import thevelopers.devsoftware.utility.ClassUtility;
import thevelopers.devsoftware.utility.SwingUtility;
import thevelopers.devsoftware.view.View;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class DoctorPatientConsultationExamView extends View {
    //Atributos da classe DoctorPatientConsultationExamView
    private final SystemModel model;
    private final DoctorPatientConsultationExamController controller;

    private Consultation consultation;
    private JTable table;

    /*Construtor da classe DoctorPatientConsultationExamView
    Recebe como parâmetro um objeto do tipo SystemModel
    Instancia os atributos da classe DoctorPatientConsultationExamView
    Instancia o atributo controller da classe DoctorPatientConsultationExamView
    Chama o método attachObserver da classe SystemModel
    Retorna um objeto do tipo DoctorPatientConsultationExamView
    */

    //Método DoctorPatientConsultationExamView
    //Recebe como parâmetro um objeto do tipo SystemModel
    //Instancia os atributos da classe DoctorPatientConsultationExamView
    //Instancia o atributo controller da classe DoctorPatientConsultationExamView
    //Chama o método attachObserver da classe SystemModel
    //Retorna um objeto do tipo DoctorPatientConsultationExamView
    //Fim do método DoctorPatientConsultationExamView
    public DoctorPatientConsultationExamView(SystemModel model) {
        this.model = model;
        this.controller = new DoctorPatientConsultationExamController(this.model, this);
    }

    //Método build
    //Sobrescreve o método build da classe View
    //Retorna um objeto do tipo void
    //Fim do método build
    @Override
    public void build() {
        getContentPane().setBackground(ClassUtility.BACKGROUND_ALMOST_WHITE_COLOR);

        SwingUtility.createPanel(this, 0, 0, 100, 800, ClassUtility.BACKGROUND_BLUE_COLOR);
        SwingUtility.createPanel(this, 800, 0, 100, 800, ClassUtility.BACKGROUND_BLUE_COLOR);
        SwingUtility.createPanel(this, 10, 0, 800, 30, ClassUtility.BACKGROUND_BLUE_COLOR);
        SwingUtility.createPanel(this, 10, 560, 800, 30, ClassUtility.BACKGROUND_BLUE_COLOR);

        SwingUtility.createImage(this, "src/" + ClassUtility.MAIN_PATH + "/assets/medical_icon.png", ((this.getWidth() / 2) - 30), 50, 60, 60);
        SwingUtility.createLabel(this, "Exames", 420, 70, 100, 100, 12, null);

        this.table = SwingUtility.createTable(this, new String[]{"Arquivo"}, 0, 0, 0, 0, true);
        DefaultTableModel model = (DefaultTableModel) this.table.getModel();

        for (Exam exam : this.consultation.getExams()) {
            model.addRow(new Object[]{
                    exam.getFile().getName()
            });
        }

        JScrollPane scrollPane = new JScrollPane(this.table);
        scrollPane.setBounds(150, 150, 600, 300);

        add(scrollPane);

        SwingUtility.createButton(this, "Baixar", 450, 500, this.controller::handleEvent);
        SwingUtility.createButton(this, "Voltar", 250, 500, this.controller::handleEvent);
    }

    //Método update
    @Override
    public void update() {

    }

    public JTable getTable() {
        return table;
    }

    public Consultation getConsultation() {
        return consultation;
    }

    public void setConsultation(Consultation consultation) {
        this.consultation = consultation;
    }
}