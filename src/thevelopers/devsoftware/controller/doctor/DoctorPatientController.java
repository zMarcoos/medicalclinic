package thevelopers.devsoftware.controller.doctor;

import thevelopers.devsoftware.controller.Controller;
import thevelopers.devsoftware.model.SystemModel;
import thevelopers.devsoftware.model.appointment.Consultation;
import thevelopers.devsoftware.model.user.types.Doctor;
import thevelopers.devsoftware.view.doctor.DoctorPatientConsultationExamView;
import thevelopers.devsoftware.view.doctor.DoctorPatientView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;

public class DoctorPatientController extends Controller {
    //Atributos da classe DoctorPatientController
    private final SystemModel model;
    private final DoctorPatientView view;

    //Construtor da classe DoctorPatientController
    public DoctorPatientController(SystemModel model, DoctorPatientView view) {
        this.model = model;
        this.view = view;
    }

    /*Método que trata os eventos da classe DoctorPatientController
    O método recebe um evento como parâmetro
    Se o evento não for um botão, o método retorna
    Se o evento for um botão, o método verifica o nome do botão
    Se o nome do botão for "Exames", o método instancia um objeto da classe DoctorPatientConsultationExamView
    O método seta o frame anterior da DoctorPatientConsultationExamView como sendo a DoctorPatientView
    O método seta a consulta da DoctorPatientConsultationExamView como sendo a consulta do paciente na DoctorPatientView
    O método chama o método build() da DoctorPatientConsultationExamView
    Se o nome do botão for "Salvar", o método pega a descrição da consulta da DoctorPatientView
    O método pega o estado da consulta da DoctorPatientView
    O método pega a consulta ativa do paciente na DoctorPatientView
    O método seta a descrição da consulta ativa como sendo a descrição da consulta da DoctorPatientView
    O método seta o estado da consulta ativa como sendo o estado da consulta da DoctorPatientView
    Se o estado da consulta ativa for falso, o método finaliza a consulta ativa do paciente na DoctorPatientView
    O método mostra uma mensagem de sucesso
    O método fecha a DoctorPatientView
    O método torna o frame anterior da DoctorPatientView visível
    Se o nome do botão for "Voltar", o método verifica se o frame anterior da DoctorPatientView é nulo
    Se for nulo, o método retorna
    Se não for nulo, o método fecha a DoctorPatientView
    O método torna o frame anterior da DoctorPatientView visível
    O método retorna */
    @Override
    public void handleEvent(ActionEvent event) {
        if (!(event.getSource() instanceof JButton)) return;
        JButton button = (JButton) event.getSource();
        String buttonName = button.getText();

        switch (buttonName) {
            case "Exames":
                DoctorPatientConsultationExamView examView = new DoctorPatientConsultationExamView(this.model);
                examView.setPreviousFrame(this.view);
                examView.setConsultation(this.view.getPatient().getConsultationWithDoctor((Doctor) this.model.getCurrentUser()));
                examView.build();
                break;
            case "Salvar":
                String description = this.view.getDescriptionArea().getText();
                boolean active = this.view.getActiveCheckBox().isSelected();

                Consultation activeConsultation = this.view.getPatient().getConsultationWithDoctor((Doctor) this.model.getCurrentUser());
                activeConsultation.setDescription(description);
                activeConsultation.setActive(active);

                if (!active) this.view.getPatient().finishConsultation(activeConsultation);

                JOptionPane.showMessageDialog(this.view, "Atualização salva com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);

                this.view.dispose();
                this.view.getPreviousFrame().setVisible(true);
                break;
            case "Voltar":
                if (this.view.getPreviousFrame() == null) return;

                this.view.dispose();
                this.view.getPreviousFrame().setVisible(true);
                break;
        }
    }

    //Método que trata os eventos da classe DoctorPatientController
    @Override
    public void handleEvent(MouseEvent event) {

    }

    //Método que atualiza a classe DoctorPatientController
    @Override
    public void update() {

    }
}