package thevelopers.devsoftware.controller.patient;

import thevelopers.devsoftware.controller.Controller;
import thevelopers.devsoftware.model.SystemModel;
import thevelopers.devsoftware.model.appointment.Consultation;
import thevelopers.devsoftware.model.user.types.Patient;
import thevelopers.devsoftware.view.patient.PatientConsultationView;
import thevelopers.devsoftware.view.patient.PatientConsultationsView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;

public class PatientConsultationsController extends Controller {

    //Atributos da classe (model e view)
    private final SystemModel model;
    private final PatientConsultationsView view;

    /**
     * Construtor da classe
     * @param model - modelo do sistema
     * @param view - view de registro
     */
    public PatientConsultationsController(SystemModel model, PatientConsultationsView view) {
        this.model = model;
        this.view = view;
    }

    /*
        Checa se o botão clicado é o botão de visualizar ou voltar
        Se for o botão de visualizar, checa se alguma consulta foi selecionada,
        se sim, pega a data da consulta selecionada e pega a consulta do paciente
        com essa data, se não, retorna!

        Depois, fecha a tela atual e abre a tela de visualização de consulta
        Se for o botão de voltar, volta para a tela anterior
     */
    @Override
    public void handleEvent(ActionEvent event) {
        if (!(event.getSource() instanceof JButton)) return;
        JButton button = (JButton) event.getSource();
        String buttonName = button.getText();

        switch (buttonName) {
            case "Visualizar":
                int selectedRow = this.view.getTable().getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(this.view, "Selecione uma consulta para visualizar!", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                String date = (String) this.view.getTable().getValueAt(selectedRow, 0);

                Patient patient = (Patient) this.model.getCurrentUser();
                Consultation consultation = patient.getConsultationByDate(date);
                if (consultation == null) return;

                this.view.dispose();

                PatientConsultationView patientConsultationView = new PatientConsultationView(this.model);
                patientConsultationView.setPreviousFrame(this.view);
                patientConsultationView.setConsultation(consultation);
                patientConsultationView.build();
                break;
            case "Voltar":
                if (this.view.getPreviousFrame() == null) return;

                this.view.dispose();
                this.view.getPreviousFrame().setVisible(true);
                break;
        }
    }

    /*
        Não é necessário implementar esse método
     */
    @Override
    public void handleEvent(MouseEvent event) {

    }

    @Override
    public void update() {

    }
}
