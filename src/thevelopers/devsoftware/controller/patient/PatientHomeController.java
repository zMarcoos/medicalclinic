package thevelopers.devsoftware.controller.patient;

import thevelopers.devsoftware.controller.Controller;
import thevelopers.devsoftware.model.SystemModel;
import thevelopers.devsoftware.view.patient.PatientAppointmentView;
import thevelopers.devsoftware.view.patient.PatientConsultationsView;
import thevelopers.devsoftware.view.patient.PatientHomeView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;

public class PatientHomeController extends Controller {

    //Atributos da classe (model e view)
    private final SystemModel model;
    private final PatientHomeView view;

    /**
     * Construtor da classe
     * @param model - modelo do sistema
     * @param view - view de registro
     */
    public PatientHomeController(SystemModel model, PatientHomeView view) {
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
            case "Agendar":
                this.view.dispose();

                new PatientAppointmentView(this.model)
                        .setPreviousFrame(this.view)
                        .build();
                break;
            case "Consultas":
                this.view.dispose();

                new PatientConsultationsView(this.model)
                        .setPreviousFrame(this.view)
                        .build();
                break;
            case "Sair":
                if (this.view.getPreviousFrame() == null) return;

                this.model.deauthenticateUser(this.model.getCurrentUser());

                this.view.dispose();
                this.view.getPreviousFrame().setVisible(true);
                break;
        }
    }
    //Método que trata os eventos do mouse
    @Override
    public void handleEvent(MouseEvent event) {

    }
    //Método que atualiza a view
    @Override
    public void update() {

    }
}
