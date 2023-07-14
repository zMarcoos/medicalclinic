package thevelopers.devsoftware.controller.patient;

import thevelopers.devsoftware.controller.Controller;
import thevelopers.devsoftware.model.SystemModel;
import thevelopers.devsoftware.view.patient.PatientConsultationExamView;
import thevelopers.devsoftware.view.patient.PatientConsultationView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;

public class PatientConsultationController extends Controller {

    //Atributos da classe (model e view)
    private final SystemModel model;
    private final PatientConsultationView view;

    /**
     * Construtor da classe
     * @param model - modelo do sistema
     * @param view - view de registro
     */
    public PatientConsultationController(SystemModel model, PatientConsultationView view) {
        this.model = model;
        this.view = view;
    }

    /**
     * Método que inicializa a view
     */
    @Override
    public void handleEvent(ActionEvent event) {
        if (!(event.getSource() instanceof JButton)) return;
        JButton button = (JButton) event.getSource();
        String buttonName = button.getText();

        switch (buttonName) {
            case "Exames":
                this.view.dispose();

                PatientConsultationExamView patientConsultationExamView = new PatientConsultationExamView(this.model);
                patientConsultationExamView.setConsultation(this.view.getConsultation());
                patientConsultationExamView.setPreviousFrame(this.view);
                patientConsultationExamView.build();
                break;
            case "Voltar":
                if (this.view.getPreviousFrame() == null) return;

                this.view.dispose();
                this.view.getPreviousFrame().setVisible(true);
                break;
        }
    }

    //Métodos não utilizados
    @Override
    public void handleEvent(MouseEvent event) {

    }

    @Override
    public void update() {

    }
}
