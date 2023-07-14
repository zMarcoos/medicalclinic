package thevelopers.devsoftware.controller.doctor;

import thevelopers.devsoftware.controller.Controller;
import thevelopers.devsoftware.model.SystemModel;
import thevelopers.devsoftware.view.doctor.DoctorHomeView;
import thevelopers.devsoftware.view.doctor.DoctorPatientsView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;

public class DoctorHomeController extends Controller {

    //Atributos da classe DoctorHomeController (model e view)
    private final SystemModel model;
    private final DoctorHomeView view;

    //Construtor da classe DoctorHomeController
    public DoctorHomeController(SystemModel model, DoctorHomeView view) {
        this.model = model;
        this.view = view;
    }

    /*Método que trata os eventos da classe DoctorHomeController
    O método recebe um evento como parâmetro e verifica se o evento é uma instância de JButton
    Se for, o método pega o texto do botão e verifica se é "Pacientes" ou "Sair"
    Se for "Pacientes", o método chama o método dispose() da classe DoctorHomeView e cria uma instância da classe DoctorPatientsView
    Se for "Sair", o método chama o método dispose() da classe DoctorHomeView e chama o método setVisible() da classe DoctorHomeView
    O método recebe um evento como parâmetro e verifica se o evento é uma instância de MouseEvent
    Se for, o método não faz nada
    O método não retorna nada
    O método não recebe nada
    O método não lança nenhuma exceção*/

    @Override
    public void handleEvent(ActionEvent event) {
        if (!(event.getSource() instanceof JButton)) return;
        JButton button = (JButton) event.getSource();
        String buttonName = button.getText();

        switch (buttonName) {
            case "Pacientes":
                this.view.dispose();

                DoctorPatientsView doctorPatientsView = new DoctorPatientsView(this.model);
                doctorPatientsView.setPreviousFrame(this.view);
                doctorPatientsView.build();
                break;
            case "Sair":
                if (this.view.getPreviousFrame() == null) return;

                this.model.deauthenticateUser(this.model.getCurrentUser());

                this.view.dispose();
                this.view.getPreviousFrame().setVisible(true);
                break;
        }
    }

    //Método que trata os eventos da classe DoctorHomeController
    @Override
    public void handleEvent(MouseEvent event) {

    }

    //Método que atualiza a classe DoctorHomeController
    @Override
    public void update() {

    }
}