package thevelopers.devsoftware.controller.patient;

import thevelopers.devsoftware.controller.Controller;
import thevelopers.devsoftware.model.SystemModel;
import thevelopers.devsoftware.model.appointment.Exam;
import thevelopers.devsoftware.view.patient.PatientConsultationExamView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.io.File;

public class PatientConsultationExamController extends Controller {

    //Atributos da classe (model e view)
    private final SystemModel model;
    private final PatientConsultationExamView view;

    /**
     * Construtor da classe
     *
     * @param model - modelo do sistema
     * @param view  - view de registro
     */
    public PatientConsultationExamController(SystemModel model, PatientConsultationExamView view) {
        this.model = model;
        this.view = view;
    }

    /*
        Método que trata os eventos da view de registro
        Checa se o evento foi disparado por um botão, e então verifica qual botão foi clicado
        e toma a ação necessária para cada botão clicado

        Se o botão clicado for o de "Agendar", então a view é fechada e a view de agendamento é aberta
        Se o botão clicado for o de "Consultas", então a view é fechada e a view de consultas é aberta
        Se o botão clicado for o de "Voltar", então a view é fechada e a view anterior é aberta
     */
    @Override
    public void handleEvent(ActionEvent event) {
        if (!(event.getSource() instanceof JButton)) return;
        JButton button = (JButton) event.getSource();
        String buttonName = button.getText();

        switch (buttonName) {
            case "Anexar": {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setMultiSelectionEnabled(true);
                fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                fileChooser.setDialogTitle("Selecione os arquivos");

                int option = fileChooser.showOpenDialog(this.view);
                if (option != JFileChooser.APPROVE_OPTION) return;

                File[] selectedFiles = fileChooser.getSelectedFiles();

                for (File selectedFile : selectedFiles) {
                    this.view.getConsultation().addExam(new Exam(selectedFile));
                }

                JOptionPane.showMessageDialog(this.view, "Exame(s) anexado(s) com sucesso!");
            }
            break;
            case "Baixar": {
                int[] selectedRows = this.view.getTable().getSelectedRows();
                if (selectedRows.length == 0) {
                    JOptionPane.showMessageDialog(this.view, "Selecione um arquivo para baixar!");
                    return;
                }

                /*
                    Cria um seletor de diretórios e define o título da janela
                    e o modo de seleção para apenas diretórios
                 */
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

                //Exibe uma mensagem de sucesso
                JOptionPane.showMessageDialog(this.view, "Arquivo(s) baixado(s) com sucesso!");
            }
            break;
            case "Voltar":
                if (this.view.getPreviousFrame() == null) return;

                this.view.dispose();
                this.view.getPreviousFrame().setVisible(true);
                break;
        }
    }

    //Metodo que trata os eventos do mouse
    @Override
    public void handleEvent(MouseEvent event) {

    }

    //Metodo que atualiza a view
    @Override
    public void update() {

    }
}
