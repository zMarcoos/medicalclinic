package thevelopers.devsoftware.controller.doctor;

import thevelopers.devsoftware.controller.Controller;
import thevelopers.devsoftware.model.SystemModel;
import thevelopers.devsoftware.model.appointment.Exam;
import thevelopers.devsoftware.view.doctor.DoctorPatientConsultationExamView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.io.File;

public class DoctorPatientConsultationExamController extends Controller {

    //Atributos privados da classe do tipo SystemModel e DoctorPatientConsultationExamView
    private final SystemModel model;
    private final DoctorPatientConsultationExamView view;

    //Construtor da classe que recebe como parâmetro um objeto do tipo SystemModel e DoctorPatientConsultationExamView
    public DoctorPatientConsultationExamController(SystemModel model, DoctorPatientConsultationExamView view) {
        this.model = model;
        this.view = view;
    }

    //Método que recebe como parâmetro um objeto do tipo ActionEvent
    //e verifica se o botão que foi clicado é o botão "Baixar" ou "Voltar"
    //e executa a ação correspondente
    @Override
    public void handleEvent(ActionEvent event) {
        if (!(event.getSource() instanceof JButton)) return;
        JButton button = (JButton) event.getSource();
        String buttonName = button.getText();

        switch (buttonName) {
            case "Baixar":
                int[] selectedRows = this.view.getTable().getSelectedRows();
                if (selectedRows.length == 0) {
                    JOptionPane.showMessageDialog(this.view, "Selecione um arquivo para baixar!");
                    return;
                }

                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                fileChooser.setDialogTitle("Selecione o diretório para salvar o arquivo");

                int option = fileChooser.showSaveDialog(this.view);
                if (option != JFileChooser.APPROVE_OPTION) return;

                for (int selectedRow : selectedRows) {
                    Exam exam = this.view.getConsultation().getExams().get(selectedRow);
                    File file = exam.getFile();

                    File selectedDirectory = fileChooser.getSelectedFile();
                    File newFile = new File(selectedDirectory, file.getName());
                    if (newFile.exists()) {
                        JOptionPane.showMessageDialog(this.view, "O arquivo " + file.getName() + " já existe! Mude de diretório para baixar.");
                        continue;
                    }

                    exam.download(newFile);
                }

                JOptionPane.showMessageDialog(this.view, "Arquivo(s) baixado(s) com sucesso!");
                break;
            case "Voltar":
                if (this.view.getPreviousFrame() == null) return;

                this.view.dispose();
                this.view.getPreviousFrame().setVisible(true);
                break;
        }
    }

    //Método que recebe como parâmetro um objeto do tipo MouseEvent
    @Override
    public void handleEvent(MouseEvent event) {

    }

    @Override
    public void update() {

    }
}
