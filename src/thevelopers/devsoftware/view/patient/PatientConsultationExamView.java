package thevelopers.devsoftware.view.patient;

import thevelopers.devsoftware.controller.patient.PatientConsultationExamController;
import thevelopers.devsoftware.model.SystemModel;
import thevelopers.devsoftware.model.appointment.Consultation;
import thevelopers.devsoftware.model.appointment.Exam;
import thevelopers.devsoftware.utility.ClassUtility;
import thevelopers.devsoftware.utility.SwingUtility;
import thevelopers.devsoftware.view.View;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class PatientConsultationExamView extends View {
    //Atributos da classe PatientConsultationExamView
    private final SystemModel model;
    private final PatientConsultationExamController controller;

    private Consultation consultation;
    private JTable table;

    //Construtor da classe PatientConsultationExamView
    public PatientConsultationExamView(SystemModel model) {
        this.model = model;
        this.controller = new PatientConsultationExamController(this.model, this);
    }

    //Método build da classe PatientConsultationExamView
    //Esse método é responsável por construir a tela de exames
    //Ele recebe como parâmetro uma consulta
    //Ele cria uma tabela com os exames da consulta
    //Ele cria um botão para voltar para a tela de consultas
    //Ele cria um botão para baixar o exame
    //Ele cria um botão para deletar o exame
    //Ele cria um botão para adicionar um exame
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

        SwingUtility.createButton(this, "Baixar", 610, 500, this.controller::handleEvent);
        SwingUtility.createButton(this, "Anexar", 380, 500, this.controller::handleEvent);
        SwingUtility.createButton(this, "Voltar", 150, 500, this.controller::handleEvent);
    }

    /*Método update da classe PatientConsultationExamView
    Esse método é responsável por atualizar a tela de exames*/
    @Override
    public void update() {

    }

    //Método getModel da classe PatientConsultationExamView
    public JTable getTable() {
        return table;
    }
    //Método setModel da classe PatientConsultationExamView
    public Consultation getConsultation() {
        return consultation;
    }

    //Método setModel da classe PatientConsultationExamView
    public void setConsultation(Consultation consultation) {
        this.consultation = consultation;
    }
}