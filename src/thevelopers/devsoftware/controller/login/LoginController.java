package thevelopers.devsoftware.controller.login;

import thevelopers.devsoftware.controller.Controller;
import thevelopers.devsoftware.model.SystemModel;
import thevelopers.devsoftware.model.user.User;
import thevelopers.devsoftware.model.user.types.Doctor;
import thevelopers.devsoftware.view.doctor.DoctorHomeView;
import thevelopers.devsoftware.view.login.LoginView;
import thevelopers.devsoftware.view.login.RegisterView;
import thevelopers.devsoftware.view.patient.PatientHomeView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;

public class LoginController extends Controller {

    //Atributos da classe (model e view)
    private final SystemModel model;
    private final LoginView view;

    /**
     * Construtor da classe
     * @param model - modelo do sistema
     * @param view - view de registro
     */
    public LoginController(SystemModel model, LoginView view) {
        this.model = model;
        this.view = view;
    }

    /*
        Método que pega o evento checa se é um botão, se for pega o nome e faz a ação
        Se o botão for o de entrar, pega os dados do login e senha e verifica se estão corretos
        Se estiverem, autentica o usuário e abre a tela de home

        Se o botão for o de criar conta, abre a tela de registro
     */
    @Override
    public void handleEvent(ActionEvent event) {
        if (!(event.getSource() instanceof JButton)) return;
        JButton button = (JButton) event.getSource();
        String buttonName = button.getText();

        if (buttonName.equals("Entrar")) {
            String login = this.view.getLoginField().getText();
            String password = String.valueOf(this.view.getPasswordField().getPassword());

            if (login.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Preencha todos os campos!", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            User user = this.model.getUser(login, password);
            if (user == null) {
                JOptionPane.showMessageDialog(null, "Usuário ou senha incorretos!", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            this.model.authenticateUser(user);
            JOptionPane.showMessageDialog(null, "Boas vindas de volta!", "Bem-vindo(a)!", JOptionPane.PLAIN_MESSAGE);

            this.view.dispose();

            if (user instanceof Doctor) {
                new DoctorHomeView(this.model)
                        .setPreviousFrame(this.view)
                        .build();
            } else {
                new PatientHomeView(this.model)
                        .setPreviousFrame(this.view)
                        .build();
            }
        }
    }

    /*
        Método que pega o evento checa se é um label, se for pega o nome e faz a ação
        Se o label for o de criar conta, abre a tela de registro
     */
    @Override
    public void handleEvent(MouseEvent event) {
        if (!(event.getSource() instanceof JLabel)) return;
        JLabel label = (JLabel) event.getSource();

        if (event.paramString().contains("MOUSE_CLICKED")) {
            if (label.getText().equals("<html><u>Crie uma conta</u></html>")) {
                this.view.dispose();

                RegisterView registerView = new RegisterView(this.model);
                registerView.setPreviousFrame(this.view);
                registerView.build();
            }
        }
    }

    @Override
    public void update() {

    }
}
