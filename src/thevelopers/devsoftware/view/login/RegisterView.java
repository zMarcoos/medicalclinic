package thevelopers.devsoftware.view.login;

import thevelopers.devsoftware.controller.login.RegisterController;
import thevelopers.devsoftware.model.SystemModel;
import thevelopers.devsoftware.utility.ClassUtility;
import thevelopers.devsoftware.utility.SwingUtility;
import thevelopers.devsoftware.view.View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class RegisterView extends View {

    //Atributos da classe RegisterView
    private final SystemModel model;
    private final RegisterController controller;

    private JTextField userName, userLogin, userSpeciality;
    private JPasswordField userPassword;
    private JRadioButton userType;
    private JLabel hasAccountLabel;

    /*Construtor da classe RegisterView
    Parâmetros: model (SystemModel)
    Objetivo: Inicializar os atributos da classe RegisterView*/
    public RegisterView(SystemModel model) {
        this.model = model;
        this.controller = new RegisterController(model, this);
        this.model.attachObserver(this);
    }

    //Método da classe RegisterView
    @Override
    public void build() {
        SwingUtility.createImage(this, "src/" + ClassUtility.MAIN_PATH + "/assets/medical_icon.png", (this.getWidth() / 2) - 160, 80, 95, 95);
        SwingUtility.createLabel(this, "Medical", (this.getWidth() / 2) - 50, 130, 250, 50, 50, null);
        SwingUtility.createLabel(this, "Cadastro", 290, 155, 300, 50, 10, null);
        SwingUtility.createLabel(this, "Nome", (this.getWidth() / 2) - 280, 200, 300, 30, 12, null);
        SwingUtility.createLabel(this, "Login", (this.getWidth() / 2) - 280, 270, 300, 30, 12, null);
        SwingUtility.createLabel(this, "Senha", (this.getWidth() / 2) + 20, 200, 300, 30, 12, null);
        SwingUtility.createLabel(this, "Especialidade (Apenas para Médicos)", (this.getWidth() / 2) + 20, 270, 300, 30, 12, null);
        SwingUtility.createLabel(this, "Tipo de Usuário", (this.getWidth() / 2) - 195, 365, 300, 30, 12, null);

        this.userName = SwingUtility.createTextField(this, "", (this.getWidth() / 2) - 280, 230, 260, 30);
        this.userLogin = SwingUtility.createTextField(this, "", (this.getWidth() / 2) - 280, 300, 260, 30);
        this.userPassword = SwingUtility.createPasswordField(this, "", (this.getWidth() / 2) + 20, 230, 260, 30);
        this.userSpeciality = SwingUtility.createTextField(this, "", (this.getWidth() / 2) + 20, 300, 260, 30);

        int radioY = 390, radioX = (this.getWidth() / 2) - 250;
        for (String type : new String[]{"Paciente", "Médico"}) {
            JRadioButton radioButton = new JRadioButton(type);
            radioButton.setBounds(radioX, radioY, 100, 30);
            radioButton.addActionListener(this.controller::handleEvent);

            add(radioButton);

            radioX += 100;
        }

        SwingUtility.createButton(this, "Cadastrar", (this.getWidth() / 2) + 80, 365, this.controller::handleEvent);

        this.hasAccountLabel = SwingUtility.createLabel(
                this, "<html><u>Já tem conta? Clique aqui!</u></html>", (this.getWidth() / 2) + 75, radioY + 3, 300, 30, 12, new MouseAdapter() {

                    @Override
                    public void mouseClicked(MouseEvent event) {
                        controller.handleEvent(event);
                    }
                });
        this.hasAccountLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    //Método da classe RegisterView
    //Parâmetros: graphics (Graphics)
    //Objetivo: Desenhar os componentes da classe RegisterView
    @Override
    public void update() {

    }

    //Método para pegar o valor do campo userPassword
    public JPasswordField getUserPassword() {
        return userPassword;
    }
    //Método para pegar o valor do campo userType
    public JRadioButton getUserType() {
        return userType;
    }
    //Método para setar o valor do campo userType
    public void setUserType(JRadioButton userType) {
        this.userType = userType;
    }
    //Método para pegar o valor do campo userLogin
    public JTextField getUserLogin() {
        return userLogin;
    }
    //Método para pegar o valor do campo userName
    public JTextField getUserName() {
        return userName;
    }
    //Método para pegar o valor do campo userSpeciality
    public JTextField getUserSpeciality() {
        return userSpeciality;
    }
}