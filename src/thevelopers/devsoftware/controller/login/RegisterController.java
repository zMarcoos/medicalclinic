package thevelopers.devsoftware.controller.login;

import thevelopers.devsoftware.controller.Controller;
import thevelopers.devsoftware.model.SystemModel;
import thevelopers.devsoftware.model.user.User;
import thevelopers.devsoftware.model.user.types.Doctor;
import thevelopers.devsoftware.model.user.types.Patient;
import thevelopers.devsoftware.view.login.RegisterView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;

public class RegisterController extends Controller {

    //Atributos da classe (model e view)
    private final SystemModel model;
    private final RegisterView view;

    /**
     * Construtor da classe
     * @param model - modelo do sistema
     * @param view - view de registro
     */
    public RegisterController(SystemModel model, RegisterView view) {
        this.model = model;
        this.view = view;
    }

    /*
        Método que checa se é um JRadioButton ou um JButton e faz a ação
        Se for um JRadioButton ele checa se o tipo de usuário já foi selecionado, se não foi ele seleciona
        Se for um JButton ele checa qual o nome do botão e faz a ação

        Se o botão for "Cadastrar" ele pega os dados do usuário e checa se todos os campos foram preenchidos
        Se não foram ele mostra uma mensagem de erro
        Ele checa se o usuário já existe, se existir ele mostra uma mensagem de erro
        Se não existir ele checa se o tipo de usuário é "Paciente" ou "Médico"
        Se for "Paciente" ele cria um novo paciente e adiciona no sistema
        Se for "Médico" ele checa se o campo de especialidade foi preenchido
        Se não foi ele mostra uma mensagem de erro
        Se foi ele cria um novo médico e adiciona no sistema
     */
    @Override
    public void handleEvent(ActionEvent event) {
        if (event.getSource() instanceof JRadioButton) {
            if (this.view.getUserType() != null) this.view.getUserType().setSelected(false);
            this.view.setUserType((JRadioButton) event.getSource());
        } else if (event.getSource() instanceof JButton) {
            JButton button = (JButton) event.getSource();
            String buttonName = button.getText();

            switch (buttonName) {
                case "Cadastrar":
                    String name = this.view.getUserName().getText();
                    String login = this.view.getUserLogin().getText();
                    String password = String.valueOf(this.view.getUserPassword().getPassword());
                    JRadioButton type = this.view.getUserType();

                    if (name.isEmpty() || login.isEmpty() || password.isEmpty() || type == null) {
                        JOptionPane.showMessageDialog(null, "Preencha todos os campos!", "Erro", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    User user = this.model.getUser(login, password);
                    if (user != null) {
                        JOptionPane.showMessageDialog(null, "Este usuário já existe!", "Erro", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    if (type.getText().equals("Paciente")) {
                        this.model.addUser(new Patient(name, login, password));
                    } else {
                        String speciality = this.view.getUserSpeciality().getText();
                        if (speciality.isEmpty()) {
                            JOptionPane.showMessageDialog(null, "Preencha todos os campos!", "Erro", JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        this.model.addUser(new Doctor(name, login, password, speciality));
                    }

                    JOptionPane.showMessageDialog(null, "Registrado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);

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
    }

    /*
        Método que checa se o JLabel foi clicado
        Se foi clicado ele checa se o texto do JLabel é "Já tem conta? Clique aqui!"
        Se for ele volta para a tela de login
     */
    @Override
    public void handleEvent(MouseEvent event) {
        JLabel label = (JLabel) event.getSource();

        if (event.paramString().contains("MOUSE_CLICKED")) {
            if (label.getText().equals("<html><u>Já tem conta? Clique aqui!</u></html>")) {
                if (this.view.getPreviousFrame() == null) return;

                this.view.dispose();
                this.view.getPreviousFrame().setVisible(true);
            }
        }
    }

    @Override
    public void update() {

    }
}